package com.dp.trains.ui.views;

import com.dp.trains.model.dto.UnitPriceDto;
import com.dp.trains.services.UnitPriceService;
import com.dp.trains.ui.components.BaseSmartTacCalcView;
import com.dp.trains.ui.components.dialogs.BasicInfoDialog;
import com.dp.trains.ui.layout.MainLayout;
import com.dp.trains.utils.SelectedYearPerUserHolder;
import com.google.common.base.Joiner;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Map;

import static com.dp.trains.utils.LocaleKeys.SHARED_APP_TITLE;

@Slf4j
@UIScope
@SpringComponent
@Route(value = CalculateSinglePriceView.NAV_CALCULATE_SINGLE_PRICE, layout = MainLayout.class)
public class CalculateSinglePriceView extends BaseSmartTacCalcView {

    @Autowired
    private UnitPriceService unitPriceService;

    static final String NAV_CALCULATE_SINGLE_PRICE = "calculate_single_price";

    public CalculateSinglePriceView() {

        super();

        H1 headerText = new H1(getTranslation(SHARED_APP_TITLE));

        VerticalLayout verticalLayout = new VerticalLayout();

        Integer currentYear = SelectedYearPerUserHolder.getForUser(SecurityContextHolder.getContext().getAuthentication().getName());

        H3 h3 = new H3("Calculate single price for current year: " + currentYear + " based on the average of the last n years");

        H3 backtrackTitle = new H3("Input the amount of years to go back: ");
        IntegerField backtrackYearsField = new IntegerField("Input years");
        backtrackYearsField.setValue(1);
        backtrackYearsField.setHasControls(true);
        backtrackYearsField.setMin(1);
        backtrackYearsField.setMax(10);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(backtrackTitle, backtrackYearsField);

        Button calculate = new Button("Calculate");
        calculate.addClickListener(e -> {

            Integer yearsToGoBack = backtrackYearsField.getValue();

            Map<Integer, Boolean> validationResult = unitPriceService.validateUnitPriceDataIntegrityForYears(yearsToGoBack, currentYear);

            if (validationResult.entrySet().size() > 0) {

                String message = "There is no Unit Price data for the following year(s): "
                        + Joiner.on(", ").join(validationResult.keySet()) +
                        " Please provide data for the missing years before calculating.";

                Dialog dialog = new BasicInfoDialog(message);
                dialog.open();

            } else {

                Collection<UnitPriceDto> unitPriceDtoList = unitPriceService.calculateSinglePriceForYears(yearsToGoBack, currentYear);
                this.unitPriceService.add(unitPriceDtoList);

                Dialog dialog = new BasicInfoDialog("Successfuly calculated new unit prices. Go to View Unit Prices to see the result");
                dialog.open();
            }
        });

        HorizontalLayout footer = getFooter();

        verticalLayout.add(headerText);
        verticalLayout.add(h3);
        verticalLayout.add(horizontalLayout);
        verticalLayout.add(calculate);
        verticalLayout.setSizeFull();

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(verticalLayout, footer);
    }
}