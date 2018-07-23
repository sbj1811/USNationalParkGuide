package com.sjani.usnationalparkguide.UI.MainList;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sjani.usnationalparkguide.Data.ParkContract;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.Details.DetailsActivity;
import com.sjani.usnationalparkguide.UI.Details.DetailsFragment;
import com.sjani.usnationalparkguide.Utils.Listeners.GridItemClickListener;
import com.sjani.usnationalparkguide.Utils.NetworkSync.NPS.AccountModel;
import com.sjani.usnationalparkguide.Utils.NetworkSync.NPS.ParkSyncAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sjani.usnationalparkguide.Utils.ParkIdlingResource;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, GridItemClickListener {

    private static final String TAG = ListFragment.class.getSimpleName();
    private static final int LOADER_ID = 1;
    private static final String SELECTED_STATE = "selected_state";
    private static final String URI = "uri";
    private static final String PARK_ID = "park_id";
    private static final String POSITION = "position";
    private static final String LATLONG = "latlong";
    private static final String PARKCODE = "parkcode";
    private static final String FROM_FAV = "from_fav";


    @BindView(R.id.rv_main)
    RecyclerView recyclerView;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;

    private Uri uri;
    private ListAdapter adapter;
    private String state;
    private boolean mDualPane;
    private AdView mAdView;
    ParkIdlingResource idlingResource;

    private static final String[] PROJECTION = new String[]{
            ParkContract.ParkEntry._ID,
            ParkContract.ParkEntry.COLUMN_PARK_ID,
            ParkContract.ParkEntry.COLUMN_PARK_NAME,
            ParkContract.ParkEntry.COLUMN_PARK_STATES,
            ParkContract.ParkEntry.COLUMN_PARK_CODE,
            ParkContract.ParkEntry.COLUMN_PARK_LATLONG,
            ParkContract.ParkEntry.COLUMN_PARK_DESCRIPTION,
            ParkContract.ParkEntry.COLUMN_PARK_DESIGNATION,
            ParkContract.ParkEntry.COLUMN_PARK_ADDRESS,
            ParkContract.ParkEntry.COLUMN_PARK_PHONE,
            ParkContract.ParkEntry.COLUMN_PARK_EMAIL,
            ParkContract.ParkEntry.COLUMN_PARK_IMAGE
    };

    public ListFragment() {
    }

    public static ListFragment newInstance(Context context, String state){
        Bundle arguments = new Bundle();
        ListFragment fragment =  new ListFragment();
        arguments.putString(SELECTED_STATE, state);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AccountModel.createSyncAccount(context);
        idlingResource = (ParkIdlingResource) ((MainListActivity)getActivity()).getIdlingResource();
        if (idlingResource != null) {
            idlingResource.setIdleState(true);
        }
        state = getArguments().getString(SELECTED_STATE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String max_article = sharedPreferences.getString(getString(R.string.settings_max_articles_key), getString(R.string.settings_max_articles_default));
        ParkSyncAdapter.performSync(state,max_article);
        MobileAds.initialize(getActivity(),"ca-app-pub-1510923228147176~1193226000");
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID,null,this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.list_fragment,container,false);
        mAdView =  new AdView(getActivity());
        mAdView.setAdUnitId("ca-app-pub-1510923228147176/3436245963");
        mAdView.setAdSize(AdSize.BANNER);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.admob_ll);
        linearLayout.addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("Somestring").build();
        mAdView.loadAd(adRequest);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new ListAdapter(this,getContext());
        if (idlingResource != null) {
            idlingResource.setIdleState(true);
        }
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapter);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        } else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        }

        View detailsView = getActivity().findViewById(R.id.details);
        mDualPane = detailsView != null && detailsView.getVisibility() == View.VISIBLE;

    }

    @Override
    public void onItemClick(String parkId, String latlong ,int position, String parkCode) {
        if(mDualPane){
            DetailsFragment detailsFragment = DetailsFragment.newInstance(uri,parkId,position,latlong,parkCode,false);
            getFragmentManager().beginTransaction()
                    .replace(R.id.details,detailsFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        } else {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(PARK_ID, parkId);
            intent.putExtra(POSITION, position);
            intent.putExtra(URI, uri);
            intent.putExtra(LATLONG, latlong);
            intent.putExtra(PARKCODE, parkCode);
            intent.putExtra(FROM_FAV, false);

//            SimpleDraweeView image = getActivity().findViewById(R.id.park_thumbnail);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),image,image.getTransitionName());
//                startActivity(intent, options.toBundle());
//            } else {
                startActivity(intent);
//            }
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        uri = ParkContract.ParkEntry.CONTENT_URI_PARKS;
        return new CursorLoader(getActivity(), uri, PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (adapter != null) {
            adapter.swapCursor(null);
        }
    }
}
