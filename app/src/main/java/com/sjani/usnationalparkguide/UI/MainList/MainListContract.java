package com.sjani.usnationalparkguide.UI.MainList;

public interface MainListContract {

    public interface MainListPresenter {

        public void loadView();

    }

    public interface MainListView {

        public void createView(String title);

    }


}
