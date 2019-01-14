package com.sjani.usnationalparkguide.UI.Details.Campgrounds;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sjani.usnationalparkguide.Data.CampContract;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.StringToGPSCoordinates;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CampDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String CAMP_ID = "camp_id";
    private static final String URI = "uri";
    private static final String URI_CAMP = "uri_camp";
    private static final String POSITION = "position";
    private static final String PARK_ID = "park_id";
    private static final String PARKCODE = "parkcode";
    private static final String LATLONG = "latlong";
    private static final int LOADER_ID = 9;
    private static final String[] PROJECTION = new String[]{
            CampContract.CampEntry._ID,
            CampContract.CampEntry.COLUMN_CAMP_ID,
            CampContract.CampEntry.COLUMN_CAMP_NAME,
            CampContract.CampEntry.COLUMN_CAMP_DESCRIPTION,
            CampContract.CampEntry.COLUMN_CAMP_PARKCODE,
            CampContract.CampEntry.COLUMN_CAMP_ADDRESSS,
            CampContract.CampEntry.COLUMN_CAMP_LATLONG,
            CampContract.CampEntry.COLUMN_CAMP_CELLRECEP,
            CampContract.CampEntry.COLUMN_CAMP_SHOWERS,
            CampContract.CampEntry.COLUMN_CAMP_INTERNET,
            CampContract.CampEntry.COLUMN_CAMP_TOILET,
            CampContract.CampEntry.COLUMN_CAMP_WHEELCHAIR,
            CampContract.CampEntry.COLUMN_CAMP_RESERVURL,
            CampContract.CampEntry.COLUMN_CAMP_DIRECTIONURL
    };
    @BindView(R.id.camp_title)
    TextView titleTv;
    @BindView(R.id.camp_address)
    TextView addressTv;
    @BindView(R.id.camp_summary_tv)
    TextView summaryTv;
    @BindView(R.id.showers_iv)
    ImageView showersIv;
    @BindView(R.id.toilets_iv)
    ImageView toiletsIv;
    @BindView(R.id.cellrecep_iv)
    ImageView cellrecepIv;
    @BindView(R.id.internet_iv)
    ImageView internetIv;
    @BindView(R.id.wheelchair_tv)
    TextView wheelchairTv;
    @BindView(R.id.reservation_button)
    Button reservationButton;
    @BindView(R.id.direction_button)
    Button directionButton;
    @BindView(R.id.camp_address_linear_layout)
    LinearLayout addressLinearLayout;
    private Uri uri;
    private String campId;
    private int position;
    private Cursor cursor;
    private Uri uriPark;
    private String parkId;
    private String parkCode;
    private String latLong;
    private String title;
    private String latitude;
    private String longitude;

    public CampDetailFragment() {
        // Required empty public constructor
    }


    public static CampDetailFragment newInstance(Uri uri, String campId, int position, String parkId, String parkCode, String latLong, Uri parkUri) {
        CampDetailFragment fragment = new CampDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(URI_CAMP, uri);
        args.putString(CAMP_ID, campId);
        args.putInt(POSITION, position);
        args.putParcelable(URI, parkUri);
        args.putString(PARK_ID, parkId);
        args.putString(LATLONG, latLong);
        args.putString(PARKCODE, parkCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_camp_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            uri = getArguments().getParcelable(URI_CAMP);
            campId = getArguments().getString(CAMP_ID);
            position = getArguments().getInt(POSITION);
            uriPark = getArguments().getParcelable(URI);
            latLong = getArguments().getString(LATLONG);
            parkCode = getArguments().getString(PARKCODE);
            parkId = getArguments().getString(PARK_ID);
        }
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                getActivity().onBackPressed();
                break;
            case R.id.action_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, title + "\nOpen in Google Maps https://maps.google.com/?q=" + latitude + "," + longitude);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            default:
                break;
        }
        return true;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getActivity(), uri, PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        cursor = data;
        if (cursor == null || cursor.getCount() <= 0) return;
        cursor.moveToPosition(position);
        title = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_NAME));
        titleTv.setText(title);
        String summary = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_DESCRIPTION));
        summaryTv.setText(summary);
        String address = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_ADDRESSS));
        addressTv.setText(address);
        String latLong = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_LATLONG));
        StringToGPSCoordinates stringToGPSCoordinates = new StringToGPSCoordinates();
        final String gpsCoodinates[] = stringToGPSCoordinates.convertToGPS(latLong);
        latitude = gpsCoodinates[0];
        latitude = gpsCoodinates[1];
        addressLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:" + latitude + "," + latitude + "?q=" + latitude + "," + longitude + "?z=10"));
                startActivity(intent);
            }
        });
        String cellrecep = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_CELLRECEP));
        if (cellrecep.equals("Yes - year round")) {
            cellrecepIv.setImageResource(R.drawable.ic_check_circle);
        } else {
            cellrecepIv.setImageResource(R.drawable.ic_cancel);
        }
        String showers = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_SHOWERS));
        if (showers.equals("None")) {
            showersIv.setImageResource(R.drawable.ic_cancel);
        } else {
            showersIv.setImageResource(R.drawable.ic_check_circle);
        }
        String toilets = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_TOILET));
        if (toilets.equals("None")) {
            toiletsIv.setImageResource(R.drawable.ic_cancel);
        } else {
            toiletsIv.setImageResource(R.drawable.ic_check_circle);
        }
        String internet = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_INTERNET));
        if (internet.equals("true")) {
            internetIv.setImageResource(R.drawable.ic_check_circle);
        } else {
            internetIv.setImageResource(R.drawable.ic_cancel);
        }
        String wheelchair = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_WHEELCHAIR));
        wheelchairTv.setText(wheelchair);
        final String reservationUrl = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_RESERVURL));
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reservationUrl.equals("")) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.reservatiton_message), Toast.LENGTH_SHORT).show();
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(reservationUrl));
                    startActivity(browserIntent);
                }
            }
        });
        final String directionUrl = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_DIRECTIONURL));
        directionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (directionUrl.equals("")) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.direction_message), Toast.LENGTH_SHORT).show();
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(directionUrl));
                    startActivity(browserIntent);
                }
            }
        });

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cursor = null;
    }

}
