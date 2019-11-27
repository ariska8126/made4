package com.ariska.submission4.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ariska.submission4.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {

    private static final String API_KEY = "342d0e3f7d613a0d31608b42a34cbc66";
    private MutableLiveData<ArrayList<Movie>>listMovie = new MutableLiveData<>();

    public void fetchListMovie(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();

        String urlListMovie = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&language=en-US";
        Log.d("API", urlListMovie);

        client.get(urlListMovie, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i=0; i<list.length(); i++){
                        JSONObject movies = list.getJSONObject(i);
                        Movie movie = new Movie();

                        movie.setTitle(movies.getString("title"));
                        movie.setRelease_date(movies.getString("release_date"));
                        movie.setPoster_path(movies.getString("poster_path"));
                        movie.setId(movies.getString("id"));

                        listItems.add(movie);
                    }
                    listMovie.setValue(listItems);
                }catch (Exception e){
                    Log.e("on failure", e.getMessage());
                    listMovie.setValue(listItems);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("in failure", error.getMessage());
                listMovie.setValue(listItems);
            }
        });
    }

    public MutableLiveData<ArrayList<Movie>> getListMovie() {
        return listMovie;
    }

    //fetch detail movie
    private MutableLiveData<Movie> detailMovie = new MutableLiveData<>();

    public void fetchDetail(String id){
        AsyncHttpClient client = new AsyncHttpClient();

        String urlDetailMovie = "https://api.themoviedb.org/3/movie/"+id+"?api_key="+API_KEY+"&language=en-US";
        Log.d("API", urlDetailMovie);
        client.get(urlDetailMovie, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject movieServer = new JSONObject(result);
                    Movie movie = new Movie();
                    movie.setTitle(movieServer.getString("title"));
                    movie.setBackdrop_path(movieServer.getString("backdrop_path"));
                    movie.setRelease_date(movieServer.getString("release_date"));
                    movie.setVote_average(movieServer.getDouble("vote_average"));
                    movie.setOverview(movieServer.getString("overview"));

                    detailMovie.setValue(movie);
                }catch (Exception e){
                    Log.e("on failure", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("on failure", error.getMessage());
            }
        });
    }

    public MutableLiveData<Movie> getDetailMovie() {
        return detailMovie;
    }
}
