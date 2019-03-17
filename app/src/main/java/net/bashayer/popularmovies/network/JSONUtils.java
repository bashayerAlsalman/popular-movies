package net.bashayer.popularmovies.network;

import net.bashayer.popularmovies.network.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static net.bashayer.popularmovies.Constants.ID_KEY;
import static net.bashayer.popularmovies.Constants.OVER_VIEW_KEY;
import static net.bashayer.popularmovies.Constants.POSTER_PATH_KEY;
import static net.bashayer.popularmovies.Constants.RELEASE_DATE_KEY;
import static net.bashayer.popularmovies.Constants.RESULTS_KEY;
import static net.bashayer.popularmovies.Constants.TITLE_KEY;
import static net.bashayer.popularmovies.Constants.VOTE_AVERAGE_KEY;

public final class JSONUtils {


    public static List<Movie> parseMoviesJSON(String response) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray(RESULTS_KEY);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject movie = jsonArray.getJSONObject(i);

            movies.add(new Movie(movie.getInt(ID_KEY),
                    movie.getDouble(VOTE_AVERAGE_KEY),
                    movie.getString(POSTER_PATH_KEY),
                    movie.getString(TITLE_KEY),
                    movie.getString(RELEASE_DATE_KEY),
                    movie.getString(OVER_VIEW_KEY)));
        }

        return movies;
    }

}
