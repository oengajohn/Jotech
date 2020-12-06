package io.jotech.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Data
@Embeddable
@NoArgsConstructor
public class Address {
    @Column
    private String street;
    @Column
    private String suite;
    @Column
    private String city;
    @Column
    private String zipcode;
    @Embedded
    private Geo geo;
}
