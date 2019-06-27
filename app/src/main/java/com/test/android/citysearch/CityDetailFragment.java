package com.test.android.citysearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link CityListActivity}
 * in two-pane mode (on tablets) or a {@link CityDetailActivity}
 * on handsets.
 */
public class CityDetailFragment extends Fragment implements OnMapReadyCallback {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_CITY_NAME = "cityName";
    public static final String ARG_COUNTRY = "country";
    public static final String ARG_LAT = "latitude";
    public static final String ARG_LONG = "longitude";
    public static final String ARG_2_PANE = "tablet";

    private String cityName;
    private double latitude;
    private double longitude;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CityDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            cityName = getArguments().getString(ARG_CITY_NAME);
            latitude = getArguments().getDouble(ARG_LAT);
            longitude = getArguments().getDouble(ARG_LONG);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.city_detail, container, false);

        if (getActivity() != null && getArguments() != null && getArguments().getBoolean(ARG_2_PANE)) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, supportMapFragment).commit();
            supportMapFragment.getMapAsync(this);
        }

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng city = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(city).title(cityName));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(city));
    }
}
