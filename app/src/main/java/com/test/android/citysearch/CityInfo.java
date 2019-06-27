package com.test.android.citysearch;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class CityInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_info);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent() != null) {
            ((TextView) findViewById(R.id.city_name)).setText(getIntent().getStringExtra(CityDetailFragment.ARG_CITY_NAME));
            ((TextView) findViewById(R.id.country_name)).setText(getIntent().getStringExtra(CityDetailFragment.ARG_COUNTRY));

            ((TextView) findViewById(R.id.city_latitude)).setText(
                    String.format("%s", getIntent().getDoubleExtra(CityDetailFragment.ARG_LAT, 0.0)));

            ((TextView) findViewById(R.id.city_longitude)).setText(
                    String.format("%s", getIntent().getDoubleExtra(CityDetailFragment.ARG_LONG, 0.0)));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
