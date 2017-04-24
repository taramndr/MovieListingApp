package tara.com.moviedb.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tara.com.moviedb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentMovieFragment extends Fragment {


    public RecentMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recent_movie, container, false);
    }

}
