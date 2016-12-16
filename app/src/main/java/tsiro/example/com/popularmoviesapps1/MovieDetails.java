package tsiro.example.com.popularmoviesapps1;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;

import tsiro.example.com.popularmoviesapps1.POJO.Movie;
import tsiro.example.com.popularmoviesapps1.utilities.NetworkUtilities;

/**
 * Created by giannis on 16/12/2016.
 */

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        if (getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title_textview = (TextView) findViewById(R.id.title_textview);
        TextView release_date_textview = (TextView) findViewById(R.id.release_date_textview);
        TextView user_rating_textview = (TextView) findViewById(R.id.user_rating_textview);
        TextView overview_textview = (TextView) findViewById(R.id.overview_textview);
        ImageView movie_poster_imageview = (ImageView) findViewById(R.id.movie_poster_imageview);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(getString(R.string.movie))) {
            Movie movie = getIntent().getExtras().getParcelable(getString(R.string.movie));
            if (movie != null) {
                if (movie.getPosterPath() != null && !movie.getPosterPath().isEmpty()) {
                    try {
                        Picasso.with(this)
                                .load(String.valueOf(NetworkUtilities.buildUrl(getString(R.string.movies_api_poster_base_endapoint, movie.getPosterPath()))))
                                .fit()
                                .into(movie_poster_imageview);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                if (movie.getTitle() != null && !movie.getTitle().isEmpty()) title_textview.setText(movie.getTitle());
                if (movie.getReleaseDate() != null && !movie.getTitle().isEmpty()) release_date_textview.setText(movie.getReleaseDate().substring(0, 4));
                if (movie.getOverview() != null && !movie.getOverview().isEmpty()) overview_textview.setText(movie.getOverview());
                if (movie.getRating() != null && !movie.getRating().isEmpty()) user_rating_textview.setText(getString(R.string.movie_rating, movie.getRating()));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
