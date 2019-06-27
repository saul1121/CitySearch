package com.test.android.citysearch;

import com.test.android.citysearch.model.City;

import java.util.List;

public interface Presenter {

    void onSuccess(List<City> cities);

    void onFail();
}
