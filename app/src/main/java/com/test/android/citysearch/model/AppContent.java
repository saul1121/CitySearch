package com.test.android.citysearch.model;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.test.android.citysearch.CityListActivity;
import com.test.android.citysearch.Presenter;
import com.test.android.citysearch.ProgressView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class AppContent extends AsyncTask<String, Void, List<City>> implements Presenter {

    private final WeakReference<CityListActivity> context;
    private final WeakReference<ProgressView> progressView;
    private final String TAG = AppContent.class.getSimpleName();

    public AppContent(ProgressView pView, CityListActivity context) {
        this.progressView = new WeakReference<>(pView);
        this.context = new WeakReference<>(context);
    }

    public void getCityInfo() {
        progressView.get().showProgress();
        execute("");
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private String getCityInfoFromAssets() {
        if (context.get() != null) {
            try {
                AssetManager manager = context.get().getAssets();
                InputStream file = manager.open("cities.json");
                byte[] formArray = new byte[file.available()];
                file.read(formArray);
                file.close();
                return new String(formArray);
            } catch (IOException ex) {
                Log.e(TAG, ex.getLocalizedMessage(), ex);
            }
        }

        return null;
    }

    @Override
    public void onSuccess(List<City> cities) {
        progressView.get().hideProgress();
        context.get().showListOfCities(cities);
    }

    @Override
    public void onFail() {
        progressView.get().hideProgress();
    }

    @Override
    protected List<City> doInBackground(String... strings) {
        String cityInfoJson = "{\"cities\":" + getCityInfoFromAssets() + "}";
        if (!cityInfoJson.isEmpty()) {
            CityData cityData = new Gson().fromJson(cityInfoJson, CityData.class);
            return cityData.getCities();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<City> cities) {
        if (cities != null) {
            onSuccess(cities);
        } else {
            onFail();
        }
    }
}
