package leskin.udacity.findoutfirst.ui.newsSources;

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

import leskin.udacity.findoutfirst.findoutfirst.R;
import leskin.udacity.findoutfirst.model.enums.CategoryOfNewsSource;

public class NewsSourcesAtivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_sources);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_sources_ativity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
