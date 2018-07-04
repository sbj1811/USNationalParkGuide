package com.example.android.usnationalparkguide.UI.Details;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.usnationalparkguide.Data.ParkContract;
import com.example.android.usnationalparkguide.R;
import com.example.android.usnationalparkguide.Utils.StringToGPSCordinates;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = InfoFragment.class.getSimpleName();
    private static final String URI = "uri";
    private static final String PARK_ID = "park_id";
    private static final String POSITION = "position";
    private static final String LATLONG = "latlong";
    private static final int LOADER_ID = 3;


    @BindView(R.id.park_title)
    TextView titleTextview;
    @BindView(R.id.park_designation)
    TextView designationTextview;
    @BindView(R.id.park_state)
    TextView stateextview;
    @BindView(R.id.park_address)
    TextView addressTextview;
    @BindView(R.id.park_description)
    TextView descriptionTextview;
    @BindView(R.id.park_latlong)
    ImageButton mapButton;
    @BindView(R.id.park_phone)
    ImageButton phoneButton;
    @BindView(R.id.park_email)
    ImageButton emailButton;


    private Uri uri;
    private String parkId;
    private int position;
    private String latLong;

    private Cursor cursor;

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


    public InfoFragment() {
        // Required empty public constructor
    }


    public static InfoFragment newInstance(Uri uri, String parkId, int position, String latlong) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(URI, uri);
        args.putString(PARK_ID, parkId);
        args.putInt(POSITION,position);
        args.putString(LATLONG,latlong);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getArguments() != null) {
            uri = getArguments().getParcelable(URI);
            parkId = getArguments().getString(PARK_ID);
            position = getArguments().getInt(POSITION);
            latLong = getArguments().getString(LATLONG);
        }
        getLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getActivity(), uri, PROJECTION, null, null, null);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        cursor = data;
        cursor.moveToPosition(position);
//        Log.e(TAG, "onLoadFinished HERE: URI: "+uri+"\npark_id: "+parkId+"position: "+position);
//        Log.e(TAG, "onLoadFinished HERE: "+ DatabaseUtils.dumpCursorToString(cursor));
        final String parkName = cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_NAME));
        titleTextview.setText(parkName);
        designationTextview.setText(cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_DESIGNATION)));
        stateextview.setText(cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_STATES)));
        addressTextview.setText(cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_ADDRESS)));
        descriptionTextview.setText(cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_DESCRIPTION)));
        StringToGPSCordinates stringToGPSCordinates = new StringToGPSCordinates();
        final String gpsCoodinates[] = stringToGPSCordinates.convertToGPS(latLong);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:"+gpsCoodinates[0]+","+gpsCoodinates[1]+"?q="+gpsCoodinates[0]+","+gpsCoodinates[1]+"("+parkName+")?z=10"));
                startActivity(intent);
            }
        });
        final String phoneNumber = cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_PHONE));
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
        final String emailId = cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_EMAIL));
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, emailId);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cursor = null;
    }
}
