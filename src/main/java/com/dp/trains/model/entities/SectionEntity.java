package com.dp.trains.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "section")
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SectionEntity extends YearDiscriminatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Column(name = "line_type")
    private String lineType;

    @Column(name = "first_key_point")
    private String firstKeyPoint;

    @Column(name = "last_key_point")
    private String lastKeyPoint;

    @Column(name = "kilometers_between_stations")
    private Double kilometersBetweenStations;

    @Column(name = "is_electrified")
    private Boolean isElectrified;

    @Column(name = "unit_price")
    private Double unitPrice;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "sectionEntity")
    private List<SubSectionEntity> subSectionEntities;
}
