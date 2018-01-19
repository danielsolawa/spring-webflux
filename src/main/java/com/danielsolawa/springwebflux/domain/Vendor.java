package com.danielsolawa.springwebflux.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Daniel Solawa on 2018-01-19.
 */

@Data
@Document
public class Vendor {

    @Id
    private String id;
    private String firstName;
    private String lastName;
}
