package com.meng.newsreader;

import android.app.Application;
import android.content.Context;

/**
 * Created by mengzhou on 9/21/17.
 */

public class MyApp extends Application {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

}
