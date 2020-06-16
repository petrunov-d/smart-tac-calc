package com.dp.trains.model.dto;

import com.dp.trains.model.entities.ServiceChargesPerTrainEntity;
import com.dp.trains.model.viewmodels.RailStationViewModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CPPTRowDataDto {

    private Double tonnage;
    private String locomotiveSeries;
    private Double locomotiveWeight;
    private RailStationViewModel stationViewModel;
    private List<ServiceChargesPerTrainEntity> serviceChargesPerTrainEntityList;
}
