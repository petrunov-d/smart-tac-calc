package com.dp.trains.event;

import com.dp.trains.model.dto.LocomotiveSeriesDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CPPTLocomotiveSeriesChangedEvent {

    private LocomotiveSeriesDto locomotiveSeriesDto;
}
