package net.bashayer.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import net.bashayer.popularmovies.network.JSONUtils;
import net.bashayer.popularmovies.network.NetworkUtils;
import net.bashayer.popularmovies.network.model.Movie;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.bashayer.popularmovies.Constants.MOVIE_KEY;
import static net.bashayer.popularmovies.Constants.POPULAR_FILTER;
import static net.bashayer.popularmovies.Constants.TOP_RATED_FILTER;

public class MainActivity extends AppCompatActivity implements OnMovieClickListener {

    private MoviesAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAdapter(); //init adapter with empty list
        getMoviesList(TOP_RATED_FILTER);
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
            getMoviesList(POPULAR_FILTER);
        } else if (id == R.id.action_top_rated) {
            getMoviesList(TOP_RATED_FILTER);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;//why we have to return boolean
    }

    private void initAdapter() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new MoviesAdapter(new ArrayList<Movie>(), this, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getMoviesList(String filter) {
        new GetMoviesTask().execute(filter);
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MOVIE_KEY, movie);
        startActivity(intent);
    }

    public class GetMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... filter) {
            try {
                String response = NetworkUtils.getMoviesListResponse(filter[0]);
                return JSONUtils.parseMoviesJSON(response);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            adapter.updateData(movies);
            //when the list is empty add holder
        }

    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
