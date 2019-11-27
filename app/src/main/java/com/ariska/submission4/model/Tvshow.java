package com.ariska.submission4.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;

public class Tvshow implements Parcelable {

    private String title;
    private String overview;
    private String release;
    private String poster_path;
    private String backdrop_path;
    private String id;
    private Double vote_average;

    public Tvshow() {
    }

    protected Tvshow(Parcel in) {
        title = in.readString();
        overview = in.readString();
        release = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        id = in.readString();
        if (in.readByte() == 0) {
            vote_average = null;
        } else {
            vote_average = in.readDouble();
        }
    }

    public static final Creator<Tvshow> CREATOR = new Creator<Tvshow>() {
        @Override
        public Tvshow createFromParcel(Parcel in) {
            return new Tvshow(in);
        }

        @Override
        public Tvshow[] newArray(int size) {
            return new Tvshow[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(release);
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeString(id);
        if (vote_average == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(vote_average);
        }
    }
}
