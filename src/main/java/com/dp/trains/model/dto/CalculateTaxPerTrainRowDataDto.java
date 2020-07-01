package com.dp.trains.model.dto;

import com.dp.trains.model.entities.ServiceChargesPerTrainEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculateTaxPerTrainRowDataDto {

    private Double tonnage;
    private String locomotiveSeries;
    private Double locomotiveWeight;
    private SectionNeighboursDto section;
    private List<ServiceChargesPerTrainEntity> serviceChargesPerTrainEntityList;

}
