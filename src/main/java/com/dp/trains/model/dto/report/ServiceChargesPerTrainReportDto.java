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
public class ServiceChargesPerTrainReportDto {

    private Integer trainNumber;
    private String railStationType;
    private String railStationName;
    private String serviceName;
    private Integer serviceCount;
    private Double servicePrice;
    private BigDecimal totalPrice;
}
