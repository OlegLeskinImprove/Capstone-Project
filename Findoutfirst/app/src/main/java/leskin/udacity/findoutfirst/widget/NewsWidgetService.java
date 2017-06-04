package leskin.udacity.findoutfirst.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import leskin.udacity.findoutfirst.findoutfirst.R;
import leskin.udacity.findoutfirst.model.Article;
import leskin.udacity.findoutfirst.model.Articles;
import leskin.udacity.findoutfirst.model.enums.SortingOfArticles;
import leskin.udacity.findoutfirst.network.APIMethods;
import leskin.udacity.findoutfirst.ui.newsSources.NewsSourcesAtivity;
import leskin.udacity.findoutfirst.utils.PrefUtils;
import leskin.udacity.findoutfirst.utils.Utils;

/**
 * Created by Oleg Leskin on 04.06.2017.
 */

public class NewsWidgetService extends IntentService {
    private static final String TAG = "NewsWidgetService";

    private Handler handler = new Handler();
    private Runnable runnable;

    public NewsWidgetService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(runnable != null) return;

        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        String source = PrefUtils.getLastSource(this);
        String sort = PrefUtils.getLastSorting(this);

        final Articles articles = APIMethods.getArticles(source, SortingOfArticles.getByValue(sort));
        if (articles == null || articles.getArticles() == null) return;

        final Iterator[] articlesIterator = new Iterator[1];

        runnable = new Runnable() {
            @Override
            public void run() {
                if (articlesIterator[0] == null || !articlesIterator[0].hasNext()) {
                    articlesIterator[0] = articles.getArticles().iterator();
                }

                Article article = (Article) articlesIterator[0].next();

                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(NewsWidgetService.this,
                        NewsWidgetProvider.class));

                String title = article.getTitle();
                String date = Utils.parseDate(article.getPublishedAt());
                String urlToImg = article.getUrlToImage();

                Bitmap bitmap = null;
                try {
                    bitmap = new GetImageByUrlTask().execute(urlToImg).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                for (int appWidgetId : appWidgetIds) {
                    RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_layout);
                    views.setTextViewText(R.id.text_title, title);
                    views.setTextViewText(R.id.text_date, date);
                    if (bitmap != null)
                        views.setImageViewBitmap(R.id.img_article, bitmap);

                    // Create an Intent to launch MainActivity
                    Intent launchIntent = new Intent(NewsWidgetService.this, NewsSourcesAtivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(NewsWidgetService.this, 0, launchIntent, 0);
                    views.setOnClickPendingIntent(R.id.widget, pendingIntent);

                    // Tell the AppWidgetManager to perform an update on the current app widget
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                }

                handler.postDelayed(runnable, 5000);
            }
        };

        handler.postDelayed(runnable, 5000);
    }

    class GetImageByUrlTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlToImg = strings[0];
            int widthOfImgArticle = getResources().getDimensionPixelOffset(R.dimen.widget_default_width);
            int heightOfImgArticle = getResources().getDimensionPixelOffset(R.dimen.widget_default_height);

            try {
                URL url = new URL(urlToImg);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return Bitmap.createScaledBitmap(myBitmap, widthOfImgArticle, heightOfImgArticle, false);
            } catch (IOException e) {
                return null;
            }

        }

    }
}
