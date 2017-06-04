package leskin.udacity.findoutfirst.ui.newsSources;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import leskin.udacity.findoutfirst.findoutfirst.R;
import leskin.udacity.findoutfirst.model.NewsSources;
import leskin.udacity.findoutfirst.model.enums.CategoryOfNewsSource;
import leskin.udacity.findoutfirst.model.enums.LanguageOfNewsSources;
import leskin.udacity.findoutfirst.network.APIMethods;

/**
 * Created by Oleg Leskin on 23.05.2017.
 */

public class NewsSourcesFragment extends Fragment {

    @BindView(R.id.list_news_sources)
    RecyclerView listOfNewsSources;

    @BindView(R.id.progressBar)
    View progressBar;

    private CategoryOfNewsSource category;
    private NewsSources newsSources = new NewsSources();
    private NewsSourcesAdapter adapter;
    GridLayoutManager layoutManager;

    public static NewsSourcesFragment newInstance(CategoryOfNewsSource category) {
        Bundle args = new Bundle();
        args.putSerializable("category", category);

        NewsSourcesFragment fragment = new NewsSourcesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundle();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_sources, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManager = new GridLayoutManager(getContext(), getSpanCount());
        listOfNewsSources.setLayoutManager(layoutManager);
        adapter = new NewsSourcesAdapter(getActivity(), newsSources);
        listOfNewsSources.setAdapter(adapter);
    }

    private int getSpanCount() {
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int cardSize = getResources().getDimensionPixelSize(R.dimen.news_source_card_size);
        return width / cardSize;
    }

    @Override
    public void onStart() {
        super.onStart();
        getNewsSources();
    }

    private void getBundle() {
        category = (CategoryOfNewsSource) getArguments().getSerializable("category");
    }

    private void getNewsSources() {
        new GetNewsSourcesTask().execute(category);
    }

    private void updateData() {
        layoutManager.onItemsChanged(listOfNewsSources);
        adapter.notifyDataSetChanged();
        hideProgress();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    private class GetNewsSourcesTask extends AsyncTask<CategoryOfNewsSource, Void, NewsSources> {
        @Override
        protected void onPreExecute() {
            showProgress();
            super.onPreExecute();
        }

        @Override
        protected NewsSources doInBackground(CategoryOfNewsSource... categoryOfNewsSources) {
            if (categoryOfNewsSources != null) {
                return APIMethods.getNewsSources(categoryOfNewsSources[0], LanguageOfNewsSources.ENGLISH);
            }
            return null;
        }

        @Override
        protected void onPostExecute(NewsSources sources) {
            super.onPostExecute(sources);
            hideProgress();

            if (sources == null)
                Toast.makeText(getActivity(), R.string.error_loading_data, Toast.LENGTH_SHORT).show();
            else{
                newsSources.getSources().clear();
                newsSources.getSources().addAll(sources.getSources());
                updateData();
            }
        }
    }
}
