package com.dp.trains.model.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rail_station")
@EqualsAndHashCode(callSuper = true)
public class RailStationEntity extends YearDiscriminatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Column(name = "station")
    private String station;

    @Column(name = "type")
    private String type;

    @Column(name = "is_key_station")
    private Boolean isKeyStation;

    @Column(name = "country")
    private String country;
}
