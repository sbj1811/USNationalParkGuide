package com.sjani.usnationalparkguide.UI.Details.Trails;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.Details.DetailViewModel;
import com.sjani.usnationalparkguide.UI.Details.DetailViewModelFactory;
import com.sjani.usnationalparkguide.Utils.FactoryUtils;
import com.sjani.usnationalparkguide.Utils.Listeners.OnListFragmentInteractionListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class TrailFragment extends Fragment implements OnListFragmentInteractionListener {

    private static final String TAG = TrailFragment.class.getSimpleName();
    private static final String LATLONG = "latlong";
    private static final String TRAIL_ID = "trail_id";
    private static final String IS_FAV = "is_fav";
    private static final String PARK_CODE = "parkcode";
    @BindView(R.id.rv_trail)
    RecyclerView recyclerView;
    @BindView(R.id.trail_loading_indicator)
    ProgressBar progressBar;
    private String latLong;
    private String trailId;
    private String parkCode;
    private TrailRecyclerViewAdapter adapter;
    private boolean isFromFavNav;


    public TrailFragment() {
    }

    public static TrailFragment newInstance(String parkCode, boolean isFromFavNav, String latLong) {
        TrailFragment fragment = new TrailFragment();
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
            trailId = savedInstanceState.getString(TRAIL_ID);
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
        return inflater.inflate(R.layout.fragment_trail_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.VISIBLE);
        String apiKey = getResources().getString(R.string.NPSapiKey);
        String trailApiKey = getResources().getString(R.string.HPapiKey);
        String fields = getResources().getString(R.string.fields_cg);
        DetailViewModelFactory factory = FactoryUtils.provideDVMFactory(this.getActivity().getApplicationContext(), parkCode, "", "", apiKey, trailApiKey, fields, latLong);
        DetailViewModel viewModel = ViewModelProviders.of(getActivity(), factory).get(DetailViewModel.class);
        viewModel.getTrails().observe(this, TrailList -> {
            adapter.swapTrails(TrailList);
        });
        adapter = new TrailRecyclerViewAdapter(this, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onListFragmentInteraction(String trailId, int position) {
        Intent intent = new Intent(getActivity(), TrailDetailActivity.class);
        intent.putExtra(PARK_CODE, parkCode);
        intent.putExtra(TRAIL_ID, trailId);
        intent.putExtra(LATLONG, latLong);
        startActivity(intent);
        getActivity().overridePendingTransition(R.xml.slide_from_right, R.xml.slide_to_left);

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PARK_CODE, parkCode);
        outState.putString(TRAIL_ID, trailId);
        outState.putString(LATLONG, latLong);
        outState.putBoolean(IS_FAV, isFromFavNav);
    }

}
