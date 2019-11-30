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
import com.ariska.submission4.database.TvshowContract;
import com.ariska.submission4.database.TvshowDBHelper;
import com.bumptech.glide.Glide;

public class FavTvshowAdapter extends RecyclerView.Adapter<FavTvshowAdapter.ViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    SQLiteDatabase mDdatabase;

    //ondelete
    private OnDeleteClickListener onDeleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public FavTvshowAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvRelease;
        private ImageView imgPhotoTvFav;
        Button btnDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRelease = itemView.findViewById(R.id.tvFavTitleMov);
            tvTitle = itemView.findViewById(R.id.tvFavTitleMov);
            imgPhotoTvFav = itemView.findViewById(R.id.imgFavPhotoMov);
            btnDel = itemView.findViewById(R.id.btnFavDelMov);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_favtvshow_cardview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }

        String title = mCursor.getString(mCursor.getColumnIndex(TvshowContract.FavTvshowEntry.COLUMN_TITLE));
        String poster = mCursor.getString(mCursor.getColumnIndex(TvshowContract.FavTvshowEntry.COLUMN_PHOTO));
        final long id = mCursor.getLong(mCursor.getColumnIndex(TvshowContract.FavTvshowEntry._ID));
        holder.tvTitle.setText(title);
        Glide.with(mContext).load(poster).into(holder.imgPhotoTvFav);
        //hapus
        TvshowDBHelper tvshowDBHelper = new TvshowDBHelper(mContext);
        mDdatabase = tvshowDBHelper.getWritableDatabase();

        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteClickListener.onDelete(id);
                Toast.makeText(mContext, "Deleted "+id, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if (mCursor != null){
            mCursor.close();
        }

        mCursor = newCursor;
        if (newCursor != null){
            notifyDataSetChanged();
        }
    }
    public interface OnDeleteClickListener{
        void onDelete(long id);
    }

}
