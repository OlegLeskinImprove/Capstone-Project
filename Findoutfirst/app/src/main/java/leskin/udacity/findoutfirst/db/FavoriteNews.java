package leskin.udacity.findoutfirst.db;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Oleg Leskin on 04.06.2017.
 */

public interface FavoriteNews {
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.TEXT)
    String TITLE = "title";

    @DataType(DataType.Type.TEXT)
    String DATE = "date";

    @DataType(DataType.Type.TEXT)
    String DESCRIPTION = "description";

    @DataType(DataType.Type.TEXT)
    String URL = "url";

    @DataType(DataType.Type.TEXT)
    String URL_TO_IMG = "urlToImg";
}
