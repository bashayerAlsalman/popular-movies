package net.bashayer.popularmovies.data;

import net.bashayer.popularmovies.data.model.MovieModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MoviesDao {

    @Insert
    void insertMovie(MovieModel movie);

    @Delete
    void deleteMovie(MovieModel movieModel);

    @Query("Select * from MovieModel")
    List<MovieModel> getAllMovies();

    @Query("Select * from MovieModel mm where mm.id =:movieId")
    MovieModel getFavoriteMovie(String movieId);
}
