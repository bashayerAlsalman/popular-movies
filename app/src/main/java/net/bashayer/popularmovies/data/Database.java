package net.bashayer.popularmovies.data;

import android.content.Context;

import net.bashayer.popularmovies.data.model.MovieModel;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import static net.bashayer.popularmovies.common.Constants.DATABASE_NAME;

@androidx.room.Database(entities = MovieModel.class, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract MoviesDao moviesDao();

    public static Database database;

    public static Database getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context, Database.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        }
        return database;
    }
}
