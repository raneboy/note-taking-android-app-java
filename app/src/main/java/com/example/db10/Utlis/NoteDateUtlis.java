package com.example.db10.Utlis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteDateUtlis {

    public static String dateFromLong(long time){
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd mmm yyyy 'at' hh:mm aaa", Locale.US);
        return dateFormat.format(new Date(time));
    }
}
