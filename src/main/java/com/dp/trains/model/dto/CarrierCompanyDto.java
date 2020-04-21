package com.dp.trains.model.dto;

import com.dp.trains.model.entities.CarrierCompanyEntity;
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
public class CarrierCompanyDto implements ExcelImportDto {

    @ExcelCell(0)
    @JMap(value = "carrierName", classes = CarrierCompanyEntity.class)
    private String carrierName;

    @ExcelCell(1)
    @JMap(value = "locomotiveSeries", classes = CarrierCompanyEntity.class)
    private String locomotiveSeries;

    @ExcelCell(2)
    @JMap(value = "locomotiveType", classes = CarrierCompanyEntity.class)
    private String locomotiveType;

    @ExcelCell(3)
    @JMap(value = "locomotiveWeight", classes = CarrierCompanyEntity.class)
    private Double locomotiveWeight;
}
