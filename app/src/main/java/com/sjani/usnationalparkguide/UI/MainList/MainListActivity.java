package com.sjani.usnationalparkguide.UI.MainList;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.sjani.usnationalparkguide.Data.ParkContract;
import com.sjani.usnationalparkguide.Data.ParkDbHelper;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.Details.DetailsActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = MainListActivity.class.getSimpleName();
    private static final String SELECTED_STATE = "selected_state";
    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;
    private static final String URI = "uri";
    private static final String PARK_ID = "park_id";
    private static final String POSITION = "position";
    private static final String LATLONG = "latlong";
    private static final String PARKCODE = "parkcode";
    private static final String FROM_FAV = "from_fav";

    private Spinner spinner;
    private String state;
    private ListFragment listFragment;
//    private FirebaseAuth mFirebaseAuth;
//    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String mUsername;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_main_list);

        FirebaseApp.initializeApp(this);

        Fresco.initialize(this);
//        mFirebaseAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list_container);

        if(listFragment == null){
            listFragment = ListFragment.newInstance(this,state);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.list_container,listFragment)
                    .commit();
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
        if((getResources().getBoolean(R.bool.dual_pane)) && fragment == null){
                EmptyStateFragment emptyStateFragment = new EmptyStateFragment();
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.details, emptyStateFragment).commit();
        }

//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//
//                } else {
//                    // User is signed out
//                    startActivityForResult(
//                            AuthUI.getInstance()
//                                    .createSignInIntentBuilder()
//                                    .setIsSmartLockEnabled(false)
//                                    .setAvailableProviders(Arrays.asList(
//                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
//                                            new AuthUI.IdpConfig.EmailBuilder().build()))
//                                    .build(),
//                            RC_SIGN_IN);
//                }
//            }
//        };

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//            if (resultCode == RESULT_OK) {
//                // Sign-in succeeded, set up the UI
//                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
//            } else if (resultCode == RESULT_CANCELED) {
//                // Sign in was canceled by the user, finish the activity
//                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_list, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        spinner = (Spinner) MenuItemCompat.getActionView(item);
        spinner.setDropDownWidth(130);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.state_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_fav) {
            if(doesTableExist("favorite")) {
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
                intent.putExtra(FROM_FAV,true);
                startActivity(intent);
            } else {
                Toast.makeText(this,"No Favorites",Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_settings) {

        }
//        else if (id == R.id.nav_logout) {
//            AuthUI.getInstance().signOut(this);
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mAuthStateListener != null) {
//            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
//        }
//    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        state = String.valueOf(spinner.getSelectedItem());
        listFragment = ListFragment.newInstance(this, state);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.list_container,listFragment)
                .commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this,"Please select a state",Toast.LENGTH_SHORT).show();
    }


    public boolean doesTableExist(String tableName) {
        ParkDbHelper mOpenHelper = new ParkDbHelper(this);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Cursor cursor = getContentResolver().query(ParkContract.ParkEntry.CONTENT_URI_FAVORITES,PROJECTION,null,null,null);
        if (cursor != null) {
            if (cursor.getCount() == 1 && cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_ID) != -1) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

//    private void onSignedInInitialize(String username) {
//        mUsername = username;
//    }
//
//    private void onSignedOutCleanup() {
//        mUsername = ANONYMOUS;
//    }




}
