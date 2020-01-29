package com.articles.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Article {
    private String title;
    private String content;
    private List<String> tags;

    public Article() {
    }

    public Article(String title, String content, List<String> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(title, article.title) &&
                Objects.equals(content, article.content) &&
                Objects.equals(tags, article.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, tags);
    }
}
