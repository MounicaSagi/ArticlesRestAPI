package com.articles.controller;

import com.articles.model.Article;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class ArticlesController {

    List<Article> articles = new ArrayList<Article>();

    @PostMapping("/articles")
    public ResponseEntity articles(@RequestBody Article article) {
        articles.add(article);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/articles")
    public List<Article> listArticles() {
        return articles;
    }

}
