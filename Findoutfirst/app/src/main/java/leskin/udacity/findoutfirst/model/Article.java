
package leskin.udacity.findoutfirst.model;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import leskin.udacity.findoutfirst.db.FavoriteNews;

public class Article implements Serializable {

    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("urlToImage")
    @Expose
    private String urlToImage;
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public static Article fromCursor(Cursor cursor) {
        Article article = new Article();
        try {
            article.setTitle(cursor.getString(cursor.getColumnIndex(FavoriteNews.TITLE)));
            article.setDescription(cursor.getString(cursor.getColumnIndex(FavoriteNews.DESCRIPTION)));
            article.setPublishedAt(cursor.getString(cursor.getColumnIndex(FavoriteNews.DATE)));
            article.setUrl(cursor.getString(cursor.getColumnIndex(FavoriteNews.URL)));
            article.setUrlToImage(cursor.getString(cursor.getColumnIndex(FavoriteNews.URL_TO_IMG)));
        }catch (Exception e){
            e.printStackTrace();
        }

        return article;
    }

}
