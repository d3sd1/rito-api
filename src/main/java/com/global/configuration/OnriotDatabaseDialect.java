/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.configuration;

import org.hibernate.dialect.PostgreSQL10Dialect;

/**
 * Database dialect used for DEV environment to not fail on constraints.
 *
 * @author d3sd1
 * @version 0.0.9
 */
/* Used on ENV dev for preventing constraint-cascade error. */
public class OnriotDatabaseDialect extends PostgreSQL10Dialect {

    /**
     * Set the sequence to get dropped only if it exists, preventing errors.
     *
     * @param sequenceName
     * @return SQL sentence
     * @author d3sd1
     */
    @Override
    public String getDropSequenceString(String sequenceName) {
        // Adding the "if exists" clause to avoid warnings
        return "drop sequence if exists " + sequenceName;
    }

    /**
     * Set the constraints to not get dropped on create-drop model.
     *
     * @author d3sd1
     */
    @Override
    public boolean dropConstraints() {
        // We don't need to drop constraints before dropping tables, that just leads to error
        // messages about missing tables when we don't have a schema in the database
        return false;
    }
}