package com.dp.trains.ui.components.dialogs;

import com.dp.trains.model.dto.PreviousYearCopyingResultDto;
import com.dp.trains.services.*;
import com.dp.trains.ui.views.EditDataView;
import com.google.common.collect.Maps;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class CopyResultDialog extends Dialog {

    public CopyResultDialog(List<PreviousYearCopyingResultDto> copyResult, Integer selectedYear) {

        Map<String, String> userFriendlyEntitiesMap = getUserFriendlyEntitiesMap();

        H3 h3Heading = new H3(String.format("%s %d %s %d", getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_SUMMARY_DIALOG_TITLE_ONE),
                selectedYear - 1, getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_SUMMARY_DIALOG_TITLE_TWO), selectedYear));

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(h3Heading);

        copyResult.forEach(element -> verticalLayout.add(new HorizontalLayout(
                new H5(String.format("%s %d %s %s", getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_SUMMARY_DIALOG_ROW_DESCRIPTION_ONE),
                        element.getCopyCount(), getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_SUMMARY_DIALOG_ROW_DESCRIPTION_TWO),
                        userFriendlyEntitiesMap.get(element.getDisplayName()))))));

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        Button okButton = new Button(getTranslation(getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_DIALOG_BUTTON_OK)),
                new Icon(VaadinIcon.CHECK_CIRCLE_O));

        okButton.addClickListener(e -> {

            this.close();
            UI.getCurrent().navigate(EditDataView.class);
        });
    }

    private Map<String, String> getUserFriendlyEntitiesMap() {

        Map<String, String> userFriendlyEntityNames = Maps.newHashMap();

        userFriendlyEntityNames.put(LineNumberService.class.getSimpleName(),
                getTranslation(getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_SUMMARY_DIALOG_ENTITIY_DISPLAY_NAME_LINE_NUMBERS)));
        userFriendlyEntityNames.put(LineTypeService.class.getSimpleName(),
                getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_SUMMARY_DIALOG_ENTITIY_DISPLAY_NAME_LINE_TYPES));
        userFriendlyEntityNames.put(RailStationService.class.getSimpleName(),
                getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_SUMMARY_DIALOG_ENTITIY_DISPLAY_NAME_STATIONS));
        userFriendlyEntityNames.put(ServiceService.class.getSimpleName(),
                getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_SUMMARY_DIALOG_ENTITIY_DISPLAY_NAME_SERVICES));
        userFriendlyEntityNames.put(StrategicCoefficientService.class.getSimpleName(),
                getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_SUMMARY_DIALOG_ENTITIY_DISPLAY_NAME_STRATEGIC_COEFFICIENTS));
        userFriendlyEntityNames.put(TrainTypeService.class.getSimpleName(),
                getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_SUMMARY_DIALOG_ENTITIY_DISPLAY_NAME_TRAIN_TYPES));
        userFriendlyEntityNames.put(UnitPriceService.class.getSimpleName(),
                getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_SUMMARY_DIALOG_ENTITIY_DISPLAY_NAME_UNIT_PRICES));

        return userFriendlyEntityNames;
    }
}