package com.hades.api.utils;

public class dataFormate {
    public static String jasonfy(String key, String value) {
        return "{\"" + key + "\": \"" + value + "\"}";
    }

    public static String makeS3Path(String userUuid, String fileName) {
        return "documents/" + userUuid + "/" + fileName;
    }
}
