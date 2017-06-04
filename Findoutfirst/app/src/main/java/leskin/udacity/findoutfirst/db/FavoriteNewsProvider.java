package leskin.udacity.findoutfirst.db;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Oleg Leskin on 06.11.2016.
 */
@ContentProvider(authority = FavoriteNewsProvider.AUTHORITY, database = FavoriteNewsDatabase.class)
public class FavoriteNewsProvider {

    public static final String AUTHORITY = "leskin.udacity.findoutfirst.db.FavoriteNewsProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String NEWS = "favorite_news";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = FavoriteNewsDatabase.FAVORITE_NEWS)
    public static class FavoriteNews {

        @ContentUri(
                path = "favorite_news",
                type = "vnd.android.cursor.dir/favorite",
                defaultSort = leskin.udacity.findoutfirst.db.FavoriteNews.TITLE + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.NEWS);

    }

}
