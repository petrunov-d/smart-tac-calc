package com.dp.trains.ui.components.dialogs;

import com.dp.trains.model.dto.PreviousYearCopyingResultDto;
import com.dp.trains.services.BaseImportService;
import com.dp.trains.services.DataCopyingService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dp.trains.utils.LocaleKeys.COPY_DATA_FROM_PREVIOUS_YEAR_DIALOG_BUTTON_OK;
import static com.dp.trains.utils.LocaleKeys.COPY_DATA_FROM_PREVIOUS_YEAR_DIALOG_TITLE;

@Slf4j
public class CopyDataFromPreviousYearDialog extends Dialog {

    public CopyDataFromPreviousYearDialog(DataCopyingService dataCopyingService,
                                          Map<BaseImportService, Integer> dataForLastYear,
                                          Map<BaseImportService, Integer> dataForCurrentYear, Integer selectedYear) {

        H3 h3Heading = new H3(getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_DIALOG_TITLE));

        Button okButton = new Button(getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_DIALOG_BUTTON_OK),
                VaadinIcon.CHECK_CIRCLE_O.create());

        ProgressBar progressBar = new ProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        progressBar.setSizeFull();

        okButton.addClickListener(event -> {

            progressBar.setVisible(true);

            List<PreviousYearCopyingResultDto> copyResult = dataCopyingService
                    .merge(dataForLastYear, dataForCurrentYear)
                    .parallelStream()
                    .map(x -> x.copyFromPreviousYear(selectedYear - 1))
                    .collect(Collectors.toList());

                    progressBar.setVisible(false);

            Dialog copyImportDialog = new CopyResultDialog(copyResult, selectedYear);
            copyImportDialog.open();
            this.close();
        });

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(h3Heading);
        verticalLayout.add(progressBar);
        verticalLayout.add(okButton);

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        this.add(verticalLayout);
    }
}