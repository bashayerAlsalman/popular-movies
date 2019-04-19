package net.bashayer.popularmovies.utils;

import net.bashayer.popularmovies.data.model.MovieModel;
import net.bashayer.popularmovies.data.model.ReviewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static net.bashayer.popularmovies.common.Constants.AUTHOR_KEY;
import static net.bashayer.popularmovies.common.Constants.CONTENT_KEY;
import static net.bashayer.popularmovies.common.Constants.ID_KEY;
import static net.bashayer.popularmovies.common.Constants.KEY;
import static net.bashayer.popularmovies.common.Constants.OVER_VIEW_KEY;
import static net.bashayer.popularmovies.common.Constants.POSTER_PATH_KEY;
import static net.bashayer.popularmovies.common.Constants.RELEASE_DATE_KEY;
import static net.bashayer.popularmovies.common.Constants.RESULTS_KEY;
import static net.bashayer.popularmovies.common.Constants.TITLE_KEY;
import static net.bashayer.popularmovies.common.Constants.VOTE_AVERAGE_KEY;

public final class JSONUtils {


    public static ReviewModel[] parseReviewsJSON(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray(RESULTS_KEY);

        ReviewModel[] reviewModels = new ReviewModel[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject review = jsonArray.getJSONObject(i);
            reviewModels[i] = new ReviewModel(review.getString(CONTENT_KEY), review.getString(AUTHOR_KEY));
        }

        return reviewModels;
    }

    public static List<String> parseTrailerJSON(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray(RESULTS_KEY);

        List<String> trailers = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject review = jsonArray.getJSONObject(i);
            trailers.add(review.getString(KEY));
        }

        return trailers;
    }

    public static List<MovieModel> parseMoviesJSON(String response) throws JSONException {
        List<MovieModel> movieModels = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray(RESULTS_KEY);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject movie = jsonArray.getJSONObject(i);

            movieModels.add(new MovieModel(movie.getInt(ID_KEY),
                    movie.getDouble(VOTE_AVERAGE_KEY),
                    movie.getString(POSTER_PATH_KEY),
                    movie.getString(TITLE_KEY),
                    movie.getString(RELEASE_DATE_KEY),
                    movie.getString(OVER_VIEW_KEY)));
        }

        return movieModels;
    }

}
