package com.dp.trains.model.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carrier_company")
@EqualsAndHashCode(callSuper = true)
public class CarrierCompanyEntity extends YearDiscriminatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "carrier_name")
    private String carrierName;

    @Column(name = "locomotive_series")
    private String locomotiveSeries;

    @Column(name = "locomotive_type")
    private String locomotiveType;

    @Column(name = "locomotive_weight")
    private Double locomotiveWeight;
}
