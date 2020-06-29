package com.dp.trains.services.reports;

import com.dp.trains.model.dto.report.ServiceChargesPerTrainReportDto;
import com.dp.trains.services.ServiceChargesPerTrainService;
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
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceChargesPerTrainReportService extends BaseReportService {

    private static final String REPORT_SERVICE_CHARGES_PER_TRAIN_FILE_PATH = "/reports/service_charges_per_train_report.jrxml";
    private static final String FILE_PREFIX_SERVICE_CHARGES_PER_TRAIN = "report_service_charges_";

    private static final String SERVICE_CHARGES_DATASOURCE_NAME = "ServiceChargesPerTrainDataSource";

    private final ServiceChargesPerTrainService serviceChargesPerTrainService;

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

    private JasperPrint getJasperPrintForServiceCharges(Integer trainNumber) throws JRException {

        JasperReport jasperReport = getJasperReport(REPORT_SERVICE_CHARGES_PER_TRAIN_FILE_PATH);

        List<ServiceChargesPerTrainReportDto> reportDtoList = serviceChargesPerTrainService.getReportDtosForTrainNumber(trainNumber);

        Map<String, Object> parameters = Maps.newHashMap();

        parameters.put(SERVICE_CHARGES_DATASOURCE_NAME, new JRBeanCollectionDataSource(reportDtoList));

        return JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
    }

    public Integer getCountByTrainNumber(Integer trainNumber) {

        return this.serviceChargesPerTrainService.getCountByTrainNumber(trainNumber);
    }

    public Set<Integer> getByTrainNumbers() {

        return this.serviceChargesPerTrainService.getAllTrainNumbersWithServiceCharges();
    }
}
