package com.dp.trains.ui.views;

import com.dp.trains.services.reports.ServiceChargesPerTrainReportService;
import com.dp.trains.services.reports.TacReportService;
import com.dp.trains.services.reports.UnitPriceReportService;
import com.dp.trains.ui.components.common.BaseSmartTacCalcView;
import com.dp.trains.ui.components.reports.ServiceChargesPerTrainReportContainer;
import com.dp.trains.ui.components.reports.SinglePriceReportContainer;
import com.dp.trains.ui.components.reports.TacReportContainer;
import com.dp.trains.ui.layout.MainLayout;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@UIScope
@SpringComponent
@Route(value = ReportsView.NAV_REPORTS_VIEW, layout = MainLayout.class)
public class ReportsView extends BaseSmartTacCalcView {

    private final UnitPriceReportService unitPriceReportService;
    private final TacReportService tacReportService;
    private final ServiceChargesPerTrainReportService serviceChargesPerTrainReportService;

    static final String NAV_REPORTS_VIEW = "reports";

    private final VerticalLayout container;
    private VerticalLayout currentlyActiveView;

    public ReportsView(UnitPriceReportService unitPriceReportService,
                       TacReportService tacReportService,
                       ServiceChargesPerTrainReportService serviceChargesPerTrainReportService) {

        super();

        this.unitPriceReportService = unitPriceReportService;
        this.tacReportService = tacReportService;
        this.serviceChargesPerTrainReportService = serviceChargesPerTrainReportService;

        this.container = new VerticalLayout();
        this.container.setSizeFull();
        this.container.add(new H1(getTranslation(SHARED_APP_TITLE)));

        constructMenuBar();

        this.container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        this.currentlyActiveView = new SinglePriceReportContainer(this.unitPriceReportService);

        add(container, getFooter());
    }

    private void constructMenuBar() {

        final HorizontalLayout layout = new HorizontalLayout();

        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setWidth(null);

        MenuBar menuBar = new MenuBar();
        menuBar.setWidth(null);

        MenuItem miSinglePriceReport = menuBar.addItem(getTranslation(REPORTS_BUTTON_LABEL_SINGLE_PRICE_REPORT));

        miSinglePriceReport.addClickListener(event -> {

            container.remove(currentlyActiveView);

            currentlyActiveView = new SinglePriceReportContainer(this.unitPriceReportService);

            container.add(currentlyActiveView);
        });

        MenuItem miTacReport = menuBar.addItem(getTranslation(REPORTS_BUTTON_LABEL_TRAIN_TAC_REPORT));

        miTacReport.addClickListener(event -> {

            container.remove(currentlyActiveView);
            currentlyActiveView = new TacReportContainer(this.tacReportService);
            container.add(currentlyActiveView);
        });

        MenuItem miServiceChargesPerTrainReport = menuBar.addItem(getTranslation(REPORTS_BUTTON_LABEL_SERVICE_CHARGES_PER_TRAIN_REPORT));

        miServiceChargesPerTrainReport.addClickListener(event -> {

            container.remove(currentlyActiveView);
            currentlyActiveView = new ServiceChargesPerTrainReportContainer(serviceChargesPerTrainReportService);
            container.add(currentlyActiveView);
        });

        layout.setAlignSelf(Alignment.CENTER, menuBar);
        layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        layout.add(menuBar);

        container.add(layout);
    }
}