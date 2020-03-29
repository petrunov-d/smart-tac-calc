package com.dp.trains.model.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sub_section")
@EqualsAndHashCode(callSuper = true)
public class SubSectionEntity extends YearDiscriminatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kilometers")
    private Double kilometers;

    @Column(name = "non_key_station")
    private String nonKeyStation;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_fk")
    private SectionEntity sectionEntity;
}
