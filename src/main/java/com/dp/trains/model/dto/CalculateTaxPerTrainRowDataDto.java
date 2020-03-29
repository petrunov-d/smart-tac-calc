package com.dp.trains.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CalculateTaxPerTrainRowDataDto {

    private SectionNeighboursDto railStation;
    private SectionNeighboursDto lineNumber;
    private Integer tonnage;
}
