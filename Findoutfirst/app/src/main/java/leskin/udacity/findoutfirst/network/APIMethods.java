package leskin.udacity.findoutfirst.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import leskin.udacity.findoutfirst.findoutfirst.BuildConfig;
import leskin.udacity.findoutfirst.model.Articles;
import leskin.udacity.findoutfirst.model.NewsSources;
import leskin.udacity.findoutfirst.model.enums.CategoryOfNewsSource;
import leskin.udacity.findoutfirst.model.enums.LanguageOfNewsSources;
import leskin.udacity.findoutfirst.model.enums.SortingOfArticles;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oleg Leskin on 22.05.2017.
 */

public class APIMethods {
    private static APIService service;

    static {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Urls.BASE_URL)
                    .build();
            service = retrofit.create(APIService.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getNewsSources(CategoryOfNewsSource category, LanguageOfNewsSources language, Callback<NewsSources> cb) {
        Map<String, String> options = new HashMap<>(2);
        options.put("category", category.toString());
        options.put("language", language.toString());

        Call<NewsSources> call = service.getNewsSources(options);
        call.enqueue(cb);
    }

    public static NewsSources getNewsSources(CategoryOfNewsSource category, LanguageOfNewsSources language){
        Map<String, String> options = new HashMap<>(2);
        options.put("category", category.toString());
        options.put("language", language.toString());

        Call<NewsSources> call = service.getNewsSources(options);
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getArticles(String source, SortingOfArticles sorting, Callback<Articles> cb) {
        Map<String, String> options = new HashMap<>();
        options.put("source", source);
        options.put("sortBy", sorting.toString());

        Call<Articles> call = service.getArticles(BuildConfig.NEWS_DB_API_KEY, options);
        call.enqueue(cb);
    }

    public static Articles getArticles(String source, SortingOfArticles sorting) {
        Map<String, String> options = new HashMap<>();
        options.put("source", source);
        options.put("sortBy", sorting.toString());

        Call<Articles> call = service.getArticles(BuildConfig.NEWS_DB_API_KEY, options);
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
