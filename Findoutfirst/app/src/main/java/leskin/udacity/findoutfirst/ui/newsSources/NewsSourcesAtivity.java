package leskin.udacity.findoutfirst.ui.newsSources;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import leskin.udacity.findoutfirst.analytics.EventMarks;
import leskin.udacity.findoutfirst.analytics.EventTracker;
import leskin.udacity.findoutfirst.findoutfirst.R;
import leskin.udacity.findoutfirst.model.enums.CategoryOfNewsSource;
import leskin.udacity.findoutfirst.ui.favorites.FavoritesActivity;

public class NewsSourcesAtivity extends AppCompatActivity {

    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.adView)
    AdView adView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_sources);
        ButterKnife.bind(this);
        EventTracker.trackEvent(this, EventMarks.SCREEN_NEWS_SOURCES);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_sources_ativity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorites) {
            startActivity(new Intent(this, FavoritesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return NewsSourcesFragment.newInstance(getCategoryByPosition(position));
        }

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getCategoryByPosition(position).toString();
        }
    }

    private CategoryOfNewsSource getCategoryByPosition(int position){
        switch (position) {
            case 0:
                return CategoryOfNewsSource.GENERAL;
            case 1:
                return CategoryOfNewsSource.ENTERTAINMENT;
            case 2:
                return CategoryOfNewsSource.BUSINESS;
            case 3:
                return CategoryOfNewsSource.GAMING;
            case 4:
                return CategoryOfNewsSource.SPORT;
            case 5:
                return CategoryOfNewsSource.POLITICS;
            case 6:
                return CategoryOfNewsSource.MUSIC;
            case 7:
                return CategoryOfNewsSource.SCIENCE_AND_NATURE;
            case 8:
                return CategoryOfNewsSource.TECHNOLOGY;

            default: return CategoryOfNewsSource.GENERAL;
        }
    }
}
