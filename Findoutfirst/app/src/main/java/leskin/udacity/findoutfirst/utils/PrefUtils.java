package leskin.udacity.findoutfirst.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import leskin.udacity.findoutfirst.findoutfirst.R;

/**
 * Created by Oleg Leskin on 04.06.2017.
 */

public final class PrefUtils {

    private PrefUtils() {
    }

    public static void saveLastSourceAndSorting(Context context, String source, String sorting){
        String keySource = context.getString(R.string.pref_source_key);
        String keySorting = context.getString(R.string.pref_sort_key);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keySource, source);
        editor.putString(keySorting, sorting);
        editor.apply();
    }

    public static String getLastSource(Context context){
        String keySource = context.getString(R.string.pref_source_key);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(keySource, context.getString(R.string.pref_default_source));
    }

    public static String getLastSorting(Context context){
        String keySort = context.getString(R.string.pref_sort_key);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(keySort, context.getString(R.string.pref_default_sort));
    }
}
