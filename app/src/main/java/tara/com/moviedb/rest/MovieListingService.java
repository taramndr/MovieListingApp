package tara.com.moviedb.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tara.com.moviedb.response.MovieDetails;
import tara.com.moviedb.response.MovieListing;

/**
 * Created by Tara on 22-Apr-17.
 */

public interface MovieListingService {

    @GET("upcoming")
    Call<MovieListing> getUpcomingMovies(@Query("api_key") String apiKey);  //Query is url manipulators , Path, body, full, part

    @GET("{id}?append_to_response=videos")
    Call<MovieDetails> getMovieMoreDetails(@Path("id") String movieId, @Query("api_key") String apiKey);
}