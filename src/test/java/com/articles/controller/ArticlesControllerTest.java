package com.articles.controller;

import com.articles.model.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Autowired
    ArticlesDB articlesDB;

    @AfterEach
    public void reset() {
        articlesDB.emptyArticles();
    }
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

        mockMvc.perform(get("/articles"))
                .andExpect(jsonPath("$[0].title", is("New Spring")))
                .andExpect(jsonPath("$[0].content", is("There is a new spring application released by master, it is amazing")))
                .andExpect(jsonPath("$[0].tags").isNotEmpty())
                .andExpect(status().isOk());
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

        mockMvc.perform(get("/articles"))
                .andExpect(jsonPath("$[0].title", is("New Rails")))
                .andExpect(jsonPath("$[0].content", is("There is a Rails application released by master, it is amazing")))
                .andExpect(jsonPath("$[0].tags", is(Arrays.asList("master", "Rails"))))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetArticleWhenArticlesPostedSuccessfullyAccordingToTheObjectPassed() throws Exception {
        String requestBody = "{\n" +
                "  \"title\": \"New Python\",\n" +
                "  \"content\": \"There is a spring application released by master, it is amazing\",\n" +
                "  \"tags\": [\"master\", \"spring\"]\n" +
                "}";
        MockHttpServletRequestBuilder request = post("/articles")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/articles"))
                .andExpect(jsonPath("$[0].title", is("New Python")))
                .andExpect(jsonPath("$[0].content", is("There is a spring application released by master, it is amazing")))
                .andExpect(jsonPath("$[0].tags", is(Arrays.asList("master", "spring"))))
                .andExpect(status().isOk());
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


        mockMvc.perform(get("/articles"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$..title", is(List.of("New Python", "New Rails"))))
                .andExpect(jsonPath("$..content", is(List.of("There is a Python application released by master, it is amazing", "There is a Rails application released by master, it is amazing"))))
                .andExpect(jsonPath("$..tags", is(List.of(List.of("master", "Python"), List.of("master", "Rails")))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetRequestedSpecificArticle() throws Exception {

        String requestBody1 = "{\n" +
                "  \"title\": \"New Python\",\n" +
                "  \"content\": \"There is a Python application released by master, it is amazing\",\n" +
                "  \"tags\": [\"master\", \"Rails\"]\n" +
                "}";
        MockHttpServletRequestBuilder request = post("/articles")
                .content(requestBody1)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

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


        mockMvc.perform(get("/articles?tag=Rails"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("New Python")))
                .andExpect(jsonPath("$[1].title", is("New Rails")))
                .andExpect(jsonPath("$..content", is(List.of("There is a Python application released by master, it is amazing", "There is a Rails application released by master, it is amazing"))))
                .andExpect(jsonPath("$..tags", is(List.of(List.of("master", "Rails"), List.of("master", "Rails")))))
                .andDo(print())
                .andExpect(status().isOk());

    }
}