package com.dp.trains.ui.components.reports;

import com.dp.trains.services.reports.ServiceChargesPerTrainReportService;
import com.dp.trains.ui.components.dialogs.BasicInfoDialog;
import com.dp.trains.ui.components.dialogs.FileDownloadDialog;
import com.dp.trains.ui.components.dialogs.PdfViewerDialog;
import com.dp.trains.ui.components.dialogs.ReportGenerationInProgressDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.IOException;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class ServiceChargesPerTrainReportContainer extends BaseReportView {

    private final Button viewReportAsPdf;
    private final Button downloadAsExcelButton;

    private final Select<Integer> trainNumber;

    public ServiceChargesPerTrainReportContainer(ServiceChargesPerTrainReportService serviceChargesPerTrainReportService) {

        super();

        this.trainNumber = new Select<>();
        this.trainNumber.setLabel(getTranslation(REPORT_TRAIN_NUMBER));

        this.trainNumber.setItems(serviceChargesPerTrainReportService.getByTrainNumbers());
        this.trainNumber.addValueChangeListener(event -> toggleGenerateReportButton());
        this.trainNumber.setRequiredIndicatorVisible(true);

        this.downloadAsExcelButton = new Button(getTranslation(REPORT_GENERATE_EXCEL_FILE_BUTTON_LABEL),
                VaadinIcon.DOWNLOAD.create());
        this.downloadAsExcelButton.setEnabled(false);

        this.viewReportAsPdf = new Button(getTranslation(REPORTS_BUTTON_VIEW_REPORT), VaadinIcon.EYE.create());
        this.viewReportAsPdf.setEnabled(false);

        H4 title = new H4(getTranslation(REPORTS_BUTTON_LABEL_SERVICE_CHARGES_PER_TRAIN_REPORT));

        viewReportAsPdf.addClickListener(event -> {

            if (serviceChargesPerTrainReportService.getCountByTrainNumber(trainNumber.getValue()) == 0) {

                BasicInfoDialog basicInfoDialog = new BasicInfoDialog(getTranslation(REPORT_PARAMETERS_NO_DATA));
                basicInfoDialog.open();
            } else {

                Dialog reportGenerationInProgressDialog = new ReportGenerationInProgressDialog();

                File outputStream;

                reportGenerationInProgressDialog.open();

                try {

                    outputStream = serviceChargesPerTrainReportService.getServiceChargesPerTrainAsPdf(this.trainNumber.getValue());

                    reportGenerationInProgressDialog.close();

                    Dialog pdfViewerDialog = new PdfViewerDialog(outputStream);

                    pdfViewerDialog.open();

                } catch (JRException | IOException jrException) {

                    log.error("Error generating report: ", jrException);
                }
            }
        });

        downloadAsExcelButton.addClickListener(buttonClickEvent -> {

            if (serviceChargesPerTrainReportService.getCountByTrainNumber(trainNumber.getValue()) == 0) {

                BasicInfoDialog basicInfoDialog = new BasicInfoDialog(getTranslation(REPORT_PARAMETERS_NO_DATA));
                basicInfoDialog.open();
            } else {

                Dialog d = new ReportGenerationInProgressDialog();
                d.open();

                try {

                    File file = serviceChargesPerTrainReportService.getServiceChargesPerTrainAsXls(this.trainNumber.getValue());

                    Dialog fileDownloadDialog = new FileDownloadDialog(file);
                    fileDownloadDialog.open();

                } catch (JRException | IOException jrException) {

                    log.error("Error generating report: ", jrException);
                }

                d.close();
            }
        });

        this.add(title);
        this.add(this.trainNumber);
        this.add(new HorizontalLayout(viewReportAsPdf, downloadAsExcelButton));
    }

    private void toggleGenerateReportButton() {

        if (this.trainNumber.getValue() != null) {

            this.downloadAsExcelButton.setEnabled(true);
            this.viewReportAsPdf.setEnabled(true);
        }
    }
}