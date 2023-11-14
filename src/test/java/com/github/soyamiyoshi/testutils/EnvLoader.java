package com.github.soyamiyoshi.testutils;

public class EnvLoader {
    public static String getEnvOrExit(String envVarName) {
        String value = System.getenv(envVarName);
        if (value == null || value.trim().isEmpty()) {
            System.err.println("Environment variable " + envVarName + " is not set.");
            assert false;
        }
        return value;
    }
}
