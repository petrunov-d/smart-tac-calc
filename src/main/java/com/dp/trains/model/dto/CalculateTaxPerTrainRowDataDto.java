package com.dp.trains.model.dto;

import com.dp.trains.model.entities.ServiceChargesPerTrainEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CalculateTaxPerTrainRowDataDto {

    private SectionNeighboursDto section;
    private Integer tonnage;
    private List<ServiceChargesPerTrainEntity> serviceChargesPerTrainEntityList;
}
