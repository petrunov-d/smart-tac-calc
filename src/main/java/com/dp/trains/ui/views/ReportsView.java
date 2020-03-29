package com.dp.trains.ui.views;

import com.dp.trains.ui.layout.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;

import static com.dp.trains.utils.LocaleKeys.SHARED_APP_TITLE;

@Slf4j
@UIScope
@SpringComponent
@Route(value = ReportsView.NAV_REPORTS_VIEW, layout = MainLayout.class)
public class ReportsView extends Composite<Div> {

    static final String NAV_REPORTS_VIEW = "reports";

    public ReportsView() {

        VerticalLayout verticalLayout = new VerticalLayout();

        H1 headerText = new H1(getTranslation(SHARED_APP_TITLE));

        verticalLayout.add(headerText);
        verticalLayout.setSizeFull();

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        getContent().add(verticalLayout);
    }
}