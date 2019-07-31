package com.shivamsatija.csmtest;

class AppConstants {

    public enum TEST_STATE {

        INSTRUCTIONS("INSTRUCTIONS"),
        TEST("TEST"),
        REPORT("REPORT"),
        SOLUTIONS("SOLUTIONS");

        private String value;

        TEST_STATE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static TEST_STATE valueOfState(String value) {
            for (TEST_STATE state : TEST_STATE.values()) {
                if (state.getValue().equals(value)) {
                    return state;
                }
            }
            return null;
        }
    }

    public enum WEB_TYPE {

        BACKGROUND("BACKGROUND"),
        QUIT("QUIT"),
        FORCE_QUIT("FORCE_QUIT"),
        FOREGROUND("FOREGROUND");

        private String value;

        public String getValue() {
            return value;
        }

        WEB_TYPE(String value) {
            this.value = value;
        }

        public static WEB_TYPE valueOfType(String type) {
            for (WEB_TYPE state : WEB_TYPE.values()) {
                if (state.getValue().equals(type)) {
                    return state;
                }
            }
            return null;
        }
    }

}
