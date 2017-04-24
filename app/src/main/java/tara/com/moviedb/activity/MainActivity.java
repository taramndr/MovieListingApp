package tara.com.moviedb.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import tara.com.moviedb.R;
import tara.com.moviedb.adapter.ViewPagerAdapter;
import tara.com.moviedb.fragments.PopularMovieFragment;
import tara.com.moviedb.fragments.RecentMovieFragment;
import tara.com.moviedb.fragments.UpcomingMovieFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check for Network Connection
        if ( !isNetworkAvailable() ){
            noInternetAlertMessage();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DB Movies");
        getSupportActionBar().setIcon(R.drawable.ic_toolbar_peace);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setUpTabIcon();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    private void noInternetAlertMessage() {
        Toast.makeText(this, "Connect to Internet is important.", Toast.LENGTH_SHORT).show();
    }

    private void setUpTabIcon() {
        int[] tabIcons = {
                R.drawable.ic_tab_upcoming,
                R.drawable.ic_tab_popular,
                R.drawable.ic_tab_recent
        };
        for(int i = 0; i<tabIcons.length; i++){
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UpcomingMovieFragment(), "Upcoming");
        adapter.addFragment(new PopularMovieFragment(), "Popular");
        adapter.addFragment(new RecentMovieFragment(), "Recent");
        viewPager.setAdapter(adapter);

    }
}
