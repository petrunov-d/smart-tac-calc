package com.dp.trains.ui.views;

import com.dp.trains.model.entities.user.UserAccess;
import com.dp.trains.services.UnitPriceService;
import com.dp.trains.ui.components.common.UserPermissionAwareView;
import com.dp.trains.ui.components.csp.CalculateSinglePriceByAverageContainer;
import com.dp.trains.ui.components.csp.CalculateSinglePriceDefaultModeContainer;
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

import javax.annotation.PostConstruct;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@UIScope
@SpringComponent
@Route(value = CalculateSinglePriceView.NAV_CALCULATE_SINGLE_PRICE, layout = MainLayout.class)
public class CalculateSinglePriceView extends UserPermissionAwareView {

    @Autowired
    private UnitPriceService unitPriceService;

    private final VerticalLayout container;
    private VerticalLayout currentlyActiveView;

    static final String NAV_CALCULATE_SINGLE_PRICE = "calculate_single_price";

    public CalculateSinglePriceView() {

        super();

        container = new VerticalLayout();
        container.add(new H1(getTranslation(SHARED_APP_TITLE)));

        constructMenuBar();

        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        add(container, getFooter());
    }

    @PostConstruct
    public void init() {

        currentlyActiveView = new CalculateSinglePriceDefaultModeContainer(unitPriceService);
        container.add(currentlyActiveView);
    }

    private void constructMenuBar() {

        final HorizontalLayout layout = new HorizontalLayout();

        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setWidth(null);

        MenuBar menuBar = new MenuBar();
        menuBar.setWidth(null);

        MenuItem muCalculateUnitPriceBasedOnTrafficAndFinancialData = menuBar.addItem(
                getTranslation(CALCULATE_SINGLE_PRICE_DEFAULT_MODE_BUTTON_LABEL));

        muCalculateUnitPriceBasedOnTrafficAndFinancialData.addClickListener(event -> {

            container.remove(currentlyActiveView);

            currentlyActiveView = new CalculateSinglePriceDefaultModeContainer(unitPriceService);

            container.add(currentlyActiveView);
        });

        MenuItem muCalculateUnitPriceBasedOnAveragesFromPreviousYears = menuBar.addItem(
                getTranslation(CALCULATE_SINGLE_PRICE_AVERAGE_MODE_BUTTON_LABEL));

        muCalculateUnitPriceBasedOnAveragesFromPreviousYears.addClickListener(event -> {

            container.remove(currentlyActiveView);
            currentlyActiveView = new CalculateSinglePriceByAverageContainer(unitPriceService);
            container.add(currentlyActiveView);
        });

        layout.setAlignSelf(Alignment.CENTER, menuBar);
        layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        layout.add(menuBar);

        container.add(layout);
    }

    @Override
    public UserAccess getViewUserAccessPermission() {

        return UserAccess.CALCULATE_SINGLE_PRICE;
    }
}