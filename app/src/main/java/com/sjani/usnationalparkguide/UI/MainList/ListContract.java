package com.sjani.usnationalparkguide.UI.MainList;

public interface ListContract {

    public interface ListFragmentView {

        public void createView();

        public void updateView();

    }

    public interface ListFragmentPresenter {

        public void createUI();

        public void updateUI();

    }

}
