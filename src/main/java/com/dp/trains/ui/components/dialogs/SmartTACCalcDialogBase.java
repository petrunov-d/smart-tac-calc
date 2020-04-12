package com.dp.trains.ui.components.dialogs;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmartTACCalcDialogBase extends Dialog {

    private Grid currentlyActiveGrid;

    public SmartTACCalcDialogBase(Grid currentlyActiveGrid) {

        super();

        this.currentlyActiveGrid = currentlyActiveGrid;

        this.setCloseOnEsc(false);
        this.setCloseOnOutsideClick(false);
    }

    protected VerticalLayout getDefaultDialogLayout(String heading, FormLayout formLayout, HorizontalLayout actions) {

        H3 h3Heading = new H3(heading);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(h3Heading);
        verticalLayout.add(formLayout);
        verticalLayout.add(actions);

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        return verticalLayout;
    }
}
