/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * The type Constants.
 *
 * @author d3sd1
 * @version 0.0.9
 */
public class Constants {
    /**
     * Format for project dates.
     */
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * The enum Call type.
     */
    public enum CALL_TYPE {
        /**
         * GET call type.
         */
        GET,
        /**
         * POST call type.
         */
        POST,
        /**
         * PUT call type.
         */
        PUT,
        /**
         * DELETE call type.
         */
        DELETE
    }
}
