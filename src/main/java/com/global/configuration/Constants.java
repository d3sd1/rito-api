package com.global.configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constants {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

    public enum CALL_TYPE {
        GET,
        POST,
        PUT,
        DELETE
    }
}
