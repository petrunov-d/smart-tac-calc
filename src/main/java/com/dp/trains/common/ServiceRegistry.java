package com.dp.trains.common;

import com.dp.trains.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceRegistry {

    private final FinancialDataService financialDataService;
    private final LineNumberService lineNumberService;
    private final LineTypeService lineTypeService;
    private final RailStationService railStationService;
    private final SectionsService sectionsService;
    private final ServiceService serviceService;
    private final StrategicCoefficientService strategicCoefficientService;
    private final TaxForServicesPerTrainService taxForServicesPerTrainService;
    private final TrafficDataService trafficDataService;
    private final TrainTypeService trainTypeService;
    private final UnitPriceService unitPriceService;

    public ExcelImportService getService(ServiceEnum serviceEnum) {

        switch (serviceEnum) {
            case FINANCIAL_DATA_SERVICE:
                return financialDataService;
            case LINE_NUMBER_SERVICE:
                return lineNumberService;
            case LINE_TYPE_SERVICE:
                return lineTypeService;
            case RAIL_STATION_SERVICE:
                return railStationService;
            case SECTIONS_SERVICE:
                return sectionsService;
            case SERVICE_SERVICE:
                return serviceService;
            case STRATEGIC_COEFFICIENTS_SERVICE:
                return strategicCoefficientService;
            case TAX_FOR_SERVICES_PER_TRAIN:
                return taxForServicesPerTrainService;
            case TRAFFIC_DATA_SERVICE:
                return trafficDataService;
            case TRAIN_TYPE_SERVICE:
                return trainTypeService;
            case UNIT_PRICE_SERVICE:
                return unitPriceService;
        }

        throw new IllegalStateException("Service not found for service enum:" + serviceEnum);
    }
}