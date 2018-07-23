package com.sjani.usnationalparkguide.UI.Details;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.sjani.usnationalparkguide.Data.ParkContract;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.Widget.ParkWidgetService;


import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = DetailsFragment.class.getSimpleName();
    private static final String URI = "uri";
    private static final String PARK_ID = "park_id";
    private static final String POSITION = "position";
    private static final String LATLONG = "latlong";
    private static final String PARKCODE = "parkcode";
    private static final String FROM_FAV = "from_fav";
    private static final int LOADER_ID = 2;
    private static final int FAV_LOADER_ID = 6;
    private static final int DETAIL_ACTIVITY = 1;
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
    boolean isMarkedFavorite;
    private boolean isFromFavNav;
    private Uri uri;
    private String parkId;
    private int position;
    private String latLong;
    private String parkCode;
    private Cursor cursor;
    private ContentValues values;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(Uri uri, String parkId, int position, String latlong, String parkCode, boolean isFromFavNav) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(URI, uri);
        args.putString(PARK_ID, parkId);
        args.putInt(POSITION, position);
        args.putString(LATLONG, latlong);
        args.putString(PARKCODE, parkCode);
        args.putBoolean(FROM_FAV, isFromFavNav);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            uri = getArguments().getParcelable(URI);
            parkId = getArguments().getString(PARK_ID);
            position = getArguments().getInt(POSITION);
            latLong = getArguments().getString(LATLONG);
            parkCode = getArguments().getString(PARKCODE);
            isFromFavNav = getArguments().getBoolean(FROM_FAV);
        }
        getLoaderManager().initLoader(LOADER_ID, null, this);
        getLoaderManager().initLoader(FAV_LOADER_ID, null, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            uri = savedInstanceState.getParcelable(URI);
            parkId = savedInstanceState.getString(PARK_ID);
            position = savedInstanceState.getInt(POSITION);
            latLong = savedInstanceState.getString(LATLONG);
            parkCode = savedInstanceState.getString(PARKCODE);
            isFromFavNav = savedInstanceState.getBoolean(FROM_FAV);
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
        FragmentSelectAdapter selectAdapter = new FragmentSelectAdapter(getFragmentManager(), getContext(), uri, parkId, position, latLong, parkCode);
        mViewPager.setAdapter(selectAdapter);
        if (getContext().getResources().getBoolean(R.bool.dual_pane)) {
            mViewPager.getAdapter().notifyDataSetChanged();
        }
        if (mViewPager.getCurrentItem() == (selectAdapter.getCount() - 1)) {
            mViewPager.setCurrentItem(selectAdapter.getCount() - 1);
        }
        mTabLayout.setupWithViewPager(mViewPager);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMarkedFavorite) {
                    favButton.setImageResource(R.drawable.ic_favorite_border);
                    isMarkedFavorite = false;
                    getActivity().getContentResolver().delete(ParkContract.ParkEntry.CONTENT_URI_FAVORITES, null, null);
                    ParkWidgetService.startActionUpdateWidgets(getContext());
                } else {
                    favButton.setImageResource(R.drawable.ic_favorite);
                    isMarkedFavorite = true;
                    getActivity().getContentResolver().delete(ParkContract.ParkEntry.CONTENT_URI_FAVORITES, null, null);
                    getActivity().getContentResolver().insert(ParkContract.ParkEntry.CONTENT_URI_FAVORITES, values);
                    ParkWidgetService.startActionUpdateWidgets(getContext());
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
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(getContext(), uri, PROJECTION, null, null, null);
            case FAV_LOADER_ID:
                return new CursorLoader(
                        getContext(),
                        ParkContract.ParkEntry.CONTENT_URI_FAVORITES,
                        PROJECTION,
                        ParkContract.ParkEntry.COLUMN_PARK_ID + "=?",
                        new String[]{parkId},
                        null);
            default:
                throw new RuntimeException("Loader not implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        cursor = data;
        if (cursor == null) return;
        try {
            cursor.moveToPosition(position);
            int id = loader.getId();
            switch (id) {
                case LOADER_ID:
                    values = new ContentValues();
                    if (cursor != null) {
                        DatabaseUtils.cursorRowToContentValues(cursor, values);
                        String imageUrl = cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_IMAGE));
                        if (imageUrl.equals("")) {
                            Glide.with(parkImageView.getContext())
                                    .load(R.drawable.empty_detail)
                                    .fitCenter()
                                    .into(parkImageView);
                        } else {
                            Glide.with(parkImageView.getContext())
                                    .load(imageUrl)
                                    .fitCenter()
                                    .into(parkImageView);
                        }
                    }
                    break;
                case FAV_LOADER_ID:
                    if (data.getCount() > 0) {
                        favButton.setImageResource(R.drawable.ic_favorite);
                        isMarkedFavorite = true;
                    } else {
                        favButton.setImageResource(R.drawable.ic_favorite_border);
                        isMarkedFavorite = false;
                    }
                    break;
                default:
                    throw new RuntimeException("Loader not implemented: " + id);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cursor = null;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(URI, uri);
        outState.putString(PARK_ID, parkId);
        outState.putInt(POSITION, position);
        outState.putString(LATLONG, latLong);
        outState.putString(PARKCODE, parkCode);
        outState.putBoolean(FROM_FAV, isFromFavNav);
        super.onSaveInstanceState(outState);
    }
}
