package net.bashayer.popularmovies.movie.details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import net.bashayer.popularmovies.R;
import net.bashayer.popularmovies.data.model.ReviewModel;

import static net.bashayer.popularmovies.common.Constants.REVIEWS;

public class ReviewsActivity extends AppCompatActivity {


    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        textView = findViewById(R.id.reviews);

        String reviews =  getIntent().getStringExtra(REVIEWS);
        textView.setText(reviews);
    }
}
