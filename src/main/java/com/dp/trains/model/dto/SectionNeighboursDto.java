package com.dp.trains.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SectionNeighboursDto {

    private Integer lineNumber;
    private String typeOfLine;
    private String station;
    private Boolean isElectrified;
    private Double unitPrice;
    private int rowCount;
}
