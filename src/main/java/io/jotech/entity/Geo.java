package io.jotech.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Data
@NoArgsConstructor
public class Geo {
    @Column
    private BigDecimal lat;

    @Column
    private BigDecimal lng;
}
