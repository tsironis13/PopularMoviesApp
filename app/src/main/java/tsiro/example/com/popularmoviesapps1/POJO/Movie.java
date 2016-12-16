package tsiro.example.com.popularmoviesapps1.POJO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by giannis on 13/12/2016.
 */

public class Movie implements Parcelable{

    private int id;
    private String title, poster_path, overview, rating, release_date;

    public Movie(int id, String title, String poster_path, String overview, String rating, String release_date) {
        this.id             = id;
        this.title          = title;
        this.poster_path    = poster_path;
        this.overview       = overview;
        this.rating         = rating;
        this.release_date   = release_date;
    }

    private Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        rating = in.readString();
        release_date = in.readString();
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getPosterPath() { return poster_path; }

    public void setPosterPath(String poster_path) { this.poster_path = poster_path; }

    public String getOverview() { return overview; }

    public void setOverview(String overview) { this.overview = overview; }

    public String getRating() { return rating; }

    public void setRating(String rating) { this.rating = rating; }

    public String getReleaseDate() { return release_date; }

    public void setReleaseDate(String release_date) { this.release_date = release_date; }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(rating);
        parcel.writeString(release_date);
    }
}
