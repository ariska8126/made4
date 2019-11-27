package com.ariska.submission4.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ariska.submission4.model.Tvshow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvshowViewModel extends ViewModel {

    private static final String API_KEY = "342d0e3f7d613a0d31608b42a34cbc66";

    //list tv
    private MutableLiveData<ArrayList<Tvshow>> listTvshow = new MutableLiveData<>();

    public void fetchListTvshow() {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Tvshow> listItems = new ArrayList<>();

        String urlListTv = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
        Log.d("API", urlListTv);

        client.get(urlListTv, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvShows = list.getJSONObject(i);
                        Tvshow tvshow = new Tvshow();

                        tvshow.setTitle(tvShows.getString("name"));
                        tvshow.setId(tvShows.getString("id"));
                        tvshow.setPoster_path(tvShows.getString("poster_path"));
                        tvshow.setRelease(tvShows.getString("first_air_date"));

                        listItems.add(tvshow);
                    }
                    listTvshow.setValue(listItems);

                } catch (Exception e) {
                    Log.e("on failure", e.getMessage());
                    listTvshow.setValue(listItems);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("on failure", error.getMessage());
                listTvshow.setValue(listItems);
            }
        });
    }

    public MutableLiveData<ArrayList<Tvshow>> getListTvshow() {
        return listTvshow;
    }

    //detail tvShow
    private MutableLiveData<Tvshow> detailTvshow = new MutableLiveData<>();

    public void fetchDetailTv(String id) {
        AsyncHttpClient client = new AsyncHttpClient();

        String urlDetailTvshow = "https://api.themoviedb.org/3/tv/" + id + "?api_key=" + API_KEY + "&language=en-US";
        Log.d("API", urlDetailTvshow);
        client.get(urlDetailTvshow, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject tvServer = new JSONObject(result);
                    Tvshow tvshow = new Tvshow();
                    tvshow.setRelease(tvServer.getString("first_air_date"));
                    tvshow.setTitle(tvServer.getString("name"));
                    tvshow.setBackdrop_path(tvServer.getString("backdrop_path"));
                    tvshow.setVote_average(tvServer.getDouble("vote_average"));
                    tvshow.setOverview(tvServer.getString("overview"));

                    detailTvshow.setValue(tvshow);
                } catch (Exception e) {
                    Log.e("On Failure", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("on failure", error.getMessage());
            }
        });
    }

    public MutableLiveData<Tvshow> getDetailTvshow() {
        return detailTvshow;
    }
}
