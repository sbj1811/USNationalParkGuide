package com.sjani.usnationalparkguide.UI.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.sjani.usnationalparkguide.Data.ParkContract;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.Details.DetailsActivity;
import com.sjani.usnationalparkguide.UI.MainList.MainListActivity;

import java.util.concurrent.ExecutionException;

/**
 * Implementation of App Widget functionality.
 */
public class ParkWidgetProvider extends AppWidgetProvider {

    private static final String TAG = ParkWidgetProvider.class.getSimpleName();

    private static final String URI = "uri";
    private static final String PARK_ID = "park_id";
    private static final String POSITION = "position";
    private static final String LATLONG = "latlong";
    private static final String PARKCODE = "parkcode";
    private static final String FROM_FAV = "from_fav";


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

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Uri uri, String parkId, int position, String latLong, String parkCode, boolean isFromFavNav, String imgUrl, String title, String weatherDetails[], Integer distance) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, uri, parkId, position, latLong, parkCode, isFromFavNav, imgUrl, title, weatherDetails, distance);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Uri uri, String parkId, int position, String latLong, String parkCode, boolean isFromFavNav, String imgUrl, String title, String weatherDetails[], Integer distance) {
        CharSequence widgetText = context.getString(R.string.app_name);
        Intent intent = new Intent(context, MainListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.park_widget);
        if (parkCode.equals("")) {
            views.setTextViewText(R.id.park_widget_title, widgetText);
            views.setImageViewResource(R.id.park_widget_thumbnail, R.drawable.empty_detail);
            views.setTextViewText(R.id.park_widget_temp, String.valueOf(R.string.NA));
            views.setTextViewText(R.id.park_widget_condition, String.valueOf(R.string.NA));
            views.setTextViewText(R.id.park_widget_distance, String.valueOf(R.string.NA));
        } else {
            views.setTextViewText(R.id.park_widget_title, title);
            views.setTextViewText(R.id.park_widget_temp, weatherDetails[0]);
            views.setTextViewText(R.id.park_widget_condition, weatherDetails[1]);
            views.setTextViewText(R.id.park_widget_distance, distance + context.getResources().getString(R.string.miles));
            BitmapRequestBuilder builder = Glide.with(context.getApplicationContext()).load(imgUrl).asBitmap().centerCrop();
            FutureTarget futureTarget = builder.into(300, 200);
            try {
                views.setImageViewBitmap(R.id.park_widget_thumbnail, (Bitmap) futureTarget.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        views.setOnClickPendingIntent(R.id.widget_main, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ParkWidgetService.startActionUpdateWidgets(context);
    }

    @Override
    public void onEnabled(Context context) {
        ParkWidgetService.startActionUpdateWidgets(context);
    }

    @Override
    public void onDisabled(Context context) {
        context.startService(new Intent(context, ParkWidgetService.class));
    }
}

