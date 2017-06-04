package leskin.udacity.findoutfirst.ui.favorites;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import leskin.udacity.findoutfirst.findoutfirst.R;
import leskin.udacity.findoutfirst.model.Article;
import leskin.udacity.findoutfirst.ui.detailsOfArticle.DetailsActivity;
import leskin.udacity.findoutfirst.utils.CursorRecyclerViewAdapter;

import static leskin.udacity.findoutfirst.utils.Utils.parseDate;

/**
 * Created by Oleg Leskin on 04.06.2017.
 */

public class FavoritesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    @BindView(R.id.list_articles)
    RecyclerView listOfArticles;

    @BindView(R.id.progressBar)
    View progressBar;

    @BindView(R.id.text_not_found)
    TextView notFoundText;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Cursor mCursor;

    private ArticlesFavoriteAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_articles);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.favorites);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listOfArticles.setLayoutManager(layoutManager);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return FavoritesLoader.newAllArticlesInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursor = cursor;
        adapter = new ArticlesFavoriteAdapter(this, cursor);
        listOfArticles.setAdapter(adapter);
        notFoundText.setVisibility(cursor.getCount() <= 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursor = null;
    }


    public class ArticlesFavoriteAdapter extends CursorRecyclerViewAdapter<ArticlesFavoriteAdapter.SourceViewHolder> {

        private Context context;

        ArticlesFavoriteAdapter(Context context, Cursor c) {
            super(context, c);
            this.context = context;
        }

        class SourceViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.text_title)
            TextView title;

            @BindView(R.id.text_date)
            TextView date;

            @BindView(R.id.img_article)
            ImageView imageOfArticle;

            SourceViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "PlayfairDisplay-Regular.ttf");
                title.setTypeface(typeface);
            }
        }

        @Override
        public SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SourceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article,
                    parent,
                    false));
        }

        @Override
        public int getItemCount() {
            return getCursor().getCount();
        }

        @Override
        public void onBindViewHolder(SourceViewHolder holder, final Cursor cursor) {
            holder.title.setText(Article.fromCursor(cursor).getTitle());
            holder.date.setText(parseDate(Article.fromCursor(cursor).getPublishedAt()));

            Glide.with(context)
                    .load(Article.fromCursor(cursor).getUrlToImage())
                    .into(holder.imageOfArticle);

            holder.imageOfArticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailsActivity.launch(context, Article.fromCursor(cursor), "");
                }
            });
        }
    }
}
