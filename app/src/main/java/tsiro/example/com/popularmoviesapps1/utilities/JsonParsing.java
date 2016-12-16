package tsiro.example.com.popularmoviesapps1.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import tsiro.example.com.popularmoviesapps1.POJO.Movie;

/**
 * Created by giannis on 14/12/2016.
 */

public class JsonParsing {

    private static final String TAG_RESULTS         = "results";
    private static final String TAG_ID              = "id";
    private static final String TAG_TITLE           = "title";
    private static final String TAG_POSTER_PATH     = "poster_path";
    private static final String TAG_OVERVIEW        = "overview";
    private static final String TAG_VOTE_AVG        = "vote_average";
    private static final String TAG_RELEASE_DATE    = "release_date";

    public static List<Movie> parseJSONResponse(String response) throws JSONException{
        JSONObject jsonObject = new JSONObject(response);

        JSONArray results = jsonObject.getJSONArray(TAG_RESULTS);

        List<Movie> movieList = new ArrayList<>();
        if (results.length() > 0) {
            for (int i = 0; i < results.length(); i++) {
                int id                  = results.getJSONObject(i).getInt(TAG_ID);
                String original_title   = results.getJSONObject(i).getString(TAG_TITLE);
                String poster_path      = results.getJSONObject(i).getString(TAG_POSTER_PATH);
                String overview         = results.getJSONObject(i).getString(TAG_OVERVIEW);
                String rating           = results.getJSONObject(i).getString(TAG_VOTE_AVG);
                String release_date     = results.getJSONObject(i).getString(TAG_RELEASE_DATE);

                movieList.add(new Movie(id, original_title, poster_path, overview, rating, release_date));
            }
        }
        return movieList;
    }

}
