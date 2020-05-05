package com.dp.trains.ui.views;

import com.dp.trains.event.SmartTACCalcNeedsRefreshEvent;
import com.dp.trains.services.BaseImportService;
import com.dp.trains.services.DataCopyingService;
import com.dp.trains.ui.components.dialogs.BasicInfoDialog;
import com.dp.trains.ui.components.dialogs.CopyDataFromPreviousYearDialog;
import com.dp.trains.ui.layout.MainLayout;
import com.dp.trains.utils.CommonConstants;
import com.dp.trains.utils.EventBusHolder;
import com.dp.trains.utils.SelectedYearPerUserHolder;
import com.google.common.base.Joiner;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@UIScope
@SpringComponent
@Route(value = MainView.NAV_MAIN_VIEW)
public class MainView extends Composite<Div> implements BeforeLeaveObserver {

    private static final Integer BEGIN_YEAR = 2015;

    static final String NAV_MAIN_VIEW = "main";

    @Autowired
    private DataCopyingService dataCopyingService;

    public MainView() {

        Image img = new Image(new StreamResource(CommonConstants.MAIN_PAGE_BACKGROUND,
                () -> MainLayout.class.getResourceAsStream("/" + CommonConstants.MAIN_PAGE_BACKGROUND)),
                getTranslation(SHARED_VIEW_BACKGROUND_PICTURE_ALT));

        img.setWidth("101%");
        img.setHeight("50%");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        H1 headerText = new H1(getTranslation(MAIN_VIEW_H1_TITLE));
        H3 subTitle = new H3(getTranslation(MAIN_VIEW_H3_SUBTITLE));

        Select<Integer> yearSelect = new Select<>();

        yearSelect.setLabel(getTranslation(MAIN_VIEW_YEAR_LABEL));
        yearSelect.setItems(IntStream.range(BEGIN_YEAR, Calendar.getInstance().get(Calendar.YEAR) + 1)
                .boxed().collect(Collectors.toList()));

        yearSelect.addValueChangeListener(event -> SelectedYearPerUserHolder.addOrUpdate(SecurityContextHolder
                .getContext().getAuthentication().getName(), event.getValue()));

        Button button = new Button(getTranslation(MAIN_VIEW_BUTTON_CONTINUE_TEXT));

        button.addClickListener(event -> {

            if (yearSelect.getValue() == null || SelectedYearPerUserHolder.getForUser(SecurityContextHolder
                    .getContext().getAuthentication().getName()) == null) {

                Dialog basicInfoDialog = new BasicInfoDialog(getTranslation(MAIN_VIEW_DIALOG_ERROR_YEAR_REQUIRED));
                basicInfoDialog.open();

            } else {

                int selectedYear = yearSelect.getValue();

                if (selectedYear == BEGIN_YEAR) {

                    log.info("Selected year is equal to begin year: " + selectedYear + " skipping data import checks");

                    UI.getCurrent().navigate(EditDataView.class);

                } else {

                    Map<BaseImportService, Integer> dataForCurrentYear = dataCopyingService.getDataForYear(selectedYear);
                    log.info("Data For current year: " + Joiner.on(",").withKeyValueSeparator("=").join(dataForCurrentYear));

                    if (!dataCopyingService.hasAllData(dataForCurrentYear)) {

                        Map<BaseImportService, Integer> dataForLastYear = dataCopyingService.getDataForYear(selectedYear - 1);
                        List<BaseImportService> mergeResultList = dataCopyingService.merge(dataForLastYear, dataForCurrentYear);

                        if (dataCopyingService.isCompletelyEmpty(dataForLastYear) || mergeResultList.isEmpty()) {

                            log.info("Data for last year is empty or merge result produced 0 meaningful records, nothing to copy, proceeding...");
                            UI.getCurrent().navigate(EditDataView.class);
                        } else {

                            log.info("Data for current year was missing some static data, trying with data from previous year: "
                                    + Joiner.on(",").withKeyValueSeparator("=").join(dataForLastYear));

                            Dialog dialog = new CopyDataFromPreviousYearDialog(mergeResultList, selectedYear);

                            dialog.open();
                        }

                    } else {

                        UI.getCurrent().navigate(EditDataView.class);
                    }
                }
            }
        });

        verticalLayout.add(img);
        verticalLayout.add(headerText);
        verticalLayout.add(subTitle);
        verticalLayout.add(yearSelect);
        verticalLayout.add(button);

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        getContent().add(verticalLayout);
        EventBusHolder.getEventBus().register(this);
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent beforeLeaveEvent) {

        beforeLeaveEvent.postpone();
        beforeLeaveEvent.getContinueNavigationAction().proceed();
        EventBusHolder.getEventBus().post(new SmartTACCalcNeedsRefreshEvent());
        UI.getCurrent().getPage().reload();
    }
}