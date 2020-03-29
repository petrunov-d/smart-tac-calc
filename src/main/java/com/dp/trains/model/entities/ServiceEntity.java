package com.dp.trains.model.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service")
@EqualsAndHashCode(callSuper = true)
public class ServiceEntity extends YearDiscriminatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private Integer code;

    @Column(name = "metric")
    private String metric;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;
}
