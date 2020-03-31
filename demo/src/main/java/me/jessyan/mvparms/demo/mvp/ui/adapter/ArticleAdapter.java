package me.jessyan.mvparms.demo.mvp.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.model.entity.Article;
import me.jessyan.mvparms.demo.mvp.ui.holder.ArticleItemHolder;

public class ArticleAdapter extends DefaultAdapter<Article> {

    public ArticleAdapter(List<Article> infos) {
        super(infos);
    }

    @NonNull
    @Override
    public BaseHolder<Article> getHolder(@NonNull View v, int viewType) {
        return new ArticleItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_article;
    }
}
