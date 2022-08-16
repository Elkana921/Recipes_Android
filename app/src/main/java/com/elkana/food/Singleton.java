package com.elkana.food;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class Singleton {

    //Properties:
    private static Singleton singletonInstance;

    //private constructor:
    private Singleton(){}

    public static Singleton getInstance(){
        if (singletonInstance == null){
            singletonInstance = new Singleton();
        }
        return singletonInstance;
    }

    // ----- Methods: -----
    //Minutes Format:
    public String minutesFormat(int minutes){
        int hour = minutes / 60;
        int minute = minutes % 60;

        String time = "";
        if (hour == 0){
            time = minute + "m";
        }else if (minute == 0){
            time = hour + "h";
        }else{
            time = hour + "h " + minute + "m";
        }

        //Anther option:
        /**

         if (hour <= 9 && minute <= 9){
         time = "0" + hour + ":0" + minute;
         }else if (hour <= 9){
         time = "0" + hour + ":" + minute;
         }else if (minute <= 9){
         time = hour + ":0" + minute;
         }else{
         time = hour + ":" + minute;
         }

         **/

        return time;
    }

}
