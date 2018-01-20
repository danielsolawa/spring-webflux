package com.danielsolawa.springwebflux.controller;

import com.danielsolawa.springwebflux.domain.Vendor;
import com.danielsolawa.springwebflux.repository.VendorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Daniel Solawa on 2018-01-20.
 */

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Flux<Vendor> getVendorList(){
        return vendorRepository.findAll();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Vendor> getVendorById(@PathVariable  String id){
        return vendorRepository.findById(id);
    }
}
