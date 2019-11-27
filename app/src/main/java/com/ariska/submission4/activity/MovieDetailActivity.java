package com.ariska.submission4.activity;

import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ariska.submission4.R;
import com.ariska.submission4.adapter.MoviesAdapter;
import com.ariska.submission4.model.Movie;
import com.ariska.submission4.viewModel.MovieViewModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    private TextView tvTitle, tvRate, tvRelease, tvOverview;
    private ImageView imgBackdrop;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().hide();

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        final MovieViewModel movieViewModel;
        initView();

        movieViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);

        movieViewModel.getDetailMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                if (movie != null){
                    tvTitle.setText(movie.getTitle());
                    tvRelease.setText(movie.getRelease_date());

                    String rate = Double.toString(movie.getVote_average());
                    tvRate.setText(rate);
                    tvOverview.setText(movie.getOverview());
                    String urlImgBackdrop = "https://image.tmdb.org/t/p/original"+movie.getBackdrop_path();
                    Glide.with(MovieDetailActivity.this).load(urlImgBackdrop).into(imgBackdrop);

                    showLoading(false);
                }
            }
        });

        String id = movie.getId();
        movieViewModel.fetchDetail(id);
    }

    private void showLoading(Boolean b) {
        if (b){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void initView() {
        tvOverview = findViewById(R.id.tvOverviewMovieDetail);
        tvRate = findViewById(R.id.tvRateMovieDetail);
        tvRelease = findViewById(R.id.tvReleaseMovieDetail);
        tvTitle = findViewById(R.id.tvTitleMovieDetail);
        imgBackdrop = findViewById(R.id.imgBackdropMovieDetail);
        progressBar = findViewById(R.id.pbMovieDetail);

        showLoading(true);

    }
}
