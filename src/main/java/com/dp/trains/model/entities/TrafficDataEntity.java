package com.dp.trains.model.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "traffic_data")
@EqualsAndHashCode(callSuper = true)
public class TrafficDataEntity extends YearDiscriminatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Column(name = "rank")
    private String rank;

    @Column(name = "line_length")
    private Integer lineLength;

    @Column(name = "is_electrified")
    private String isElectrified;

    @Column(name = "train_type")
    private Integer trainType;

    @Column(name = "freight_traffic_train_kilometers")
    private Integer freightTrafficTrainKilometers;

    @Column(name = "freight_traffic_train_bruto_ton_kilometers")
    private Integer freightTrafficTrainBrutoTonKilometers;

    @Column(name = "passenger_traffic_train_kilometers")
    private Integer passengerTrafficTrainKilometers;

    @Column(name = "passenger_traffic_train_bruto_ton_kilometers")
    private Integer passengerTrafficTrainBrutoTonKilometers;
}
