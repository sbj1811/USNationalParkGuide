package com.sjani.usnationalparkguide.UI.MainList;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

public class ListViewModel extends ViewModel {

    private static final String TAG = ListViewModel.class.getSimpleName();
    private int currentPosition;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
