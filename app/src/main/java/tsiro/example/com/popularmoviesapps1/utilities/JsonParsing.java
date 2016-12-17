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

    private static JSONArray results;
    private static int id;
    private static String title, poster_path, overview, rating, release_date;

    public static List<Movie> parseJSONResponse(String response) throws JSONException{
        JSONObject jsonObject = new JSONObject(response);

        if (jsonObject.has(TAG_RESULTS)) results = jsonObject.getJSONArray(TAG_RESULTS);

        List<Movie> movieList = new ArrayList<>();
        if (results.length() > 0) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject object = results.getJSONObject(i);

                if (object.has(TAG_ID)) id = results.getJSONObject(i).getInt(TAG_ID);
                if (object.has(TAG_TITLE)) title  = results.getJSONObject(i).getString(TAG_TITLE);
                if (object.has(TAG_POSTER_PATH)) poster_path = results.getJSONObject(i).getString(TAG_POSTER_PATH);
                if (object.has(TAG_OVERVIEW)) overview = results.getJSONObject(i).getString(TAG_OVERVIEW);
                if (object.has(TAG_VOTE_AVG)) rating = results.getJSONObject(i).getString(TAG_VOTE_AVG);
                if (object.has(TAG_RELEASE_DATE)) release_date = results.getJSONObject(i).getString(TAG_RELEASE_DATE);

                movieList.add(new Movie(id, title, poster_path, overview, rating, release_date));
            }
        }
        return movieList;
    }

}
