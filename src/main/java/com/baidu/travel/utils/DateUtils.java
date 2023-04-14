package com.baidu.travel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private DateUtils(){}
    public static String getCurrentTime(){
        return new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss").format(new Date());
    }

}
