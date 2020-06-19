package com.dp.trains.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CPPTCarrierCompanyChangedEvent {

    private String selectedDto;
}
