package com.dp.trains.ui.components.reports;

import com.dp.trains.services.ReportService;
import com.dp.trains.ui.components.common.BaseSmartTacCalcView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

import static com.dp.trains.utils.LocaleKeys.REPORTS_BUTTON_LABEL_GET_AS_WORD;
import static com.dp.trains.utils.LocaleKeys.REPORTS_BUTTON_LABEL_SINGLE_PRICE_REPORT;

@Slf4j
public class SinglePriceReportContainer extends BaseSmartTacCalcView {

    public SinglePriceReportContainer(ReportService reportService) {

        Button downloadAsWordFileButton = new Button(getTranslation(REPORTS_BUTTON_LABEL_GET_AS_WORD),
                VaadinIcon.FILE_TEXT_O.create());

        H4 title = new H4(getTranslation(REPORTS_BUTTON_LABEL_SINGLE_PRICE_REPORT));

        downloadAsWordFileButton.addClickListener(buttonClickEvent -> {

            try {

                reportService.getUnitPriceReportAsWordFile();

            } catch (JRException jrException) {

                log.error("Error generating report: ", jrException);
            }
        });

        this.add(title);

        this.add(new HorizontalLayout(downloadAsWordFileButton));
    }
}