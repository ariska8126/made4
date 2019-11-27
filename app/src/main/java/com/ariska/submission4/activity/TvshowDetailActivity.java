package com.ariska.submission4.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ariska.submission4.R;
import com.ariska.submission4.model.Tvshow;
import com.ariska.submission4.viewModel.TvshowViewModel;
import com.bumptech.glide.Glide;

public class TvshowDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_tv";

private TextView tvTitle, tvRate, tvRelease, tvOverview;
private ImageView imgBackdrop;
private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);

        getSupportActionBar().hide();

        Tvshow tvshow = getIntent().getParcelableExtra(EXTRA_TV);
        final TvshowViewModel tvshowViewModel;
        initView();

        tvshowViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(TvshowViewModel.class);

        tvshowViewModel.getDetailTvshow().observe(this, new Observer<Tvshow>() {
            @Override
            public void onChanged(Tvshow tvshow) {
                if (tvshow != null){
                    tvTitle.setText(tvshow.getTitle());
                    tvRelease.setText(tvshow.getRelease());

                    String rate = Double.toString(tvshow.getVote_average());
                    tvRate.setText(rate);
                    tvOverview.setText(tvshow.getOverview());
                    String urlImgBackdrop = "https://image.tmdb.org/t/p/original"+tvshow.getBackdrop_path();
                    Glide.with(TvshowDetailActivity.this).load(urlImgBackdrop).into(imgBackdrop);

                    showLoading(false);
                }
            }
        });

        String id = tvshow.getId();
        tvshowViewModel.fetchDetailTv(id);
    }

    private void showLoading(Boolean b) {
        if (b){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void initView() {
        tvOverview = findViewById(R.id.tvOverviewDeTv);
        tvRate = findViewById(R.id.tvRateDeTv);
        tvTitle = findViewById(R.id.tvTitleDeTv);
        tvRelease = findViewById(R.id.tvReleaseDeTv);
        imgBackdrop = findViewById(R.id.imgPhotoDeTv);
        progressBar = findViewById(R.id.pbTvshowDetail);

        showLoading(true);

    }
}
