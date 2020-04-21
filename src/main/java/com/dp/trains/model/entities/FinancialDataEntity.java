package com.dp.trains.model.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @Column(name = "direct_cost_description")
    private String directCostDescrption;

    @Column(name = "direct_cost_value")
    private BigDecimal directCostValue;

    @Column(name = "code")
    private String code;
}
