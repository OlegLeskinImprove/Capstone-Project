package leskin.udacity.findoutfirst.db;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Oleg Leskin on 06.11.2016.
 */
@Database(version = FavoriteNewsDatabase.VERSION)
public class FavoriteNewsDatabase {

    public static final int VERSION = 1;

    @Table(FavoriteNews.class) public static final String FAVORITE_NEWS = "favorite_news";

}
