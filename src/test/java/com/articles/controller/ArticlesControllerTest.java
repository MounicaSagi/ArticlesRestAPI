package com.articles.controller;

import com.articles.model.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ArticlesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ArticlesDB articlesDB;

    @Test
    public void shouldReturn204WhenArticlesPostedSuccessfully() throws Exception {
        String requestBody = "{\n" +
                "  \"title\": \"New Spring\",\n" +
                "  \"content\": \"There is a new spring application released by master, it is amazing\",\n" +
                "  \"tags\": [\"master\", \"spring\"]\n" +
                "}";

        MockHttpServletRequestBuilder request = post("/articles")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        Article expectedArticle = new Article("New Spring", "There is a new spring application released by master, it is amazing", Arrays.asList("master", "spring"));
        verify(articlesDB).addArticle(expectedArticle);
    }

    @Test
    public void shouldGetArticleWhenArticlesPostedSuccessfully() throws Exception {
        String requestBody = "{\n" +
                "  \"title\": \"New Rails\",\n" +
                "  \"content\": \"There is a Rails application released by master, it is amazing\",\n" +
                "  \"tags\": [\"master\", \"Rails\"]\n" +
                "}";
        MockHttpServletRequestBuilder request = post("/articles")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        Article expectedArticle = new Article("New Rails", "There is a Rails application released by master, it is amazing", Arrays.asList("master", "Rails"));
        verify(articlesDB).addArticle(expectedArticle);
    }

    @Test
    public void shouldGetArticlesListWhenArticlesPostedSuccessfully() throws Exception {
        String requestBody1 = "{\n" +
                "  \"title\": \"New Python\",\n" +
                "  \"content\": \"There is a Python application released by master, it is amazing\",\n" +
                "  \"tags\": [\"master\", \"Python\"]\n" +
                "}";
        MockHttpServletRequestBuilder request = post("/articles")
                .content(requestBody1)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        Article expectedArticle1 = new Article("New Python", "There is a Python application released by master, it is amazing", Arrays.asList("master", "Python"));
        verify(articlesDB).addArticle(expectedArticle1);

        String requestBody2 = "{\n" +
                "  \"title\": \"New Rails\",\n" +
                "  \"content\": \"There is a Rails application released by master, it is amazing\",\n" +
                "  \"tags\": [\"master\", \"Rails\"]\n" +
                "}";

        mockMvc.perform(post("/articles")
                .content(requestBody2)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        Article expectedArticle2 = new Article("New Rails", "There is a Rails application released by master, it is amazing", Arrays.asList("master", "Rails"));
        verify(articlesDB).addArticle(expectedArticle2);

    }

    @Test
    public void shouldGetRequestedSpecificArticle() throws Exception {

        Article article1 = new Article("New Python", "There is a Python application released by master, it is amazing", Arrays.asList("master", "Python", "Rails"));
        Article article2 = new Article("New Rails", "There is a Rails application released by master, it is amazing", Arrays.asList("master", "Rails"));

        when(articlesDB.getArticles()).thenReturn(Arrays.asList(article1, article2));

        mockMvc.perform(get("/articles?tag=Rails"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("New Python")))
                .andExpect(jsonPath("$[1].title", is("New Rails")))
                .andExpect(jsonPath("$..content", is(List.of("There is a Python application released by master, it is amazing", "There is a Rails application released by master, it is amazing"))))
                .andExpect(jsonPath("$..tags", is(List.of(List.of("master", "Python", "Rails"), List.of("master", "Rails")))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetAllArticles() throws Exception {

        Article article1 = new Article("New Python", "There is a Python application released by master, it is amazing", Arrays.asList("master", "Python", "Rails"));
        Article article2 = new Article("New Rails", "There is a Rails application released by master, it is amazing", Arrays.asList("master", "Rails"));

        when(articlesDB.getArticles()).thenReturn(Arrays.asList(article1, article2));

        mockMvc.perform(get("/articles"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("New Python")))
                .andExpect(jsonPath("$[1].title", is("New Rails")))
                .andExpect(jsonPath("$..content", is(List.of("There is a Python application released by master, it is amazing", "There is a Rails application released by master, it is amazing"))))
                .andExpect(jsonPath("$..tags", is(List.of(List.of("master", "Python", "Rails"), List.of("master", "Rails")))))
                .andDo(print())
                .andExpect(status().isOk());
    }
}