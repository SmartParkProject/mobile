package com.example.rommo_000.smartpark;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

/**
 * Created by rommo_000 on 10/14/2016.
 */
public class Networking {
    private static Networking mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    public static String urlMain = "https://smartparkproject.tk/";
    public static String uID = "123456789123456";


    private Networking(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized Networking getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Networking(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
