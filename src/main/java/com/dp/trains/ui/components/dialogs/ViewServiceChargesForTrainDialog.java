package com.dp.trains.ui.components.dialogs;

import com.dp.trains.services.ServiceChargesPerTrainService;
import com.dp.trains.ui.components.grids.ServiceChargesPerTrainGrid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;

import static com.dp.trains.utils.LocaleKeys.SHARED_BUTTON_TEXT_CANCEL;

@Slf4j
public class ViewServiceChargesForTrainDialog extends SmartTACCalcDialogBase {

    public ViewServiceChargesForTrainDialog(ServiceChargesPerTrainService serviceChargesPerTrainService) {

        super();

        setWidth("calc(90vw - (4*var(--lumo-space-m)))");
        setHeight("calc(50vw - (1*var(--lumo-space-m)))");

        H3 h3Heading = new H3(getTranslation("Viewing Service Charges For Train"));

        Button cancelDialog = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), VaadinIcon.CLOSE_SMALL.create());

        cancelDialog.addClickListener(event -> this.close());

        Grid serviceChargesPerTrainGrid = new ServiceChargesPerTrainGrid(serviceChargesPerTrainService);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.add(h3Heading);
        verticalLayout.add(serviceChargesPerTrainGrid);

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        this.add(verticalLayout);
        verticalLayout.add(cancelDialog);
    }
}