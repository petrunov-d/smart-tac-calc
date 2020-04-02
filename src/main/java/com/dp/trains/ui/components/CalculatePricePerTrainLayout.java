package com.dp.trains.ui.components;

import com.dp.trains.event.CPPTFinalRowRemovedEvent;
import com.dp.trains.event.CPPTRowRemovedEvent;
import com.dp.trains.event.CPPTStationChangedEvent;
import com.dp.trains.model.dto.CalculateTaxPerTrainRowDataDto;
import com.dp.trains.services.SectionsService;
import com.dp.trains.services.ServiceChargesPerTrainService;
import com.dp.trains.utils.EventBusHolder;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.dp.trains.utils.LocaleKeys.SHARED_APP_TITLE;

@Slf4j
public class CalculatePricePerTrainLayout extends VerticalLayout {

    private int nextRowIdex = 1;
    private String currentKeyStation;

    private List<CalculatePricePerTrainRow> calculatePricePerTrainRows;

    public CalculatePricePerTrainLayout() {

        calculatePricePerTrainRows = Lists.newArrayList();

        H1 headerText = new H1(getTranslation(SHARED_APP_TITLE));

        this.add(headerText);
        this.setSizeFull();

        this.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        EventBusHolder.getEventBus().register(this);

        setMargin(false);
        setPadding(false);
        setSpacing(false);
    }

    public void addRow(Integer trainNumber, boolean isFinal, SectionsService sectionsService,
                       ServiceChargesPerTrainService serviceChargesPerTrainService) {

        this.add(new CalculatePricePerTrainRow(nextRowIdex, isFinal, trainNumber,
                sectionsService, serviceChargesPerTrainService, currentKeyStation));
        nextRowIdex++;
    }

    @Override
    public void add(Component... components) {

        super.add(components);

        calculatePricePerTrainRows.addAll(Arrays
                .stream(components)
                .filter(component -> component instanceof CalculatePricePerTrainRow)
                .map(x -> (CalculatePricePerTrainRow) x)
                .collect(Collectors.toList()));
    }

    public void resetContainer() {

        this.getChildren().filter(x -> x instanceof CalculatePricePerTrainRow).forEach(this::remove);
        nextRowIdex = 1;
        currentKeyStation = null;
        calculatePricePerTrainRows.clear();
    }

    public void updateTrainNumberForRows(Integer trainNumber) {

        calculatePricePerTrainRows.forEach(x -> x.setTrainNumber(trainNumber));
    }

    @Subscribe
    public void handleRowRemovedEvent(CPPTRowRemovedEvent cpptRowRemovedEvent) {

        log.info("Row Removed: " + cpptRowRemovedEvent.toString());

        calculatePricePerTrainRows.remove(cpptRowRemovedEvent.getRowIndex() - 1);

        CalculatePricePerTrainRow toRemove = (CalculatePricePerTrainRow) this.getChildren()
                .filter(x -> x instanceof CalculatePricePerTrainRow)
                .filter(x -> ((CalculatePricePerTrainRow) x).getRowIndex() == cpptRowRemovedEvent.getRowIndex())
                .findFirst().get();

        if (toRemove.getIsFinal()) {

            EventBusHolder.getEventBus().post(new CPPTFinalRowRemovedEvent());
        }

        this.remove(toRemove);

        nextRowIdex = 1;

        for (CalculatePricePerTrainRow row : calculatePricePerTrainRows) {

            row.setRowIndex(nextRowIdex, row.getIsFinal());
            nextRowIdex++;
        }

        if (calculatePricePerTrainRows.size() > 0) {

            calculatePricePerTrainRows.get(calculatePricePerTrainRows.size() - 1).enableRow();
        }

    }

    @Subscribe
    public void handleStationChangedFromRow(CPPTStationChangedEvent CPPTStationChangedEvent) {

        currentKeyStation = CPPTStationChangedEvent.getSelectedKeyStation();
        log.info("Current Key Station set to:" + currentKeyStation);
    }

    public List<CalculateTaxPerTrainRowDataDto> gatherAllRowData() {

        return this.calculatePricePerTrainRows.stream()
                .map(CalculatePricePerTrainRow::getRowData)
                .collect(Collectors.toList());
    }
}
