package com.dp.trains.model.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "tax_for_services_per_train")
public class TaxForServicesPerTrainEntity extends YearDiscriminatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "train_number")
    private Integer trainNumber;

    @Column(name = "station")
    private String station;

    @Column(name = "code_of_service")
    private String code;

    @Column(name = "tax")
    private Integer tax;

    @Column(name = "number")
    private Integer number;
}
