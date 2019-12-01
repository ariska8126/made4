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
import com.ariska.submission4.database.TvshowDBHelper;
import com.ariska.submission4.model.Tvshow;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.ariska.submission4.database.TvshowContract.FavTvshowEntry.*;

public class TvshowAdapter extends RecyclerView.Adapter<TvshowAdapter.MyViewHolder> {

    private ArrayList<Tvshow> tData = new ArrayList<>();
    private OnclickListener onclickListener;

    public void settData(ArrayList<Tvshow> items){
        tData.clear();
        tData.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnclickListener(OnclickListener onclickListener) {
        this.onclickListener = onclickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow_cardview, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(tData.get(position));
    }

    @Override
    public int getItemCount() {
        return tData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvRelease;
        private ImageView imgPhoto;
        private Button btnFav, btnDetail;

        //save to SQLite
        SQLiteDatabase mDatabase;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRelease = itemView.findViewById(R.id.tvFavReleaseMov);
            tvTitle = itemView.findViewById(R.id.tvFavTitleMov);
            imgPhoto = itemView.findViewById(R.id.imgFavPhotoMov);
            btnDetail = itemView.findViewById(R.id.btnDetailTv);
            btnFav = itemView.findViewById(R.id.btnFavDelMov);
        }

        public void bind(final Tvshow tvshow) {

            final String posterTvUrl = "https://image.tmdb.org/t/p/original"+tvshow.getPoster_path();

            Glide.with(itemView.getContext()).load(posterTvUrl).into(imgPhoto);

            tvTitle.setText(tvshow.getTitle());
            tvRelease.setText(tvshow.getRelease());

            final String title = tvshow.getTitle();
            final String imdb = tvshow.getId();

            TvshowDBHelper tvshowDBHelper = new TvshowDBHelper(itemView.getContext());
            mDatabase = tvshowDBHelper.getWritableDatabase();

            btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{

                        ContentValues cvTv =new ContentValues();
                        cvTv.put(COLUMN_TITLE, title);
                        cvTv.put(COLUMN_IMDB, imdb);
                        cvTv.put(COLUMN_PHOTO, posterTvUrl);

                        mDatabase.insert(TABLE_NAME, null, cvTv);
                        Toast.makeText(itemView.getContext(), "Added Favourite", Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        Log.d("Eror: ", e.getMessage());
                    }
                }
            });

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclickListener.onClick(tvshow);
                }
            });
        }
    }

    public interface OnclickListener{
        void onClick(Tvshow tvshow);
    }
}
