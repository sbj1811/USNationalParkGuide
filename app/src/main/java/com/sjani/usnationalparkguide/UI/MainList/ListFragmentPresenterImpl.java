package com.sjani.usnationalparkguide.UI.MainList;

public class ListFragmentPresenterImpl implements ListContract.ListFragmentPresenter {

    private ListContract.ListFragmentView view;

    public ListFragmentPresenterImpl(ListContract.ListFragmentView view) {
        this.view = view;
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
