package com.leantechnologies.saucedemo.pagesandservices.utils;

import com.leantechnologies.saucedemo.pagesandservices.constant.ConstantPath;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateAndTimeOperation {

    public static String getTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyhhmmss");
        //dateFormat.format(Calendar.getInstance().getTime());
        return dateFormat.format(new Date());
    }

    public static String getFileFormattedName(String fileName) {
        fileName = fileName
                + DateAndTimeOperation.getTimeStamp() + "." + ConstantPath.FILEEXT;
        String filePath = ConstantPath.SCREENSHOT + fileName;
        return filePath;
    }

    private String[] getDateComponents() {
        Date date1 = Calendar.getInstance().getTime();
        String sysDate = String.valueOf(date1);
        System.out.println("date: "+sysDate);
        String dateArray[] = sysDate.split(" ");
        return dateArray;
    }
    public static void main(String[] args) {
		getTimeStamp();
    }

    public String systemDay() {
        String dateArray[] = getDateComponents();
        return dateArray[0];
    }

    public int systemDate() {
        String dateArray[] = getDateComponents();
        System.out.println(Integer.parseInt(dateArray[2]));
        return Integer.parseInt(dateArray[2]);
    }

    public String systemMonth() {
        String dateArray[] = getDateComponents();
        return dateArray[1];
    }

    public int systemYear() {
        String dateArray[] = getDateComponents();
        return Integer.parseInt(dateArray[5]);
    }

    public String systemCurrentTime() {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }
}

