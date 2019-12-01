package com.ariska.submission4.database;

import android.provider.BaseColumns;

public class TvshowContract {

    private TvshowContract() {}

    public static final class FavTvshowEntry implements BaseColumns{
        public static final String TABLE_NAME = "tvlist";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMDB = "imdb";
        public static final String COLUMN_PHOTO = "photo";
    }

}
