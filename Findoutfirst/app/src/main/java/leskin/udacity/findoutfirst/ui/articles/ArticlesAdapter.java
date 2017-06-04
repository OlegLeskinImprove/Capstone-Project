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

import butterknife.BindView;
import butterknife.ButterKnife;
import leskin.udacity.findoutfirst.findoutfirst.R;
import leskin.udacity.findoutfirst.model.Article;
import leskin.udacity.findoutfirst.model.Articles;
import leskin.udacity.findoutfirst.ui.detailsOfArticle.DetailsActivity;

import static leskin.udacity.findoutfirst.utils.Utils.parseDate;

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
    public void onBindViewHolder(SourceViewHolder holder, final int position) {
        holder.title.setText(getItem(position).getTitle());
        holder.date.setText(parseDate(getItem(position).getPublishedAt()));

        Glide.with(context)
                .load(getItem(position).getUrlToImage())
                .into(holder.imageOfArticle);

        holder.imageOfArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailsActivity.launch(context, articles.getArticles().get(position), articles.getSource());
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.getArticles().size();
    }

    private Article getItem(int position) {
        return articles.getArticles().get(position);
    }

}
