package com.dp.trains.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CalculateFinalTaxPerTrainDto {

    private Double totalKilometers;
    private Double totalBruttoTonneKilometers;
    private Double finalTax;
}
