package com.test.android.citysearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.test.android.citysearch.model.AppContent;
import com.test.android.citysearch.model.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CityDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CityListActivity extends AppCompatActivity implements ProgressView {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ProgressBar progressBar;
    private List<City> originalCityList = new ArrayList<>();
    private List<City> searchedCityList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SimpleItemRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        progressBar = findViewById(R.id.progressBar);
        EditText searchBox = findViewById(R.id.search_city);
        searchBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchKey = editable.toString();
                if (searchKey.length() > 0) {
                    searchedCityList = originalCityList.stream()
                            .filter(p -> p.getName().startsWith(searchKey)).collect(Collectors.toList());
                } else {
                    searchedCityList = originalCityList;
                }

                // Sort list before display
                Collections.sort(searchedCityList, (o1, o2) -> o1.getName().compareTo(o2.getName()));

                adapter = new SimpleItemRecyclerViewAdapter(CityListActivity.this, searchedCityList, mTwoPane);
                if (recyclerView == null) {
                    recyclerView = findViewById(R.id.item_list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(CityListActivity.this,
                            LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                }
                recyclerView.setAdapter(adapter);
            }
        });

        AppContent ap = new AppContent(CityListActivity.this, CityListActivity.this);
        ap.getCityInfo();
    }

    public void showListOfCities(List<City> cities) {
        originalCityList = cities;
        searchedCityList = originalCityList;

        recyclerView = findViewById(R.id.item_list);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Sort list before display
        Collections.sort(searchedCityList, (o1, o2) -> o1.getName().compareTo(o2.getName()));

        adapter = new SimpleItemRecyclerViewAdapter(this, searchedCityList, mTwoPane);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        findViewById(R.id.frameLayout).setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        findViewById(R.id.frameLayout).setVisibility(View.VISIBLE);
    }
}
