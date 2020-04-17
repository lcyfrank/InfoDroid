package com.ck19.infodroid;

import android.text.format.Time;

public class IntentHijackUtil {
    String operation_time = null;
    String operation = null;

    public IntentHijackUtil(String operation) {
        this.operation = operation;
        Time time = new Time();
        time.setToNow();
        int year = time.year;
        int month = time.month + 1;
        int day = time.monthDay;
        int hour = time.hour;
        int minute = time.minute;
        int second = time.second;
        this.operation_time = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }

    public String getOperation() {
        String op_code = operation.split(":")[0];
        String op_obj = operation.split(":")[1];
        String op_des = " User want to ";
        switch (op_code) {
            case "https":
                op_des = op_des + "open a url " + operation;
                break;
            case "http":
                op_des = op_des + "open a url " + operation;
                break;
            case "tel":
                op_des = op_des + "dial " + op_obj;
                break;
            case "smsto":
                op_des = op_des + "send short message to " + op_obj;
                break;
            case "mailto":
                op_des = op_des + "send email to " + op_obj;
                break;
            case "geo":
                op_des = op_des + "open map to find " + op_obj;
                break;
        }
        return operation_time + op_des;
    }

}
