package leskin.udacity.findoutfirst.ui.newsSources;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import leskin.udacity.findoutfirst.findoutfirst.R;
import leskin.udacity.findoutfirst.model.enums.CategoryOfNewsSource;

/**
 * Created by Oleg Leskin on 23.05.2017.
 */

public class NewsSourcesFragment extends Fragment {

    @BindView(R.id.list_news_sources)
    RecyclerView listOfNewsSources;

    private CategoryOfNewsSource category;

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

    private void getBundle() {
        category = (CategoryOfNewsSource) getArguments().getSerializable("category");
    }
}
