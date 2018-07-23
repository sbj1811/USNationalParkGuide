package com.sjani.usnationalparkguide.UI.Details.Alerts;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjani.usnationalparkguide.Data.AlertContract;
import com.sjani.usnationalparkguide.Models.Alerts.Alert;
import com.sjani.usnationalparkguide.Models.Alerts.AlertDatum;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.NetworkSync.NPS.NPSApiConnection;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class AlertFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = AlertFragment.class.getSimpleName();
    private static final String ALERT_ID = "alert_id";
    private static final String URI = "uri";
    private static final String URI_ALERT = "uri_alert";
    private static final String POSITION = "position";
    private static final String PARK_ID = "park_id";
    private static final String PARKCODE = "parkcode";
    private static final String LATLONG = "latlong";
    private static final int LOADER_ID = 11;
    private String parkCode;
    private Uri uri;
    private String parkId;
    private String latLong;
    private List<AlertDatum> alerts;
    private AlertRecyclerViewAdapter adapter;
    private Context mContext;

    @BindView(R.id.rv_alert)
    RecyclerView recyclerView;

    private static final String[] PROJECTION = new String[]{
            AlertContract.AlertEntry._ID,
            AlertContract.AlertEntry.COLUMN_ALERT_ID,
            AlertContract.AlertEntry.COLUMN_ALERT_NAME,
            AlertContract.AlertEntry.COLUMN_ALERT_DESCRIPTION,
            AlertContract.AlertEntry.COLUMN_ALERT_PARKCODE,
            AlertContract.AlertEntry.COLUMN_ALERT_CATEGORY
    };

    public AlertFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AlertFragment newInstance(Uri uri, String parkId, int position, String latlong, String parkCode) {
        AlertFragment fragment = new AlertFragment();
        Bundle args = new Bundle();
        args.putString(PARK_ID, parkId);
        args.putInt(POSITION,position);
        args.putString(LATLONG,latlong);
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
        return inflater.inflate(R.layout.fragment_alert_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new AlertRecyclerViewAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
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
        mContext.getContentResolver().delete(AlertContract.AlertEntry.CONTENT_URI_ALERT, null, null);
    }

    private class NetworkCall extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            alerts = loadAlertData();
            ContentValues[] alertContent = makeContentFromAlertList(alerts);
            if (alertContent != null) {
                ContentResolver contentResolver =  mContext.getContentResolver();
                contentResolver.delete(AlertContract.AlertEntry.CONTENT_URI_ALERT, null, null);
                contentResolver.bulkInsert(AlertContract.AlertEntry.CONTENT_URI_ALERT, alertContent);
            }
            return null;
        }
    }


    private List<AlertDatum>  loadAlertData() {
        String apiKey = mContext.getResources().getString(R.string.NPSapiKey);
        Call<Alert> alertData = NPSApiConnection.getApi().getAlerts(parkCode,apiKey);
        Response<Alert> response = null;
        try {
            response = alertData.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null) {
            return response.body().getData();
        } else
            return null;
    }

    public static ContentValues[] makeContentFromAlertList(List<AlertDatum> list) {
        if (list == null) {
            return null;
        }
        ContentValues[] result = new ContentValues[list.size()];

        for (int i = 0; i < list.size(); i++) {
            AlertDatum data = list.get(i);
            ContentValues alertValues = new ContentValues();
            alertValues.put(AlertContract.AlertEntry.COLUMN_ALERT_ID, data.getId());
            alertValues.put(AlertContract.AlertEntry.COLUMN_ALERT_NAME, data.getTitle());
            alertValues.put(AlertContract.AlertEntry.COLUMN_ALERT_DESCRIPTION, data.getDescription());
            alertValues.put(AlertContract.AlertEntry.COLUMN_ALERT_PARKCODE, data.getParkCode());
            alertValues.put(AlertContract.AlertEntry.COLUMN_ALERT_CATEGORY, data.getCategory());
            result[i] = alertValues;
        }
        return result;
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        uri = AlertContract.AlertEntry.CONTENT_URI_ALERT;
        return new android.support.v4.content.CursorLoader(getActivity(),uri,PROJECTION,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (adapter != null) {
            adapter.swapCursor(null);
        }
    }

}
