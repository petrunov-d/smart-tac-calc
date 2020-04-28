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
    private Double tonnage;
    private String locomotiveSeries;
    private Double locomotiveWeight;
    private List<ServiceChargesPerTrainEntity> serviceChargesPerTrainEntityList;
}
