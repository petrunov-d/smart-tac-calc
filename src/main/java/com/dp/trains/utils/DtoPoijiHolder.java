package com.dp.trains.utils;

import com.dp.trains.common.ServiceEnum;
import com.dp.trains.model.dto.ExcelImportDto;
import com.poiji.option.PoijiOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoPoijiHolder {

    private Class<? extends ExcelImportDto> dtoClass;
    private ServiceEnum serviceEnum;
    private PoijiOptions poijiOptions;
}
