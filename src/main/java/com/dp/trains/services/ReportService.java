package com.dp.trains.services;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private static final String REPORT_UNIT_PRICE_FILE_PATH = "/reports/unit_price_report.jrxml";
    private static final String REPORT_TAC_FILE_PATH = "/reports/tac_report.jrxml";
    private static final String REPORT_TRAIN_WEIGHT_CHANGE_FILE_PATH = "/reports/train_weight_change_report.jrxml";

    private final UnitPriceService unitPriceService;

    public void getUnitPriceReportAsWordFile() throws JRException {

        JasperReport jasperReport = getJasperReport(REPORT_UNIT_PRICE_FILE_PATH);

        Map<String, Object> parameters = Maps.newHashMap();
        parameters.put("UP_1", 1);
        parameters.put("UP_2", 2);
        parameters.put("UP_3", 3);
        parameters.put("UP_4", 4);
        parameters.put("UP_5", 5);
        parameters.put("UP_6", 6);
        parameters.put("UP_7", 7);
        parameters.put("UP_8", 8);
        parameters.put("UP_9", 9);
        parameters.put("UP_10", 10);
        parameters.put("UP_11", 11);
        parameters.put("UP_12", 12);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                parameters, new JREmptyDataSource());

        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        File exportReportFile = new File("/Users/datskopetrunov/Desktop/sdfad.docx");
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));
        exporter.exportReport();
    }


    public void getTacReport(Integer value) throws JRException {

        JasperReport jasperReport = getJasperReport(REPORT_TAC_FILE_PATH);
    }

    public void getTrainWeightChangeReport(Integer value) throws JRException {

        JasperReport jasperReport = getJasperReport(REPORT_TRAIN_WEIGHT_CHANGE_FILE_PATH);
    }

    private JasperReport getJasperReport(String reportName) throws JRException {

        InputStream employeeReportStream = getClass().getResourceAsStream(reportName);

        return JasperCompileManager.compileReport(employeeReportStream);
    }
}