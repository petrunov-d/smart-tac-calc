package com.dp.trains.services;

import com.dp.trains.model.dto.ServiceChargesPerTrainReportDto;
import com.dp.trains.model.entities.TaxPerTrainEntity;
import com.dp.trains.utils.TaxPerTrainEntityMerger;
import com.dp.trains.utils.UnitPriceRetrieverByCode;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private static final String REPORT_UNIT_PRICE_FILE_PATH = "/reports/unit_price_report.jrxml";
    private static final String REPORT_TAC_FILE_PATH = "/reports/tac_report.jrxml";
    private static final String REPORT_SERVICE_CHARGES_PER_TRAIN_FILE_PATH = "/reports/service_charges_per_train_report.jrxml";

    private static final String FILE_EXTENSION_XLS = ".xls";
    private static final String FILE_EXTENSION_PDF = ".pdf";

    private static final String FILE_PREFIX_SINGLE_PRICE_REPORT = "report_single_price_";
    private static final String FILE_PREFIX_SERVICE_CHARGES_PER_TRAIN = "report_service_charges_";
    private static final String FILE_PREFIX_TAC = "report_tac_";
    public static final String FILE_TIMESTAMP = "dd-MM-yyyy_HH-mm-ss";

    private final UnitPriceService unitPriceService;
    private final TaxPerTrainService taxPerTrainService;
    private final ServiceChargesPerTrainService serviceChargesPerTrainService;

    public File getUnitPriceReportAsXLSFile() throws JRException, IOException {

        JasperPrint jasperPrint = getJasperPrintForUnitPrices();

        File tmpFile = File.createTempFile(FILE_PREFIX_SINGLE_PRICE_REPORT + this.getDateFilePathComponent(), FILE_EXTENSION_XLS);

        JRXlsExporter xlsExporter = getJrXlsExporter(jasperPrint, tmpFile);

        xlsExporter.exportReport();

        return tmpFile;
    }

    public File getUnitPriceReportAsPdf() throws JRException, IOException {

        JasperPrint jasperPrint = getJasperPrintForUnitPrices();

        File tmpFile = File.createTempFile(FILE_PREFIX_SINGLE_PRICE_REPORT + this.getDateFilePathComponent(), FILE_EXTENSION_PDF);

        JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(tmpFile));

        return tmpFile;
    }

    public File getTacReportAsPdf(Integer trainNumber) throws JRException, IOException {

        JasperPrint jasperPrint = getJasperPrintForTac(trainNumber);

        File tmpFile = File.createTempFile(FILE_PREFIX_TAC + this.getDateFilePathComponent(), FILE_EXTENSION_PDF);

        JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(tmpFile));

        return tmpFile;
    }

    public File getTacReportAsExcel(Integer trainNumber) throws JRException, IOException {

        JasperPrint jasperPrint = getJasperPrintForServiceCharges(trainNumber);

        File tmpFile = File.createTempFile(FILE_PREFIX_TAC + this.getDateFilePathComponent(), FILE_EXTENSION_XLS);

        JRXlsExporter xlsExporter = getJrXlsExporter(jasperPrint, tmpFile);

        xlsExporter.exportReport();

        return tmpFile;
    }

    public File getServiceChargesPerTrainAsPdf(Integer trainNumber) throws JRException, IOException {

        JasperPrint jasperPrint = getJasperPrintForServiceCharges(trainNumber);

        File tmpFile = File.createTempFile(FILE_PREFIX_SERVICE_CHARGES_PER_TRAIN + this.getDateFilePathComponent(), FILE_EXTENSION_PDF);

        JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(tmpFile));

        return tmpFile;
    }

    public File getServiceChargesPerTrainAsXls(Integer trainNumber) throws JRException, IOException {

        JasperPrint jasperPrint = getJasperPrintForServiceCharges(trainNumber);

        File tmpFile = File.createTempFile(FILE_PREFIX_SERVICE_CHARGES_PER_TRAIN + this.getDateFilePathComponent(), FILE_EXTENSION_XLS);

        JRXlsExporter xlsExporter = getJrXlsExporter(jasperPrint, tmpFile);

        xlsExporter.exportReport();

        return tmpFile;
    }

    private JasperPrint getJasperPrintForTac(Integer trainNumber) throws JRException {

        JasperReport jasperReport = getJasperReport(REPORT_TAC_FILE_PATH);

        List<TaxPerTrainEntity> reportDtoList = taxPerTrainService.getByTrainNumber(trainNumber);
        TaxPerTrainEntityMerger taxPerTrainEntityMerger = new TaxPerTrainEntityMerger(reportDtoList);

        List<TaxPerTrainEntity> mergedReportDtos = taxPerTrainEntityMerger.getReportDtos();

        JRBeanCollectionDataSource serviceChargesDataSource = new JRBeanCollectionDataSource(mergedReportDtos, false);

        return JasperFillManager.fillReport(jasperReport, Maps.newHashMap(), serviceChargesDataSource);
    }

    private JasperPrint getJasperPrintForServiceCharges(Integer trainNumber) throws JRException {

        JasperReport jasperReport = getJasperReport(REPORT_SERVICE_CHARGES_PER_TRAIN_FILE_PATH);

        List<ServiceChargesPerTrainReportDto> reportDtoList = serviceChargesPerTrainService.getReportDtosForTrainNumber(trainNumber);
        JRBeanCollectionDataSource serviceChargesDataSource = new JRBeanCollectionDataSource(reportDtoList, false);

        return JasperFillManager.fillReport(jasperReport, Maps.newHashMap(), serviceChargesDataSource);
    }

    private JasperPrint getJasperPrintForUnitPrices() throws JRException {

        JasperReport jasperReport = getJasperReport(REPORT_UNIT_PRICE_FILE_PATH);

        UnitPriceRetrieverByCode unitPriceRetrieverByCode =
                new UnitPriceRetrieverByCode(this.unitPriceService.fetch(0, 0));

        Map<String, Object> parameters = Maps.newHashMap();

        parameters.put("UP_1", unitPriceRetrieverByCode.getUnitPriceForCode("0110"));
        parameters.put("UP_2", unitPriceRetrieverByCode.getUnitPriceForCode("0111"));
        parameters.put("UP_3", unitPriceRetrieverByCode.getUnitPriceForCode("1010"));
        parameters.put("UP_4", unitPriceRetrieverByCode.getUnitPriceForCode("1011"));
        parameters.put("UP_5", unitPriceRetrieverByCode.getUnitPriceForCode("2010"));
        parameters.put("UP_6", unitPriceRetrieverByCode.getUnitPriceForCode("2011"));

        parameters.put("UP_7", unitPriceRetrieverByCode.getUnitPriceForCode("0100"));
        parameters.put("UP_8", unitPriceRetrieverByCode.getUnitPriceForCode("0101"));
        parameters.put("UP_9", unitPriceRetrieverByCode.getUnitPriceForCode("1000"));
        parameters.put("UP_10", unitPriceRetrieverByCode.getUnitPriceForCode("1001"));
        parameters.put("UP_11", unitPriceRetrieverByCode.getUnitPriceForCode("2000"));
        parameters.put("UP_12", unitPriceRetrieverByCode.getUnitPriceForCode("2001"));

        return JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
    }

    private JasperReport getJasperReport(String reportName) throws JRException {

        InputStream employeeReportStream = getClass().getResourceAsStream(reportName);

        return JasperCompileManager.compileReport(employeeReportStream);
    }

    private String getDateFilePathComponent() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(FILE_TIMESTAMP);

        return dateFormat.format(new Date());
    }

    private JRXlsExporter getJrXlsExporter(JasperPrint jasperPrint, File tmpFile) throws FileNotFoundException {

        JRXlsExporter xlsExporter = new JRXlsExporter();

        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new FileOutputStream(tmpFile)));

        SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
        xlsReportConfiguration.setOnePagePerSheet(false);
        xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
        xlsReportConfiguration.setDetectCellType(false);
        xlsReportConfiguration.setWhitePageBackground(false);
        xlsExporter.setConfiguration(xlsReportConfiguration);
        return xlsExporter;
    }
}