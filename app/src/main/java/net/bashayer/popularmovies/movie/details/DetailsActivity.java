package net.bashayer.popularmovies.movie.details;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.bashayer.popularmovies.R;
import net.bashayer.popularmovies.data.FavoriteMoviesWorker;
import net.bashayer.popularmovies.data.model.MovieModel;
import net.bashayer.popularmovies.data.model.ReviewModel;
import net.bashayer.popularmovies.movie.MovieCallback;
import net.bashayer.popularmovies.utils.JSONUtils;
import net.bashayer.popularmovies.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static net.bashayer.popularmovies.common.Constants.IMAGES_BASE_URL;
import static net.bashayer.popularmovies.common.Constants.MOVIES_URL;
import static net.bashayer.popularmovies.common.Constants.MOVIE_KEY;
import static net.bashayer.popularmovies.common.Constants.REVIEWS;
import static net.bashayer.popularmovies.common.Constants.VIDEOS;
import static net.bashayer.popularmovies.common.Constants.YOUTUBE;

public class DetailsActivity extends AppCompatActivity implements MovieCallback {

    private Toolbar toolbar;

    private ImageView posterImageView;
    private TextView titleTextView;
    private TextView releaseDateTextView;
    private TextView averageVoteTextView;
    private TextView overviewTextView;

    private ImageView favImageView;
    private LinearLayout trailersLayout;
    private TextView reviewsTextView;

    private MovieModel movieModel;
    private boolean isFavorite;

    private FavoriteMoviesWorker worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initViews();
        initToolbar();
        worker = new FavoriteMoviesWorker(this, this);

        movieModel = (MovieModel) getIntent().getSerializableExtra(MOVIE_KEY);
        worker.isFavoriteMovie(movieModel);
        bindMovie(movieModel);
        initListeners();
        new TrailerMoviesAsyncTask().execute(movieModel.getId().toString());
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.tool_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        posterImageView = findViewById(R.id.movie_poster);
        titleTextView = findViewById(R.id.title);
        releaseDateTextView = findViewById(R.id.release_date);
        averageVoteTextView = findViewById(R.id.vote_average);
        overviewTextView = findViewById(R.id.overview);
        favImageView = findViewById(R.id.star);
        reviewsTextView = findViewById(R.id.reviews);
        trailersLayout = findViewById(R.id.trailers);
    }

    private void bindMovie(MovieModel movieModel) {
        String image = IMAGES_BASE_URL + movieModel.getPosterPath();
        Glide.with(this)
                .load(image)
                .placeholder(R.drawable.ic_local_movies_black_24dp)
                .into(posterImageView);

        titleTextView.setText(movieModel.getTitle());
        releaseDateTextView.setText(movieModel.getReleaseDate());
        averageVoteTextView.setText(movieModel.getVoteAverage().toString());
        overviewTextView.setText(movieModel.getOverView());
    }

    private void initListeners() {
        favImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite) {
                    worker.deleteFavoriteMovie(movieModel);
                    favImageView.setImageResource(R.drawable.ic_star_border_black_24dp);
                } else {
                    worker.addFavoriteMovie(movieModel);
                    favImageView.setImageResource(R.drawable.ic_star_yellow_24dp);
                }
                isFavorite = !isFavorite;
            }
        });

        reviewsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ReviewsMoviesAsyncTask().execute();
            }

        });

    }

    @Override
    public void setIFMovieFavorite(boolean isMovieFavorite) {
        isFavorite = isMovieFavorite;
        if (isFavorite) {
            favImageView.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
    }

    public void openDialog(ReviewModel[] reviewModels) {
        Intent intent = new Intent(this, ReviewsActivity.class);

        StringBuilder reviews = new StringBuilder();

        if (reviewModels != null && reviewModels.length > 0) {
            for (int i = 0; i < reviewModels.length; i++) {
                reviews.append(reviewModels[i].toString());
            }
        }

        intent.putExtra(REVIEWS, reviews.toString());
        startActivity(intent);
    }

    public class ReviewsMoviesAsyncTask extends AsyncTask<Void, Void, ReviewModel[]> {

        @Override
        protected ReviewModel[] doInBackground(Void... voids) {
            ReviewModel[] reviewModels = null;
            try {
                String response = NetworkUtils.getMovieReviews(movieModel.getId().toString());
                reviewModels = JSONUtils.parseReviewsJSON(response);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return reviewModels;
        }

        @Override
        protected void onPostExecute(ReviewModel[] models) {
            openDialog(models);
        }
    }

    public class TrailerMoviesAsyncTask extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... movieId) {
            List<String> trailers = null;
            try {
                String response = NetworkUtils.getMovieRTrailer(movieModel.getId().toString());
                trailers = JSONUtils.parseTrailerJSON(response);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return trailers;
        }

        @Override
        protected void onPostExecute(List<String> keys) {
            displayTrailer(keys);
        }
    }

    private void displayTrailer(final List<String> keys) {
        for (int i = 0; i < keys.size(); i++) {
            final int y = i;
            View view = LayoutInflater.from(this).inflate(R.layout.item_trailer, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openTrailer(keys.get(y));
                }
            });
            trailersLayout.addView(view);
        }
    }

    private void openTrailer(String key) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE + key));

        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
