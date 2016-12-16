package tsiro.example.com.popularmoviesapps1.rest;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import tsiro.example.com.popularmoviesapps1.MainActivity;
import tsiro.example.com.popularmoviesapps1.helpers.MainActivityCallbacks;
import tsiro.example.com.popularmoviesapps1.POJO.Movie;
import tsiro.example.com.popularmoviesapps1.R;
import tsiro.example.com.popularmoviesapps1.utilities.AppConfig;
import tsiro.example.com.popularmoviesapps1.utilities.JsonParsing;
import tsiro.example.com.popularmoviesapps1.utilities.NetworkUtilities;

/**
 * Created by giannis on 13/12/2016.
 */

public class MoviesAsyncTaskLoader extends AsyncTaskLoader<List<Movie>> {

    private static final String DEBUG_TAG = MoviesAsyncTaskLoader.class.getSimpleName();

    private MainActivityCallbacks mainActivityCallbacks;
    private String action;
    private List<Movie> movieList;

    public MoviesAsyncTaskLoader(Context context, String action) {
        super(context);
        if (context instanceof MainActivity) mainActivityCallbacks = (MainActivityCallbacks) context;
        this.action = action;
    }

    @Override
    protected void onStartLoading() {
        if (movieList != null) {
            deliverResult(movieList);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Movie> loadInBackground() {
//        Log.e(DEBUG_TAG, "LOAD IN BACKGROUND: "+ action);
        List<Movie> movieList = null;
        try {
            String response = NetworkUtilities.fetchDataFromMovieDatabaseAPi(NetworkUtilities.buildUrl(getContext().getString(R.string.movies_api_base_endpoint, action)));
            movieList =JsonParsing.parseJSONResponse(response);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    @Override
    protected void onStopLoading() {
//        Log.e(DEBUG_TAG, "onStopLoading");
        cancelLoad();
    }

    @Override
    public void deliverResult(List<Movie> data) {
        if (data != null) {
            this.movieList = data;
            super.deliverResult(data);
        } else {
            mainActivityCallbacks.onError(AppConfig.ERROR_OCCURRED);
        }
//        Log.e(DEBUG_TAG, "deliverResult");
    }
}
