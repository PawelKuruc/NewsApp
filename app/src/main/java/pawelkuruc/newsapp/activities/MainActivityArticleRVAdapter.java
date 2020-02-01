package pawelkuruc.newsapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pawelkuruc.newsapp.R;
import pawelkuruc.newsapp.model.Article;
import pawelkuruc.newsapp.model.ArticlesList;

public class MainActivityArticleRVAdapter extends RecyclerView.Adapter<MainActivityArticleRVAdapter.ViewHolder>{

    List<Article> articlesList = new ArticlesList().getArticles();
    Context context;

    public MainActivityArticleRVAdapter(Context context, List<Article> articlesList) {
        this.articlesList = articlesList;
        this.context = context;
    }

    private Context getContext(){return this.context;}

    @NonNull
    @Override
    public MainActivityArticleRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_article, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Article article = articlesList.get(i);
        viewHolder.tvTitle.setText(article.getTitle());
        viewHolder.tvDescription.setText(article.getDescription());
        viewHolder.tvSource.setText(article.getSource().getName());
        Picasso.with(getContext()).load(article.getUrlToImage()).into(viewHolder.ivArticleImage);
        viewHolder.itemView.setContentDescription(article.getTitle());
    }

    @Override
    public int getItemCount() {
        try {
            return articlesList.size();
        }catch (NullPointerException e){
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.ivArticleImage)
        ImageView ivArticleImage;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.tvSource)
        TextView tvSource;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Snackbar.make(v, "Z torsu do dupy (inverse Zdupynators)", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    }
}
