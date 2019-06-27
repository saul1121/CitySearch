package com.test.android.citysearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.android.citysearch.model.City;
import com.test.android.citysearch.model.Location;

import java.util.List;

public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final CityListActivity mParentActivity;
    private final List<City> mCities;
    private final boolean mTwoPane;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            City city = (City) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(CityDetailFragment.ARG_CITY_NAME, city.getName());
                arguments.putString(CityDetailFragment.ARG_COUNTRY, city.getCountry());
                arguments.putDouble(CityDetailFragment.ARG_LAT, city.getLocation().getLat());
                arguments.putDouble(CityDetailFragment.ARG_LONG, city.getLocation().getLon());
                arguments.putBoolean(CityDetailFragment.ARG_2_PANE, true);

                CityDetailFragment fragment = new CityDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, CityDetailActivity.class);
                intent.putExtra(CityDetailFragment.ARG_CITY_NAME, city.getName());
                intent.putExtra(CityDetailFragment.ARG_COUNTRY, city.getCountry());
                intent.putExtra(CityDetailFragment.ARG_LAT, city.getLocation().getLat());
                intent.putExtra(CityDetailFragment.ARG_LONG, city.getLocation().getLon());

                context.startActivity(intent);
            }
        }
    };

    SimpleItemRecyclerViewAdapter(CityListActivity parent, List<City> cities, boolean twoPane) {
        mCities = cities;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mCityName.setText(String.format("%s : %s",
                mCities.get(position).getName(), mCities.get(position).getCountry()));

        Location location = mCities.get(position).getLocation();
        if (mTwoPane) {
            holder.mCityLocation.setText(
                    String.format("Lat: %s, \nLong: %s", location.getLat(), location.getLon()));
        } else {
            holder.mCityLocation.setText(
                    String.format("Lat: %s, Long: %s", location.getLat(), location.getLon()));
        }

        holder.mCityDetails.setOnClickListener(view -> {
            City city = mCities.get(holder.getAdapterPosition());

            Intent intent = new Intent(view.getContext(), CityInfo.class);
            intent.putExtra(CityDetailFragment.ARG_CITY_NAME, city.getName());
            intent.putExtra(CityDetailFragment.ARG_COUNTRY, city.getCountry());
            intent.putExtra(CityDetailFragment.ARG_LAT, city.getLocation().getLat());
            intent.putExtra(CityDetailFragment.ARG_LONG, city.getLocation().getLon());

            view.getContext().startActivity(intent);
        });

        holder.itemView.setTag(mCities.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mCityName;
        final TextView mCityLocation;
        final ImageView mCityDetails;

        ViewHolder(View view) {
            super(view);

            mCityName = view.findViewById(R.id.city_name_cc);
            mCityLocation = view.findViewById(R.id.city_coordinates);
            mCityDetails = view.findViewById(R.id.city_details);
        }
    }
}
