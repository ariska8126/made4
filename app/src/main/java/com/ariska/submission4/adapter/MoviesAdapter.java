package com.ariska.submission4.adapter;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ariska.submission4.R;
import com.ariska.submission4.database.MovieDBHelper;
import com.ariska.submission4.model.Movie;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.ariska.submission4.database.MovieContract.MovieEntry.*;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private ArrayList<Movie> mData = new ArrayList<>();
    private OnclickListener onclickListener;

    public void setmData(ArrayList<Movie> items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnclickListener(OnclickListener onclickListener) {
        this.onclickListener = onclickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_cardview, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvRelease;
        private ImageView imgPhoto;
        private Button btnDetail, btnFav;

        //save to SQLite
        SQLiteDatabase movDatabase;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRelease = itemView.findViewById(R.id.tvMovieRelease);
            tvTitle = itemView.findViewById(R.id.tvMovieTitle);
            imgPhoto = itemView.findViewById(R.id.imgMoviePhoto);
            btnDetail = itemView.findViewById(R.id.btnMovieDetail);
            btnFav = itemView.findViewById(R.id.btnMovieAddFav);
        }

        public void bind(final Movie movie) {

            final String posterMovieUrl = "https://image.tmdb.org/t/p/original"+movie.getPoster_path();

            Glide.with(itemView.getContext()).load(posterMovieUrl).into(imgPhoto);

            tvTitle.setText(movie.getTitle());
            tvRelease.setText(movie.getRelease_date());

            final String title = movie.getTitle();
            final String imdb = movie.getId();

            MovieDBHelper movieDBHelper = new MovieDBHelper(itemView.getContext());
            movDatabase = movieDBHelper.getWritableDatabase();

            btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {

                        ContentValues cvMv = new ContentValues();
                        cvMv.put(COLUMN_TITLE, title);
                        cvMv.put(COLUMN_IMDB, imdb);
                        cvMv.put(COLUMN_PHOTO, posterMovieUrl);

                        movDatabase.insert(TABLE_NAME, null,cvMv);
                        Toast.makeText(itemView.getContext(), "Added Favourite", Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        Log.d("Eror: ", e.getMessage());
                    }
                }
            });

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclickListener.onClick(movie);
                }
            });
        }
    }

    public interface OnclickListener{
        void onClick(Movie movie);
    }
}
