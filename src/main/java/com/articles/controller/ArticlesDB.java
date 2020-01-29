package com.articles.controller;

import com.articles.model.Article;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticlesDB {
    private List<Article> articles = new ArrayList<Article>();

    public ArticlesDB() {
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void addArticle(Article article) {
        this.articles.add(article);
    }
}
