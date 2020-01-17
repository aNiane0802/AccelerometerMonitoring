package com.continental.accelerometerexperimentation.formatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TextFormatter {
    private static String concatenateAndSeparate(String[] arguments, String separator) {
        StringBuilder stringBuilder = new StringBuilder() ;
        for (String string : arguments){
            stringBuilder.append(string+separator);
        }
        return stringBuilder.toString() ;
    }

    private static String addCurrentDate(String text) {
        return text+currentDate() ;
    }

    public static String currentDate() {
        Calendar calendar = new GregorianCalendar() ;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy") ;
        return dateFormat.format(calendar.getTime());

    }

    private static String addExtension(String text,String extension) {
        return text+extension;
    }

    public static String formatWith(String[] arguments, String separator, String extension) {
        String separatedAndConcatenated = concatenateAndSeparate(arguments,separator) ;
        String dateAdded = addCurrentDate(separatedAndConcatenated) ;
        return addExtension(dateAdded,extension).replace(" ","").replace("/","") ;
    }
}
