package leskin.udacity.findoutfirst.ui.articles;

import android.content.Context;
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

import leskin.udacity.findoutfirst.findoutfirst.R;
import leskin.udacity.findoutfirst.model.Source;
import leskin.udacity.findoutfirst.model.enums.SortingOfArticles;

public class ArticlesAtivity extends AppCompatActivity {

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

    private Source source;

    public static void launch(Context context, Source source){
        Intent intent = new Intent(context, ArticlesAtivity.class);
        intent.putExtra("source", source);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        getExtra();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(source.getName());

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

    private void getExtra(){
        if(getIntent().getExtras() != null){
            source = (Source) getIntent().getSerializableExtra("source");
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ArticlesFragment.newInstance(source.getId(), getSortingByPosition(position));
        }

        @Override
        public int getCount() {
            return source.getSortBysAvailable().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getSortingByPosition(position).toString();
        }
    }

    private SortingOfArticles getSortingByPosition(int position){
        String sorting = source.getSortBysAvailable().get(position);
        if(sorting.equals(SortingOfArticles.TOP.toString()))
            return SortingOfArticles.TOP;

        if(sorting.equals(SortingOfArticles.POPULAR.toString()))
            return SortingOfArticles.POPULAR;

        if(sorting.equals(SortingOfArticles.LATEST.toString()))
            return SortingOfArticles.LATEST;

        return SortingOfArticles.TOP;
    }
}
