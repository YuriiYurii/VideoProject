package com.yuriitsap.videoproject;

import com.squareup.otto.Bus;

/**
 * Created by yuriitsap on 05.12.16.
 */
public class Application extends android.app.Application {

    private static Application sSelf;
    private Bus mBus;

    public static Application self() {
        return sSelf;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sSelf = this;
        mBus = new Bus();
    }

    public Bus getBus() {
        return mBus;
    }
}
