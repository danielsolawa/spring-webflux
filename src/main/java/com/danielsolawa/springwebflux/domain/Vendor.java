package com.danielsolawa.springwebflux.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Daniel Solawa on 2018-01-19.
 */

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vendor {

    @Id
    private String id;
    private String firstName;
    private String lastName;
}
