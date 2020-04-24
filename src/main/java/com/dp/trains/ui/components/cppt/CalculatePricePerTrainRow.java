package com.dp.trains.ui.components.cppt;

import com.dp.trains.event.*;
import com.dp.trains.model.dto.CalculateTaxPerTrainRowDataDto;
import com.dp.trains.model.dto.LocomotiveSeriesDto;
import com.dp.trains.model.dto.SectionNeighboursDto;
import com.dp.trains.model.entities.ServiceChargesPerTrainEntity;
import com.dp.trains.model.viewmodels.StationViewModel;
import com.dp.trains.services.SectionsService;
import com.dp.trains.services.ServiceChargesPerTrainService;
import com.dp.trains.ui.components.dialogs.BasicInfoDialog;
import com.dp.trains.ui.components.dialogs.ViewServiceChargesForTrainDialog;
import com.dp.trains.utils.EventBusHolder;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class CalculatePricePerTrainRow extends HorizontalLayout {

    private int rowIndex;
    private Integer trainNumber;
    private final boolean isFinal;
    private StationViewModel selectedStation;
    private final Collection<SectionNeighboursDto> neighbours;
    private List<ServiceChargesPerTrainEntity> serviceChargesPerTrainEntityList = Lists.newArrayList();

    private final SectionsService sectionsService;

    private final H5 rowTitle;
    private final H5 serviceChargesLabel;

    private Button doneButton;
    private Button removeButton;
    private Button serviceButton;

    private NumberField tonnage;
    private NumberField trainLength;

    private Select<Integer> lineNumbers;
    private Select<SectionNeighboursDto> station;
    private Select<LocomotiveSeriesDto> locomotiveSeriesDtoSelect;

    public CalculatePricePerTrainRow(int rowIndex,
                                     boolean isFinal,
                                     Integer trainNumber,
                                     SectionsService sectionsService,
                                     ServiceChargesPerTrainService serviceChargesPerTrainService,
                                     StationViewModel selectedStation,
                                     Double tonnageDouble,
                                     LocomotiveSeriesDto selectedLocomotiveSeriesDto,
                                     Collection<LocomotiveSeriesDto> locomotiveSeriesDtos,
                                     Double trainLengthDouble) {

        EventBusHolder.getEventBus().register(this);

        this.rowTitle = new H5();
        this.isFinal = isFinal;
        this.selectedStation = selectedStation;
        this.trainNumber = trainNumber;
        this.sectionsService = sectionsService;
        this.setRowIndex(rowIndex);

        this.neighbours = sectionsService.getDirectNeighboursForSource(selectedStation, isFirst());

        initalizeStationSelect(trainNumber, serviceChargesPerTrainService, neighbours);
        initializeLineNumberSelect(neighbours);
        initializeTonnageField(tonnageDouble);
        initializeTrainLength(trainLengthDouble);

        this.serviceChargesLabel = new H5();
        this.serviceChargesLabel.setWidth("100%");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(rowTitle, station, lineNumbers, tonnage, trainLength);

        initializeRowButtons(serviceChargesPerTrainService);
        initializeLocomotiveSeriesDtoSelect(selectedLocomotiveSeriesDto, locomotiveSeriesDtos);

        HorizontalLayout serviceLayout = new HorizontalLayout();

        serviceLayout.setSizeFull();
        serviceLayout.setPadding(false);
        serviceLayout.setMargin(false);
        serviceLayout.setWidth("100%");

        serviceLayout.add(serviceButton, doneButton, removeButton, serviceChargesLabel);

        VerticalLayout serviceButtonLayout = new VerticalLayout();
        serviceButtonLayout.add(serviceLayout);
        serviceButtonLayout.setSpacing(false);
        serviceButtonLayout.setPadding(true);
        serviceButtonLayout.setMargin(true);

        horizontalLayout.add(serviceButtonLayout);
        this.add(horizontalLayout);
    }

    private void initializeTrainLength(Double trainLengthDouble) {

        trainLength = new NumberField(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TRAIN_LENGTH));
        trainLength.setValue(trainLengthDouble);
        trainLength.setValueChangeMode(ValueChangeMode.EAGER);
        trainLength.addValueChangeListener(event -> {

            EventBusHolder.getEventBus().post(CPPTTrainLengthChangedFromRowEvent.builder()
                    .trainLength(event.getValue())
                    .build());

            checkRowDone();
        });
    }

    private void initializeLocomotiveSeriesDtoSelect(LocomotiveSeriesDto selectedLocomotiveSeriesDto,
                                                     Collection<LocomotiveSeriesDto> locomotiveSeriesDtos) {

        this.locomotiveSeriesDtoSelect = new Select<>();
        this.locomotiveSeriesDtoSelect.setItemLabelGenerator(x -> String.format("%s - %.3f", x.getSeries(), x.getWeight()));
        this.locomotiveSeriesDtoSelect.setItems(locomotiveSeriesDtos);
        this.locomotiveSeriesDtoSelect.setValue(selectedLocomotiveSeriesDto);
        this.locomotiveSeriesDtoSelect.addValueChangeListener(event -> {

            EventBusHolder.getEventBus().post(CPPTLocomotiveSeriesChangedEvent.builder()
                    .locomotiveSeriesDto(event.getValue())
                    .build());

            checkRowDone();
        });
    }

    private void initializeRowButtons(ServiceChargesPerTrainService serviceChargesPerTrainService) {

        removeButton = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_ROW_BUTTON_REMOVE), VaadinIcon.CLOSE_SMALL.create());
        removeButton.setWidth("100%");
        removeButton.addClickListener(event -> EventBusHolder.getEventBus().post(CPPTRowRemovedEvent.builder()
                .rowIndex(getRowIndex()).build()));

        serviceButton = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_ROW_SERVICE_CHARGES), VaadinIcon.TRAIN.create());
        serviceButton.setEnabled(false);
        serviceButton.setWidth("100%");

        serviceButton.addClickListener(event -> {

            Dialog servicesDialog = new ViewServiceChargesForTrainDialog(serviceChargesPerTrainService);
            servicesDialog.open();
        });

        doneButton = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_ROW_BUTTON_DONE), VaadinIcon.CHECK_CIRCLE_O.create());
        doneButton.setWidth("100%");
        doneButton.setEnabled(false);
        doneButton.addClickListener(event -> {

            if (station.getValue() != null && lineNumbers.getValue() != null && tonnage.getValue() != null) {

                disableRow();

            } else {

                Dialog dialog = new BasicInfoDialog(getTranslation(CALCULATE_PRICE_PER_TRAIN_ROW_FILL_IN_ALL_WARNING));
                dialog.open();
            }
        });
    }

    private void initializeTonnageField(Double tonnageDouble) {

        tonnage = new NumberField(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TONNAGE));
        tonnage.setValue(tonnageDouble);
        tonnage.setValueChangeMode(ValueChangeMode.EAGER);
        tonnage.addValueChangeListener(event -> {

            EventBusHolder.getEventBus().post(CPPTTonnageChangedFromRowEvent.builder()
                    .tonnage(event.getValue())
                    .build());

            checkRowDone();
        });
    }

    private boolean isFirst() {

        return this.rowIndex == 1;
    }

    private void checkRowDone() {

        if (station.getValue() != null && lineNumbers.getValue() != null && tonnage.getValue() != null
                && locomotiveSeriesDtoSelect.getValue() != null && trainLength.getValue() != null) {

            doneButton.setEnabled(true);
        }
    }

    private void disableRow() {

        log.info("Disabling row:" + this.rowIndex);

        this.station.setEnabled(false);
        this.lineNumbers.setEnabled(false);
        this.tonnage.setEnabled(false);
        this.serviceButton.setEnabled(false);
        this.removeButton.setEnabled(false);
        this.doneButton.setEnabled(false);
        this.locomotiveSeriesDtoSelect.setEnabled(false);
        this.trainLength.setEnabled(false);

        EventBusHolder.getEventBus().post(CPPTRowDoneEvent.builder()
                .rowIndex(getRowIndex())
                .isFinal(getIsFinal())
                .build());
    }

    public void enableRow() {

        log.info("Enabling row:" + this.rowIndex);

        this.station.setEnabled(true);
        this.lineNumbers.setEnabled(true);
        this.tonnage.setEnabled(true);
        this.serviceButton.setEnabled(true);
        this.removeButton.setEnabled(true);
        this.doneButton.setEnabled(true);
        this.locomotiveSeriesDtoSelect.setEnabled(true);
        this.trainLength.setEnabled(false);
    }

    private void initializeLineNumberSelect(Collection<SectionNeighboursDto> neighbours) {

        lineNumbers = new Select<>();
        lineNumbers.addValueChangeListener(event -> checkRowDone());
        lineNumbers.setLabel(getTranslation(GRID_SERVICE_COLUMN_HEADER_LINE_NUMBER));

        if (this.selectedStation == null) {

            lineNumbers.setItems(Sets.newHashSet());

        } else {

            lineNumbers.setItems(this.sectionsService.getLineNumbersFromSectionNeighbourDtos(neighbours));
        }
    }

    private void initalizeStationSelect(Integer trainNumber, ServiceChargesPerTrainService serviceChargesPerTrainService,
                                        Collection<SectionNeighboursDto> neighbours) {

        Set<SectionNeighboursDto> visibleSectionNeighbourDtos = this.sectionsService.getVisibleSectionNeighbourDtos(
                this.rowIndex, this.isFinal, neighbours);

        station = new Select<>();
        station.setLabel(getTranslation(CALCULATE_PRICE_PER_TRAIN_ROW_STATION));
        station.setItems(visibleSectionNeighbourDtos);
        station.addValueChangeListener(event -> checkRowDone());
        station.setItemLabelGenerator(SectionNeighboursDto::getDisplayName);
        station.addValueChangeListener(event -> {

            Boolean isKeyStation = event.getValue().getIsKeyStation();
            String newStation = isKeyStation ? event.getValue().getDestination().getStation() : event.getValue().getNonKeyStation().getStation();

            EventBusHolder.getEventBus().post(CPPTStationChangedEvent.builder().selectedStation(newStation).isKeyStation(isKeyStation).build());

            this.selectedStation = StationViewModel.builder().selectedStation(newStation).isKeyStation(isKeyStation).build();

            if (this.selectedStation != null) {

                if (lineNumbers.getDataProvider().size(new Query<>()) == 0) {

                    Collection<SectionNeighboursDto> sectionNeighboursDtos = this.sectionsService
                            .getDirectNeighboursForSource(selectedStation, isFirst());

                    lineNumbers.setItems(this.sectionsService.getLineNumbersFromSectionNeighbourDtos(sectionNeighboursDtos));
                }

                if (this.trainNumber != null) {

                    this.serviceChargesPerTrainEntityList = serviceChargesPerTrainService
                            .findByTrainNumberAndRailRoadStation(trainNumber, this.selectedStation.getSelectedStation());

                    serviceChargesLabel.setText(String.format("%s %.3f",
                            getTranslation(CALCULATE_PRICE_PER_TRAIN_ROW_SERVICE_CHARGES_LBL),
                            serviceChargesPerTrainService.getTotalServiceChargesForTrainNumberAndRailStation(
                                    serviceChargesPerTrainEntityList)));

                    serviceButton.setEnabled(!this.serviceChargesPerTrainEntityList.isEmpty());
                }
            }
        });
    }

    public void setTrainNumber(Integer trainNumber) {

        log.debug("Setting train number to: " + trainNumber);
        this.trainNumber = trainNumber;
        this.station.setValue(new SectionNeighboursDto());
        this.station.setValue(this.station.getValue());
    }

    public int getRowIndex() {

        return this.rowIndex;
    }

    public boolean getIsFinal() {

        return this.isFinal;
    }

    public void setRowIndex(int rowIndex) {

        this.rowIndex = rowIndex;

        String rowTitleString;

        if (isFirst()) {

            rowTitleString = getTranslation(CALCULATE_PRICE_PER_TRAIN_ROW_TITLE_FIRST_STATION);

        } else if (getIsFinal()) {

            rowTitleString = getTranslation(CALCULATE_PRICE_PER_TRAIN_ROW_TITLE_FINAL_STATION);

        } else {

            rowTitleString = String.format("%d - %s", this.rowIndex, getTranslation(CALCULATE_PRICE_PER_TRAIN_ROW_TITLE_N_STATION));
        }

        this.rowTitle.setText(rowTitleString);
    }

    public StationViewModel getSelectedStation() {

        return this.selectedStation;
    }

    public CalculateTaxPerTrainRowDataDto getRowData() {

        SectionNeighboursDto sectionNeighboursDto = this.sectionsService.getByStationAndLineNumber(station.getValue(), neighbours);

        return CalculateTaxPerTrainRowDataDto
                .builder()
                .section(sectionNeighboursDto
                        .toBuilder()
                        .lineNumber(lineNumbers.getValue())
                        .rowIndex(this.getRowIndex())
                        .build())
                .tonnage(this.tonnage.getValue())
                .serviceChargesPerTrainEntityList(serviceChargesPerTrainEntityList)
                .build();
    }
}