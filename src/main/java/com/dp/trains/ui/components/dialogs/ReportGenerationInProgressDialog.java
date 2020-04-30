package com.dp.trains.ui.components.dialogs;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;

import static com.dp.trains.utils.LocaleKeys.REPORT_GENERATING;

public class ReportGenerationInProgressDialog extends SmartTACCalcDialogBase {

    public ReportGenerationInProgressDialog() {

        super();

        H3 h3Heading = new H3(getTranslation(getTranslation(REPORT_GENERATING)));

        ProgressBar progressBar = new ProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(true);
        progressBar.setSizeFull();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.add(h3Heading);
        verticalLayout.add(progressBar);

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        this.add(verticalLayout);
        this.setSizeFull();
    }
}