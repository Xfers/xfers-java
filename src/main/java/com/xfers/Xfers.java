package com.xfers;

public class Xfers {
    private static final String SG_SANDBOX_API_BASE = "https://sandbox.xfers.io/api/v3";
    private static final String SG_PRODUCTION_API_BASE = "https://www.xfers.io/api/v3";
    private static final String ID_SANDBOX_API_BASE = "https://sandbox-id.xfers.com/api/v3";
    private static final String ID_PRODUCTION_API_BASE = "https://id.xfers.com/api/v3";

    public static volatile String apiKey;

    private static volatile String apiBase = "";

    public static void setSGSandbox() {
        apiBase = SG_SANDBOX_API_BASE;
    }

    public static void setSGProduction() {
        apiBase = SG_PRODUCTION_API_BASE;
    }

    public static void setIDSandbox() {
        apiBase = ID_SANDBOX_API_BASE;
    }

    public static void setIDProduction() {
        apiBase = ID_PRODUCTION_API_BASE;
    }

    public static void setEndpoint(String endpoint) {
        apiBase = endpoint;
    }

    public static String getApiBase() {
        return apiBase;
    }
}