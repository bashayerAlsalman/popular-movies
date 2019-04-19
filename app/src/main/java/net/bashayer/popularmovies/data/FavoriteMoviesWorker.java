package net.bashayer.popularmovies.data;

import android.content.Context;
import android.os.AsyncTask;

import net.bashayer.popularmovies.data.model.MovieModel;
import net.bashayer.popularmovies.movie.MovieCallback;

public class FavoriteMoviesWorker {

    private boolean isFavorite = true;
    private Database database;
    private MovieCallback callback;

    public FavoriteMoviesWorker(Context context, MovieCallback callback) {
        database = Database.getDatabase(context);
        this.callback = callback;
    }

    public void addFavoriteMovie(MovieModel movieModel) {
        isFavorite = true;
        new FavoriteMoviesAsyncTask().execute(movieModel);
    }

    public void deleteFavoriteMovie(MovieModel movieModel) {
        isFavorite = false;
        new FavoriteMoviesAsyncTask().execute(movieModel);
    }

    public void isFavoriteMovie(MovieModel movieModel) {
        new IsFavoriteMoviesAsyncTask().execute(movieModel);
    }

    private class IsFavoriteMoviesAsyncTask extends AsyncTask<MovieModel, Void, MovieModel> {

        @Override
        protected MovieModel doInBackground(MovieModel... movieModel) {
            MovieModel movieModel1 = database.moviesDao().getFavoriteMovie(movieModel[0].getId().toString());
            return movieModel1;
        }

        @Override
        protected void onPostExecute(MovieModel movieModel) {
//            super.onPostExecute(movieModel);
            callback.setIFMovieFavorite(movieModel != null ? true : false);
        }
    }

    private class FavoriteMoviesAsyncTask extends AsyncTask<MovieModel, Void, Void> {

        @Override
        protected Void doInBackground(MovieModel... movieModel) {
            if (isFavorite) {
                database.moviesDao().insertMovie(movieModel[0]);
            } else {
                database.moviesDao().deleteMovie(movieModel[0]);
            }
            return null;
        }
    }
}
