package com.sjani.usnationalparkguide;

import android.app.Application;

import io.branch.referral.Branch;

public class NPGApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Branch.getAutoInstance(this);
    }
}
