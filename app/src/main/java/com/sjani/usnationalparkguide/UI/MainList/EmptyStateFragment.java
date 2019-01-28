package com.sjani.usnationalparkguide.UI.MainList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjani.usnationalparkguide.R;

import androidx.fragment.app.Fragment;

public class EmptyStateFragment extends Fragment {

    public EmptyStateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empty_state, container, false);
    }


}
