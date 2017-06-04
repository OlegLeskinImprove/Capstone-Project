package leskin.udacity.findoutfirst.ui.favorites;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;

import leskin.udacity.findoutfirst.db.FavoriteNews;
import leskin.udacity.findoutfirst.db.FavoriteNewsProvider;


/**
 * Created by Oleg Leskin on 04.06.2017.
 */

public class FavoritesLoader extends CursorLoader {
    public static FavoritesLoader newAllArticlesInstance(Context context) {
        return new FavoritesLoader(context, FavoriteNewsProvider.FavoriteNews.CONTENT_URI);
    }
    public FavoritesLoader(Context context) {
        super(context);
    }

    public FavoritesLoader(Context context,
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    private FavoritesLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, null);
    }

    public interface Query {
        String[] PROJECTION = {
                FavoriteNews._ID,
                FavoriteNews.TITLE,
                FavoriteNews.DESCRIPTION,
                FavoriteNews.DATE,
                FavoriteNews.URL,
                FavoriteNews.URL_TO_IMG
                };

        int _ID = 0;
        int TITLE = 1;
        int DESCRIPTION = 2;
        int DATE = 3;
        int URL = 4;
        int URL_TO_IMG = 5;
    }
}
