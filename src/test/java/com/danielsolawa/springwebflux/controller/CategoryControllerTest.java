package com.danielsolawa.springwebflux.controller;

import com.danielsolawa.springwebflux.domain.Category;
import com.danielsolawa.springwebflux.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * Created by Daniel Solawa on 2018-01-20.
 */
public class CategoryControllerTest {


    CategoryController categoryController;

    CategoryRepository categoryRepository;

    WebTestClient webTestClient;


    @Before
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);

        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void testGetCategoryList() throws Exception {
        given(categoryRepository.findAll()).willReturn(Flux.just(
                Category.builder().description("cars").build(),
                Category.builder().description("sports").build()
        ));

        webTestClient.get()
                .uri("/api/v1/categories/")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Category.class)
                .hasSize(2);

        then(categoryRepository).should().findAll();


    }

    @Test
    public void testGetCategoryById() throws Exception {
        Category category = Category.builder().id("43434344").description("music").build();

        given(categoryRepository.findById(anyString())).willReturn(Mono.just(category));

        webTestClient.get()
                .uri("/api/v1/categories/43434344")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Category.class);

        then(categoryRepository).should().findById(anyString());

    }

    @Test
    public void testCreateNewCategory() throws Exception {

        Mono<Category> categoryMono = Mono.just(Category.builder().description("Music").build());
        given(categoryRepository.saveAll(any(Publisher.class))).willReturn(Flux.just(
               Category.builder().build()));

        webTestClient.post()
                .uri(CategoryController.BASE_URL)
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Category.class);

        then(categoryRepository).should().saveAll(any(Publisher.class));




    }
}