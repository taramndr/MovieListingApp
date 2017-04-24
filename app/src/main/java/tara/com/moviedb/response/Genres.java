package tara.com.moviedb.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tara on 23-Apr-17.
 */

public class Genres {

    @SerializedName("id")
    private int genreId;

    @SerializedName("name")
    private String genreName;

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
