package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.data.Movie;
import com.example.movieapp.data.MovieRvAdapter;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class DetailActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mMovieTitle;
    private TextView mMovieRating;
    private TextView mMovieDescription;
    private TextView mReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageView = findViewById(R.id.movie_image);
        mMovieDescription = findViewById(R.id.movie_description);
        mMovieTitle = findViewById(R.id.movie_title);
        mReleaseDate = findViewById(R.id.movie_release_date);
        mMovieRating = findViewById(R.id.movie_rating);

        Intent intent = getIntent();

        Movie movieDetails = (Movie) intent.getSerializableExtra(MovieRvAdapter.KEY_MOVIE);

        assert movieDetails != null;
        mMovieTitle.setText(movieDetails.getmMovieTitle());
        mMovieRating.setText(String.valueOf(movieDetails.getmRating()));
        mMovieDescription.setText(movieDetails.getmMovieDescription());
        mReleaseDate.setText(movieDetails.getmReleaseDate());

        Picasso.get().load(movieDetails.getmImageUrl()).into(mImageView);


    }
}
