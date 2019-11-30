package com.ariska.submission4.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MovieDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie.db";
    public static final int DATABASE_VERSION = 1;

    public MovieDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dbMovie) {
        final String SQL_CREATE_TABLE_MOVIE = "CREATE TABLE "+
                MovieContract.MovieEntry.TABLE_NAME+" ("+
                MovieContract.MovieEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                MovieContract.MovieEntry.COLUMN_TITLE+" TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_PHOTO+" TEXT NOT NULL "+")";

        dbMovie.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbMovie, int i, int i1) {
        dbMovie.execSQL("DROP TABLE IF EXISTS "+ MovieContract.MovieEntry.TABLE_NAME);
        onCreate(dbMovie);
    }
}
