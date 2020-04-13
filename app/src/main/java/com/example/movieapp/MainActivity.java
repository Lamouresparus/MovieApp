package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.movieapp.data.Movie;
import com.example.movieapp.data.MovieRvAdapter;
import com.example.movieapp.utils.MoviesFromJson;
import com.example.movieapp.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private MovieRvAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private TextView mErrorMessageTv;
    private ProgressBar mProgressBar;
    private Movie[] movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mErrorMessageTv = findViewById(R.id.error_message_tv);
        mRecyclerView = findViewById(R.id.rv_movie_cataloge);
        mProgressBar = findViewById(R.id.progress_bar);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);



        mMovieAdapter = new MovieRvAdapter();

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.hasFixedSize();

        mRecyclerView.setAdapter(mMovieAdapter);

        //default movie catalog will be in sort order of popular
        loadMovieData(NetworkUtils.POPULAR);




    }

    @SuppressLint("StaticFieldLeak")
    public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected Movie[] doInBackground(String... strings) {

            String param = strings[0];

            URL movieRequest = NetworkUtils.buildUrl(param);

            try {
                String jsonMovieResponse = NetworkUtils.httpUrlConnection(movieRequest);

                Movie[] movies = MoviesFromJson.getMoviesFromJson(MainActivity.this, jsonMovieResponse);

                assert movies != null;
                Log.v(TAG, movies[3].getmMovieDescription());

                return movies;


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            super.onPostExecute(movies);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (movies != null) {
                movieList = movies;
                mMovieAdapter.setMovieData(movieList);
            }
            else showErrorMessage();
        }
    }

    public void loadMovieData(String sort) {

        showMovieCatalog();

        new FetchMoviesTask().execute(sort);
    }

    public void showMovieCatalog() {
        mErrorMessageTv.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageTv.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.sort_by_rating) {

            mMovieAdapter.setMovieData(null);

            loadMovieData(NetworkUtils.RATING);
            return true;
        }
        if (itemId == R.id.sort_by_popular) {

            mMovieAdapter.setMovieData(null);

            loadMovieData(NetworkUtils.POPULAR);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
