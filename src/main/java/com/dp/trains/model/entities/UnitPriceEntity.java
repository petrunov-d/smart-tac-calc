package com.dp.trains.model.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unit_price")
@EqualsAndHashCode(callSuper = true)
public class UnitPriceEntity extends YearDiscriminatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "measure")
    private String measure;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;
}
