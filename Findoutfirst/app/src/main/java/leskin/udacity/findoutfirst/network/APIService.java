package leskin.udacity.findoutfirst.network;

import java.util.Map;

import leskin.udacity.findoutfirst.model.NewsSources;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Oleg Leskin on 22.05.2017.
 */

public interface APIService {
    @GET(Urls.NEWS_SOURCES)
    Call<NewsSources> getNewsSources(@QueryMap Map<String, String> options);

    @GET(Urls.NEWS_ARTICLES)
    Call<NewsSources> getArticles(@QueryMap Map<String, String> options);
}
