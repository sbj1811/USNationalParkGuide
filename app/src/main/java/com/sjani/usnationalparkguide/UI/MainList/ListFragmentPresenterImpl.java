package com.sjani.usnationalparkguide.UI.MainList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;

import com.sjani.usnationalparkguide.Utils.NetworkSync.NPS.ParkSyncAdapter;

public class ListFragmentPresenterImpl implements ListContract.ListFragmentPresenter {

    private ListContract.ListFragmentView view;
    private Uri uri;
    private String[] PROJECTION;
    private String state;
    private String max_article;
    private Context context;

    public ListFragmentPresenterImpl(ListContract.ListFragmentView view, Uri uri, String[] PROJECTION, String state, String max_article, Context context) {
        this.view = view;
        this.uri = uri;
        this.PROJECTION = PROJECTION;
        this.state = state;
        this.max_article = max_article;
        this.context = context;
    }

    @Override
    public void getDataFromServer() {
        ParkSyncAdapter.performSync(state, max_article);
    }

    @Override
    public Loader<Cursor> getDatafromDatabase() {
        return new CursorLoader(context, uri, PROJECTION, null, null, null);
    }

    @Override
    public void createUI() {
        view.createView();
    }

    @Override
    public void updateUI() {
        view.updateView();
    }
}
