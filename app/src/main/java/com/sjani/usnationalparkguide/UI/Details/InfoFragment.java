package com.sjani.usnationalparkguide.UI.Details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sjani.usnationalparkguide.Data.ParkEntity;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.FactoryUtils;
import com.sjani.usnationalparkguide.Utils.StringToGPSCoordinates;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;


public class InfoFragment extends Fragment {

    private static final String TAG = InfoFragment.class.getSimpleName();
    private static final String PARK_CODE = "park_code";
    private static final String LATLONG = "latlong";
    private static final String IS_FAV = "is_fav";
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
    private String parkCode;
    private ParkEntity parkEntity;
    private boolean isFromFavNav;
    private String latLong;

    public InfoFragment() {
        // Required empty public constructor
    }


    public static InfoFragment newInstance(String parkCode, boolean isFromFavNav, String latLong) {
        InfoFragment fragment = new InfoFragment();
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
            latLong = savedInstanceState.getString(LATLONG);
            isFromFavNav = savedInstanceState.getBoolean(IS_FAV);
        } else {
            if (getArguments() != null) {
                parkCode = getArguments().getString(PARK_CODE);
                latLong = getArguments().getString(LATLONG);
                isFromFavNav = getArguments().getBoolean(IS_FAV);
            }
        }

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
        String apiKey = getResources().getString(R.string.NPSapiKey);
        String trailApiKey = getResources().getString(R.string.HPapiKey);
        String fields = getResources().getString(R.string.fields_cg);
        DetailViewModelFactory factory = FactoryUtils.provideDVMFactory(this.getActivity().getApplicationContext(), parkCode, "", "", apiKey, trailApiKey, fields, latLong);
        DetailViewModel viewModel = ViewModelProviders.of(getActivity(), factory).get(DetailViewModel.class);
        if (!isFromFavNav) {
            viewModel.getPark().observe(this, Park -> {
                parkCode = Park.getParkCode();
                updateUI(Park);
            });
        } else {
            viewModel.getfavpark().observe(this, Park -> {
                if (Park.size() == 1) {
                    parkCode = Park.get(0).getParkCode();
                    updateUI(Park.get(0));
                }
            });
        }
    }


    private void updateUI(ParkEntity parkEntity) {
        titleTextview.setText(parkEntity.getPark_name());
        designationTextview.setText(parkEntity.getDesignation());
        stateextview.setText(parkEntity.getStates());
        addressTextview.setText(parkEntity.getAddress());
        descriptionTextview.setText(parkEntity.getDescription());
        StringToGPSCoordinates stringToGPSCoordinates = new StringToGPSCoordinates();
        final String gpsCoodinates[] = stringToGPSCoordinates.convertToGPS(parkEntity.getLatLong());
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:" + gpsCoodinates[0] + "," + gpsCoodinates[1] + "?q=" + gpsCoodinates[0] + "," + gpsCoodinates[1] + "(" + parkEntity.getPark_name() + ")?z=10"));
                startActivity(intent);
            }
        });
        final String phoneNumber = parkEntity.getPhone();
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneNumber.equals(getActivity().getResources().getString(R.string.na))) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.phone_message), Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                    startActivity(intent);
                }
            }
        });
        final String emailId = parkEntity.getEmail();
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailId.equals(getActivity().getResources().getString(R.string.na))) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.email_message), Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/html");
                    intent.putExtra(Intent.EXTRA_EMAIL, emailId);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(PARK_CODE, parkCode);
        outState.putBoolean(IS_FAV, isFromFavNav);
        outState.putString(LATLONG, latLong);
        super.onSaveInstanceState(outState);
    }
}
