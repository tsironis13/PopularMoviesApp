package tsiro.example.com.popularmoviesapps1;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import tsiro.example.com.popularmoviesapps1.POJO.Movie;
import tsiro.example.com.popularmoviesapps1.adapters.MoviesArrayAdapter;
import tsiro.example.com.popularmoviesapps1.helpers.GridViewItemClickCallback;
import tsiro.example.com.popularmoviesapps1.helpers.MainActivityCallbacks;
import tsiro.example.com.popularmoviesapps1.rest.MoviesAsyncTaskLoader;
import tsiro.example.com.popularmoviesapps1.utilities.AppConfig;
import tsiro.example.com.popularmoviesapps1.utilities.NetworkUtilities;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>, MainActivityCallbacks, GridViewItemClickCallback {

    private static final String DEBUG_TAG = MainActivity.class.getSimpleName();

    private GridView moviesGridView;
    private LinearLayout errorContainerLlt;
    private TextView textView;
    private ProgressBar progressBar;
    private String action, title;
    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesGridView      = (GridView) findViewById(R.id.movies_gridview);
        errorContainerLlt   = (LinearLayout) findViewById(R.id.error_container_llt);
        textView            = (TextView) findViewById(R.id.error_textview);
        Button retryButton  = (Button) findViewById(R.id.retry_button);
        progressBar         = (ProgressBar) findViewById(R.id.progress_bar);

        if (savedInstanceState == null) {
            action = getString(R.string.popular);
            initLoaderUnderConditions(action);
            title = getString(R.string.app_name);
        } else {
            if (savedInstanceState.containsKey("action") && getSupportActionBar() != null) action = savedInstanceState.getString("action");
            if (savedInstanceState.containsKey("title") && getSupportActionBar() != null) {
                title = savedInstanceState.getString("title");
                getSupportActionBar().setTitle(title);
            }
            if (savedInstanceState.containsKey("movie_list")) {
                movieList = savedInstanceState.getParcelableArrayList("movie_list");
                if (movieList != null && movieList.size() == 0) {
                    initLoaderUnderConditions(savedInstanceState.getString("action"));
                }
                moviesGridView.setAdapter(new MoviesArrayAdapter(this, 0 , movieList));
            }
        }
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorContainerLlt.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                initLoaderUnderConditions(action);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movie_list", (ArrayList<? extends Parcelable>) movieList);
        outState.putString("action", action);
        outState.putString("title", title);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        String action= "";
        if (args != null && args.containsKey("sort_order")) action = args.getString("sort_order");
        progressBar.setVisibility(View.VISIBLE);
        return new MoviesAsyncTaskLoader(this, action);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        // ensure returned array list of data is not null and then
        // set the adapter to gridview
        if (data != null) {
            movieList = data;
            progressBar.setVisibility(View.GONE);
            if (moviesGridView.getVisibility() == View.GONE) moviesGridView.setVisibility(View.VISIBLE);
            moviesGridView.setAdapter(new MoviesArrayAdapter(this, 0, data));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) { Log.e(DEBUG_TAG, "onLoaderReset"); }

    // callback to handle async loader error and
    // update UI accordingly
    @Override
    public void onError(int code) {
        moviesGridView.setVisibility(View.GONE);
        switch (code) {
            case AppConfig.NO_CONNECTION:
                updateUIonError(getString(R.string.no_connection));
                break;
            case AppConfig.ERROR_OCCURRED:
                updateUIonError(getString(R.string.error_occured));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (movieList != null) movieList.clear();
        if (item.getItemId() == R.id.top_rated_movies) {
            action  = getString(R.string.top_rated);
            title   = getString(R.string.top_rated_title);
        } else {
            action  =  getString(R.string.popular);
            title   = getString(R.string.app_name);
        }
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
        initLoaderUnderConditions(action);
        return true;
    }

    @Override
    public void onItemClick(int position) {
        if (movieList != null) {
            Movie movie = movieList.get(position);
            Intent intent = new Intent(this, MovieDetails.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        }
    }

    private void initLoaderUnderConditions(String action) {
        //no need to start new loader or restart an existing one
        // when no connection is available
        if (!NetworkUtilities.checkNetworkAvailability(this)) {
            updateUIonError(getString(R.string.no_connection));
            moviesGridView.setVisibility(View.GONE);
        } else {
            if (errorContainerLlt.getVisibility() == View.VISIBLE) errorContainerLlt.setVisibility(View.GONE);
            Bundle bundle = new Bundle();
            bundle.putString("sort_order", action);
            getSupportLoaderManager().restartLoader(AppConfig.LOADER_ID, bundle, this);
        }
    }

    private void updateUIonError(String error_text) {
        progressBar.setVisibility(View.GONE);
        errorContainerLlt.setVisibility(View.VISIBLE);
        textView.setText(error_text);
    }

}
