package com.ariska.submission4.database;

import android.provider.BaseColumns;

public class MovieContract {

    public MovieContract() {}

    public static final class MovieEntry implements BaseColumns{
        public static final String TABLE_NAME = "movielist";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PHOTO = "photo";

    }
}
