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
import com.ariska.submission4.adapter.FavTvshowAdapter;
import com.ariska.submission4.database.TvshowContract;
import com.ariska.submission4.database.TvshowDBHelper;

public class FavTvShowFragment extends Fragment {

    private SQLiteDatabase tvDatabase;
    private FavTvshowAdapter adapter;
    private RecyclerView recyclerView;

    public FavTvShowFragment() {
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
        View view = inflater.inflate(R.layout.fragment_fav_tv_show, container, false);
        recyclerView = view.findViewById(R.id.rvFavTvshow);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupView();
    }

    private void setupView() {
        TvshowDBHelper tvshowDBHelper = new TvshowDBHelper(getContext());
        tvDatabase = tvshowDBHelper.getWritableDatabase();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavTvshowAdapter(getContext(),getAllTvItems());
        recyclerView.setAdapter(adapter);

        adapter.setOnDeleteClickListener(new FavTvshowAdapter.OnDeleteClickListener() {
            @Override
            public void onDelete(String id) {

                tvDatabase.delete(TvshowContract.FavTvshowEntry.TABLE_NAME,
                        TvshowContract.FavTvshowEntry.COLUMN_IMDB+" = "+id, null);
                adapter.swapCursor(getAllTvItems());
            }
        });
    }

    private Cursor getAllTvItems() {
        return tvDatabase.query(TvshowContract.FavTvshowEntry.TABLE_NAME,
                null, null, null, null, null,
                null);
    }
}
