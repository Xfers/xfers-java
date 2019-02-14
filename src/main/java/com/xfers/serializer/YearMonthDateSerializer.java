package com.xfers.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class YearMonthDateSerializer {
    public static Gson create() {
        return new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();
    }
}
