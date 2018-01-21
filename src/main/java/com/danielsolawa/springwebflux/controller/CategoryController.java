package com.danielsolawa.springwebflux.controller;

import com.danielsolawa.springwebflux.domain.Category;
import com.danielsolawa.springwebflux.domain.Vendor;
import com.danielsolawa.springwebflux.repository.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Daniel Solawa on 2018-01-20.
 */

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {


    public static final String BASE_URL = "/api/v1/categories";

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Flux<Category> getCategoryList(){

        return categoryRepository.findAll();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Category> getCategoryById(@PathVariable String id){
        return categoryRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> createNewCategory(@RequestBody Publisher<Category> category){

       return categoryRepository.saveAll(category).then();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Category> updateCategory(@PathVariable String id,@RequestBody Category category){
        category.setId(id);

        return categoryRepository.save(category);
    }

    @PatchMapping("/{id}")
    Mono<Category> patchCategory(@PathVariable String id, @RequestBody Category category){
        Category foundCategory = categoryRepository.findById(id).block();

        if(category.getDescription() != foundCategory.getDescription()){
            foundCategory.setDescription(category.getDescription());

            return categoryRepository.save(foundCategory);
        }

        return Mono.just(foundCategory);
    }
}
