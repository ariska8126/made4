package com.ariska.submission4.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ariska.submission4.R;
import com.ariska.submission4.adapter.FavMovieAdapter;
import com.ariska.submission4.database.MovieContract;
import com.ariska.submission4.database.MovieDBHelper;

public class FavMovieFragment extends Fragment {

    private SQLiteDatabase movDatabase;
    private FavMovieAdapter movAdapter;
    private RecyclerView rvMov;

    public FavMovieFragment() {
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
        View view = inflater.inflate(R.layout.fragment_fav_movie, container, false);
        rvMov = view.findViewById(R.id.rvFavMovie);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
    }

    private void setupView() {
        MovieDBHelper movieDBHelper = new MovieDBHelper(getContext());
        movDatabase = movieDBHelper.getWritableDatabase();
        rvMov.setLayoutManager(new LinearLayoutManager(getContext()));
        movAdapter = new FavMovieAdapter(getContext(), getAllMovieItems());
        rvMov.setAdapter(movAdapter);

        movAdapter.setOnDeleteMovClickListener(new FavMovieAdapter.OnDeleteMovClickListener() {
            @Override
            public void onDeleteMov(String id) {

                movDatabase.delete(MovieContract.MovieEntry.TABLE_NAME,
                        MovieContract.MovieEntry.COLUMN_IMDB+" = "+id, null);
                movAdapter.swapCursor(getAllMovieItems());
            }
        });

    }

    private Cursor getAllMovieItems() {
        return movDatabase.query(MovieContract.MovieEntry.TABLE_NAME,
                null,null,null,null,null,null);
    }
}
