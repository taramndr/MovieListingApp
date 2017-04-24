package tara.com.moviedb.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tara.com.moviedb.BuildConfig;
import tara.com.moviedb.R;
import tara.com.moviedb.response.Genres;
import tara.com.moviedb.response.MovieDetails;
import tara.com.moviedb.response.MovieVideos;
import tara.com.moviedb.response.Result;
import tara.com.moviedb.response.VideoResult;
import tara.com.moviedb.rest.RetrofitManager;

public class DetailActivity extends AppCompatActivity {

    private static String TAG = DetailActivity.class.getSimpleName();
    private TextView tvMovieTitle, tvMovieOverview, tvReleaseDate, tvMoviePopularity, tvMovieVoteCount, tvMovieVoteAverage, tvMovieTagline, tvMovieGenre;
    private ImageView ivMovieImage, ivBackdropImage;
    private Toolbar toolbar;
    private TextView txtViewTool;
    private ImageView imgViewTool;
    private String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Result movieDetail = (Result) intent.getExtras().get("MOVIE_DETAIL");

        txtViewTool.setText(movieDetail.getTitle());

        //set values on View
        setValuesOnView(movieDetail);

        movieId = movieDetail.getId().toString();
        getMovieMoreDetails(movieId);

    }

    private void getMovieMoreDetails(String movieId) {
        RetrofitManager.getInstance().getMovieMoreDetail( movieId, BuildConfig.TMDBMOVIEAPIKEY, new Callback<MovieDetails>(){

            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if (response.code() == 200){
                    Log.i(TAG, "onResponse: Detsil chk"+ response.body());
                    updateResponseMovieDetails(response);

                } else{
                    Log.i(TAG, "onResponse: " + response.message());
                    Toast.makeText(DetailActivity.this, response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Log.i(TAG, "onFailure: "+ t.getLocalizedMessage());
            }
        });
    }

    private void updateResponseMovieDetails(Response<MovieDetails> response) {
        ArrayList<Genres> genreList;
        ArrayList<VideoResult> videoLinkList;

        //Get Movie TagLine value and set it to view
        String movieTagLine = response.body().getTagline();
        if( !TextUtils.isEmpty(movieTagLine)){
            tvMovieTagline.setVisibility(View.VISIBLE);
            tvMovieTagline.setText(movieTagLine);
        }

        //Get Movie Genres array values and set it to view
        genreList = response.body().getGenres();
        List<String> genreNames = new ArrayList<>();
        for(int i = 0; i < genreList.size(); i++){
            genreNames.add(genreList.get(i).getGenreName());
        }
        String movieGenre =  TextUtils.join(", ",genreNames);
        if( !TextUtils.isEmpty(movieGenre)){
            tvMovieGenre.setVisibility(View.VISIBLE);
            tvMovieGenre.setText(movieGenre);
        }

        //Get Movie Video LInks and set value to views
        MovieVideos movieVideos = response.body().getMovieVideos();
        videoLinkList = movieVideos.getResults();
        List<String> videoLinks = new ArrayList<>();
        for(int i = 0; i < videoLinkList.size(); i++){
            videoLinks.add(videoLinkList.get(i).getKey());
        }

        Log.i(TAG, "updateResponseMovieDetails: "+ videoLinks);

        //Dynamically creating button to view videos
        createVideoBtnLayoutDynamically(videoLinks);

    }

    private void createVideoBtnLayoutDynamically (final List<String> videoLinksArray) {

            for (int i = 0; i < videoLinksArray.size(); i++) {
                Button vButton = new Button(this);
                vButton.setText("Video" + i);
                vButton.setId(i);
                final int id_ = vButton.getId();

                LinearLayout layout = (LinearLayout) findViewById(R.id.movie_video_btns);
                layout.addView(vButton);

                vButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+ videoLinksArray.get(id_) )));
                        //Toast.makeText(DetailActivity.this, "Button clicked index = " + videoLinksArray.get(id_), Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }

    private void setValuesOnView(Result movieDetail) {
        tvMovieTitle.setText(movieDetail.getOriginalTitle());
        tvReleaseDate.setText(movieDetail.getReleaseDate());
        Glide.with(DetailActivity.this)
                .load("http://image.tmdb.org/t/p/w185//"+ movieDetail.getPosterPath())
                .into(ivMovieImage);

        //Making second image Round shape
        final Context context = getApplicationContext();
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185//"+ movieDetail.getBackdropPath())
                .asBitmap().centerCrop().into(new BitmapImageViewTarget(ivBackdropImage){
            @Override
            protected void setResource(Bitmap resource) {
                //super.setResource(resource);
                RoundedBitmapDrawable circleBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circleBitmapDrawable.setCircular(true);
                ivBackdropImage.setImageDrawable(circleBitmapDrawable);
            }
        });

        tvMovieOverview.setText(movieDetail.getOverview());
        tvMoviePopularity.setText(movieDetail.getPopularity().toString());
        tvMovieVoteCount.setText(movieDetail.getVoteCount().toString());
        tvMovieVoteAverage.setText(String.valueOf(movieDetail.getVoteAverage()));
    }

    private void initializeViews() {

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        txtViewTool = (TextView) toolbar.findViewById(R.id.detail_toolbar_title);
        imgViewTool = (ImageView) toolbar.findViewById(R.id.detail_toolbar_image);

        tvMovieTitle = (TextView) findViewById(R.id.tv_detail_title);
        ivMovieImage = (ImageView) findViewById(R.id.iv_detail_image);
        ivBackdropImage = (ImageView) findViewById(R.id.iv_detail_backdrop_image);

        tvReleaseDate = (TextView) findViewById(R.id.tv_detail_release_date);
        tvMovieOverview = (TextView) findViewById(R.id.tv_detail_overview);
        tvMoviePopularity = (TextView) findViewById(R.id.tv_detail_popularity);
        tvMovieVoteAverage = (TextView) findViewById(R.id.tv_detail_vote_average);
        tvMovieVoteCount = (TextView) findViewById(R.id.tv_detail_vote_count);

        tvMovieTagline = (TextView) findViewById(R.id.tv_detail_tagline);
        tvMovieGenre = (TextView) findViewById(R.id.tv_detail_genres);

    }

    public static Intent getLaunchIntent(Context context, Result result){
        Intent movieDetailActivityIntent = new Intent(context, DetailActivity.class);
        movieDetailActivityIntent.putExtra("MOVIE_DETAIL", result);
        return movieDetailActivityIntent;
    }

}
