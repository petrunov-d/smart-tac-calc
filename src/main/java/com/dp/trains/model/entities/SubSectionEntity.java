package com.dp.trains.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sub_section")
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SubSectionEntity extends YearDiscriminatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kilometers")
    private Double kilometers;

    @Column(name = "non_key_station")
    private String nonKeyStation;

    @ToString.Exclude
    @JoinColumn(name = "section_fk")
    @ManyToOne(cascade = {CascadeType.ALL},fetch= FetchType.EAGER)
    private SectionEntity sectionEntity;
}
