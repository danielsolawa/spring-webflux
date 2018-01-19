package com.danielsolawa.springwebflux.repository;

import com.danielsolawa.springwebflux.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by Daniel Solawa on 2018-01-19.
 */
public interface CategoryRepository extends ReactiveMongoRepository<Category, String>{
}
