package com.sjani.usnationalparkguide.UI.Details.Campgrounds;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.sjani.usnationalparkguide.Data.CampEntity;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.FactoryUtils;
import com.sjani.usnationalparkguide.Utils.StringToGPSCoordinates;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;


public class CampDetailDialogFragment extends DialogFragment {

    private static final String CAMP_ID = "camp_id";
    private static final String PARKCODE = "parkcode";

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
    @BindView(R.id.camp_back_button)
    ImageButton backButton;
    @BindView(R.id.camp_address_linear_layout)
    LinearLayout addressLinearLayout;

    private String campId;
    private String parkCode;
    private String title;
    private String latitude;
    private String longitude;

    public CampDetailDialogFragment() {
        // Required empty public constructor
    }


    public static CampDetailDialogFragment newInstance(String campId, String parkCode) {
        CampDetailDialogFragment fragment = new CampDetailDialogFragment();
        Bundle args = new Bundle();
        args.putString(CAMP_ID, campId);
        args.putString(PARKCODE, parkCode);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        String apiKey = getResources().getString(R.string.NPSapiKey);
        CampDetailViewModelFactory factory = FactoryUtils.provideCDVMFactory(this.getActivity().getApplicationContext(), apiKey, parkCode);
        CampDetailViewModel viewModel = ViewModelProviders.of(getActivity(), factory).get(CampDetailViewModel.class);
        viewModel.getCamp(campId).observe(this, CampEntity -> {
            updateUI(CampEntity);
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            campId = getArguments().getString(CAMP_ID);
            parkCode = getArguments().getString(PARKCODE);
        }
    }

    private void updateUI(CampEntity campEntity) {
        title = campEntity.getCamp_name();
        titleTv.setText(title);
        String summary = campEntity.getDescription();
        summaryTv.setText(summary);
        String address = campEntity.getAddress();
        addressTv.setText(address);
        String latLong = campEntity.getLatLong();
        StringToGPSCoordinates stringToGPSCoordinates = new StringToGPSCoordinates();
        final String gpsCoodinates[] = stringToGPSCoordinates.convertToGPS(latLong);
        latitude = gpsCoodinates[0];
        latitude = gpsCoodinates[1];
        addressLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:" + latitude + "," + latitude + "?q=" + latitude + "," + longitude + "?z=10"));
                startActivity(intent);
            }
        });
        String cellrecep = campEntity.getCellPhoneReception();
        if (cellrecep.equals("Yes - year round")) {
            cellrecepIv.setImageResource(R.drawable.ic_check_circle);
        } else {
            cellrecepIv.setImageResource(R.drawable.ic_cancel);
        }
        String showers = campEntity.getShowers();
        if (showers.equals("None")) {
            showersIv.setImageResource(R.drawable.ic_cancel);
        } else {
            showersIv.setImageResource(R.drawable.ic_check_circle);
        }
        String toilets = campEntity.getToilets();
        if (toilets.equals("None")) {
            toiletsIv.setImageResource(R.drawable.ic_cancel);
        } else {
            toiletsIv.setImageResource(R.drawable.ic_check_circle);
        }
        String internet = campEntity.getInternetConnectivity();
        if (internet.equals("true")) {
            internetIv.setImageResource(R.drawable.ic_check_circle);
        } else {
            internetIv.setImageResource(R.drawable.ic_cancel);
        }
        String wheelchair = campEntity.getWheelchairAccess();
        wheelchairTv.setText(wheelchair);
        final String reservationUrl = campEntity.getReservationsUrl();
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
        final String directionUrl = campEntity.getDirectionsUrl();
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
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, title + "\nOpen in Google Maps https://maps.google.com/?q=" + gpsCoodinates[0] + "," + gpsCoodinates[1]);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                getActivity().onBackPressed();
            }
        });
    }


}
