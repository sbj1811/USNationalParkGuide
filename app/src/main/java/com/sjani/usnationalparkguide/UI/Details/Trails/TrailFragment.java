package com.sjani.usnationalparkguide.UI.Details.Trails;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjani.usnationalparkguide.Data.TrailContract;
import com.sjani.usnationalparkguide.Models.Trails.Trail;
import com.sjani.usnationalparkguide.Models.Trails.TrailDatum;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.Listeners.OnListFragmentInteractionListener;
import com.sjani.usnationalparkguide.Utils.NetworkSync.Trails.HikingprojectApiConnection;
import com.sjani.usnationalparkguide.Utils.StringToGPSCoordinates;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class TrailFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, OnListFragmentInteractionListener {

    private static final String TAG = TrailFragment.class.getSimpleName();
    private static final String LATLONG = "latlong";
    private static final String TRAIL_ID = "trail_id";
    private static final String URI = "uri";
    private static final String URI_TRAIL = "uri_trail";
    private static final String POSITION = "position";
    private static final String PARK_ID = "park_id";
    private static final String PARKCODE = "parkcode";
    private static final int DETAIL_ACTIVITY = 1;
    private static final int LOADER_ID = 5;
    private String latLong;
    private Uri uri;
    private String parkId;
    private String parkCode;
    private TrailRecyclerViewAdapter adapter;
    private List<TrailDatum> trails;
    private Context mContext;

    @BindView(R.id.rv_trail)
    RecyclerView recyclerView;


    private static final String[] PROJECTION = new String[]{
            TrailContract.TrailEntry._ID,
            TrailContract.TrailEntry.COLUMN_TRAIL_ID,
            TrailContract.TrailEntry.COLUMN_TRAIL_NAME,
            TrailContract.TrailEntry.COLUMN_TRAIL_SUMMARY,
            TrailContract.TrailEntry.COLUMN_TRAIL_DIFFICULTY,
            TrailContract.TrailEntry.COLUMN_TRAIL_IMAGE_SMALL,
            TrailContract.TrailEntry.COLUMN_TRAIL_IMAGE_MED,
            TrailContract.TrailEntry.COLUMN_TRAIL_LENGTH,
            TrailContract.TrailEntry.COLUMN_TRAIL_ASCENT,
            TrailContract.TrailEntry.COLUMN_TRAIL_LAT,
            TrailContract.TrailEntry.COLUMN_TRAIL_LONG,
            TrailContract.TrailEntry.COLUMN_TRAIL_LENGTH,
            TrailContract.TrailEntry.COLUMN_TRAIL_CONDITION,
            TrailContract.TrailEntry.COLUMN_TRAIL_MOREINFO
    };


    public TrailFragment() {
    }

    public static TrailFragment newInstance(Uri uri, String parkId, int position, String latLong, String parkCode) {
        TrailFragment fragment = new TrailFragment();
        Bundle args = new Bundle();
        args.putParcelable(URI, uri);
        args.putString(PARK_ID, parkId);
        args.putInt(POSITION,position);
        args.putString(LATLONG,latLong);
        args.putString(PARKCODE,parkCode);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID,null,this);
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
        adapter = new TrailRecyclerViewAdapter(this,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (getArguments() != null) {
            latLong = getArguments().getString(LATLONG);
            parkId = getArguments().getString(PARK_ID);
            parkCode = getArguments().getString(PARKCODE);
            uri = getArguments().getParcelable(URI);
        }
        new NetworkCall().execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.getContentResolver().delete(TrailContract.TrailEntry.CONTENT_URI_TRAIL, null, null);
    }

    @Override
    public void onListFragmentInteraction(String id, int position) {
        Uri uriTrail = TrailContract.TrailEntry.CONTENT_URI_TRAIL;
        Intent intent = new Intent(getActivity(),TrailDetailActivity.class);
        intent.putExtra(URI,uri);
        intent.putExtra(URI_TRAIL,uriTrail);
        intent.putExtra(PARK_ID,parkId);
        intent.putExtra(PARKCODE,parkCode);
        intent.putExtra(LATLONG,latLong);
        intent.putExtra(TRAIL_ID,id);
        intent.putExtra(POSITION,position);
        startActivity(intent);

    }

    private class NetworkCall extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            trails = loadTrailData();
            ContentValues[] trailContent = makeContentFromTrailList(trails);
            if (trailContent != null) {
                ContentResolver contentResolver =  mContext.getContentResolver();
                contentResolver.delete(TrailContract.TrailEntry.CONTENT_URI_TRAIL, null, null);
                contentResolver.bulkInsert(TrailContract.TrailEntry.CONTENT_URI_TRAIL, trailContent);
            }
            return null;
        }


    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        uri = TrailContract.TrailEntry.CONTENT_URI_TRAIL;
        return new android.support.v4.content.CursorLoader(getActivity(),uri,PROJECTION,null,null,null);
    }

    private List<TrailDatum>  loadTrailData() {
        String apiKey = mContext.getResources().getString(R.string.HPapiKey);
        StringToGPSCoordinates stringToGPSCoordinates = new StringToGPSCoordinates();
        final String gpsCoodinates[] = stringToGPSCoordinates.convertToGPS(latLong);
        Call<Trail> trailData = HikingprojectApiConnection.getApi().getTrails(gpsCoodinates[0],gpsCoodinates[1],apiKey);
        Response<Trail> response = null;
        try {
            response = trailData.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null) {
            return response.body().getTrails();
        } else
            return null;
    }

    public static ContentValues[] makeContentFromTrailList(List<TrailDatum> list) {

        if (list == null) {
            return null;
        }
        ContentValues[] result = new ContentValues[list.size()];

        for (int i = 0; i < list.size(); i++) {
            TrailDatum data = list.get(i);
            ContentValues trailValues = new ContentValues();
            trailValues.put(TrailContract.TrailEntry.COLUMN_TRAIL_ID, data.getId());
            trailValues.put(TrailContract.TrailEntry.COLUMN_TRAIL_NAME, data.getName());
            trailValues.put(TrailContract.TrailEntry.COLUMN_TRAIL_SUMMARY, data.getSummary());
            trailValues.put(TrailContract.TrailEntry.COLUMN_TRAIL_DIFFICULTY, data.getDifficulty());
            trailValues.put(TrailContract.TrailEntry.COLUMN_TRAIL_IMAGE_SMALL, data.getImgSmall());
            trailValues.put(TrailContract.TrailEntry.COLUMN_TRAIL_IMAGE_MED, data.getImgMedium());
            trailValues.put(TrailContract.TrailEntry.COLUMN_TRAIL_LENGTH, data.getLength());
            trailValues.put(TrailContract.TrailEntry.COLUMN_TRAIL_ASCENT, data.getAscent());
            trailValues.put(TrailContract.TrailEntry.COLUMN_TRAIL_LAT, data.getLatitude());
            trailValues.put(TrailContract.TrailEntry.COLUMN_TRAIL_LONG, data.getLongitude());
            trailValues.put(TrailContract.TrailEntry.COLUMN_TRAIL_LOCATION, data.getLocation());
            trailValues.put(TrailContract.TrailEntry.COLUMN_TRAIL_CONDITION, data.getConditionDetails());
            trailValues.put(TrailContract.TrailEntry.COLUMN_TRAIL_MOREINFO, data.getUrl());
            result[i] = trailValues;
        }
        return result;
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {
        if (adapter != null) {
            adapter.swapCursor(null);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(URI,uri);
        outState.putString(PARK_ID,parkId);
        outState.putString(PARKCODE,parkCode);
        outState.putString(LATLONG,latLong);
    }
}
