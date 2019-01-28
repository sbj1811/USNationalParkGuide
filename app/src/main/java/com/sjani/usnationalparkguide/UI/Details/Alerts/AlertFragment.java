package com.sjani.usnationalparkguide.UI.Details.Alerts;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.Details.DetailViewModel;
import com.sjani.usnationalparkguide.UI.Details.DetailViewModelFactory;
import com.sjani.usnationalparkguide.Utils.FactoryUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertFragment extends Fragment {

    private static final String TAG = AlertFragment.class.getSimpleName();
    private static final String PARK_CODE = "parkcode";
    private static final String LATLONG = "latlong";
    private static final String IS_FAV = "is_fav";
    @BindView(R.id.rv_alert)
    RecyclerView recyclerView;
    private String parkCode;
    private AlertRecyclerViewAdapter adapter;
    private boolean isFromFavNav;
    private String latLong;

    public AlertFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AlertFragment newInstance(String parkCode, boolean isFromFavNav, String latLong) {
        AlertFragment fragment = new AlertFragment();
        Bundle args = new Bundle();
        args.putString(PARK_CODE, parkCode);
        args.putBoolean(IS_FAV, isFromFavNav);
        args.putString(LATLONG, latLong);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            parkCode = savedInstanceState.getString(PARK_CODE);
            latLong = savedInstanceState.getString(LATLONG);
            isFromFavNav = savedInstanceState.getBoolean(IS_FAV);
        } else {
            if (getArguments() != null) {
                parkCode = getArguments().getString(PARK_CODE);
                latLong = getArguments().getString(LATLONG);
                isFromFavNav = getArguments().getBoolean(IS_FAV);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alert_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        String apiKey = getResources().getString(R.string.NPSapiKey);
        String trailApiKey = getResources().getString(R.string.HPapiKey);
        String fields = getResources().getString(R.string.fields_cg);
        DetailViewModelFactory factory = FactoryUtils.provideDVMFactory(this.getActivity().getApplicationContext(), parkCode, "", "", apiKey, trailApiKey, fields, latLong);
        DetailViewModel viewModel = ViewModelProviders.of(getActivity(), factory).get(DetailViewModel.class);
        viewModel.getAlerts().observe(this, AlertList -> {
            adapter.swapAlerts(AlertList);
        });
        adapter = new AlertRecyclerViewAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PARK_CODE, parkCode);
        outState.putString(LATLONG, latLong);
        outState.putBoolean(IS_FAV, isFromFavNav);
    }

}
