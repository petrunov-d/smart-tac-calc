package com.dp.trains.ui.components.reports;

import com.dp.trains.services.ReportService;
import com.dp.trains.ui.components.common.BaseSmartTacCalcView;
import com.dp.trains.ui.components.common.LocalizedDatePicker;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

import java.time.LocalDate;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class TacReportContainer extends BaseSmartTacCalcView {

    private LocalizedDatePicker fromDatePicker;
    private LocalizedDatePicker toDatePicker;
    private Button downloadAsExcelFileButton;
    private IntegerField trainNumber;

    public TacReportContainer(ReportService reportService) {

        downloadAsExcelFileButton = new Button(getTranslation(REPORTS_BUTTON_LABEL_GET_AS_EXCEL),
                VaadinIcon.FILE_TABLE.create());

        downloadAsExcelFileButton.setEnabled(false);

        H4 title = new H4(getTranslation(REPORTS_BUTTON_LABEL_TRAIN_TAC_REPORT));

        fromDatePicker = new LocalizedDatePicker();
        fromDatePicker.setLabel(getTranslation(REPORT_FROM_DATE));
        toDatePicker = new LocalizedDatePicker();
        toDatePicker.setLabel(getTranslation(REPORT_TO_DATE));
        toDatePicker.setEnabled(false);

        fromDatePicker.addValueChangeListener(event -> {

            toggleGenerateReportButton();
            LocalDate selectedDate = event.getValue();
            LocalDate endDate = toDatePicker.getValue();
            if (selectedDate != null) {
                toDatePicker.setEnabled(true);
                toDatePicker.setMin(selectedDate.plusDays(1));

                if (endDate == null) {
                    toDatePicker.setOpened(true);
                }
            } else {
                toDatePicker.setMin(null);
            }
        });

        toDatePicker.addValueChangeListener(event -> {

            toggleGenerateReportButton();
            LocalDate selectedDate = event.getValue();
            if (selectedDate != null) {

                fromDatePicker.setMax(selectedDate.minusDays(1));

            } else {
                fromDatePicker.setMax(null);
            }
        });

        trainNumber = new IntegerField();
        trainNumber.setLabel(getTranslation(REPORT_TRAIN_NUMBER));

        trainNumber.setValueChangeMode(ValueChangeMode.EAGER);
        trainNumber.addValueChangeListener(event -> toggleGenerateReportButton());
        trainNumber.setRequiredIndicatorVisible(true);

        downloadAsExcelFileButton.addClickListener(buttonClickEvent -> {

            try {

                reportService.getTacReport(trainNumber.getValue(), fromDatePicker.getValue(), toDatePicker.getValue());

            } catch (JRException jrException) {

                log.error("Error generating report: ", jrException);
            }
        });

        HorizontalLayout layout = new HorizontalLayout(downloadAsExcelFileButton);
        layout.setPadding(true);
        layout.setMargin(true);
        layout.setSpacing(true);

        this.add(title);
        this.add(new HorizontalLayout(fromDatePicker, toDatePicker, trainNumber));
        this.add(layout);
    }

    private void toggleGenerateReportButton() {

        if (this.fromDatePicker.getValue() != null && this.toDatePicker.getValue()
                != null && this.trainNumber.getValue() != null) {

            this.downloadAsExcelFileButton.setEnabled(true);
        }
    }
}