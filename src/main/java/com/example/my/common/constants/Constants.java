package com.example.my.common.constants;

public class Constants {

    public static class Regex {
        public static final String TODO_DONE_YN = "^[NY]$";
    }

    public static class ResCode {
        public static final Integer OK = 0;
        public static final Integer BAD_REQUEST_EXCEPTION = -1;
        public static final Integer MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION = -2;
        public static final Integer BIND_EXCEPTION = -3;
        public static final Integer HTTP_MESSAGE_NOT_READABLE_EXCEPTION = -4;
        public static final Integer HTTP_REQUEST_METHOD_NOT_SUPPORT_EXCEPTION = -5;
        public static final Integer METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION = -6;
        public static final Integer CONVERSION_FAILED_EXCEPTION = -7;
        public static final Integer EXCEPTION = -99;
    }

}
