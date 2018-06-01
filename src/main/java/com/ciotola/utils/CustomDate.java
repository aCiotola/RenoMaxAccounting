package com.ciotola.utils;

import java.text.SimpleDateFormat;

/**
 * Util class which is used for formatting the date.
 *
 * @author Alessandro Ciotola
 * @version 2018/05/20
 *
 */
public class CustomDate extends java.sql.Date {
    public CustomDate(long date) {
        super(date);
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("MMMM dd yyyy").format(this);
    }
}