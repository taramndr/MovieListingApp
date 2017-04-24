package tara.com.moviedb.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Tara on 23-Apr-17.
 */

public class MovieDetails {

    @SerializedName("genres")
    private ArrayList<Genres> genres = new ArrayList<>();

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("videos")
    private MovieVideos movieVideos;

    public ArrayList<Genres> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genres> genres) {
        this.genres = genres;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTagline() {
        return tagline;
    }

    public MovieVideos getMovieVideos() {
        return movieVideos;
    }

    public void setMovieVideos(MovieVideos movieVideos) {
        this.movieVideos = movieVideos;
    }
}
