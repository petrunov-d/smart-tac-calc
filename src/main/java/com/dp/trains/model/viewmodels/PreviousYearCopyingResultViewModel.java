package com.dp.trains.model.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreviousYearCopyingResultViewModel {

    private String displayName;
    private Integer copyCount;
}
