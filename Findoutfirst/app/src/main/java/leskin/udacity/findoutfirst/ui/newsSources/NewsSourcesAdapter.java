package leskin.udacity.findoutfirst.ui.newsSources;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import leskin.udacity.findoutfirst.findoutfirst.R;
import leskin.udacity.findoutfirst.model.NewsSources;
import leskin.udacity.findoutfirst.model.Source;
import leskin.udacity.findoutfirst.ui.articles.ArticlesAtivity;

/**
 * Created by Oleg Leskin on 23.05.2017.
 */

public class NewsSourcesAdapter extends RecyclerView.Adapter<NewsSourcesAdapter.SourceViewHolder> {

    private Context context;
    private NewsSources newsSources;


    public NewsSourcesAdapter(Context context, NewsSources newsSources) {
        this.newsSources = newsSources;
        this.context = context;

    }

    static class SourceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_source)
        TextView source;

        SourceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "PlayfairDisplay-Regular.ttf");
            source.setTypeface(typeface);
        }
    }

    @Override
    public SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SourceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news_source,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(SourceViewHolder holder, final int position) {
        holder.source.setText(getItem(position).getName());
        holder.source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticlesAtivity.launch(context, getItem(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        return newsSources.getSources().size();
    }

    private Source getItem(int position) {
        return newsSources.getSources().get(position);
    }

}
