package com.sjani.usnationalparkguide.UI.Details.Trails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sjani.usnationalparkguide.Data.TrailEntity;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.FactoryUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailDetailDialogFragment extends DialogFragment {

    private static final String TAG = TrailDetailFragment.class.getSimpleName();
    private static final String TRAIL_ID = "trail_id";
    private static final String PARKCODE = "parkcode";
    private static final String LATLONG = "latlong";

    @BindView(R.id.trail_detail_title)
    TextView titleTv;
    @BindView(R.id.trail_address)
    TextView trailAddressTv;
    @BindView(R.id.difficulty_tv)
    TextView difficultyTv;
    @BindView(R.id.distance_tv)
    TextView distanceTv;
    @BindView(R.id.elevation_tv)
    TextView elevationTv;
    @BindView(R.id.condition_tv)
    TextView conditionTv;
    @BindView(R.id.summary_tv)
    TextView summaryTv;
    @BindView(R.id.trail_detail_photo)
    ImageView trailIv;
    @BindView(R.id.trail_share_button)
    ImageButton shareButton;
    @BindView(R.id.trail_back_button)
    ImageButton backButton;
    @BindView(R.id.trail_address_linear_layout)
    LinearLayout addressLl;

    private String parkCode;
    private String trailId;
    private String latLong;
    private String title;
    private String latitude;
    private String longitude;


    public TrailDetailDialogFragment() {
        // Required empty public constructor
    }

    public static TrailDetailDialogFragment newInstance(String trailId, String parkCode, String latLong) {
        TrailDetailDialogFragment fragment = new TrailDetailDialogFragment();
        Bundle args = new Bundle();
        args.putString(TRAIL_ID, trailId);
        args.putString(LATLONG, latLong);
        args.putString(PARKCODE, parkCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DetailDialog);
        return inflater.inflate(R.layout.fragment_trail_detail_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        String trailApiKey = getResources().getString(R.string.HPapiKey);
        TrailDetailViewModelFactory factory = FactoryUtils.provideTDVMFactory(this.getActivity().getApplicationContext(), trailApiKey, latLong);
        TrailDetailViewModel viewModel = ViewModelProviders.of(getActivity(), factory).get(TrailDetailViewModel.class);
        viewModel.getTrail(trailId).observe(this, TrailEntity -> {
            updateUI(TrailEntity);
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            trailId = getArguments().getString(TRAIL_ID);
            latLong = getArguments().getString(LATLONG);
            parkCode = getArguments().getString(PARKCODE);
        }
    }


    private void updateUI(TrailEntity trailEntity) {
        if (trailEntity != null) {
            title = trailEntity.getTrail_name();
            titleTv.setText(title);
            String distance = trailEntity.getLength();
            distanceTv.setText(distance + " " + getContext().getResources().getString(R.string.miles));
            String elevation = trailEntity.getAscent();
            elevationTv.setText(elevation + " " + getContext().getResources().getString(R.string.ft));
            String address = trailEntity.getLocation();
            ;
            trailAddressTv.setText(address);
            latitude = trailEntity.getLatitude();
            ;
            longitude = trailEntity.getLongitude();
            ;
            addressLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("geo:" + latitude + "," + longitude + "?z=10"));
                    startActivity(intent);
                }
            });
            String summary = trailEntity.getSummary();
            summaryTv.setText(summary);
            String condition = trailEntity.getCondition();
            if (!(condition == null)) {
                if (!condition.equals("")) {
                    conditionTv.setText(condition);
                }
            } else {
                conditionTv.setText(getActivity().getResources().getString(R.string.na));
            }
            String difficultyMark = trailEntity.getDifficulty();
            String difficultyLevel;
            if (difficultyMark.equals("greenBlue")) {
                difficultyLevel = getContext().getResources().getString(R.string.easy);
            } else if (difficultyMark.equals("blue")) {
                difficultyLevel = getContext().getResources().getString(R.string.moderate);
            } else if (difficultyMark.equals("blueBlack")) {
                difficultyLevel = getContext().getResources().getString(R.string.strenuous);
            } else {
                difficultyLevel = getContext().getResources().getString(R.string.unknown);
            }
            difficultyTv.setText(difficultyLevel);
            String imageUrl = trailEntity.getImage_med();
            if (imageUrl.equals("")) {
                Glide.with(trailIv.getContext())
                        .load(R.drawable.empty_detail)
                        .apply(new RequestOptions()
                                .fitCenter())
                        .into(trailIv);
            } else {
                Glide.with(trailIv.getContext())
                        .load(imageUrl)
                        .apply(new RequestOptions()
                                .fitCenter())
                        .into(trailIv);
            }

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, title + "\nOpen in Google Maps https://maps.google.com/?q=" + latitude + "," + longitude);
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


}
