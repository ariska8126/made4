package com.ariska.submission4.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ariska.submission4.R;
import com.ariska.submission4.activity.MovieDetailActivity;
import com.ariska.submission4.adapter.MoviesAdapter;
import com.ariska.submission4.model.Movie;
import com.ariska.submission4.viewModel.MovieViewModel;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private MovieViewModel movieViewModel;
    private MoviesAdapter moviesAdapter = new MoviesAdapter();
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    public MovieFragment() {
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
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        progressBar = view.findViewById(R.id.pbMovie);
        recyclerView = view.findViewById(R.id.rvMovie);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupView();
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        observeListMovieLiveData();
        movieViewModel.fetchListMovie();
        showLoading(true);
    }

    private void setupView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(moviesAdapter);
        moviesAdapter.setOnclickListener(new MoviesAdapter.OnclickListener() {
            @Override
            public void onClick(Movie movie) {
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                startActivity(intent);
            }
        });
    }

    private void showLoading(Boolean b) {
        int visibility = b ? View.VISIBLE : View.GONE;
        progressBar.setVisibility(visibility);
    }

    private void observeListMovieLiveData() {
        movieViewModel.getListMovie().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if (null != movies) {
                    Log.d("Movie", "movies: " + movies.size());
                    moviesAdapter.setmData(movies);
                }
                showLoading(false);
            }
        });
    }
}
