package com.dp.trains.model.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "tax_per_train")
public class TaxPerTrainEntity extends YearDiscriminatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "train_number")
    private Integer trainNumber;

    @Column(name = "train_type")
    private String trainType;

    @Column(name = "is_electrified")
    private Boolean isElectrified;

    @Column(name = "locomotive_series")
    private String locomotiveSeries;

    @Column(name = "locomotive_weight")
    private Double locomotiveWeight;

    @Column(name = "train_weight_without_locomotive")
    private Double trainWeightWithoutLocomotive;

    @Column(name = "total_train_weight")
    private Double totalTrainWeight;

    @Column(name = "train_length")
    private Double trainLength;

    @Column(name = "start_station")
    private String startStation;

    @Column(name = "end_station")
    private String endStation;

    @Column(name = "calendar_of_movement")
    private String calendarOfMovement;

    @Column(name = "notes")
    private String notes;

    @Column(name = "kilometers_on_electrified_lines")
    private Double kilometersOnElectrifiedLines;

    @Column(name = "kilometers_on_non_elecrified_highway_and_regional_lines")
    private Double kilometersOnNonElectrifiedHighwayAndRegionalLines;

    @Column(name = "kilometers_on_non_electrified_local_lines")
    private Double kilometersOnNonElectrifiedLocalLines;

    @Column(name = "tax")
    private BigDecimal tax;

    @Column(name = "correlation_id")
    private UUID correlationId;

    @Column(name = "strategic_coefficient")
    private BigDecimal strategicCoefficient;
}