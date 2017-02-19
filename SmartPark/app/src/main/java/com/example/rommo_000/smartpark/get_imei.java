package com.example.rommo_000.smartpark;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by rommo_000 on 9/26/2016.
 */
public class get_imei
{
    public static String get_dev_id(Context ctx){

        //Getting the Object of TelephonyManager
        TelephonyManager tManager = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);

        //Getting IMEI Number of Devide
        String Imei=tManager.getDeviceId();

        return Imei;
    }
}
