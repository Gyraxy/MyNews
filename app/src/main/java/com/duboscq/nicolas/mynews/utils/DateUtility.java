package com.duboscq.nicolas.mynews.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nicolas DUBOSCQ on 10/08/2018
 */
public class DateUtility {

    public static String convertingDate(String date) {

        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd"+"/"+"MM"+"/"+"yyyy");

        String convertedDate = "";

        try {
            Date date_parse = inputFormat.parse(date);
            convertedDate = outputFormat.format(date_parse);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertedDate;
    }

    public static String convertingSearchDate(String date) {

        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");

        String convertedDate = "";

        try {
            Date date_parse = inputFormat.parse(date);
            convertedDate = outputFormat.format(date_parse);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertedDate;
    }

}
