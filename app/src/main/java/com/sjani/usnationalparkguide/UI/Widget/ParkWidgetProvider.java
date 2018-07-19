package com.sjani.usnationalparkguide.UI.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.RemoteViews;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.Details.DetailsActivity;

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

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Uri uri, String parkId, int position, String latlong, String parkCode, boolean isFromFavNav, String imgUrl, String title){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,uri,parkId,position,latlong,parkCode,isFromFavNav,imgUrl,title);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Uri uri, String parkId, int position, String latlong, String parkCode, boolean isFromFavNav,String imgUrl,String title) {

        CharSequence widgetText = context.getString(R.string.app_name);
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(PARK_ID, parkId);
        intent.putExtra(POSITION, position);
        intent.putExtra(URI, uri);
        intent.putExtra(LATLONG, latlong);
        intent.putExtra(PARKCODE, parkCode);
        intent.putExtra(FROM_FAV,true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.park_widget);
        if (parkCode.equals("")){
            views.setTextViewText(R.id.park_widget_title, widgetText);
            views.setImageViewResource(R.id.park_widget_thumbnail,R.drawable.empty_detail);
        } else {
            views.setTextViewText(R.id.park_widget_title, title);
            BitmapRequestBuilder builder = Glide.with(context.getApplicationContext()).load(imgUrl).asBitmap().centerCrop();
            FutureTarget futureTarget = builder.into(300, 200);
            try {
                views.setImageViewBitmap(R.id.park_widget_thumbnail,(Bitmap) futureTarget.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        views.setOnClickPendingIntent(R.id.widget_main,pendingIntent);
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
        context.startService(new Intent(context,ParkWidgetService.class));
    }
}

