package com.sjani.usnationalparkguide.UI.MainList;

import android.database.Cursor;
import android.support.v4.content.Loader;

public interface ListContract {

    public interface ListFragmentView{

        public void createView();
        public void updateView();

    }

    public interface ListFragmentPresenter{

        public void getDataFromServer();

        public Loader<Cursor> getDatafromDatabase();

        public void createUI();

        public void updateUI();

    }

}
