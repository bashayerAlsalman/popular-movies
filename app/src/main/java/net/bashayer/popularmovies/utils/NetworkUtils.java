package net.bashayer.popularmovies.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static net.bashayer.popularmovies.common.Constants.API_KEY;
import static net.bashayer.popularmovies.common.Constants.MOVIES_URL;
import static net.bashayer.popularmovies.common.Constants.REVIEWS;
import static net.bashayer.popularmovies.common.Constants.TOKEN;
import static net.bashayer.popularmovies.common.Constants.VIDEOS;

public final class NetworkUtils {

    public static String getMoviesResponse(String filter) throws IOException {
        URL url = getMoviesURL(filter);
        return getResponse(url);
    }

    public static String getMovieReviews(String movieId) throws IOException {
        URL url = getReviewsURL(movieId, REVIEWS);
        return getResponse(url);
    }

    public static String getMovieRTrailer(String movieId) throws IOException {
        URL url = getTrailerURL(movieId, VIDEOS);
        return getResponse(url);
    }

    private static String getResponse(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasNext = scanner.hasNext();
            if (hasNext) {
                return scanner.next();
            } else {
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }
    }

    private static URL getReviewsURL(String movieId, String path) throws MalformedURLException {
        Uri uri = Uri.parse(MOVIES_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(path)
                .appendQueryParameter(API_KEY, TOKEN)
                .build();
        return new URL(uri.toString());
    }

    private static URL getMoviesURL(String filter) throws MalformedURLException {
        Uri uri = Uri.parse(MOVIES_URL).buildUpon()
                .appendPath(filter)
                .appendQueryParameter(API_KEY, TOKEN)
                .build();
        return new URL(uri.toString());
    }


    public static URL getTrailerURL(String movieId, String path) throws MalformedURLException {
        Uri uri = Uri.parse(MOVIES_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(path)
                .appendQueryParameter(API_KEY, TOKEN)
                .build();
        return new URL(uri.toString());
    }
}
