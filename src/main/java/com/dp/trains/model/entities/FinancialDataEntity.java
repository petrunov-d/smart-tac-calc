package com.dp.trains.model.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "financial_data")
@EqualsAndHashCode(callSuper = true)
public class FinancialDataEntity extends YearDiscriminatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Column(name = "description")
    private String lineType;

    @Column(name = "line_length")
    private Integer lineLength;

    @Column(name = "is_electrified")
    private String isElectrified;

    @Column(name = "account_group_1")
    private Integer accountGroup1;

    @Column(name = "account_group_21")
    private Integer accountGroup21;

    @Column(name = "account_group_40")
    private Integer accountGroup40;

    @Column(name = "account_group_42")
    private Integer accountGroup42;

    @Column(name = "account_group_43")
    private Integer accountGroup43;

    @Column(name = "account_group_44")
    private Integer accountGroup44;

    @Column(name = "account_group_45")
    private Integer accountGroup45;

    @Column(name = "account_group_46")
    private Integer accountGroup46;

    @Column(name = "account_group_47")
    private Integer accountGroup47;

    @Column(name = "account_group_48")
    private Integer accountGroup48;

    @Column(name = "account_group_49")
    private Integer accountGroup49;
}
