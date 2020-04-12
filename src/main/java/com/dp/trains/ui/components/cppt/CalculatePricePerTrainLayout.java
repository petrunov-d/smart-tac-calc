package com.dp.trains.ui.components.cppt;

import com.dp.trains.event.*;
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
    private Double tonnage;

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
                       ServiceChargesPerTrainService serviceChargesPerTrainService, Double tonnage) {

        if (this.tonnage == null) {
            this.tonnage = tonnage;
        }

        this.add(new CalculatePricePerTrainRow(nextRowIdex, isFinal, trainNumber,
                sectionsService, serviceChargesPerTrainService, currentKeyStation, this.tonnage));

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
        this.tonnage = null;
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

        this.remove(toRemove);

        nextRowIdex = 1;

        for (CalculatePricePerTrainRow row : calculatePricePerTrainRows) {

            row.setRowIndex(nextRowIdex, row.getIsFinal());
            nextRowIdex++;
        }

        if (calculatePricePerTrainRows.size() > 0) {

            CalculatePricePerTrainRow previousRow = calculatePricePerTrainRows.get(calculatePricePerTrainRows.size() - 1);

            previousRow.enableRow();
            currentKeyStation = previousRow.getCurrentKeyStation();
            tonnage = previousRow.getRowData().getTonnage();

        } else {

            EventBusHolder.getEventBus().post(new CPPTAllRowsRemovedEvent());
            currentKeyStation = null;
            tonnage = null;
        }

        if (toRemove.getIsFinal()) {

            EventBusHolder.getEventBus().post(new CPPTFinalRowRemovedEvent());
        }
    }

    @Subscribe
    public void handleStationChangedFromRow(CPPTStationChangedEvent CPPTStationChangedEvent) {

        currentKeyStation = CPPTStationChangedEvent.getSelectedKeyStation();
        log.info("Current Key Station set to:" + currentKeyStation);
    }

    @Subscribe
    public void handleTonnageChangedFromRow(CPPTTonnageChangedFromRowEvent cpptTonnageChangedFromRowEvent) {

        this.tonnage = cpptTonnageChangedFromRowEvent.getTonnage();
        log.info("Tonnage set to:" + this.tonnage);
    }

    public List<CalculateTaxPerTrainRowDataDto> gatherAllRowData() {

        return this.calculatePricePerTrainRows.stream()
                .map(CalculatePricePerTrainRow::getRowData)
                .collect(Collectors.toList());
    }
}