package com.ariska.submission4.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ariska.submission4.R;
import com.ariska.submission4.activity.TvshowDetailActivity;
import com.ariska.submission4.adapter.TvshowAdapter;
import com.ariska.submission4.model.Tvshow;
import com.ariska.submission4.viewModel.TvshowViewModel;

import java.util.ArrayList;

public class TvShowFragment extends Fragment {

    private TvshowViewModel tvshowViewModel;
    private TvshowAdapter tvshowAdapter = new TvshowAdapter();
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        progressBar = view.findViewById(R.id.pbTvshow);
        recyclerView = view.findViewById(R.id.rvTvshow);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setupView();
        tvshowViewModel = new ViewModelProvider(this).get(TvshowViewModel.class);
        observeListTvshowLiveData();
        tvshowViewModel.fetchListTvshow();
        showLoading(true);
    }

    private void observeListTvshowLiveData() {
        tvshowViewModel.getListTvshow().observe(getViewLifecycleOwner(), new Observer<ArrayList<Tvshow>>() {
            @Override
            public void onChanged(ArrayList<Tvshow> tvshows) {
                if (null!= tvshows){
                    Log.d("Tv Show", "tv Show:"+tvshows.size());
                    tvshowAdapter.settData(tvshows);
                }
                showLoading(false);
            }
        });
    }

    private void setupView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(tvshowAdapter);
        tvshowAdapter.setOnclickListener(new TvshowAdapter.OnclickListener() {
            @Override
            public void onClick(Tvshow tvshow) {
                Intent intent = new Intent(getContext(), TvshowDetailActivity.class);
                intent.putExtra(TvshowDetailActivity.EXTRA_TV, tvshow);
                startActivity(intent);
            }
        });
    }

    private void showLoading(Boolean b) {
        int visibility = b ? View.VISIBLE : View.GONE;
        progressBar.setVisibility(visibility);
    }
}
