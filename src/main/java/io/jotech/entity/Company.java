package io.jotech.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class Company {
    @Column
    private String companyName;
    @Column
    private String catchPhrase;
    @Column
    private String bs;
}
