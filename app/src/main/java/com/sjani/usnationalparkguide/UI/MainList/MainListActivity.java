package com.sjani.usnationalparkguide.UI.MainList;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sjani.usnationalparkguide.Data.ParkContract;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.Details.DetailsActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.google.firebase.FirebaseApp;
import com.sjani.usnationalparkguide.UI.Settings.SettingsActivity;
import com.sjani.usnationalparkguide.Utils.ParkIdlingResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;

public class MainListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;
    private static final String TAG = MainListActivity.class.getSimpleName();
    private static final String SELECTED_STATE = "selected_state";
    private static final String URI = "uri";
    private static final String PARK_ID = "park_id";
    private static final String POSITION = "position";
    private static final String LATLONG = "latlong";
    private static final String PARKCODE = "parkcode";
    private static final String FROM_FAV = "from_fav";
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
    private Spinner spinner;
    private String state;
    private ListFragment listFragment;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String mUsername;
    private ParkIdlingResource idlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        ButterKnife.bind(this);
        setContentView(R.layout.activity_main_list);

        FirebaseApp.initializeApp(this);

        Fresco.initialize(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        state = sharedPreferences.getString(getString(R.string.settings_state_key), getString(R.string.settings_states_default));
        String[] allStates = getResources().getStringArray(R.array.state_name_array);
        String[] allStatesAbr = getResources().getStringArray(R.array.state_arrays);
        String state2 = "";
        List<String> stateListAbr = Arrays.asList(allStatesAbr);
        List<String> stateList = Arrays.asList(allStates);
        for (int i=0; i < stateListAbr.size(); i++){
            if (stateListAbr.get(i).equals(state)){
                state2 = stateList.get(i);
                break;
            }
        }
        String title = this.getResources().getString(R.string.toolbar_title_prompt)+" "+state2;
        toolbar.setTitle(title);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(view.getContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });


        if (savedInstanceState == null) {
            listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list_container);
            if (listFragment == null) {
                listFragment = ListFragment.newInstance(this);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_container, listFragment)
                        .commit();
            }
        }

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Fragment fragment = getFragmentManager().findFragmentById(R.id.details);
        if ((getResources().getBoolean(R.bool.dual_pane)) && fragment == null) {
            EmptyStateFragment emptyStateFragment = new EmptyStateFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details, emptyStateFragment).commit();
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {
                    // User is signed out
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_list, menu);
//        MenuItem item = menu.findItem(R.id.spinner);
//        spinner = (Spinner) MenuItemCompat.getActionView(item);
//        item.setTitle(getResources().getString(R.string.state_prompt));
//        spinner.setDropDownWidth(130);
//        spinner.setPopupBackgroundResource(R.color.primaryText);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.state_arrays, R.layout.spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
//        return true;
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_fav) {
            if (doesTableExist("favorite")) {
                Cursor cursor = getContentResolver().query(ParkContract.ParkEntry.CONTENT_URI_FAVORITES,
                        null,
                        null,
                        null, null);
                cursor.moveToNext();
                Uri uri = ParkContract.ParkEntry.CONTENT_URI_FAVORITES;
                String parkId = cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_ID));
                int position = cursor.getPosition();
                String latLong = cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_LATLONG));
                String parkCode = cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_CODE));
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra(PARK_ID, parkId);
                intent.putExtra(POSITION, position);
                intent.putExtra(URI, uri);
                intent.putExtra(LATLONG, latLong);
                intent.putExtra(PARKCODE, parkCode);
                intent.putExtra(FROM_FAV, true);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No Favorites", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        } else if (id == R.id.nav_logout) {
            AuthUI.getInstance().signOut(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    public boolean doesTableExist(String tableName) {
        Cursor cursor = getContentResolver().query(ParkContract.ParkEntry.CONTENT_URI_FAVORITES, PROJECTION, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() == 1 && cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_ID) != -1) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new ParkIdlingResource();
        }
        return idlingResource;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SELECTED_STATE, state);
        super.onSaveInstanceState(outState);
    }

    //    private void onSignedInInitialize(String username) {
//        mUsername = username;
//    }
//
//    private void onSignedOutCleanup() {
//        mUsername = ANONYMOUS;
//    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.transition.Transition slide = new android.transition.Slide();
            slide.setDuration(300);
            getWindow().setEnterTransition(slide);
        }
    }


}
