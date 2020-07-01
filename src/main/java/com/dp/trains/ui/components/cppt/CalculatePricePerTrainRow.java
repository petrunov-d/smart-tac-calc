package com.dp.trains.ui.components.cppt;

import com.dp.trains.event.*;
import com.dp.trains.model.dto.CPPTRowDataDto;
import com.dp.trains.model.dto.CarrierCompanyDto;
import com.dp.trains.model.dto.LocomotiveSeriesDto;
import com.dp.trains.model.entities.ServiceChargesPerTrainEntity;
import com.dp.trains.model.viewmodels.RailStationViewModel;
import com.dp.trains.services.CarrierCompanyService;
import com.dp.trains.services.RailStationService;
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
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class CalculatePricePerTrainRow extends HorizontalLayout {

    private final CarrierCompanyService carrierCompanyService;
    private int rowIndex;
    private Integer trainNumber;
    private final boolean isFinal;
    private RailStationViewModel selectedStation;
    private List<ServiceChargesPerTrainEntity> serviceChargesPerTrainEntityList = Lists.newArrayList();

    private final RailStationService railStationService;

    private final H5 rowTitle;

    private Button doneButton;
    private Button removeButton;
    private Button serviceButton;

    private NumberField tonnage;
    private NumberField trainLength;

    private Select<Integer> lineNumbers;
    private Select<RailStationViewModel> station;
    private Select<LocomotiveSeriesDto> locomotiveSeriesDtoSelect;
    private Select<String> carrierCompanyDtoSelect;

    public CalculatePricePerTrainRow(int rowIndex,
                                     boolean isFinal,
                                     Integer trainNumber,
                                     RailStationService railStationService,
                                     ServiceChargesPerTrainService serviceChargesPerTrainService,
                                     CarrierCompanyService carrierCompanyService,
                                     RailStationViewModel selectedStation,
                                     Double tonnageDouble,
                                     CarrierCompanyDto selectedCarrierCompany,
                                     LocomotiveSeriesDto selectedLocomotiveSeriesDto,
                                     Collection<LocomotiveSeriesDto> locomotiveSeriesDtos,
                                     Double trainLengthDouble) {

        EventBusHolder.getEventBus().register(this);

        this.rowTitle = new H5();
        this.isFinal = isFinal;
        this.selectedStation = selectedStation;
        this.trainNumber = trainNumber;
        this.railStationService = railStationService;
        this.carrierCompanyService = carrierCompanyService;

        this.setRowIndex(rowIndex);

        initializeStationsSelect(trainNumber, serviceChargesPerTrainService, selectedStation);
        initializeLineNumberSelect();
        initializeTonnageField(tonnageDouble);
        initializeTrainLength(trainLengthDouble);
        initializeLocomotiveSeriesDtoSelect(selectedLocomotiveSeriesDto, locomotiveSeriesDtos);
        initializeCarrierCompanyDtoSelect(selectedCarrierCompany);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(rowTitle, station, lineNumbers, tonnage, trainLength, carrierCompanyDtoSelect, locomotiveSeriesDtoSelect);

        initializeRowButtons(serviceChargesPerTrainService);

        HorizontalLayout serviceLayout = new HorizontalLayout();

        serviceLayout.setSizeFull();
        serviceLayout.setPadding(false);
        serviceLayout.setMargin(false);
        serviceLayout.setWidth("100%");

        serviceLayout.add(serviceButton, doneButton, removeButton);

        VerticalLayout serviceButtonLayout = new VerticalLayout();
        serviceButtonLayout.add(serviceLayout);
        serviceButtonLayout.setSpacing(false);
        serviceButtonLayout.setPadding(true);
        serviceButtonLayout.setMargin(true);

        horizontalLayout.add(serviceButtonLayout);
        this.add(horizontalLayout);

        this.setSpacing(false);
        this.setMargin(false);
        this.setPadding(false);
    }

    private void initializeTrainLength(Double trainLengthDouble) {

        this.trainLength = new NumberField(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TRAIN_LENGTH));
        this.trainLength.setValue(trainLengthDouble);
        this.trainLength.setValueChangeMode(ValueChangeMode.EAGER);
        this.trainLength.addValueChangeListener(event -> {

            EventBusHolder.getEventBus().post(CPPTTrainLengthChangedFromRowEvent.builder()
                    .trainLength(event.getValue())
                    .build());

            checkRowDone();
        });
    }

    private void initializeCarrierCompanyDtoSelect(CarrierCompanyDto selectedCarrierCompanyDto) {

        this.carrierCompanyDtoSelect = new Select<>();
        this.carrierCompanyDtoSelect.setLabel(getTranslation(LABEL_CARRIER_COMPANY));
        this.carrierCompanyDtoSelect.setItems(this.carrierCompanyService.getDtos().stream().map(CarrierCompanyDto::getCarrierName).distinct().collect(Collectors.toList()));
        this.carrierCompanyDtoSelect.setValue(selectedCarrierCompanyDto.getCarrierName());
        this.carrierCompanyDtoSelect.addValueChangeListener(event -> {

            EventBusHolder.getEventBus().post(CPPTCarrierCompanyChangedEvent.builder()
                    .selectedDto(event.getValue())
                    .build());

            Collection<LocomotiveSeriesDto> newLocomotiveSeriesDto = this.carrierCompanyService
                    .getByCarrierName(event.getValue());

            locomotiveSeriesDtoSelect.setItems(newLocomotiveSeriesDto);
        });
    }

    private void initializeLocomotiveSeriesDtoSelect(LocomotiveSeriesDto selectedLocomotiveSeriesDto,
                                                     Collection<LocomotiveSeriesDto> locomotiveSeriesDtos) {

        this.locomotiveSeriesDtoSelect = new Select<>();
        this.locomotiveSeriesDtoSelect.setLabel(getTranslation(GRID_CARRIER_COMPANY_LOCOMOTIVE_WEIGHT));
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

        this.removeButton = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_ROW_BUTTON_REMOVE), VaadinIcon.CLOSE_SMALL.create());
        this.removeButton.setWidth("100%");
        this.removeButton.addClickListener(event -> EventBusHolder.getEventBus().post(CPPTRowRemovedEvent.builder()
                .rowIndex(getRowIndex()).build()));

        this.serviceButton = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_ROW_SERVICE_CHARGES), VaadinIcon.TRAIN.create());
        this.serviceButton.setEnabled(false);
        this.serviceButton.setWidth("100%");

        this.serviceButton.addClickListener(event -> {

            Dialog servicesDialog = new ViewServiceChargesForTrainDialog(serviceChargesPerTrainService);
            servicesDialog.open();
        });

        this.doneButton = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_ROW_BUTTON_DONE), VaadinIcon.CHECK_CIRCLE_O.create());
        this.doneButton.setWidth("100%");
        this.doneButton.setEnabled(false);
        this.doneButton.addClickListener(event -> {

            if (station.getValue() != null && lineNumbers.getValue() != null && tonnage.getValue() != null) {

                disableRow();

            } else {

                Dialog dialog = new BasicInfoDialog(getTranslation(CALCULATE_PRICE_PER_TRAIN_ROW_FILL_IN_ALL_WARNING));
                dialog.open();
            }
        });
    }

    private void initializeTonnageField(Double tonnageDouble) {

        this.tonnage = new NumberField(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TONNAGE));
        this.tonnage.setValue(tonnageDouble);
        this.tonnage.setValueChangeMode(ValueChangeMode.EAGER);
        this.tonnage.addValueChangeListener(event -> {

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
        this.carrierCompanyDtoSelect.setEnabled(false);

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
        this.trainLength.setEnabled(true);
        this.carrierCompanyDtoSelect.setEnabled(true);
    }

    private void initializeLineNumberSelect() {

        this.lineNumbers = new Select<>();
        this.lineNumbers.addValueChangeListener(event -> checkRowDone());
        this.lineNumbers.setLabel(getTranslation(GRID_SERVICE_COLUMN_HEADER_LINE_NUMBER));
        this.lineNumbers.setItems(Sets.newHashSet());
    }

    private Set<RailStationViewModel> initializeStationsSelect(Integer trainNumber, ServiceChargesPerTrainService serviceChargesPerTrainService,
                                                               RailStationViewModel selectedStation) {

        Set<RailStationViewModel> neighbouringRailStations = this.railStationService
                .getNeighbouringRailStations(getRowIndex(), getIsFinal(), selectedStation)
                .stream()
                .sorted(Comparator.comparing(RailStationViewModel::getRailStation))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        this.station = new Select<>();
        this.station.setLabel(getTranslation(CALCULATE_PRICE_PER_TRAIN_ROW_STATION));
        this.station.setItems(neighbouringRailStations);
        this.station.addValueChangeListener(event -> checkRowDone());
        this.station.setItemLabelGenerator(RailStationViewModel::getRailStation);
        this.station.addValueChangeListener(event -> {

            Boolean isKeyStation = event.getValue().getIsKeyStation();
            String newStation = event.getValue().getRailStation();

            EventBusHolder.getEventBus().post(CPPTStationChangedEvent.builder().selectedStation(newStation)
                    .isKeyStation(isKeyStation).lineNumber(event.getValue().getLineNumber()).build());

            this.selectedStation = RailStationViewModel.builder()
                    .railStation(newStation)
                    .lineNumber(event.getValue().getLineNumber())
                    .isKeyStation(isKeyStation).build();

            if (this.selectedStation != null) {

                Collection<RailStationViewModel> newNeighbouringRailStations = this.railStationService
                        .getNeighbouringRailStations(getRowIndex() + 1, getIsFinal(), this.selectedStation);

                lineNumbers.setItems(this.railStationService.getLineNumbersForRailStationNeighbours(newNeighbouringRailStations));

                if (this.trainNumber != null) {

                    this.serviceChargesPerTrainEntityList = serviceChargesPerTrainService
                            .findByTrainNumberAndRailRoadStation(trainNumber, this.selectedStation.getRailStation());

                    serviceButton.setEnabled(!this.serviceChargesPerTrainEntityList.isEmpty());
                }
            }
        });

        return neighbouringRailStations;
    }

    public void setTrainNumber(Integer trainNumber) {

        log.debug("Setting train number to: " + trainNumber);
        this.trainNumber = trainNumber;
        this.station.setValue(new RailStationViewModel());
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

    public RailStationViewModel getSelectedStation() {

        return this.selectedStation;
    }

    public CPPTRowDataDto getRowData() {

        return CPPTRowDataDto
                .builder()
                .stationViewModel(this.selectedStation)
                .tonnage(this.tonnage.getValue())
                .locomotiveSeries(this.locomotiveSeriesDtoSelect.getValue().getSeries())
                .locomotiveWeight(this.locomotiveSeriesDtoSelect.getValue().getWeight())
                .serviceChargesPerTrainEntityList(serviceChargesPerTrainEntityList)
                .build();
    }
}