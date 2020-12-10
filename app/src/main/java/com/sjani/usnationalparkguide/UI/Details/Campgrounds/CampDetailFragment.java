package com.sjani.usnationalparkguide.UI.Details.Campgrounds;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.sjani.usnationalparkguide.Data.CampEntity;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.FactoryUtils;
import com.sjani.usnationalparkguide.Utils.StringToGPSCoordinates;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;


public class CampDetailFragment extends Fragment {

    private static final String TAG = CampDetailFragment.class.getSimpleName();
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
    @BindView(R.id.camp_address_linear_layout)
    LinearLayout addressLinearLayout;

    private String campId;
    private String parkCode;
    private String title;
    private String latitude;
    private String longitude;

    public CampDetailFragment() {
        // Required empty public constructor
    }


    public static CampDetailFragment newInstance(String campId, String parkCode) {
        CampDetailFragment fragment = new CampDetailFragment();
        Bundle args = new Bundle();
        args.putString(CAMP_ID, campId);
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
                getActivity().overridePendingTransition(R.xml.slide_from_left, R.xml.slide_to_right);
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


    private void updateUI(CampEntity campEntity) {
        if (campEntity != null) {
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
            if (cellrecep != null) {
                if (cellrecep.equals("Yes - year round")) {
                    cellrecepIv.setImageResource(R.drawable.ic_check_circle);
                } else {
                    cellrecepIv.setImageResource(R.drawable.ic_cancel);
                }
            } else {
                cellrecepIv.setImageResource(R.drawable.ic_cancel);
            }
            String showers = campEntity.getShowers();
            if (showers != null) {
                if (showers.equals("None")) {
                    showersIv.setImageResource(R.drawable.ic_cancel);
                } else {
                    showersIv.setImageResource(R.drawable.ic_check_circle);
                }
            } else {
                showersIv.setImageResource(R.drawable.ic_cancel);
            }
            String toilets = campEntity.getToilets();
            if (toilets != null) {
                if (toilets.equals("None")) {
                    toiletsIv.setImageResource(R.drawable.ic_cancel);
                } else {
                    toiletsIv.setImageResource(R.drawable.ic_check_circle);
                }
            } else {
                toiletsIv.setImageResource(R.drawable.ic_cancel);
            }
            String internet = campEntity.getInternetConnectivity();
            if (internet != null) {
                if (internet.equals("true")) {
                    internetIv.setImageResource(R.drawable.ic_check_circle);
                } else {
                    internetIv.setImageResource(R.drawable.ic_cancel);
                }
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
        }
    }


}
