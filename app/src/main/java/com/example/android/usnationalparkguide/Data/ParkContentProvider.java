package com.example.android.usnationalparkguide.Data;

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

public class ParkContentProvider extends ContentProvider {

    private static final String TAG = ParkContentProvider.class.getSimpleName();
    private static final int CODE_PARKS = 100;
    private static final int CODE_PARKS_WITH_ID = 101;
    private static final int CODE_FAVORITES = 200;
    private static final int CODE_FAVORITES_WITH_ID = 201;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private ParkDbHelper mOpenHelper;

    public static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ParkContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ParkContract.PATH_PARKS, CODE_PARKS);
        matcher.addURI(authority, ParkContract.PATH_PARKS + "/#", CODE_PARKS_WITH_ID);
        matcher.addURI(authority, ParkContract.PATH_FAVORITES, CODE_FAVORITES);
        matcher.addURI(authority, ParkContract.PATH_FAVORITES + "/#", CODE_FAVORITES_WITH_ID);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new ParkDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        String movieId = uri.getLastPathSegment();
        switch (sUriMatcher.match(uri)){
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
                movieId = uri.getLastPathSegment();
                cursor = mOpenHelper.getReadableDatabase().query(
                        ParkContract.ParkEntry.TABLE_NAME_PARKS,
                        projection,
                        ParkContract.ParkEntry.COLUMN_PARK_ID + " = ?",
                        new String[]{movieId},
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
                        new String[]{movieId},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri in query: "+uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
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
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        long ids;
        switch (sUriMatcher.match(uri)){
            case CODE_FAVORITES:
                ids = db.insert(ParkContract.ParkEntry.TABLE_NAME_FAVORITES,null,contentValues);
                if(ids > 0) {
                    returnUri = ContentUris.withAppendedId(uri, ids);
                } else {
                    throw new SQLException("Filed to insert row "+uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri in insert: "+uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsInserted = 0;
        switch (sUriMatcher.match(uri)) {
            case CODE_PARKS:
                db.beginTransaction();
                try{
                    for (ContentValues value: values) {
                     //   Log.e(TAG, "bulkInsert: "+value);
                        long ids = db.insert(ParkContract.ParkEntry.TABLE_NAME_PARKS,null,value);
                        if(ids != -1){
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                if(rowsInserted > 0){
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                return  rowsInserted;
            default:
                return super.bulkInsert(uri,values);

        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numRowsDeleted;
        String movieId = uri.getLastPathSegment();
        if (null == selection) selection = "1";

        switch (sUriMatcher.match(uri)){
            case CODE_PARKS:
                numRowsDeleted = db.delete(
                        ParkContract.ParkEntry.TABLE_NAME_PARKS,
                        selection,
                        selectionArgs);
                break;
            case CODE_FAVORITES:
                numRowsDeleted = db.delete(ParkContract.ParkEntry.TABLE_NAME_FAVORITES,selection,selectionArgs);
                break;
            case CODE_FAVORITES_WITH_ID:
                numRowsDeleted = db.delete(ParkContract.ParkEntry.TABLE_NAME_FAVORITES,ParkContract.ParkEntry.COLUMN_PARK_ID + " = ?",new String[]{movieId});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri in delete: "+uri);
        }
        if(numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numRowsDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new RuntimeException("Not implemented");
    }
}
