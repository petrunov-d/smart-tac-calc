package com.dp.trains.utils;

import com.poiji.option.PoijiOptions;

public class PoijiOptionsFactory {

    public static PoijiOptions defaultOptions() {

        return PoijiOptions.PoijiOptionsBuilder.settings()
                .ignoreHiddenSheets(true)
                .preferNullOverDefault(true)
                .trimCellValue(true)
                .caseInsensitive(true)
                .build();
    }

    public static PoijiOptions defaultWithSkip(int skip) {

        return PoijiOptions.PoijiOptionsBuilder.settings()
                .ignoreHiddenSheets(true)
                .preferNullOverDefault(true)
                .skip(skip)
                .trimCellValue(true)
                .caseInsensitive(true)
                .build();
    }
}