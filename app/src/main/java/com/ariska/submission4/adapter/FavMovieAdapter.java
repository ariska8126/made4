package com.ariska.submission4.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.ariska.submission4.database.MovieContract;
import com.ariska.submission4.database.MovieDBHelper;
import com.bumptech.glide.Glide;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.MovieHolder> {

    private Context movContext;
    private Cursor movCursor;

    SQLiteDatabase movDatabase;

    private OnDeleteMovClickListener onDeleteMovClickListener;

    public FavMovieAdapter(Context context, Cursor cursor) {
        this.movContext = context;
        this.movCursor = cursor;
    }

    public void setOnDeleteMovClickListener(OnDeleteMovClickListener onDeleteMovClickListener) {
        this.onDeleteMovClickListener = onDeleteMovClickListener;
    }

    @NonNull
    @Override
    public FavMovieAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(movContext);
        View view = inflater.inflate(R.layout.item_favmovie_cardview, parent, false);

        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavMovieAdapter.MovieHolder holder, int position) {
        if (!movCursor.moveToPosition(position)){
            return;
        }

        String title = movCursor.getString(movCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
        String photo = movCursor.getString(movCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_PHOTO));
        final long id = movCursor.getLong(movCursor.getColumnIndex(MovieContract.MovieEntry._ID));
        holder.tvTitle.setText(title);
        Glide.with(movContext).load(photo).into(holder.imgFavMov);

        MovieDBHelper movieDBHelper = new MovieDBHelper(movContext);
        movDatabase = movieDBHelper.getWritableDatabase();

        holder.btnDelMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteMovClickListener.onDeleteMov(id);
                Toast.makeText(movContext, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movCursor.getCount();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private Button btnDelMov;
        private ImageView imgFavMov;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvFavTitleMov);
            imgFavMov = itemView.findViewById(R.id.imgFavPhotoMov);
            btnDelMov = itemView.findViewById(R.id.btnFavDelMov);
        }
    }

    public void swapCursor(Cursor newCursor){
        if (movCursor != null){
            movCursor.close();
        }

        movCursor = newCursor;
        if (newCursor != null){
            notifyDataSetChanged();
        }
    }

    public interface OnDeleteMovClickListener{
        void onDeleteMov(long id);
    }
}
