package com.example.movieapp.data;

import java.io.Serializable;

public class Movie implements Serializable {

    private  String mImageUrl;
    private String mMovieTitle;
    private String mMovieDescription;
    private String mReleaseDate;
    private double mRating;

    public Movie(String mImageUrl, String movieTitle, String movieDescription, String releaseDate, double rating) {
        this.mImageUrl = mImageUrl;
        this.mMovieTitle = movieTitle;
        this.mMovieDescription = movieDescription;
        this.mReleaseDate = releaseDate;
        this.mRating = rating;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmMovieTitle() {
        return mMovieTitle;
    }

    public String getmMovieDescription() {
        return mMovieDescription;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public double getmRating() {
        return mRating;
    }
}

