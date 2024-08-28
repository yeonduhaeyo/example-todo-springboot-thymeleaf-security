DROP TABLE IF EXISTS `todo`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    id          INT          NOT NULL AUTO_INCREMENT,
    username    VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    create_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    delete_date TIMESTAMP NULL,
    PRIMARY KEY (id),
    UNIQUE (id)
);

CREATE TABLE `user_role`
(
    id          INT          NOT NULL AUTO_INCREMENT,
    user_id     INT          NOT NULL,
    role        VARCHAR(255) NOT NULL,
    create_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id),
    CONSTRAINT check_role CHECK (role IN ('ADMIN', 'USER'))
);

CREATE TABLE `todo`
(
    id          INT          NOT NULL AUTO_INCREMENT,
    user_id     INT          NOT NULL,
    content     VARCHAR(255) NOT NULL,
    done_yn     VARCHAR(1)   NOT NULL DEFAULT 'N',
    create_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    delete_date TIMESTAMP NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id)
);