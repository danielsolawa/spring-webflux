package com.danielsolawa.springwebflux.controller;

import com.danielsolawa.springwebflux.domain.Category;
import com.danielsolawa.springwebflux.domain.Vendor;
import com.danielsolawa.springwebflux.repository.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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


    @Test
    public void testCreateNewVendor() throws Exception {
        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Emma").lastName("Watson").build());

        given(vendorRepository.saveAll(any(Publisher.class))).willReturn(Flux.just(Vendor.builder().build()));

        webTestClient.post()
                    .uri(VendorController.BASE_URL)
                    .body(vendorMono, Vendor.class)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(Vendor.class);

        then(vendorRepository).should().saveAll(any(Publisher.class));

    }


    @Test
    public void testUpdateVendor() throws Exception {
        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("tom").lastName("hanks").build());

        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));

        webTestClient.put()
                    .uri(VendorController.BASE_URL + "/dfrdf")
                    .body(vendorMono, Vendor.class)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(Vendor.class);

        then(vendorRepository).should().save(any(Vendor.class));


    }

    @Test
    public void namePatchVendorWithChanges() {
        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Martin").lastName("Luther").build());

        given(vendorRepository.findById(anyString())).willReturn(Mono.just(Vendor.builder().build()));
        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));

        webTestClient.patch()
                .uri(VendorController.BASE_URL + "/eddfdf")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Vendor.class);


        verify(vendorRepository, times(1)).findById(anyString());
        verify(vendorRepository, times(1)).save(any(Vendor.class));
    }

    @Test
    public void namePatchVendorWithNoChanges() {
        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().build());

        given(vendorRepository.findById(anyString())).willReturn(Mono.just(Vendor.builder().build()));
        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));

        webTestClient.patch()
                .uri(VendorController.BASE_URL + "/eddfdf")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Vendor.class);


        verify(vendorRepository, times(1)).findById(anyString());
        verify(vendorRepository, never()).save(any(Vendor.class));
    }
}