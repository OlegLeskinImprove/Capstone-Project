package leskin.udacity.findoutfirst.ui.detailsOfArticle;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import leskin.udacity.findoutfirst.analytics.EventMarks;
import leskin.udacity.findoutfirst.analytics.EventTracker;
import leskin.udacity.findoutfirst.db.FavoriteNews;
import leskin.udacity.findoutfirst.db.FavoriteNewsProvider;
import leskin.udacity.findoutfirst.findoutfirst.R;
import leskin.udacity.findoutfirst.model.Article;

import static leskin.udacity.findoutfirst.utils.Utils.parseDate;

/**
 * Created by Oleg Leskin on 04.06.2017.
 */

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.photo)
    ImageView photoImg;

    @BindView(R.id.meta_bar)
    View metaBarView;

    @BindView(R.id.toolbar_collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.favorite_fab)
    FloatingActionButton favoriteFab;

    @BindView(R.id.article_title)
    TextView titleText;
    @BindView(R.id.article_date)
    TextView dateText;
    @BindView(R.id.article_body)
    TextView bodyText;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Article article;
    private String source = "";
    private int mMutedColor = 0xFF333333;


    public static void launch(Context context, Article article, String source) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("article", article);
        intent.putExtra("source", source);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        ButterKnife.bind(this);
        getExtra();
        EventTracker.trackEvent(this, EventMarks.SCREEN_ARTICLE_DETAIL);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(source);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        fillView();
    }

    private void fillView() {
        try {
            Glide.with(this)
                    .load(article.getUrlToImage())
                    .asBitmap()
                    .override(600, 400)
                    .into(new BitmapImageViewTarget(photoImg) {
                        @Override
                        protected void setResource(Bitmap bitmap) {
                            if (bitmap != null) {
                                new Palette.Builder(bitmap).generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        mMutedColor = palette.getDarkMutedColor(0xFF333333);
                                        metaBarView.setBackgroundColor(mMutedColor);
                                        collapsingToolbarLayout.setContentScrimColor(mMutedColor);
                                        updateStatusBarColor();
                                    }
                                });
                            }
                            super.setResource(bitmap);
                        }
                    });

            titleText.setText(article.getTitle());
            dateText.setText(parseDate(article.getPublishedAt()));

            String body = article.getDescription() + "\n" + article.getUrl();
            bodyText.setMovementMethod(LinkMovementMethod.getInstance());
            bodyText.setText(body);

            if(checkInFavorite(article)){
                favoriteFab.setImageResource(R.drawable.ic_favorite_24px);
            }else{
                favoriteFab.setImageResource(R.drawable.ic_favorite_border_24px);
            }

            favoriteFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!checkInFavorite(article)) {
                        EventTracker.trackEvent(DetailsActivity.this, EventMarks.ACTION_ADD_TO_FAVORITES);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(FavoriteNews.TITLE, article.getTitle());
                        contentValues.put(FavoriteNews.DESCRIPTION, article.getDescription());
                        contentValues.put(FavoriteNews.URL, article.getUrl());
                        contentValues.put(FavoriteNews.URL_TO_IMG, article.getUrlToImage());
                        contentValues.put(FavoriteNews.DATE, article.getPublishedAt());
                        getContentResolver().insert(FavoriteNewsProvider.FavoriteNews.CONTENT_URI, contentValues);
                        favoriteFab.setImageResource(R.drawable.ic_favorite_24px);
                    } else {
                        EventTracker.trackEvent(DetailsActivity.this, EventMarks.ACTION_REMOVE_FROM_FAVORITES);
                        String where = FavoriteNews.URL + "= '" + article.getUrl() + "'";
                        getContentResolver().delete(FavoriteNewsProvider.FavoriteNews.CONTENT_URI, where, null);
                        favoriteFab.setImageResource(R.drawable.ic_favorite_border_24px);
                    }
                }
            });

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean checkInFavorite(Article article) {
        try {
            Cursor cursor = getContentResolver().query(FavoriteNewsProvider.FavoriteNews.CONTENT_URI, new String[]{FavoriteNews.URL}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    if (article.getUrl().equals(cursor.getString(cursor.getColumnIndex(FavoriteNews.URL)))) {
                        return true;
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void updateStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(mMutedColor);
        }
    }

    private void getExtra() {
        if (getIntent().getExtras() != null) {
            article = (Article) getIntent().getSerializableExtra("article");
            source = getIntent().getStringExtra("source");
        }
    }

}
