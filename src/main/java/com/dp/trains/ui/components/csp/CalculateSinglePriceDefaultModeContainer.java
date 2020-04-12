package com.dp.trains.ui.components.csp;

import com.dp.trains.model.dto.UnitPriceDataIntegrity;
import com.dp.trains.services.UnitPriceService;
import com.dp.trains.ui.components.dialogs.BasicInfoDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class CalculateSinglePriceDefaultModeContainer extends VerticalLayout {

    public CalculateSinglePriceDefaultModeContainer(UnitPriceService unitPriceService) {

        super();

        Boolean hasCalculatedData = unitPriceService.checkIfAlreadyHasDataCalculated();

        if (hasCalculatedData) {

            Dialog dialog = new BasicInfoDialog(getTranslation(CALCULATE_SINGLE_PRICE_DEFAULT_MODE_ALREADY_HAS_DATA_WARNING));
            dialog.open();
        }

        H3 h3 = new H3(getTranslation(CALCULATE_SINGLE_PRICE_DEFAULT_MODE_TITLE));

        Button calculate = new Button(getTranslation(CALCULATE_SINGLE_PRICE_INPUT_YEARS_BUTTON_CALCULATE),
                VaadinIcon.CALC_BOOK.create());
        calculate.addClickListener(e -> {

            UnitPriceDataIntegrity unitPriceDataIntegrity = unitPriceService.getDataIntegrityState();

            if (!unitPriceDataIntegrity.equals(UnitPriceDataIntegrity.ALL_DATA_PRESENT)) {

                String missingDataLabel;

                if (unitPriceDataIntegrity.equals(UnitPriceDataIntegrity.ALL_DATA_MISSING)) {

                    missingDataLabel = getTranslation(CALCULATE_SINGLE_PRICE_DEFAULT_MODE_MISSING_DATA_ALL);
                } else {

                    missingDataLabel = UnitPriceDataIntegrity.MISSING_FINANCIAL_DATA.equals(unitPriceDataIntegrity)
                            ? getTranslation(CALCULATE_SINGLE_PRICE_DEFAULT_MODE_MISSING_DATA_FINANCIAL) :
                            getTranslation(CALCULATE_SINGLE_PRICE_DEFAULT_MODE_MISSING_DATA_TRAFFIC);
                }

                Dialog dialog = new BasicInfoDialog(String.format("%s %s %s",
                        getTranslation(CALCULATE_SINGLE_PRICE_DEFAULT_MODE_MISSING), missingDataLabel,
                        getTranslation(CALCULATE_SINGLE_PRICE_DEFAULT_MODE_MISSING_ADDITIONAL)));

                dialog.open();

            } else {

                try {
                    unitPriceService.calculateSinglePriceForCurrentYear();

                    Dialog dialog = new BasicInfoDialog(
                            getTranslation(CALCULATE_SINGLE_PRICE_DEFAULT_MODE_CALCULATION_SUCCESS));
                    dialog.open();
                } catch (Exception exception) {

                    Dialog dialog = new BasicInfoDialog(getTranslation(
                            getTranslation(CALCULATE_SINGLE_PRICE_DEFAULT_MODE_CALCULATION_ERROR)
                                    + ExceptionUtils.getStackTrace(exception)));
                    dialog.open();
                }
            }
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setHeight("70px");

        this.add(h3);
        this.add(horizontalLayout);
        this.add(calculate);
        this.setSizeFull();


        this.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
    }
}