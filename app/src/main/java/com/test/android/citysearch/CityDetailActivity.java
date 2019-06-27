package com.test.android.citysearch;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link CityListActivity}.
 */
public class CityDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        String title = getIntent().getStringExtra(CityDetailFragment.ARG_CITY_NAME)
                + " : " + getIntent().getStringExtra(CityDetailFragment.ARG_COUNTRY);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(CityDetailFragment.ARG_CITY_NAME,
                    getIntent().getStringExtra(CityDetailFragment.ARG_CITY_NAME));
            arguments.putString(CityDetailFragment.ARG_COUNTRY,
                    getIntent().getStringExtra(CityDetailFragment.ARG_COUNTRY));
            arguments.putDouble(CityDetailFragment.ARG_LAT,
                    getIntent().getDoubleExtra(CityDetailFragment.ARG_LAT, 0.0));
            arguments.putDouble(CityDetailFragment.ARG_LONG,
                    getIntent().getDoubleExtra(CityDetailFragment.ARG_LONG, 0.0));

            CityDetailFragment fragment = new CityDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String cityName = getIntent().getStringExtra(CityDetailFragment.ARG_CITY_NAME);
        double lat = getIntent().getDoubleExtra(CityDetailFragment.ARG_LAT, 0.0);
        double lon = getIntent().getDoubleExtra(CityDetailFragment.ARG_LONG, 0.0);

        LatLng city = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions().position(city).title(cityName));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(city));
    }
}
