package com.dp.trains.model.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "line_type")
@EqualsAndHashCode(callSuper = true)
public class LineTypeEntity extends YearDiscriminatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_type")
    private String lineType;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private Integer code;
}
