package tara.com.moviedb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tara.com.moviedb.R;
import tara.com.moviedb.response.Result;

/**
 * Created by Tara on 22-Apr-17.
 */

public class MovieListingAdapter extends RecyclerView.Adapter<MovieListingAdapter.MovieListViewHolder> {

    private ArrayList<Result> movieListingDetailArrayList;
    private Context context;
    private MovieItemClickListener movieItemClickListener;

    public MovieListingAdapter(Context context, ArrayList<Result> movieListingDetailArrayList){
        this.movieListingDetailArrayList = movieListingDetailArrayList;
        this.context = context;
    }

    public void setClickListener(MovieItemClickListener movieItemClickListener) {
        this.movieItemClickListener = movieItemClickListener;
    }


    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView;
        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_listing,parent,false ); //.layout.movie_listing, parent, false);
        MovieListViewHolder rvh = new MovieListViewHolder(layoutView);
        return rvh;

    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, final int position) {
        holder.tvMovieName.setText(movieListingDetailArrayList.get(position).getOriginalTitle());
        //holder.ivMoviePoster.setImageResource(movieListingDetailArrayList.get(position).getMoviePosterPath());
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185//"+ movieListingDetailArrayList.get(position).getPosterPath())
                .into(holder.ivMoviePoster);
        holder.tvMovieReleaseDate.setText(movieListingDetailArrayList.get(position).getReleaseDate());
        String overview = "";//TextUtils.substring(movieListingDetailArrayList.get(position).getOverview(), 0, 50) + "...";
        holder.tvMovieDescription.setText(overview);

        //Movie Rating Star
        float orgratingValue = movieListingDetailArrayList.get(position).getVoteAverage();
        float ratingValue = (movieListingDetailArrayList.get(position).getVoteAverage())/2;

        holder.rbMovieRating.setRating(ratingValue);
        holder.tvMovieRating.setText( String.valueOf(orgratingValue));

        //On each Movie item click
        holder.rlMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movieItemClickListener != null){
                    movieItemClickListener.onClick(movieListingDetailArrayList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieListingDetailArrayList.size();
    }

    public class MovieListViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivMoviePoster;
        public TextView tvMovieName, tvMovieReleaseDate, tvMovieDescription, tvMovieRating;
        RelativeLayout rlMainLayout;
        RatingBar rbMovieRating;

        public MovieListViewHolder(View layoutView) {
            super(layoutView);
            ivMoviePoster = (ImageView) layoutView.findViewById(R.id.iv_movie_poster);
            tvMovieName = (TextView) layoutView.findViewById(R.id.tv_movie_name);
            tvMovieReleaseDate = (TextView) layoutView.findViewById(R.id.tv_movie_release_date);
            tvMovieDescription = (TextView) layoutView.findViewById(R.id.tv_movie_description);
            tvMovieRating = (TextView) layoutView.findViewById(R.id.tv_movie_rating);
            rlMainLayout = (RelativeLayout) layoutView.findViewById(R.id.rl_main_layout);
            rbMovieRating = (RatingBar) layoutView.findViewById(R.id.rb_movie_rating);
        }
    }

    public interface MovieItemClickListener {
        void onClick(Result result);
    }
}
