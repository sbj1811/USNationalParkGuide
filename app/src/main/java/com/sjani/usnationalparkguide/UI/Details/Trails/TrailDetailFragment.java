package com.sjani.usnationalparkguide.UI.Details.Trails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.sjani.usnationalparkguide.Data.TrailEntity;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.FactoryUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailDetailFragment extends Fragment {

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
    @BindView(R.id.trail_address_linear_layout)
    LinearLayout addressLl;
    @BindView(R.id.trail_detail_collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private String parkCode;
    private String trailId;
    private String latLong;
    private String title;
    private String latitude;
    private String longitude;


    public TrailDetailFragment() {
        // Required empty public constructor
    }

    public static TrailDetailFragment newInstance(String trailId, String parkCode, String latLong) {
        TrailDetailFragment fragment = new TrailDetailFragment();
        Bundle args = new Bundle();
        args.putString(TRAIL_ID, trailId);
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
        return inflater.inflate(R.layout.fragment_trail_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        String trailApiKey = getResources().getString(R.string.HPapiKey);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
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

    private void updateUI(TrailEntity trailEntity) {
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

            Picasso.with(trailIv.getContext())
                    .load(imageUrl)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Palette.from(bitmap).maximumColorCount(24).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {

                                    int defaultColor = 0x000000;
                                    int lightMutedColor = palette.getLightMutedColor(defaultColor);
                                    int darkMutedColor = palette.getDarkMutedColor(defaultColor);
                                    if (collapsingToolbarLayout != null) {
                                        collapsingToolbarLayout.setContentScrimColor(lightMutedColor);
                                        collapsingToolbarLayout.setStatusBarScrimColor(darkMutedColor);
                                    }
                                }
                            });
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        }
    }

}