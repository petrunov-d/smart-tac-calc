package com.dp.trains.ui.components.reports;

import com.dp.trains.services.ReportService;
import com.dp.trains.services.UnitPriceService;
import com.dp.trains.ui.components.dialogs.BasicInfoDialog;
import com.dp.trains.ui.components.dialogs.FileDownloadDialog;
import com.dp.trains.ui.components.dialogs.PdfViewerDialog;
import com.dp.trains.ui.components.dialogs.ReportGenerationInProgressDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.IOException;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class SinglePriceReportContainer extends BaseReportView {

    public SinglePriceReportContainer(UnitPriceService unitPriceService, ReportService reportService) {

        super();

        Button downloadAsExcelButton = new Button(getTranslation(REPORTS_BUTTON_LABEL_GET_AS_EXCEL),
                VaadinIcon.DOWNLOAD.create());

        Button viewReportAsPdf = new Button(getTranslation(REPORTS_BUTTON_VIEW_REPORT), VaadinIcon.EYE.create());

        H4 title = new H4(getTranslation(REPORTS_BUTTON_LABEL_SINGLE_PRICE_REPORT));

        viewReportAsPdf.addClickListener(event -> {

            if (unitPriceService.fetch(0, 0).isEmpty()) {

                Dialog dialog = new BasicInfoDialog(getTranslation(REPORTS_SINGLE_PRICE_REPORT_MISSING_UNIT_PRICE));
                dialog.open();
            } else {

                Dialog reportGenerationInProgressDialog = new ReportGenerationInProgressDialog();

                File outputStream;

                reportGenerationInProgressDialog.open();

                try {

                    outputStream = reportService.getUnitPriceReportAsPdf();

                    reportGenerationInProgressDialog.close();

                    Dialog pdfViewerDialog = new PdfViewerDialog(outputStream);

                    pdfViewerDialog.open();

                } catch (JRException | IOException jrException) {

                    log.error("Error generating report: ", jrException);
                }
            }
        });

        downloadAsExcelButton.addClickListener(buttonClickEvent -> {

            if (unitPriceService.fetch(0, 0).isEmpty()) {

                Dialog dialog = new BasicInfoDialog(getTranslation(REPORTS_SINGLE_PRICE_REPORT_MISSING_UNIT_PRICE));
                dialog.open();

            } else {

                Dialog d = new ReportGenerationInProgressDialog();
                d.open();

                try {

                    File file = reportService.getUnitPriceReportAsXLSFile();

                    Dialog fileDownloadDialog = new FileDownloadDialog(file);
                    fileDownloadDialog.open();

                } catch (JRException | IOException jrException) {

                    log.error("Error generating report: ", jrException);
                }

                d.close();
            }
        });

        this.add(title);

        this.add(new HorizontalLayout(viewReportAsPdf, downloadAsExcelButton));
    }
}