package com.sjani.usnationalparkguide.UI.Widget;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.sjani.usnationalparkguide.Data.ParkContract;
import com.sjani.usnationalparkguide.R;

public class ParkWidgetService extends IntentService {


    private static final String TAG = ParkWidgetService.class.getSimpleName();
    public static final String UPDATE_WIDGET = "update_widget";
    private static final String NOTIFICATION_CHANNEL_ID = "notification_channel";

    private Cursor cursor;

    private static final String[] PROJECTION = new String[]{
            ParkContract.ParkEntry._ID,
            ParkContract.ParkEntry.COLUMN_PARK_ID,
            ParkContract.ParkEntry.COLUMN_PARK_NAME,
            ParkContract.ParkEntry.COLUMN_PARK_STATES,
            ParkContract.ParkEntry.COLUMN_PARK_CODE,
            ParkContract.ParkEntry.COLUMN_PARK_LATLONG,
            ParkContract.ParkEntry.COLUMN_PARK_DESCRIPTION,
            ParkContract.ParkEntry.COLUMN_PARK_DESIGNATION,
            ParkContract.ParkEntry.COLUMN_PARK_ADDRESS,
            ParkContract.ParkEntry.COLUMN_PARK_PHONE,
            ParkContract.ParkEntry.COLUMN_PARK_EMAIL,
            ParkContract.ParkEntry.COLUMN_PARK_IMAGE
    };

    Uri uri;
    String parkId;
    int position;
    String latLong;
    String parkCode;
    String imgUrl;
    String title;

    public ParkWidgetService() {
        super("ParkWidgetService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Bitmap largeIcon = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        NotificationManager notificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "Primary",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Added to Favorite")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(largeIcon);

        Notification notification = notificationBuilder.build();
        startForeground(1,notification);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent == null) return;
        if (intent.getAction().equals(UPDATE_WIDGET)) {
            Context context = getApplicationContext();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ParkWidgetProvider.class));
            cursor = getContentResolver().query(ParkContract.ParkEntry.CONTENT_URI_FAVORITES,
                    PROJECTION,
                    null,
                    null,
                    null);
            if (cursor != null) {
                    cursor.moveToNext();
                    uri = ParkContract.ParkEntry.CONTENT_URI_FAVORITES;
                    parkId = cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_ID));
                    position = cursor.getPosition();
                    latLong = cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_LATLONG));
                    parkCode = cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_CODE));
                    imgUrl = cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_IMAGE));
                    title = cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_NAME));
                ParkWidgetProvider.updateAppWidgets(context,appWidgetManager,appWidgetIds,uri,parkId,position,latLong,parkCode,true,imgUrl,title);
            } else {
                Log.e(TAG, "onHandleIntent: Data NULL");
                return;
            }
        }
    }

    public static void startActionUpdateWidgets(Context context) {
        Intent intent = new Intent(context, ParkWidgetService.class);
        intent.setAction(UPDATE_WIDGET);
        // context.startService(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }



}
