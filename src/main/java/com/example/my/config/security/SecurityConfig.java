package com.example.my.config.security;

import com.example.my.config.security.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록
@RequiredArgsConstructor
// 스프링 시큐리티와 스프링 MVC 통합하기 위해서는
// @Configuration, @EnableWebSecurity 추가
public class SecurityConfig {

        @Nullable
        @Value("${spring.profiles.active}") // Spring Profile 가져오기
        String activeProfile;

        // 개발, 운영, 서버를 구분하기 위해 profile을 설정하며
        // 개발(dev), 테스트(stage), 알파(alpha), 상용(prod) 4가지의 host가 존재함.

        private final CustomAccessDeniedHandler customAccessDeniedHandler;
        private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
        private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
        private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                        HandlerMappingIntrospector introspector) throws Exception {
                // HandlerMappingIntrospector 스프링 시큐리티와 mvc의 중간다리 역할
                // 핸들러 매핑 정보 제공
                // 요청 매처 생성 : MvcRequestMatcher와 함께 사용되어 요청 매칭 로직 구성. 이를 통해 특정 패턴에 대한 요청 식별,
                // 이 요청이 어떤 핸들러에 의해 처리 될 수 있는지 확인
                // 스프링 시큐리티와의 통합

                MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
                // 스프링 시큐리티에서 사용되는 요청 매칭 클래스
                // 스프링 MVC의 핸들러 매핑 정보를 기반으로 특정 URL 패턴에 대한 요청을 확인하고 처리하는 역할
                // MVC 요청 패턴 매칭 : Spring MVC에서 정의된 URL 매핑에 따라 요청 매칭. 특정 요청이 어떤 컨트롤러 메서드에 매핑되는지
                // 기반으로 보안 규칙 적용
                // 핸들러 매핑 정보 활용 : HandlerMappingIntrospector를 통해 제공된 핸들러 매핑 정보를 사용하여 요청을 처리할 수
                // 있는 컨트롤러가 존재하는지 확인
                // 이로 인해 요청이 실제로 존재하는 핸들러와 일치하는지 판단할 수 있음.
                // 동적 URL 패턴 처리 : URL 패턴을 동적으로 매칭 할 수 있어, 요청의 형식이 변화할 때에도 유연하게 대처할 수 있음.

                if ("dev".equals(activeProfile)) {
                        httpSecurity.headers(config -> config
                                        .frameOptions(frameOptionsConfig -> frameOptionsConfig
                                                        .disable()));
                        httpSecurity.authorizeHttpRequests(config -> config
                                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2/**"))
                                        .hasRole("ADMIN"));
                }

                httpSecurity.csrf(config -> config.disable());

                httpSecurity.exceptionHandling(config -> config
                                .accessDeniedHandler(customAccessDeniedHandler));

                httpSecurity.authorizeHttpRequests(config -> config
                                .requestMatchers(
                                                mvcMatcherBuilder.pattern("/css/**"),
                                                mvcMatcherBuilder.pattern("/js/**"),
                                                mvcMatcherBuilder.pattern("/assets/**"),
                                                mvcMatcherBuilder.pattern("/springdoc/**"),
                                                mvcMatcherBuilder.pattern("/favicon.ico"))
                                .permitAll()
                                .requestMatchers(
                                                mvcMatcherBuilder.pattern("/js/admin*.js"),
                                                mvcMatcherBuilder.pattern("/temp/**"))
                                .hasRole("ADMIN"));

                httpSecurity.authorizeHttpRequests(config -> config
                                .requestMatchers(
                                                mvcMatcherBuilder.pattern("/auth/**"),
                                                mvcMatcherBuilder.pattern("/v*/auth/**"),
                                                mvcMatcherBuilder.pattern("/docs/**"),
                                                mvcMatcherBuilder.pattern("/swagger-ui/**"))
                                .permitAll()
                                .requestMatchers(
                                                mvcMatcherBuilder.pattern("/admin/**"),
                                                mvcMatcherBuilder.pattern("/v*/admin/**"))
                                .hasRole("ADMIN")
                                .anyRequest()
                                .authenticated());

                httpSecurity.formLogin(config -> config
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/v*/auth/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .successHandler(customAuthenticationSuccessHandler)
                                .failureHandler(customAuthenticationFailureHandler)
                                .permitAll());

                httpSecurity.logout(config -> config
                                .logoutUrl("/auth/logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessHandler(customLogoutSuccessHandler)
                                .permitAll());

                return httpSecurity.getOrBuild();
                // HttpSecurity 설정을 최종적으로 구성하고, SecurityFilterChain 객체를 생성하는 역할.

        }

}
