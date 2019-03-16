package net.bashayer.popularmovies;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.bashayer.popularmovies.network.model.Movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static net.bashayer.popularmovies.Constants.IMAGES_BASE_URL;
import static net.bashayer.popularmovies.Constants.MOVIE_KEY;

public class DetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ImageView posterImageView;
    private TextView titleTextView;
    private TextView releaseDateTextView;
    private TextView averageVoteTextView;
    private TextView overviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initViews();
        initToolbar();
        Movie movie = (Movie) getIntent().getSerializableExtra(MOVIE_KEY);
        bindMovie(movie);
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
    }

    private void bindMovie(Movie movie) {
        String image = IMAGES_BASE_URL + movie.getPosterPath();
        Glide.with(this)
                .load(image)
                .placeholder(R.drawable.ic_local_movies_black_24dp)
                .into(posterImageView);

        titleTextView.setText(movie.getTitle());
        releaseDateTextView.setText(movie.getReleaseDate());
        averageVoteTextView.setText(movie.getVoteAverage().toString());
        overviewTextView.setText(movie.getOverView());
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
