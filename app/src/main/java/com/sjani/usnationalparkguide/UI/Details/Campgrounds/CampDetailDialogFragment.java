package com.sjani.usnationalparkguide.UI.Details.Campgrounds;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sjani.usnationalparkguide.Data.CampContract;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.StringToGPSCoordinates;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CampDetailDialogFragment extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String CAMP_ID = "camp_id";
    private static final String URI = "uri";
    private static final String POSITION = "position";
    private static final int LOADER_ID = 10;
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
    @BindView(R.id.camp_share_button)
    ImageButton shareButton;
    @BindView(R.id.camp_address_linear_layout)
    LinearLayout addressLinearLayout;
    private Uri uri;
    private String campId;
    private int position;
    private Cursor cursor;
    private OnFragmentInteractionListener mListener;

    public CampDetailDialogFragment() {
        // Required empty public constructor
    }


    public static CampDetailDialogFragment newInstance(Uri uri, String campId, int position) {
        CampDetailDialogFragment fragment = new CampDetailDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(URI, uri);
        args.putString(CAMP_ID, campId);
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (2 * displayMetrics.heightPixels) / 3;
        int width = displayMetrics.widthPixels;
        getDialog().getWindow().setLayout(width / 2, height);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DetailDialog);
        return inflater.inflate(R.layout.fragment_camp_detail_dialog, container, false);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        getActivity().finish();
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
            uri = getArguments().getParcelable(URI);
            campId = getArguments().getString(CAMP_ID);
            position = getArguments().getInt(POSITION);
        }
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getActivity(), uri, PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        cursor = data;
        if (cursor == null) return;
        try {
            cursor.moveToPosition(position);
            final String title = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_NAME));
            titleTv.setText(title);
            String summary = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_DESCRIPTION));
            summaryTv.setText(summary);
            String address = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_ADDRESSS));
            addressTv.setText(address);
            String latLong = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_LATLONG));
            StringToGPSCoordinates stringToGPSCoordinates = new StringToGPSCoordinates();
            final String gpsCoodinates[] = stringToGPSCoordinates.convertToGPS(latLong);
            addressLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("geo:" + gpsCoodinates[0] + "," + gpsCoodinates[1] + "?q=" + gpsCoodinates[0] + "," + gpsCoodinates[1] + "?z=10"));
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
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, title + "\nOpen in Google Maps https://maps.google.com/?q=" + gpsCoodinates[0] + "," + gpsCoodinates[1]);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cursor = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
