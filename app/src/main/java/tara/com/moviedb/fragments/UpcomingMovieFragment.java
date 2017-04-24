package tara.com.moviedb.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tara.com.moviedb.R;
import tara.com.moviedb.activity.DetailActivity;
import tara.com.moviedb.adapter.MovieListingAdapter;
import tara.com.moviedb.response.MovieListing;
import tara.com.moviedb.response.Result;
import tara.com.moviedb.rest.RetrofitManager;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingMovieFragment extends Fragment {

    private RecyclerView upcMovieRecyclerView;
    private MovieListingAdapter mListAdapter;
    private ArrayList<Result> upcomingMovieList = new ArrayList<>();

    public UpcomingMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upcoming_movie, container, false); // Inflate the layout for this fragment

        upcMovieRecyclerView = (RecyclerView) v.findViewById(R.id.rv_movie_listing);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()); // getActivity() bcoz fragment depend on main activity //it says recycle view to be Linear List
        upcMovieRecyclerView.setLayoutManager(linearLayoutManager);
        getMovieListing();
        mListAdapter = new MovieListingAdapter(getActivity(), upcomingMovieList);
        upcMovieRecyclerView.setAdapter(mListAdapter);
         return v;
    }

    private void getMovieListing() {

        RetrofitManager.getInstance().getUpcomingMovieList("943fe7183253884cd7dfa899153695a0", new Callback<MovieListing>() {

            @Override
            public void onResponse(Call<MovieListing> call, Response<MovieListing> response) {

                if (response.code() == 200){
                    updateMovies(response);
                }
                else{
                    Log.i(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieListing> call, Throwable t) {
                Log.i(TAG, "onFailure: "+ t.getLocalizedMessage());
            }
        });
    }

    //update movie list when response is success
    private void updateMovies(Response<MovieListing> response) {
        upcomingMovieList.addAll(response.body().getResults());
        mListAdapter.notifyDataSetChanged();
        mListAdapter.setClickListener(new MovieListingAdapter.MovieItemClickListener() {
            @Override
            public void onClick(Result result) {
                //Log.i(TAG, "onClickItem: movieList");
                startActivity(DetailActivity.getLaunchIntent(getActivity(), result));
            }
        });
    }

    }
