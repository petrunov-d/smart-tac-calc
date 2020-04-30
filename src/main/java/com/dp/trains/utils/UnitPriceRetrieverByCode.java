package com.dp.trains.utils;

import com.dp.trains.model.entities.UnitPriceEntity;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;

public class UnitPriceRetrieverByCode {

    private static final int MINIMUM_FRACTION_DIGITS = 0;
    private static final int MAXIMUM_FRACTION_DIGITS = 0;

    private final DecimalFormat decimalFormat = new DecimalFormat();
    private final Map<String, UnitPriceEntity> backingMap = Maps.newHashMap();

    public UnitPriceRetrieverByCode(Collection<UnitPriceEntity> unitPriceEntities) {

        unitPriceEntities.forEach(x -> this.backingMap.put(x.getCode(), x));

        this.decimalFormat.setMinimumFractionDigits(MINIMUM_FRACTION_DIGITS);
        this.decimalFormat.setMaximumFractionDigits(MAXIMUM_FRACTION_DIGITS);

        this.decimalFormat.setGroupingUsed(true);
    }

    public String getUnitPriceForCode(String code) {

        BigDecimal unitPrice = this.backingMap.get(code).getUnitPrice();
        BigDecimal stripped = unitPrice.stripTrailingZeros();

        return decimalFormat.format(stripped);
    }
}