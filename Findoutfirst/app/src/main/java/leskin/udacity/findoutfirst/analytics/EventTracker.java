package leskin.udacity.findoutfirst.analytics;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Oleg Leskin on 05.06.2017.
 */

public class EventTracker {

    public static void trackEvent(Context context, String event){
        FirebaseAnalytics.getInstance(context).logEvent(event, null);
    }
}
