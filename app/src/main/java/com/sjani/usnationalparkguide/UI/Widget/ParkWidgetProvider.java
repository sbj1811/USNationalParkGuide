package com.sjani.usnationalparkguide.UI.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.MainList.MainListActivity;
import com.sjani.usnationalparkguide.Utils.GlideApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Implementation of App Widget functionality.
 */
public class ParkWidgetProvider extends AppWidgetProvider {

    private static final String TAG = ParkWidgetProvider.class.getSimpleName();

    private static AppWidgetTarget appWidgetTarget;

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, String parkCode, String imgUrl, String title, Integer distance) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, parkCode, imgUrl, title, distance);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String parkCode, String imgUrl, String title, Integer distance) {
        CharSequence widgetText = context.getString(R.string.app_name);
        Intent intent = new Intent(context, MainListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.park_widget);
        if (title.equals("")) {
            views.setTextViewText(R.id.park_widget_title, widgetText);
            views.setImageViewResource(R.id.park_widget_thumbnail, R.drawable.empty_detail);
//            views.setTextViewText(R.id.park_widget_temp, String.valueOf(R.string.NA));
//            views.setTextViewText(R.id.park_widget_condition, String.valueOf(R.string.NA));
//            views.setTextViewText(R.id.park_widget_distance, String.valueOf(R.string.NA));
        } else {
            views.setTextViewText(R.id.park_widget_title, title);
//            views.setTextViewText(R.id.park_widget_temp, weatherDetails[0]);
//            views.setTextViewText(R.id.park_widget_condition, weatherDetails[1]);
//            views.setTextViewText(R.id.park_widget_distance, distance + context.getResources().getString(R.string.miles));
            appWidgetTarget = new AppWidgetTarget(context, R.id.park_widget_thumbnail, views, appWidgetId) {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    super.onResourceReady(resource, transition);
                }
            };

            GlideApp
                    .with(context.getApplicationContext())
                    .asBitmap()
                    .load(imgUrl)
                    .override(300, 200)
                    .into(appWidgetTarget);

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
        ParkWidgetService.startActionUpdateWidgets(context);
    }
}

