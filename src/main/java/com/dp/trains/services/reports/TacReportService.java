package com.dp.trains.services.reports;

import com.dp.trains.model.dto.report.TaxPerTrainReportDto;
import com.dp.trains.model.entities.TaxPerTrainEntity;
import com.dp.trains.services.TaxPerTrainService;
import com.dp.trains.utils.TaxPerTrainEntityMerger;
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
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TacReportService extends BaseReportService {

    private static final String REPORT_TAC_FILE_PATH = "/reports/tac_report.jrxml";
    private static final String FILE_PREFIX_TAC = "report_tac_";

    private static final String TAC_DATASOURCE_NAME = "TacDataSource";

    private final TaxPerTrainService taxPerTrainService;

    public File getTacReportAsPdf(Integer trainNumber) throws JRException, IOException {

        JasperPrint jasperPrint = getJasperPrintForTac(trainNumber);

        File tmpFile = File.createTempFile(FILE_PREFIX_TAC + this.getDateFilePathComponent(), FILE_EXTENSION_PDF);

        JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(tmpFile));

        return tmpFile;
    }

    public File getTacReportAsExcel(Integer trainNumber) throws JRException, IOException {

        JasperPrint jasperPrint = getJasperPrintForTac(trainNumber);

        File tmpFile = File.createTempFile(FILE_PREFIX_TAC + this.getDateFilePathComponent(), FILE_EXTENSION_XLS);

        JRXlsExporter xlsExporter = getJrXlsExporter(jasperPrint, tmpFile);

        xlsExporter.exportReport();

        return tmpFile;
    }

    private JasperPrint getJasperPrintForTac(Integer trainNumber) throws JRException {

        JasperReport jasperReport = getJasperReport(REPORT_TAC_FILE_PATH);

        List<TaxPerTrainEntity> reportDtoList = taxPerTrainService.getByTrainNumber(trainNumber);
        TaxPerTrainEntityMerger taxPerTrainEntityMerger = new TaxPerTrainEntityMerger(reportDtoList);

        List<TaxPerTrainReportDto> mergedReportDtos = taxPerTrainEntityMerger.getReportDtos();

        Map<String, Object> parameters = Maps.newHashMap();

        parameters.put(TAC_DATASOURCE_NAME, new JRBeanCollectionDataSource(mergedReportDtos));

        return JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
    }

    public Integer getCountByTrainNumber(Integer trainNumber) {

        return this.taxPerTrainService.getCountByTrainNumber(trainNumber);
    }
}