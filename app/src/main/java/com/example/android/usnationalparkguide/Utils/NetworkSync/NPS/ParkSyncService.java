package com.example.android.usnationalparkguide.Utils.NetworkSync.NPS;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class ParkSyncService extends Service {
    private static final Object LOCK = new Object();
    private static ParkSyncAdapter syncAdapter;

    @Override
    public void onCreate() {
        synchronized (LOCK){
            syncAdapter = new ParkSyncAdapter(this,false);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}
