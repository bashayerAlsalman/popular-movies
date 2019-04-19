package net.bashayer.popularmovies.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.bashayer.popularmovies.R;
import net.bashayer.popularmovies.data.model.MovieModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static net.bashayer.popularmovies.common.Constants.IMAGES_BASE_URL;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<MovieModel> movieModels;
    private Context context;
    private OnMovieClickListener onMovieClickListener;

    public MoviesAdapter(List<MovieModel> movieModels, Context context, OnMovieClickListener onMovieClickListener) {
        this.movieModels = movieModels;
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
        final MovieModel movieModel = movieModels.get(position);
        holder.bind(movieModel);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMovieClickListener.onMovieClicked(movieModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    public void updateData(List<MovieModel> movieModels) {
        this.movieModels = movieModels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_poster);
        }

        public void bind(MovieModel movieModel) {
            String image = IMAGES_BASE_URL + movieModel.getPosterPath();
            Glide.with(context)
                    .load(image)
                    .placeholder(R.drawable.ic_local_movies_black_24dp)
                    .into(imageView);
        }

    }
}
