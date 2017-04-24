package tara.com.moviedb.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Tara on 23-Apr-17.
 */

public class MovieVideos {

    @SerializedName("results")
    private ArrayList<VideoResult> results = new ArrayList<>();

    public ArrayList<VideoResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<VideoResult> results) {
        this.results = results;
    }
}
