package com.dp.trains.ui.components.reports;

import com.dp.trains.model.dto.report.UnitPriceReportSelectDto;
import com.dp.trains.services.reports.UnitPriceReportService;
import com.dp.trains.ui.components.dialogs.BasicInfoDialog;
import com.dp.trains.ui.components.dialogs.FileDownloadDialog;
import com.dp.trains.ui.components.dialogs.PdfViewerDialog;
import com.dp.trains.ui.components.dialogs.ReportGenerationInProgressDialog;
import com.google.common.collect.Lists;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class SinglePriceReportContainer extends BaseReportView {

    private static final Integer ID_PASSENGER_REPORT = 1;
    private static final Integer ID_FREIGHT_REPORT = 2;

    private final Button viewReportAsPdf;
    private final Button downloadAsExcelButton;

    private final Select<UnitPriceReportSelectDto> unitPriceReportSelect;

    public SinglePriceReportContainer(UnitPriceReportService unitPriceReportService) {

        super();

        downloadAsExcelButton = new Button(getTranslation(REPORTS_BUTTON_LABEL_GET_AS_EXCEL),
                VaadinIcon.DOWNLOAD.create());
        downloadAsExcelButton.setEnabled(false);

        viewReportAsPdf = new Button(getTranslation(REPORTS_BUTTON_VIEW_REPORT), VaadinIcon.EYE.create());
        viewReportAsPdf.setEnabled(false);

        unitPriceReportSelect = new Select<>();

        List<UnitPriceReportSelectDto> unitPriceReportSelectDtos = Lists.newArrayList();
        unitPriceReportSelectDtos.add(new UnitPriceReportSelectDto(ID_PASSENGER_REPORT, "Passenger Train Unit Price Report"));
        unitPriceReportSelectDtos.add(new UnitPriceReportSelectDto(ID_FREIGHT_REPORT, "Freight Train Unit Price Report"));

        unitPriceReportSelect.setItems(unitPriceReportSelectDtos);

        unitPriceReportSelect.addValueChangeListener(event -> {

            viewReportAsPdf.setEnabled(true);
            downloadAsExcelButton.setEnabled(true);
        });

        H4 title = new H4(getTranslation(REPORTS_BUTTON_LABEL_SINGLE_PRICE_REPORT));

        viewReportAsPdf.addClickListener(event -> {

            if (!unitPriceReportService.hasData()) {

                Dialog dialog = new BasicInfoDialog(getTranslation(REPORTS_SINGLE_PRICE_REPORT_MISSING_UNIT_PRICE));
                dialog.open();
            } else {

                Dialog reportGenerationInProgressDialog = new ReportGenerationInProgressDialog();

                File file = null;

                reportGenerationInProgressDialog.open();

                try {

                    if (Objects.equals(unitPriceReportSelect.getValue().getReportId(), ID_PASSENGER_REPORT)) {
                        file = unitPriceReportService.getUnitPriceReportForPassengerTrainAsPdf();
                    } else if (Objects.equals(unitPriceReportSelect.getValue().getReportId(), ID_FREIGHT_REPORT)) {
                        file = unitPriceReportService.getUnitPriceReportForFreightTrainAsPdf();
                    }

                    reportGenerationInProgressDialog.close();

                    Dialog pdfViewerDialog = new PdfViewerDialog(file);

                    pdfViewerDialog.open();

                } catch (JRException | IOException jrException) {

                    log.error("Error generating report: ", jrException);
                }
            }
        });

        downloadAsExcelButton.addClickListener(buttonClickEvent -> {

            if (!unitPriceReportService.hasData()) {

                Dialog dialog = new BasicInfoDialog(getTranslation(REPORTS_SINGLE_PRICE_REPORT_MISSING_UNIT_PRICE));
                dialog.open();

            } else {

                Dialog d = new ReportGenerationInProgressDialog();
                d.open();

                try {

                    File file = null;

                    if (Objects.equals(unitPriceReportSelect.getValue().getReportId(), ID_PASSENGER_REPORT)) {
                        file = unitPriceReportService.getUnitPriceReportForPassengerTrainAsXLSFile();
                    } else if (Objects.equals(unitPriceReportSelect.getValue().getReportId(), ID_FREIGHT_REPORT)) {
                        file = unitPriceReportService.getUnitPriceReportForFreightTrainAsXLSFile();
                    }

                    Dialog fileDownloadDialog = new FileDownloadDialog(file);
                    fileDownloadDialog.open();

                } catch (JRException | IOException jrException) {

                    log.error("Error generating report: ", jrException);
                }

                d.close();
            }
        });

        this.add(title);

        this.add(unitPriceReportSelect);
        this.add(new HorizontalLayout(viewReportAsPdf, downloadAsExcelButton));

    }
}