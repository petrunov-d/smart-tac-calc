package com.dp.trains.ui.views;

import com.dp.trains.services.ReportService;
import com.dp.trains.ui.components.common.BaseSmartTacCalcView;
import com.dp.trains.ui.components.reports.SinglePriceReportContainer;
import com.dp.trains.ui.components.reports.TacReportContainer;
import com.dp.trains.ui.components.reports.TrainWieghtChangeReportContainer;
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
import org.springframework.beans.factory.annotation.Autowired;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@UIScope
@SpringComponent
@Route(value = ReportsView.NAV_REPORTS_VIEW, layout = MainLayout.class)
public class ReportsView extends BaseSmartTacCalcView {

    @Autowired
    private ReportService reportService;

    static final String NAV_REPORTS_VIEW = "reports";

    private final VerticalLayout container;
    private VerticalLayout currentlyActiveView;

    public ReportsView() {

        super();

        container = new VerticalLayout();
        container.setSizeFull();
        container.add(new H1(getTranslation(SHARED_APP_TITLE)));

        constructMenuBar();

        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        currentlyActiveView = new SinglePriceReportContainer(reportService);

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

            currentlyActiveView = new SinglePriceReportContainer(reportService);

            container.add(currentlyActiveView);
        });

        MenuItem miTrainWeightChangeReport = menuBar.addItem(getTranslation(REPORTS_BUTTON_LABEL_TRAIN_WEIGHT_CHANGE_REPORT));

        miTrainWeightChangeReport.addClickListener(event -> {

            container.remove(currentlyActiveView);
            currentlyActiveView = new TrainWieghtChangeReportContainer(reportService);
            container.add(currentlyActiveView);
        });

        MenuItem miTacReport = menuBar.addItem(getTranslation(REPORTS_BUTTON_LABEL_TRAIN_TAC_REPORT));

        miTacReport.addClickListener(event -> {

            container.remove(currentlyActiveView);
            currentlyActiveView = new TacReportContainer(reportService);
            container.add(currentlyActiveView);
        });

        layout.setAlignSelf(Alignment.CENTER, menuBar);
        layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        layout.add(menuBar);

        container.add(layout);
    }
}