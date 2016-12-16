package tsiro.example.com.popularmoviesapps1.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.Log;
import org.json.JSONException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import tsiro.example.com.popularmoviesapps1.R;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by giannis on 13/12/2016.
 */

public class NetworkUtilities {

    private static final String DEBUG_TAG = NetworkUtilities.class.getSimpleName();


    public static URL buildUrl(String base_endpoint) throws MalformedURLException{
        return new URL(Uri.parse(base_endpoint).buildUpon()
                .build().toString());
    }
    //check if network connection is available
    public static boolean checkNetworkAvailability(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static String fetchDataFromMovieDatabaseAPi(URL url) throws IOException, JSONException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = httpURLConnection.getInputStream();

            byte[] buffer = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (inputStream.read(buffer, 0, buffer.length) != -1) {
                byteArrayOutputStream.write(buffer, 0, buffer.length);
            }
            Log.e(DEBUG_TAG, byteArrayOutputStream.toString());
            return byteArrayOutputStream.toString();
        } finally {
            httpURLConnection.disconnect();
        }
    }

}
