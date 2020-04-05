package com.dp.trains.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class CalculateFinalTaxPerTrainDto {

    private BigDecimal totalKilometers;
    private BigDecimal totalBruttoTonneKilometers;
    private BigDecimal finalTax;
    private boolean errorInCalculation;
    private String stackTrace;
}
