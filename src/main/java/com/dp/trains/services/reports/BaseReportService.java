package com.dp.trains.services.reports;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseReportService {

    protected static final String FILE_EXTENSION_XLS = ".xls";
    protected static final String FILE_EXTENSION_PDF = ".pdf";

    protected static final String FILE_TIMESTAMP = "dd-MM-yyyy_HH-mm";
    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat(FILE_TIMESTAMP);

    protected JasperReport getJasperReport(String reportName) throws JRException {

        InputStream reportStream = getClass().getResourceAsStream(reportName);

        log.info("Compiling report: " + reportName);

        return JasperCompileManager.compileReport(reportStream);
    }

    protected String getDateFilePathComponent() {

        return dateFormat.format(new Date());
    }

    protected JRXlsExporter getJrXlsExporter(JasperPrint jasperPrint, File tmpFile) throws FileNotFoundException {

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
