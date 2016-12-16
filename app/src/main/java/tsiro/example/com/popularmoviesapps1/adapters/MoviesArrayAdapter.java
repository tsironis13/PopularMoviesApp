package tsiro.example.com.popularmoviesapps1.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.net.MalformedURLException;
import java.util.List;
import tsiro.example.com.popularmoviesapps1.helpers.GridViewItemClickCallback;
import tsiro.example.com.popularmoviesapps1.MainActivity;
import tsiro.example.com.popularmoviesapps1.POJO.Movie;
import tsiro.example.com.popularmoviesapps1.R;
import tsiro.example.com.popularmoviesapps1.utilities.NetworkUtilities;

/**
 * Created by giannis on 14/12/2016.
 */

public class MoviesArrayAdapter extends ArrayAdapter<Movie> {

    //private static final String DEBUG_TAG = MoviesArrayAdapter.class.getSimpleName();
    private List<Movie> movieList;
    private GridViewItemClickCallback gridViewItemClickCallback;

    public MoviesArrayAdapter(Context context, int resource, List<Movie> movieList) {
        super(context, resource, movieList);
        this.movieList = movieList;
        if (context instanceof MainActivity) gridViewItemClickCallback = (GridViewItemClickCallback) context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
//        Log.e(DEBUG_TAG, "getView " + position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_image_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.grid_imageview);
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (movieList != null) gridViewItemClickCallback.onItemClick(position);
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (movieList != null && movieList.size() > 0) {
            try {
                Picasso.with(getContext())
                        .load(String.valueOf(NetworkUtilities.buildUrl(getContext().getString(R.string.movies_api_poster_base_endapoint, movieList.get(position).getPosterPath()))))
                        .fit()
                        .into(viewHolder.imageView);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public int getViewTypeCount() {
        return movieList.size() > 0 ? getCount() : 1;
    }

    @Override
    public int getItemViewType(int position) { return position; }

    private static class ViewHolder {
        ImageView imageView;
    }
}
