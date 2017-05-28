package leskin.udacity.findoutfirst.ui.articles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import leskin.udacity.findoutfirst.findoutfirst.R;
import leskin.udacity.findoutfirst.model.Articles;
import leskin.udacity.findoutfirst.model.enums.SortingOfArticles;
import leskin.udacity.findoutfirst.network.APIMethods;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Oleg Leskin on 23.05.2017.
 */

public class ArticlesFragment extends Fragment {

    @BindView(R.id.list_articles)
    RecyclerView listOfArticles;

    @BindView(R.id.progressBar)
    View progressBar;

    private SortingOfArticles sorting;
    private String source;
    private Articles articles = new Articles();
    private ArticlesAdapter adapter;
    LinearLayoutManager layoutManager;

    public static ArticlesFragment newInstance(String source, SortingOfArticles sorting) {
        Bundle args = new Bundle();
        args.putSerializable("sorting", sorting);
        args.putString("source", source);

        ArticlesFragment fragment = new ArticlesFragment();
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
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listOfArticles.setLayoutManager(layoutManager);
        adapter = new ArticlesAdapter(getActivity(), articles);
        listOfArticles.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        getArticles();
    }

    private void getBundle() {
        sorting = (SortingOfArticles) getArguments().getSerializable("sorting");
        source = getArguments().getString("source");
    }

    private void getArticles() {
        showProgress();
        APIMethods.getArticles(source, sorting, new Callback<Articles>() {
            @Override
            public void onResponse(Call<Articles> call, Response<Articles> response) {
                if (response.body() != null) {
                    articles.getArticles().clear();
                    articles.getArticles().addAll(response.body().getArticles());
                    updateData();
                } else {
                    hideProgress();
                    Toast.makeText(getActivity(), R.string.error_loading_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Articles> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.error_loading_data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateData() {
        layoutManager.onItemsChanged(listOfArticles);
        adapter.notifyDataSetChanged();
        hideProgress();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
