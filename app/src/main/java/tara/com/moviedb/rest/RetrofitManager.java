package tara.com.moviedb.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tara.com.moviedb.response.MovieDetails;
import tara.com.moviedb.response.MovieListing;

/**
 * Created by Tara on 22-Apr-17.
 */

public class RetrofitManager {
    public static Retrofit retrofit = null;
    public static MovieListingService movieListingService = null;
    public static RetrofitManager retroFitManager = null;
    private static String BASE_URL = "https://api.themoviedb.org/3/movie/";

    private RetrofitManager() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().build();

        //if(retrofit == null) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        //}
        movieListingService = retrofit.create(MovieListingService.class);

    }

    public static RetrofitManager getInstance() {
        if (retroFitManager == null) {
            retroFitManager = new RetrofitManager();
        }
        return retroFitManager;
    }

    public static void getUpcomingMovieList(String apiKey, retrofit2.Callback<MovieListing> getMovieListingCallback) {
        Call<MovieListing> getMovieListing = movieListingService.getUpcomingMovies(apiKey);
        getMovieListing.enqueue(getMovieListingCallback);
    }

    public static void getMovieMoreDetail(String movieId, String apiKey, retrofit2.Callback<MovieDetails> getMovieDetailCallback ){
        Call<MovieDetails> getMovieMoreDetails = movieListingService.getMovieMoreDetails(movieId, apiKey);
        getMovieMoreDetails.enqueue(getMovieDetailCallback);
    }
}
