package net.bashayer.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.bashayer.popularmovies.network.model.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static net.bashayer.popularmovies.Constants.IMAGES_BASE_URL;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> movies;
    private Context context;
    private OnMovieClickListener onMovieClickListener;

    public MoviesAdapter(List<Movie> movies, Context context, OnMovieClickListener onMovieClickListener) {
        this.movies = movies;
        this.context = context;
        this.onMovieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        holder.bind(movie);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMovieClickListener.onMovieClicked(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void updateData(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_poster);
        }

        public void bind(Movie movie) {
            String image = IMAGES_BASE_URL + movie.getPosterPath();
            Glide.with(context)
                    .load(image)
                    .placeholder(R.drawable.ic_local_movies_black_24dp)
                    .into(imageView);
        }

    }
}
