package net.bashayer.popularmovies.movie;

import android.app.Application;
import android.os.AsyncTask;

import net.bashayer.popularmovies.data.Database;
import net.bashayer.popularmovies.data.model.MovieModel;
import net.bashayer.popularmovies.utils.JSONUtils;
import net.bashayer.popularmovies.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MovieViewModel extends AndroidViewModel {

    Database database;
    public MutableLiveData<List<MovieModel>> mutableLiveData;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        database = Database.getDatabase(application);
        mutableLiveData = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(boolean favoriteMovies, String filter) {
        if (favoriteMovies) {
            new FavoriteMoviesAsyncTask().execute();
            return mutableLiveData;
        } else {
            new MoviesFromServerAsyncTask().execute(filter);
            return mutableLiveData;
        }
    }

    public class MoviesFromServerAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... filter) {
            String response;
            List<MovieModel> movies = null;
            try {
                response = NetworkUtils.getMoviesResponse(filter[0]);
                movies = JSONUtils.parseMoviesJSON(response);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mutableLiveData.postValue(movies);
            return null;
        }

    }

    public class FavoriteMoviesAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            mutableLiveData.postValue(database.moviesDao().getAllMovies());
            return null;
        }

    }
}
