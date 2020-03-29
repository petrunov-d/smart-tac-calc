package com.dp.trains.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_charges_per_train")
public class ServiceChargesPerTrainEntity extends YearDiscriminatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "train_number")
    private Integer trainNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rail_station_id")
    private RailStationEntity railStationEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private ServiceEntity serviceEntity;

    @Column(name = "service_count")
    private Integer serviceCount;
}