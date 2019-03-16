package net.bashayer.popularmovies;

import net.bashayer.popularmovies.network.model.Movie;

public interface OnMovieClickListener {
    void onMovieClicked(Movie movie);
}
