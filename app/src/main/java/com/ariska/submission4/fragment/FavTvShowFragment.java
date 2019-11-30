package com.ariska.submission4.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ariska.submission4.R;
import com.ariska.submission4.adapter.FavTvshowAdapter;
import com.ariska.submission4.database.TvshowContract;
import com.ariska.submission4.database.TvshowDBHelper;


public class FavTvShowFragment extends Fragment {

    private SQLiteDatabase tvDatabase;

    private FavTvshowAdapter adapter;

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

        TvshowDBHelper tvshowDBHelper = new TvshowDBHelper(getContext());
        tvDatabase = tvshowDBHelper.getWritableDatabase();

        RecyclerView recyclerView = view.findViewById(R.id.rvFavTvshow);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavTvshowAdapter(getContext(), getAllTvItems());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private Cursor getAllTvItems() {
        return tvDatabase.query(TvshowContract.FavTvshowEntry.TABLE_NAME,
                null, null, null, null, null,
                null);
    }


}
