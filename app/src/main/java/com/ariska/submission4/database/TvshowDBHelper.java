package com.ariska.submission4.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ariska.submission4.database.TvshowContract.*;

public class TvshowDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "tv.db";
    public static final int DATABASE_VERSION = 1;

    public TvshowDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE_TVSHOW = "CREATE TABLE " +
                FavTvshowEntry.TABLE_NAME+" ("+
                FavTvshowEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                FavTvshowEntry.COLUMN_TITLE+" TEXT NOT NULL, "+
                FavTvshowEntry.COLUMN_PHOTO+" TEXT NOT NULL "+")";

        db.execSQL(SQL_CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbtvshow, int oldVersion, int newVersion1) {
        dbtvshow.execSQL("DROP TABLE IF EXISTS " +FavTvshowEntry.TABLE_NAME);
        onCreate(dbtvshow);
    }
}