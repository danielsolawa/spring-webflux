package com.danielsolawa.springwebflux.controller;

import com.danielsolawa.springwebflux.domain.Vendor;
import com.danielsolawa.springwebflux.repository.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * Created by Daniel Solawa on 2018-01-20.
 */
public class VendorControllerTest {

    VendorController vendorController;

    VendorRepository vendorRepository;

    WebTestClient webTestClient;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);

        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void testGetVendorList() throws Exception {
        given(vendorRepository.findAll()).willReturn(Flux.just(
                Vendor.builder().firstName("Thomas").lastName("Wayne").build(),
                Vendor.builder().firstName("Jasmine").lastName("Tooks").build(),
                Vendor.builder().firstName("Angelina").lastName("Jolie").build()
        ));

        webTestClient.get()
                    .uri(VendorController.BASE_URL)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBodyList(Vendor.class)
                    .hasSize(3);

        then(vendorRepository).should().findAll();
    }

    @Test
    public void testGetVendorById() throws Exception {
        Vendor vendor = Vendor.builder().id("123").firstName("Thomas").lastName("Wayne").build();

        given(vendorRepository.findById(anyString())).willReturn(Mono.just(vendor));

        webTestClient.get()
                    .uri(VendorController.BASE_URL + "/123")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(Vendor.class);

        then(vendorRepository).should().findById(anyString());

    }
}