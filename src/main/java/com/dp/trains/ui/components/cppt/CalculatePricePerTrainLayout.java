package com.dp.trains.ui.components.cppt;

import com.dp.trains.event.*;
import com.dp.trains.model.dto.CPPTRowDataDto;
import com.dp.trains.model.dto.CarrierCompanyDto;
import com.dp.trains.model.dto.LocomotiveSeriesDto;
import com.dp.trains.model.viewmodels.RailStationViewModel;
import com.dp.trains.services.CarrierCompanyService;
import com.dp.trains.services.RailStationService;
import com.dp.trains.services.ServiceChargesPerTrainService;
import com.dp.trains.utils.EventBusHolder;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.dp.trains.utils.LocaleKeys.SHARED_APP_TITLE;

@Slf4j
public class CalculatePricePerTrainLayout extends VerticalLayout {

    private int nextRowIdex = 1;
    private RailStationViewModel currentStation;
    private Double tonnage;
    private Double trainLength;
    private Collection<LocomotiveSeriesDto> locomotiveSeriesDtos;
    private LocomotiveSeriesDto selectedLocomotiveSeriesDto;
    private String selectedCarrierCompany;

    private final List<CalculatePricePerTrainRow> calculatePricePerTrainRows;

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

    public void addRow(Integer trainNumber,
                       boolean isFinal,
                       RailStationService railStationService,
                       ServiceChargesPerTrainService serviceChargesPerTrainService,
                       CarrierCompanyService carrierCompanyService,
                       Double tonnage,
                       CarrierCompanyDto carrierCompanyDto,
                       Collection<LocomotiveSeriesDto> locomotiveSeriesDtos,
                       LocomotiveSeriesDto locomotiveSeriesDto,
                       Double trainLength) {

        this.locomotiveSeriesDtos = locomotiveSeriesDtos;

        if (this.tonnage == null) {

            this.tonnage = tonnage;
        }

        if (this.trainLength == null) {

            this.trainLength = trainLength;
        }

        if (this.selectedCarrierCompany == null) {

            this.selectedCarrierCompany = carrierCompanyDto.getCarrierName();
        }

        if (this.selectedLocomotiveSeriesDto == null) {

            this.selectedLocomotiveSeriesDto = locomotiveSeriesDto;
        }

        CalculatePricePerTrainRow calculatePricePerTrainRow = new CalculatePricePerTrainRow(
                this.nextRowIdex,
                isFinal,
                trainNumber,
                railStationService,
                serviceChargesPerTrainService,
                carrierCompanyService,
                this.currentStation,
                this.tonnage,
                carrierCompanyService.getByName(this.selectedCarrierCompany),
                this.selectedLocomotiveSeriesDto,
                this.locomotiveSeriesDtos,
                this.trainLength);

        this.add(calculatePricePerTrainRow);
        this.calculatePricePerTrainRows.add(calculatePricePerTrainRow);

        nextRowIdex++;
    }

    public void resetContainer() {

        this.getChildren().filter(x -> x instanceof CalculatePricePerTrainRow).forEach(this::remove);
        this.nextRowIdex = 1;
        this.currentStation = null;
        this.tonnage = null;
        this.trainLength = null;
        this.locomotiveSeriesDtos = null;
        this.selectedLocomotiveSeriesDto = null;
        this.selectedCarrierCompany = null;
        this.calculatePricePerTrainRows.clear();
    }

    public void updateTrainNumberForRows(Integer trainNumber) {

        this.calculatePricePerTrainRows.forEach(x -> x.setTrainNumber(trainNumber));
    }

    @Subscribe
    public void handleRowRemovedEvent(CPPTRowRemovedEvent cpptRowRemovedEvent) {

        log.info("Row Removed: " + cpptRowRemovedEvent.toString());

        this.calculatePricePerTrainRows.remove(cpptRowRemovedEvent.getRowIndex() - 1);

        CalculatePricePerTrainRow toRemove = (CalculatePricePerTrainRow) this.getChildren()
                .filter(x -> x instanceof CalculatePricePerTrainRow)
                .filter(x -> ((CalculatePricePerTrainRow) x).getRowIndex() == cpptRowRemovedEvent.getRowIndex())
                .findFirst().get();

        this.remove(toRemove);

        this.nextRowIdex = 1;

        for (CalculatePricePerTrainRow row : calculatePricePerTrainRows) {

            row.setRowIndex(nextRowIdex);
            this.nextRowIdex++;
        }

        if (this.calculatePricePerTrainRows.size() > 0) {

            CalculatePricePerTrainRow previousRow = calculatePricePerTrainRows.get(calculatePricePerTrainRows.size() - 1);

            previousRow.enableRow();
            currentStation = previousRow.getSelectedStation();
            tonnage = previousRow.getRowData().getTonnage();

        } else {

            EventBusHolder.getEventBus().post(new CPPTAllRowsRemovedEvent());
            currentStation = null;
            tonnage = null;
        }

        if (toRemove.getIsFinal()) {

            EventBusHolder.getEventBus().post(new CPPTFinalRowRemovedEvent());
        }
    }

    @Subscribe
    public void handleStationChangedFromRow(CPPTStationChangedEvent cPPTStationChangedEvent) {

        this.currentStation = RailStationViewModel.builder()
                .railStation(cPPTStationChangedEvent.getSelectedStation())
                .isKeyStation(cPPTStationChangedEvent.getIsKeyStation())
                .lineNumber(cPPTStationChangedEvent.getLineNumber())
                .build();

        log.info("Current Key Station set to:" + this.currentStation.toString());
    }

    @Subscribe
    public void handleTonnageChangedFromRow(CPPTTonnageChangedFromRowEvent cpptTonnageChangedFromRowEvent) {

        this.tonnage = cpptTonnageChangedFromRowEvent.getTonnage();
        log.info("Tonnage set to:" + this.tonnage);
    }

    @Subscribe
    public void carrierCompanyChangedFromRow(CPPTCarrierCompanyChangedEvent carrierCompanyChangedEvent) {

        this.selectedCarrierCompany = carrierCompanyChangedEvent.getSelectedDto();
        log.info("Carrier Company set to:" + this.tonnage);
    }

    @Subscribe
    public void handleLocomotiveSeriesChangedFromRow(CPPTLocomotiveSeriesChangedEvent cpptLocomotiveSeriesChangedEvent) {

        this.selectedLocomotiveSeriesDto = cpptLocomotiveSeriesChangedEvent.getLocomotiveSeriesDto();
        log.info("Locomotive series set to:" + this.selectedLocomotiveSeriesDto);
    }

    @Subscribe
    public void handleTrainLengthChangedFromRow(CPPTTrainLengthChangedFromRowEvent cpptTrainLengthChangedFromRowEvent) {

        this.trainLength = cpptTrainLengthChangedFromRowEvent.getTrainLength();
        log.info("Train Length set to:" + this.trainLength);
    }

    public List<CPPTRowDataDto> gatherAllRowData() {

        return this.calculatePricePerTrainRows
                .stream()
                .map(CalculatePricePerTrainRow::getRowData)
                .collect(Collectors.toList());
    }

    public Collection<LocomotiveSeriesDto> getLocomotiveSeriesDtos() {

        return locomotiveSeriesDtos;
    }

    public void setLocomotiveSeriesDtos(Collection<LocomotiveSeriesDto> locomotiveSeriesDtos) {

        this.locomotiveSeriesDtos = locomotiveSeriesDtos;
    }
}