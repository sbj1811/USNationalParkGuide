package com.sjani.usnationalparkguide.UI.Details.Campgrounds;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class CampgroundFragment extends Fragment implements OnListFragmentInteractionListener {

    private static final String TAG = CampgroundFragment.class.getSimpleName();
    private static final String CAMP_ID = "camp_id";
    private static final String LATLONG = "latlong";
    private static final String IS_FAV = "is_fav";
    private static final String PARK_CODE = "parkcode";
    @BindView(R.id.rv_camp)
    RecyclerView recyclerView;
    private String parkCode;
    private String campId;
    private CampgroundRecyclerViewAdapter adapter;
    private boolean isFromFavNav;
    private String latLong;

    public CampgroundFragment() {
    }

    public static CampgroundFragment newInstance(String parkCode, boolean isFromFavNav, String latLong) {
        CampgroundFragment fragment = new CampgroundFragment();
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
            campId = savedInstanceState.getString(CAMP_ID);
            latLong = savedInstanceState.getString(LATLONG);
            isFromFavNav = savedInstanceState.getBoolean(IS_FAV);
        } else {
            if (getArguments() != null) {
                parkCode = getArguments().getString(PARK_CODE);
                campId = getArguments().getString(CAMP_ID);
                latLong = getArguments().getString(LATLONG);
                isFromFavNav = getArguments().getBoolean(IS_FAV);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campgound_list, container, false);
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
        viewModel.getCamps().observe(this, CampList -> {
            adapter.swapCamps(CampList);
        });
        adapter = new CampgroundRecyclerViewAdapter(this, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void onListFragmentInteraction(String id, int position) {


        Intent intent = new Intent(getActivity(), CampDetailActivity.class);
        intent.putExtra(PARK_CODE, parkCode);
        intent.putExtra(CAMP_ID, id);
        startActivity(intent);
        getActivity().overridePendingTransition(R.xml.slide_from_right, R.xml.slide_to_left);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PARK_CODE, parkCode);
        outState.putString(LATLONG, latLong);
        outState.putBoolean(IS_FAV, isFromFavNav);
        outState.putString(CAMP_ID, campId);
    }

}
