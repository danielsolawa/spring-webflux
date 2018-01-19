package com.danielsolawa.springwebflux.bootstrap;

import com.danielsolawa.springwebflux.domain.Category;
import com.danielsolawa.springwebflux.domain.Vendor;
import com.danielsolawa.springwebflux.repository.CategoryRepository;
import com.danielsolawa.springwebflux.repository.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Daniel Solawa on 2018-01-19.
 */
@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadVendors();



    }

    private void loadVendors() {
        if(vendorRepository.count().block() == 0){
            Vendor v1 = new Vendor();
            v1.setFirstName("Selena");
            v1.setLastName("Gomez");

            Vendor v2 = new Vendor();
            v2.setFirstName("Ariana");
            v2.setLastName("Grande");

            Vendor v3 = new Vendor();
            v3.setFirstName("Taylor");
            v3.setLastName("Hill");

            vendorRepository.save(v1).block();
            vendorRepository.save(v2).block();
            vendorRepository.save(v3).block();
        }

        log.info("Vendors loaded " + vendorRepository.count().block());

    }

    private void loadCategories() {
        if(categoryRepository.count().block() == 0){
            Category c1 = new Category();
            c1.setDescription("Books");

            Category c2 = new Category();
            c2.setDescription("Sports");

            Category c3 = new Category();
            c3.setDescription("Cars");

            categoryRepository.save(c1).block();
            categoryRepository.save(c2).block();
            categoryRepository.save(c3).block();


        }

        log.info("Categories loaded " + categoryRepository.count().block());

    }
}
