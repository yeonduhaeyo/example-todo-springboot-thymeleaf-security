package com.example.my.common.constants;

public class Constants {

    public static class Regex {
        public static final String TODO_DONE_YN = "^[NY]$";
    }

    public static class ResCode {
        public static final int ENTITY_ALREADY_EXIST_EXCEPTION = 1;
        public static final int OK = 0;
        public static final int BAD_REQUEST_EXCEPTION = -1;
        public static final int MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION = -2;
        public static final int BIND_EXCEPTION = -3;
        public static final int CONSTRAINT_VIOLATION_EXCEPTION = -3;
        public static final int HTTP_MESSAGE_NOT_READABLE_EXCEPTION = -4;
        public static final int HTTP_REQUEST_METHOD_NOT_SUPPORT_EXCEPTION = -5;
        public static final int METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION = -6;
        public static final int CONVERSION_FAILED_EXCEPTION = -7;
        public static final int AUTHENTICATION_EXCEPTION = -9;
        public static final int AUTHORITY_EXCEPTION = -10;
        public static final int EXCEPTION = -99;
    }

}
