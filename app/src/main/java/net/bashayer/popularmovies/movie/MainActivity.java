package net.bashayer.popularmovies.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import net.bashayer.popularmovies.data.model.MovieModel;
import net.bashayer.popularmovies.movie.details.DetailsActivity;
import net.bashayer.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

import static net.bashayer.popularmovies.common.Constants.FAVORITE_MOVIES;
import static net.bashayer.popularmovies.common.Constants.MOVIE_KEY;
import static net.bashayer.popularmovies.common.Constants.POPULAR_FILTER;
import static net.bashayer.popularmovies.common.Constants.TOP_RATED_FILTER;

public class MainActivity extends AppCompatActivity implements OnMovieClickListener {

    private MoviesAdapter adapter;
    private RecyclerView recyclerView;
    private List<MovieModel> movies;
    MovieViewModel viewModel;
    private String selectedFilter = TOP_RATED_FILTER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movies = new ArrayList<MovieModel>();
        initAdapter(); //init adapter with empty list
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        getMoviesList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMoviesList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_popular) {
            selectedFilter = POPULAR_FILTER;
            getMoviesList();
        } else if (id == R.id.action_top_rated) {
            selectedFilter = TOP_RATED_FILTER;
            getMoviesList();
        } else if (id == R.id.action_favorite) {
            selectedFilter = FAVORITE_MOVIES;
            getMoviesList();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;//why we have to return boolean
    }

    private void initAdapter() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new MoviesAdapter(movies, this, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getMoviesList() {
        if (selectedFilter.equals(POPULAR_FILTER) || selectedFilter.equals(TOP_RATED_FILTER)) {
            viewModel.getMovies(false, selectedFilter).observe(this, new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(List<MovieModel> movieModels) {
                    movies.clear();
                    movies.addAll(movieModels);
                    adapter.notifyDataSetChanged();
                }
            });
        } else if (selectedFilter.equals(FAVORITE_MOVIES)) {
            viewModel.getMovies(true, selectedFilter).observe(this, new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(List<MovieModel> movieModels) {
                    movies.clear();
                    movies.addAll(movieModels);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onMovieClicked(MovieModel movieModel) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MOVIE_KEY, movieModel);
        startActivity(intent);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
