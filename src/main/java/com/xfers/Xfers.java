package com.xfers;

public class Xfers {
    private static final String SANDBOX_API_BASE = "http://localhost:3000/api";
    private static final String PRODUCTION_API_BASE = "https://www.xfers.io/api";
    public static volatile String apiKey;

    private static volatile String apiBase = SANDBOX_API_BASE;

    public static void setSandbox() {
        apiBase = SANDBOX_API_BASE;
    }

    public static void setProduction() {
        apiBase = PRODUCTION_API_BASE;
    }

    public static String getApiBase() {
        return apiBase;
    }

}