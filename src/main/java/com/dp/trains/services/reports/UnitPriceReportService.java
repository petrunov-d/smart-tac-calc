package com.dp.trains.services.reports;

import com.dp.trains.model.dto.report.SinglePriceReportContainerDto;
import com.dp.trains.services.UnitPriceService;
import com.dp.trains.utils.UnitPriceRetrieverByCode;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnitPriceReportService extends BaseReportService {

    private static final String REPORT_UNIT_PRICE_REPORT_FOR_FREIGHT_TRAINS = "/reports/unit_price_report_for_freight_trains.jrxml";
    private static final String REPORT_UNIT_PRICE_REPORT_FOR_PASSENGER_TRAINS = "/reports/unit_price_report_for_passenger_trains.jrxml";

    private static final String FILE_PREFIX_UNIT_PRICE_PASSENGER_TRAIN_REPORT = "report_single_price_passenger_train";
    private static final String FILE_PREFIX_UNIT_PRICE_FREIGHT_TRAIN_REPORT = "report_single_price_freight_train";

    private static final String UNIT_PRICE_DATASOURCE_NAME = "UnitPriceDataSource";

    private final UnitPriceService unitPriceService;

    public boolean hasData() {

        return unitPriceService.count() > 0;
    }

    public File getUnitPriceReportForPassengerTrainAsXLSFile() throws JRException, IOException {

        JasperPrint jasperPrint = getJasperPrintForPassengerTrainUnitPrices();

        File tmpFile = File.createTempFile(FILE_PREFIX_UNIT_PRICE_PASSENGER_TRAIN_REPORT + this.getDateFilePathComponent(), FILE_EXTENSION_XLS);

        log.info("Generated temp file:" + tmpFile.toString());

        JRXlsExporter xlsExporter = getJrXlsExporter(jasperPrint, tmpFile);

        xlsExporter.exportReport();

        return tmpFile;
    }

    public File getUnitPriceReportForPassengerTrainAsPdf() throws JRException, IOException {

        JasperPrint jasperPrint = getJasperPrintForPassengerTrainUnitPrices();

        File tmpFile = File.createTempFile(FILE_PREFIX_UNIT_PRICE_PASSENGER_TRAIN_REPORT + this.getDateFilePathComponent(), FILE_EXTENSION_PDF);

        log.info("Generated temp file:" + tmpFile.toString());

        JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(tmpFile));

        return tmpFile;
    }

    public File getUnitPriceReportForFreightTrainAsPdf() throws JRException, IOException {

        JasperPrint jasperPrint = getJasperPrintForFreightTrainUnitPrices();

        File tmpFile = File.createTempFile(FILE_PREFIX_UNIT_PRICE_PASSENGER_TRAIN_REPORT + this.getDateFilePathComponent(), FILE_EXTENSION_PDF);

        log.info("Generated temp file:" + tmpFile.toString());

        JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(tmpFile));

        return tmpFile;
    }

    public File getUnitPriceReportForFreightTrainAsXLSFile() throws JRException, IOException {
        JasperPrint jasperPrint = getJasperPrintForFreightTrainUnitPrices();

        File tmpFile = File.createTempFile(FILE_PREFIX_UNIT_PRICE_FREIGHT_TRAIN_REPORT + this.getDateFilePathComponent(), FILE_EXTENSION_XLS);

        log.info("Generated temp file:" + tmpFile.toString());

        JRXlsExporter xlsExporter = getJrXlsExporter(jasperPrint, tmpFile);

        xlsExporter.exportReport();

        return tmpFile;
    }

    private JasperPrint getJasperPrintForPassengerTrainUnitPrices() throws JRException {

        JasperReport unitPriceReportForPassengerTrains = getJasperReport(REPORT_UNIT_PRICE_REPORT_FOR_PASSENGER_TRAINS);

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(getReportDataForPassengerTrains());

        Map<String, Object> parameters = Maps.newHashMap();
        parameters.put(UNIT_PRICE_DATASOURCE_NAME, jrBeanCollectionDataSource);

        return JasperFillManager.fillReport(unitPriceReportForPassengerTrains, parameters, new JREmptyDataSource());
    }


    private JasperPrint getJasperPrintForFreightTrainUnitPrices() throws JRException {

        JasperReport unitPriceReportForPassengerTrains = getJasperReport(REPORT_UNIT_PRICE_REPORT_FOR_FREIGHT_TRAINS);

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(getReportDataForFreightTrains());

        Map<String, Object> parameters = Maps.newHashMap();
        parameters.put(UNIT_PRICE_DATASOURCE_NAME, jrBeanCollectionDataSource);

        return JasperFillManager.fillReport(unitPriceReportForPassengerTrains, parameters, new JREmptyDataSource());
    }


    private List<SinglePriceReportContainerDto> getReportDataForPassengerTrains() {

        UnitPriceRetrieverByCode unitPriceRetrieverByCode = new
                UnitPriceRetrieverByCode(this.unitPriceService.fetch(0, 0));

        SinglePriceReportContainerDto singlePriceReportContainerDto = new SinglePriceReportContainerDto();

        singlePriceReportContainerDto.setUp1(unitPriceRetrieverByCode.getUnitPriceForCode("0110"));
        singlePriceReportContainerDto.setUp2(unitPriceRetrieverByCode.getUnitPriceForCode("0111"));
        singlePriceReportContainerDto.setUp3(unitPriceRetrieverByCode.getUnitPriceForCode("1010"));
        singlePriceReportContainerDto.setUp4(unitPriceRetrieverByCode.getUnitPriceForCode("1011"));
        singlePriceReportContainerDto.setUp5(unitPriceRetrieverByCode.getUnitPriceForCode("2010"));
        singlePriceReportContainerDto.setUp6(unitPriceRetrieverByCode.getUnitPriceForCode("2011"));

        return Collections.singletonList(singlePriceReportContainerDto);
    }

    private List<SinglePriceReportContainerDto> getReportDataForFreightTrains() {

        UnitPriceRetrieverByCode unitPriceRetrieverByCode = new
                UnitPriceRetrieverByCode(this.unitPriceService.fetch(0, 0));

        SinglePriceReportContainerDto singlePriceReportContainerDto = new SinglePriceReportContainerDto();

        singlePriceReportContainerDto.setUp1(unitPriceRetrieverByCode.getUnitPriceForCode("0100"));
        singlePriceReportContainerDto.setUp2(unitPriceRetrieverByCode.getUnitPriceForCode("0101"));
        singlePriceReportContainerDto.setUp3(unitPriceRetrieverByCode.getUnitPriceForCode("1000"));
        singlePriceReportContainerDto.setUp4(unitPriceRetrieverByCode.getUnitPriceForCode("1001"));
        singlePriceReportContainerDto.setUp5(unitPriceRetrieverByCode.getUnitPriceForCode("2000"));
        singlePriceReportContainerDto.setUp6(unitPriceRetrieverByCode.getUnitPriceForCode("2001"));

        return Collections.singletonList(singlePriceReportContainerDto);
    }
}