package com.articles.controller;

import com.articles.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ArticlesController {

    @Autowired
    ArticlesDB articles;

    @PostMapping("/articles")
    public ResponseEntity articles(@RequestBody Article article) {
        articles.addArticle(article);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/articles")
    public List<Article> listArticles(@RequestParam (required = false) String tag) {
        if (tag == null || tag.isEmpty()) {
            return articles.getArticles();
        }
        return articles.getArticles().stream().filter(article -> article.getTags().contains(tag)).collect(Collectors.toList());
    }

}
