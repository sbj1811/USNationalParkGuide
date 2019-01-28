package com.sjani.usnationalparkguide.UI.MainList;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.Details.DetailsActivity;
import com.sjani.usnationalparkguide.UI.Details.DetailsFragment;
import com.sjani.usnationalparkguide.Utils.FactoryUtils;
import com.sjani.usnationalparkguide.Utils.Listeners.GridItemClickListener;
import com.sjani.usnationalparkguide.Utils.ParkIdlingResource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment implements GridItemClickListener, ListContract.ListFragmentView {

    private static final String TAG = ListFragment.class.getSimpleName();
    private static final String LIST_STATE_KEY = "list_state_key";
    private static final String PARKCODE = "parkcode";
    private static final String FROM_FAV = "from_fav";
    private static final String LATLONG = "latlong";
    static int currentVisiblePosition = 0;
    @BindView(R.id.rv_main)
    RecyclerView recyclerView;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;
    ParkIdlingResource idlingResource;
    private Uri uri;
    private ListAdapter adapter;
    private String state;
    private boolean mDualPane;
    private AdView mAdView;
    private GridLayoutManager layoutManager;
    private ListFragmentPresenterImpl presenter;
    private MainListViewModel viewModel;


    public ListFragment() {
    }

    public static ListFragment newInstance(Context context) {
        Bundle arguments = new Bundle();
        ListFragment fragment = new ListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        idlingResource = (ParkIdlingResource) ((MainListActivity) getActivity()).getIdlingResource();
        if (idlingResource != null) {
            idlingResource.setIdleState(true);
        }
        MobileAds.initialize(getActivity(), "ca-app-pub-1510923228147176~5607247189");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        mAdView = new AdView(getActivity());
        mAdView.setAdUnitId("ca-app-pub-1510923228147176/1177047580");
        mAdView.setAdSize(AdSize.BANNER);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.admob_ll);
        linearLayout.addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("82DB6E6641D0CCA845D58CE176AF15E").build();
        mAdView.loadAd(adRequest);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String max_article = sharedPreferences.getString(getString(R.string.settings_max_articles_key), getString(R.string.settings_max_articles_default));
        state = sharedPreferences.getString(getString(R.string.settings_state_key), getString(R.string.settings_states_default));
        String fields = getContext().getResources().getString(R.string.fields);
        String apiKey = getContext().getResources().getString(R.string.NPSapiKey);
        MainListViewModelFactory factory = FactoryUtils.provideMLVFactory(this.getActivity().getApplicationContext(), apiKey, fields, state, max_article);
        viewModel = ViewModelProviders.of(this, factory).get(MainListViewModel.class);
        presenter = new ListFragmentPresenterImpl(this);
        presenter.createUI();
    }

    @Override
    public void onItemClick(String parkId, String latlong, int position, String parkCode) {
        if (mDualPane) {
            DetailsFragment detailsFragment = DetailsFragment.newInstance(parkCode, latlong, false);
            getFragmentManager().beginTransaction()
                    .replace(R.id.details, detailsFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        } else {
            ImageView imageView = (ImageView) getActivity().findViewById(R.id.park_thumbnail);

            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(PARKCODE, parkCode);
            intent.putExtra(LATLONG, latlong);
            intent.putExtra(FROM_FAV, false);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                ActivityOptionsCompat options = ActivityOptionsCompat.
//                        makeSceneTransitionAnimation(getActivity(), imageView, getString(R.string.transition_image));
//                startActivity(intent, options.toBundle());
                startActivity(intent);
                getActivity().overridePendingTransition(R.xml.slide_from_right, R.xml.slide_to_left);
            } else {
                startActivity(intent);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        currentVisiblePosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        outState.putInt(LIST_STATE_KEY, currentVisiblePosition);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void createView() {
        adapter = new ListAdapter(this, getContext());
        if (idlingResource != null) {
            idlingResource.setIdleState(true);
        }
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapter);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 4);
            recyclerView.setLayoutManager(layoutManager);
        }
        viewModel.getParksfromViewModel().observe(this, ParkList -> {
            adapter.swapParks(ParkList);
            recyclerView.getLayoutManager().scrollToPosition(currentVisiblePosition);
            currentVisiblePosition = 0;
            presenter.updateUI();
        });
        View detailsView = getActivity().findViewById(R.id.details);
        mDualPane = detailsView != null && detailsView.getVisibility() == View.VISIBLE;
    }

    @Override
    public void updateView() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

}
