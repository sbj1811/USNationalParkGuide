package com.sjani.usnationalparkguide.UI.MainList;

public class MainListPresenterImpl implements MainListContract.MainListPresenter {

    private MainListContract.MainListView view;
    private String title;

    public MainListPresenterImpl(MainListContract.MainListView view, String title) {
        this.view = view;
        this.title = title;
    }

    @Override
    public void loadView() {
        view.createView(title);
    }
}
