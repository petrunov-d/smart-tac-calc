package com.dp.trains.model.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxPerTrainReportDto {

    private Integer trainNumber;
    private String trainType;
    private Boolean isElectrified;
    private String locomotiveSeries;
    private Double locomotiveWeight;
    private Double trainWeightWithoutLocomotive;
    private Double totalTrainWeight;
    private Double trainLength;
    private String startStation;
    private String endStation;
    private String calendarOfMovement;
    private String notes;
    private Double kilometersOnElectrifiedLines;
    private Double kilometersOnNonElectrifiedHighwayAndRegionalLines;
    private Double kilometersOnNonElectrifiedLocalLines;
    private BigDecimal tax;
    private BigDecimal strategicCoefficient;
}
