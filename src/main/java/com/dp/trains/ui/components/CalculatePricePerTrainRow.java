package com.dp.trains.ui.components;

import com.dp.trains.event.CPPTRowRemovedEvent;
import com.dp.trains.event.CPPTStationChangedEvent;
import com.dp.trains.model.dto.CalculateTaxPerTrainRowDataDto;
import com.dp.trains.model.dto.SectionNeighboursDto;
import com.dp.trains.model.entities.ServiceChargesPerTrainEntity;
import com.dp.trains.services.SectionsService;
import com.dp.trains.services.ServiceChargesPerTrainService;
import com.dp.trains.ui.components.dialogs.ViewServiceChargesForTrainDialog;
import com.dp.trains.utils.EventBusHolder;
import com.google.common.collect.Lists;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@Slf4j
public class CalculatePricePerTrainRow extends HorizontalLayout {

    private int rowIndex;
    private boolean isFinal;
    private Integer trainNumber;
    private String currentKeyStation;
    private Select<SectionNeighboursDto> station;
    private Select<SectionNeighboursDto> lineNumbers;
    private IntegerField tonnage;
    private Button serviceButton;
    private Button removeButton;
    private H5 serviceChargesLabel;
    private H5 rowTitle;

    private List<ServiceChargesPerTrainEntity> serviceChargesPerTrainEntityList = Lists.newArrayList();

    public CalculatePricePerTrainRow(int i, boolean isFinal,
                                     Integer trainNumber, SectionsService sectionsService,
                                     ServiceChargesPerTrainService serviceChargesPerTrainService,
                                     String currentKeyStation) {

        EventBusHolder.getEventBus().register(this);

        rowTitle = new H5();
        this.isFinal = isFinal;
        this.currentKeyStation = currentKeyStation;
        this.trainNumber = trainNumber;
        this.setRowIndex(i, isFinal);

        Set<SectionNeighboursDto> neighbours = sectionsService.getDirectKeyStationNeighboursForSource(currentKeyStation);

        initalizeStationSelect(i, trainNumber, serviceChargesPerTrainService, neighbours);
        initializeLineNumberSelect(neighbours);

        tonnage = new IntegerField("Tonnage");

        serviceChargesLabel = new H5();
        serviceChargesLabel.setWidth("100%");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(rowTitle, station, lineNumbers, tonnage);

        removeButton = new Button("Remove", VaadinIcon.CLOSE_SMALL.create());
        removeButton.setWidth("100%");
        removeButton.addClickListener(event -> EventBusHolder.getEventBus().post(CPPTRowRemovedEvent.builder()
                .rowIndex(getRowIndex()).build()));

        serviceButton = new Button("View Service Charges", VaadinIcon.TRAIN.create());
        serviceButton.setEnabled(false);
        serviceButton.setWidth("100%");

        serviceButton.addClickListener(event -> {

            Dialog servicesDialog = new ViewServiceChargesForTrainDialog(serviceChargesPerTrainService);
            servicesDialog.open();
        });

        HorizontalLayout serviceLayout = new HorizontalLayout();
        serviceLayout.setSizeFull();
        serviceLayout.setPadding(false);
        serviceLayout.setMargin(false);
        serviceLayout.setWidth("100%");

        serviceLayout.add(serviceButton, removeButton, serviceChargesLabel);

        VerticalLayout serviceButtonLayout = new VerticalLayout();
        serviceButtonLayout.add(serviceLayout);
        serviceButtonLayout.setSpacing(false);
        serviceButtonLayout.setPadding(true);
        serviceButtonLayout.setMargin(true);

        horizontalLayout.add(serviceButtonLayout);
        this.add(horizontalLayout);
    }

    private void initializeLineNumberSelect(Set<SectionNeighboursDto> neighbours) {

        lineNumbers = new Select<>();
        lineNumbers.setLabel("Line Number");
        lineNumbers.setItems(neighbours);
        lineNumbers.setItemLabelGenerator(sectionNeighboursDto -> String.valueOf(sectionNeighboursDto.getLineNumber()));
    }

    private void initalizeStationSelect(int i, Integer trainNumber,
                                        ServiceChargesPerTrainService serviceChargesPerTrainService,
                                        Set<SectionNeighboursDto> neighbours) {
        station = new Select<>();
        station.setLabel(String.format("%d - key station", i));
        station.setItems(neighbours);
        station.setItemLabelGenerator(SectionNeighboursDto::getKeyStation);
        station.addValueChangeListener(event -> {

            String newKeyStation = event.getValue().getKeyStation();

            EventBusHolder.getEventBus().post(CPPTStationChangedEvent.builder()
                    .selectedKeyStation(event.getValue().getKeyStation())
                    .build());

            this.currentKeyStation = newKeyStation;

            if (this.currentKeyStation != null && this.trainNumber != null) {

                serviceChargesPerTrainEntityList = serviceChargesPerTrainService
                        .findByTrainNumberAndRailRoadStation(trainNumber, this.currentKeyStation);

                serviceChargesLabel.setText(String.format("Total service charges: %f", serviceChargesPerTrainService
                        .getTotalServiceChargesForTrainNumberAndRailStation(serviceChargesPerTrainEntityList)));

                serviceButton.setEnabled(true);
            } else {

                serviceButton.setEnabled(false);
            }
        });
    }

    public void setTrainNumber(Integer trainNumber) {

        log.info("Setting train number to: " + trainNumber);
        this.trainNumber = trainNumber;
        this.station.setValue(new SectionNeighboursDto());
        this.station.setValue(this.station.getValue());
    }

    public int getRowIndex() {

        return rowIndex;
    }

    public boolean getIsFinal() {
        return this.isFinal;
    }

    public void setRowIndex(int i, boolean isFinal) {

        this.rowIndex = i;

        String rowTitleString;

        if (this.rowIndex == 1) {

            rowTitleString = "First Station";
        } else if (isFinal) {

            rowTitleString = "Final Station";
        } else {
            rowTitleString = String.format("%d - key station", this.rowIndex);
        }

        rowTitle.setText(rowTitleString);
    }

    public CalculateTaxPerTrainRowDataDto getRowData() {

        return null;
    }
}