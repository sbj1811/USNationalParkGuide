package com.sjani.usnationalparkguide.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class CommonContentProvider extends ContentProvider {

    private static final String TAG = CommonContentProvider.class.getSimpleName();
    private static final int CODE_PARKS = 100;
    private static final int CODE_PARKS_WITH_ID = 101;
    private static final int CODE_FAVORITES = 200;
    private static final int CODE_FAVORITES_WITH_ID = 201;
    private static final int CODE_TRAILS = 300;
    private static final int CODE_TRAILS_WITH_ID = 301;
    private static final int CODE_CAMPS = 400;
    private static final int CODE_CAMPS_WITH_ID = 401;
    private static final int CODE_ALERTS = 500;
    private static final int CODE_ALERTS_WITH_ID = 501;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private ParkDbHelper mOpenHelper;
    private TrailDbHelper trailDbHelper;
    private CampDbHelper campDbHelper;
    private AlertDbHelper alertDbHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ParkContract.CONTENT_AUTHORITY;
        final String trailAuthority = TrailContract.CONTENT_AUTHORITY;
        final String campAuthority = CampContract.CONTENT_AUTHORITY;
        final String alertAuthority = AlertContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ParkContract.PATH_PARKS, CODE_PARKS);
        matcher.addURI(authority, ParkContract.PATH_PARKS + "/#", CODE_PARKS_WITH_ID);
        matcher.addURI(authority, ParkContract.PATH_FAVORITES, CODE_FAVORITES);
        matcher.addURI(authority, ParkContract.PATH_FAVORITES + "/#", CODE_FAVORITES_WITH_ID);

        matcher.addURI(trailAuthority, TrailContract.PATH_TRAILS, CODE_TRAILS);
        matcher.addURI(trailAuthority, TrailContract.PATH_TRAILS + "/#", CODE_TRAILS_WITH_ID);

        matcher.addURI(campAuthority, CampContract.PATH_CAMPS, CODE_CAMPS);
        matcher.addURI(campAuthority, CampContract.PATH_CAMPS + "/#", CODE_CAMPS_WITH_ID);

        matcher.addURI(alertAuthority, AlertContract.PATH_ALERTS, CODE_ALERTS);
        matcher.addURI(alertAuthority, AlertContract.PATH_ALERTS + "/#", CODE_ALERTS_WITH_ID);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new ParkDbHelper(getContext());
        trailDbHelper = new TrailDbHelper(getContext());
        campDbHelper = new CampDbHelper(getContext());
        alertDbHelper = new AlertDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        String parkId = uri.getLastPathSegment();
        switch (sUriMatcher.match(uri)) {
            case CODE_PARKS:
                cursor = mOpenHelper.getReadableDatabase().query(
                        ParkContract.ParkEntry.TABLE_NAME_PARKS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_PARKS_WITH_ID:
                parkId = uri.getLastPathSegment();
                cursor = mOpenHelper.getReadableDatabase().query(
                        ParkContract.ParkEntry.TABLE_NAME_PARKS,
                        projection,
                        ParkContract.ParkEntry.COLUMN_PARK_ID + " = ?",
                        new String[]{parkId},
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_FAVORITES:
                cursor = mOpenHelper.getReadableDatabase().query(
                        ParkContract.ParkEntry.TABLE_NAME_FAVORITES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_FAVORITES_WITH_ID:
                cursor = mOpenHelper.getReadableDatabase().query(
                        ParkContract.ParkEntry.TABLE_NAME_FAVORITES,
                        projection,
                        ParkContract.ParkEntry.COLUMN_PARK_ID + " = ?",
                        new String[]{parkId},
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_TRAILS:
                cursor = trailDbHelper.getReadableDatabase().query(
                        TrailContract.TrailEntry.TABLE_NAME_TRAIL,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_TRAILS_WITH_ID:
                parkId = uri.getLastPathSegment();
                cursor = trailDbHelper.getReadableDatabase().query(
                        TrailContract.TrailEntry.TABLE_NAME_TRAIL,
                        projection,
                        TrailContract.TrailEntry.COLUMN_TRAIL_ID + " = ?",
                        new String[]{parkId},
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_CAMPS:
                cursor = campDbHelper.getReadableDatabase().query(
                        CampContract.CampEntry.TABLE_NAME_CAMP,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_CAMPS_WITH_ID:
                parkId = uri.getLastPathSegment();
                cursor = campDbHelper.getReadableDatabase().query(
                        CampContract.CampEntry.TABLE_NAME_CAMP,
                        projection,
                        CampContract.CampEntry.COLUMN_CAMP_ID + " = ?",
                        new String[]{parkId},
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_ALERTS:
                cursor = alertDbHelper.getReadableDatabase().query(
                        AlertContract.AlertEntry.TABLE_NAME_ALERT,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_ALERTS_WITH_ID:
                parkId = uri.getLastPathSegment();
                cursor = alertDbHelper.getReadableDatabase().query(
                        AlertContract.AlertEntry.TABLE_NAME_ALERT,
                        projection,
                        AlertContract.AlertEntry.COLUMN_ALERT_ID + " = ?",
                        new String[]{parkId},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri in query: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("Not implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db_park = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        long ids;
        switch (sUriMatcher.match(uri)) {
            case CODE_FAVORITES:
                ids = db_park.insert(ParkContract.ParkEntry.TABLE_NAME_FAVORITES, null, contentValues);
                if (ids > 0) {
                    returnUri = ContentUris.withAppendedId(uri, ids);
                } else {
                    throw new SQLException("Filed to insert row " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri in insert: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db_park = mOpenHelper.getWritableDatabase();
        final SQLiteDatabase db_trail = trailDbHelper.getWritableDatabase();
        final SQLiteDatabase db_camp = campDbHelper.getWritableDatabase();
        final SQLiteDatabase db_alert = alertDbHelper.getWritableDatabase();
        int rowsInserted = 0;
        switch (sUriMatcher.match(uri)) {
            case CODE_PARKS:
                db_park.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long ids = db_park.insert(ParkContract.ParkEntry.TABLE_NAME_PARKS, null, value);
                        if (ids != -1) {
                            rowsInserted++;
                        }
                    }
                    db_park.setTransactionSuccessful();
                } finally {
                    db_park.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;
            case CODE_TRAILS:
                db_trail.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long ids = db_trail.insert(TrailContract.TrailEntry.TABLE_NAME_TRAIL, null, value);
                        if (ids != -1) {
                            rowsInserted++;
                        }
                    }
                    db_trail.setTransactionSuccessful();
                } finally {
                    db_trail.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;
            case CODE_CAMPS:
                db_camp.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long ids = db_camp.insert(CampContract.CampEntry.TABLE_NAME_CAMP, null, value);
                        if (ids != -1) {
                            rowsInserted++;
                        }
                    }
                    db_camp.setTransactionSuccessful();
                } finally {
                    db_camp.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;
            case CODE_ALERTS:
                db_alert.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long ids = db_alert.insert(AlertContract.AlertEntry.TABLE_NAME_ALERT, null, value);
                        if (ids != -1) {
                            rowsInserted++;
                        }
                    }
                    db_alert.setTransactionSuccessful();
                } finally {
                    db_alert.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;
            default:
                return super.bulkInsert(uri, values);

        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db_park = mOpenHelper.getWritableDatabase();
        final SQLiteDatabase db_trail = trailDbHelper.getWritableDatabase();
        final SQLiteDatabase db_camp = campDbHelper.getWritableDatabase();
        final SQLiteDatabase db_alert = alertDbHelper.getWritableDatabase();
        int numRowsDeleted;
        String parkId = uri.getLastPathSegment();
        if (null == selection) selection = "1";

        switch (sUriMatcher.match(uri)) {
            case CODE_PARKS:
                numRowsDeleted = db_park.delete(
                        ParkContract.ParkEntry.TABLE_NAME_PARKS,
                        selection,
                        selectionArgs);
                break;
            case CODE_FAVORITES:
                numRowsDeleted = db_park.delete(ParkContract.ParkEntry.TABLE_NAME_FAVORITES, selection, selectionArgs);
                break;
            case CODE_FAVORITES_WITH_ID:
                numRowsDeleted = db_park.delete(ParkContract.ParkEntry.TABLE_NAME_FAVORITES, ParkContract.ParkEntry.COLUMN_PARK_ID + " = ?", new String[]{parkId});
                break;
            case CODE_TRAILS:
                numRowsDeleted = db_trail.delete(
                        TrailContract.TrailEntry.TABLE_NAME_TRAIL,
                        selection,
                        selectionArgs);
                break;
            case CODE_CAMPS:
                numRowsDeleted = db_camp.delete(
                        CampContract.CampEntry.TABLE_NAME_CAMP,
                        selection,
                        selectionArgs);
                break;
            case CODE_ALERTS:
                numRowsDeleted = db_alert.delete(
                        AlertContract.AlertEntry.TABLE_NAME_ALERT,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri in delete: " + uri);
        }
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numRowsDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new RuntimeException("Not implemented");
    }


}
