package net.bashayer.popularmovies.network;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static net.bashayer.popularmovies.Constants.API_KEY;
import static net.bashayer.popularmovies.Constants.MOVIES_URL;
import static net.bashayer.popularmovies.Constants.TOKEN;

public class NetworkUtils {

    public static String getMoviesListResponse(String filter) throws IOException {
        URL url = getURL(filter);

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

    private static URL getURL(String filter) throws MalformedURLException {
        Uri uri = Uri.parse(MOVIES_URL).buildUpon()
                .appendPath(filter)
                .appendQueryParameter(API_KEY, TOKEN)
                .build();
        return new URL(uri.toString());
    }
}
