package leskin.udacity.findoutfirst.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Oleg Leskin on 04.06.2017.
 */

public class Utils {

    public static String parseDate(String unparsedDate){
        if (unparsedDate == null) return "";

        SimpleDateFormat unparsedSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        SimpleDateFormat parsedSDF = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        try {
            Date date = unparsedSDF.parse(unparsedDate);
            return parsedSDF.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return unparsedDate;
        }
    }
}
