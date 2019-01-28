package com.sjani.usnationalparkguide.UI.Details;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.sjani.usnationalparkguide.Data.FavParkEntity;
import com.sjani.usnationalparkguide.Data.ParkEntity;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.Widget.ParkWidgetService;
import com.sjani.usnationalparkguide.Utils.FactoryUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class DetailsFragment extends Fragment {

    private static final String TAG = DetailsFragment.class.getSimpleName();
    private static final String PARKCODE = "parkcode";
    private static final String FROM_FAV = "from_fav";
    private static final String LATLONG = "latlong";
    private static final String CURRENT_TAB = "current_tab";
    @BindView(R.id.detail_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.detail_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.detail_photo)
    ImageView parkImageView;
    @BindView(R.id.fav_button)
    FloatingActionButton favButton;
    @BindView(R.id.detail_collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private boolean isMarkedFavorite;
    private boolean isFromFavNav;
    private String parkCode;
    private String latLong;
    private int currentTabPosition = 0;
    private DetailViewModel viewModel;
    private FavParkEntity favPark, parkFromFavDb;
    private ParkEntity commonParkEntity;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(String parkCode, String latLong, boolean isFromFavNav) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(PARKCODE, parkCode);
        args.putBoolean(FROM_FAV, isFromFavNav);
        args.putString(LATLONG, latLong);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            parkCode = savedInstanceState.getString(PARKCODE);
            isFromFavNav = savedInstanceState.getBoolean(FROM_FAV);
            latLong = savedInstanceState.getString(LATLONG);
            currentTabPosition = savedInstanceState.getInt(CURRENT_TAB);
        } else {
            if (getArguments() != null) {
                parkCode = getArguments().getString(PARKCODE);
                latLong = getArguments().getString(LATLONG);
                isFromFavNav = getArguments().getBoolean(FROM_FAV);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        String apiKey = getResources().getString(R.string.NPSapiKey);
        String trailApiKey = getResources().getString(R.string.HPapiKey);
        String fields = getResources().getString(R.string.fields_cg);
        DetailViewModelFactory factory = FactoryUtils.provideDVMFactory(this.getActivity().getApplicationContext(), parkCode, "", "", apiKey, trailApiKey, fields, latLong);
        viewModel = ViewModelProviders.of(getActivity(), factory).get(DetailViewModel.class);
        if (!isFromFavNav) {
            viewModel.getPark().observe(this, Park -> {
                parkCode = Park.getParkCode();
                latLong = Park.getLatLong();
                favPark = new FavParkEntity(Park);
                updateUI(Park, false);
            });
            viewModel.getfavpark().observe(this, FavParkList -> {
                if (FavParkList.size() == 1) {
                    parkFromFavDb = FavParkList.get(0);
                }
                updateFavUI(parkFromFavDb, commonParkEntity);
            });
        } else {
            viewModel.getfavpark().observe(this, FavParkList -> {
                if (FavParkList.size() == 1) {
                    parkFromFavDb = FavParkList.get(0);
                    updateUI(parkFromFavDb, true);
                    updateFavUI(parkFromFavDb, parkFromFavDb);
                } else {
                    updateFavUI(parkFromFavDb, parkFromFavDb);
                    getActivity().onBackPressed();
                }
            });
        }
    }

    private void updateUI(ParkEntity parkEntity, boolean isFromFavNav) {
        if (parkEntity == null) {
            return;
        }
        FragmentSelectAdapter selectAdapter = new FragmentSelectAdapter(getFragmentManager(), getContext(), parkEntity.getLatLong(), parkEntity.getParkCode(), isFromFavNav);
        mViewPager.setAdapter(selectAdapter);
        mViewPager.setCurrentItem(currentTabPosition);
        mTabLayout.setupWithViewPager(mViewPager);
        String imageUrl = parkEntity.getImage();
        if (imageUrl.equals("")) {
            Glide.with(parkImageView.getContext())
                    .load(R.drawable.empty_detail)
                    .apply(new RequestOptions()
                            .fitCenter())
                    .into(parkImageView);
        } else {
            Glide.with(parkImageView.getContext())
                    .load(imageUrl)
                    .apply(new RequestOptions()
                            .fitCenter())
                    .into(parkImageView);
        }
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMarkedFavorite) {
                    favButton.setImageResource(R.drawable.ic_favorite_border);
                    isMarkedFavorite = false;
                    Observable.fromCallable(() -> {
                        viewModel.clearFav();
                        return false;
                    })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe();
                } else {
                    favButton.setImageResource(R.drawable.ic_favorite);
                    isMarkedFavorite = true;
                    Observable.fromCallable(() -> {
                        viewModel.setFavPark(favPark);
                        ParkWidgetService.startActionUpdateWidgets(getContext());
                        return false;
                    })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe();
                }
//                        isMarkedFavorite = !isMarkedFavorite;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageSelected(int position) {
            }
        });
        if (!isFromFavNav) {
            commonParkEntity = parkEntity;
        } else {
            parkFromFavDb = new FavParkEntity(parkEntity);
        }
    }

    private void updateFavUI(FavParkEntity parkFromFavdb, ParkEntity commomParkEntity) {
        if (parkFromFavdb != null && commomParkEntity != null) {
            if (parkFromFavdb.getPark_id().equals(commomParkEntity.getPark_id())) {
                favButton.setImageResource(R.drawable.ic_favorite);
                isMarkedFavorite = true;
            } else {
                favButton.setImageResource(R.drawable.ic_favorite_border);
                isMarkedFavorite = false;
            }
        } else {
            favButton.setImageResource(R.drawable.ic_favorite_border);
            isMarkedFavorite = false;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case android.R.id.home:
                getActivity().onBackPressed();
                getActivity().overridePendingTransition(R.xml.slide_from_left, R.xml.slide_to_right);
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        currentTabPosition = mViewPager.getCurrentItem();
        outState.putInt(CURRENT_TAB, currentTabPosition);
        outState.putString(PARKCODE, parkCode);
        outState.putString(LATLONG, latLong);
        outState.putBoolean(FROM_FAV, isFromFavNav);
        super.onSaveInstanceState(outState);
    }
}