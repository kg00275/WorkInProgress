package com.example.android.bluetoothlegatt;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Singleton {

    // Variables to store singleton and request queue instances
    private static Singleton instance;
    private RequestQueue requestQueue;

    /**
     * Constructor for the Singleton class
     * @param context the application context
     */
    private Singleton(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Function to get an instance of the Volley Singleton
     * @param context the application context
     * @return a Volley Singleton object in the specified context
     */
    public static Singleton getInstance(Context context) {
        if (instance == null) {
            instance = new Singleton(context);
        }
            return instance;
        }

    /**
     * Function to get an instance of the Volley request queue for the application
      * @return a Volley RequestQueue object in the application context
     */
    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

}
