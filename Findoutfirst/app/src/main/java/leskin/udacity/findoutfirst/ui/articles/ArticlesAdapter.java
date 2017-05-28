package leskin.udacity.findoutfirst.ui.articles;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import leskin.udacity.findoutfirst.findoutfirst.R;
import leskin.udacity.findoutfirst.model.Article;
import leskin.udacity.findoutfirst.model.Articles;

/**
 * Created by Oleg Leskin on 23.05.2017.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.SourceViewHolder> {

    private Context context;
    private Articles articles;


    public ArticlesAdapter(Context context, Articles articles) {
        this.articles = articles;
        this.context = context;

    }

    static class SourceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_title)
        TextView title;

        @BindView(R.id.text_date)
        TextView date;

        @BindView(R.id.img_article)
        ImageView imageOfArticle;

        SourceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "PlayfairDisplay-Regular.ttf");
            title.setTypeface(typeface);
        }
    }

    @Override
    public SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SourceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(SourceViewHolder holder, int position) {
        holder.title.setText(getItem(position).getTitle());
        holder.date.setText(parseDate(getItem(position).getPublishedAt()));

        Glide.with(context)
                .load(getItem(position).getUrlToImage())
                .into(holder.imageOfArticle)
                .onLoadStarted(context.getResources().getDrawable(R.mipmap.placeholder));
    }

    private String parseDate(String unparsedDate) {
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

    @Override
    public int getItemCount() {
        return articles.getArticles().size();
    }

    private Article getItem(int position) {
        return articles.getArticles().get(position);
    }

}
