package com.dp.trains.model.dto;

import com.dp.trains.model.entities.TaxForServicesPerTrainEntity;
import com.googlecode.jmapper.annotations.JMap;
import com.poiji.annotation.ExcelCell;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxForServicesPerTrainDto implements ExcelImportDto {

    @ExcelCell(0)
    @JMap(value = "trainNumber", classes = TaxForServicesPerTrainEntity.class)
    private Integer trainNumber;

    @ExcelCell(1)
    @JMap(value = "station", classes = TaxForServicesPerTrainEntity.class)
    private String station;

    @ExcelCell(2)
    @JMap(value = "codeOfService", classes = TaxForServicesPerTrainEntity.class)
    private Integer codeOfService;

    @ExcelCell(3)
    @JMap(value = "tax", classes = TaxForServicesPerTrainEntity.class)
    private Integer tax;

    @ExcelCell(4)
    @JMap(value = "number", classes = TaxForServicesPerTrainEntity.class)
    private Integer number;
}
