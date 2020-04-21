package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.ServiceChargesPerTrainEntity;
import com.dp.trains.services.ServiceChargesPerTrainService;
import com.dp.trains.ui.components.common.FilteringTextField;
import com.dp.trains.ui.components.dialogs.edit.EditServiceChargesPerTrainDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@SuppressWarnings("unchecked")
public class ServiceChargesPerTrainGrid extends SmartTACCalcGrid<ServiceChargesPerTrainEntity> {

    public ServiceChargesPerTrainGrid(ServiceChargesPerTrainService serviceChargesPerTrainService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(serviceChargesPerTrainService.fetch(0, 0)));

        Grid.Column<ServiceChargesPerTrainEntity> trainNumberColumn = this.addColumn(ServiceChargesPerTrainEntity::getTrainNumber)
                .setHeader(getTranslation(SERVICE_CHARGES_PER_TRAIN_GRID_COLUMN_HEADER_TRAIN_NUMBER))
                .setSortable(true)
                .setResizable(true)
                .setFooter(String.format("%s %d", getTranslation(GRID_FOOTERS_TOTAL), serviceChargesPerTrainService.count()));

        Grid.Column<ServiceChargesPerTrainEntity> stationColumn = this.addColumn(s -> s.getRailStationEntity().getStation())
                .setHeader(getTranslation(GRID_RAIL_STATION_COLUMN_HEADER_STATION))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<ServiceChargesPerTrainEntity> serviceNameColumn = this.addColumn(s -> s.getServiceEntity().getName())
                .setHeader(getTranslation(SERVICE_CHARGES_PER_TRAIN_GRID_COLUMN_HEADER_SERVICE))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<ServiceChargesPerTrainEntity> serviceCountColumn = this.addColumn(ServiceChargesPerTrainEntity::getServiceCount)
                .setHeader(getTranslation(SERVICE_CHARGES_PER_TRAIN_GRID_COLUMN_HEADER_SERVICE_COUNT))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<ServiceChargesPerTrainEntity> serviceUnitPriceColumn = this.addColumn(x -> x.getServiceEntity().getUnitPrice())
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_UNIT_PRICE))
                .setSortable(true)
                .setResizable(true);

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), VaadinIcon.EDIT.create(), click -> {

            Dialog editDialog = new EditServiceChargesPerTrainDialog(this, serviceChargesPerTrainService, item);
            editDialog.open();
        }));

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE),
                new Icon(VaadinIcon.TRASH), click -> {

            ListDataProvider<ServiceChargesPerTrainEntity> dataProvider =
                    (ListDataProvider<ServiceChargesPerTrainEntity>) this.getDataProvider();

            serviceChargesPerTrainService.remove(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        }));

        HeaderRow filterRow = this.appendHeaderRow();

        FilteringTextField trainNumberFieldFilter = new FilteringTextField();
        FilteringTextField stationFieldFilter = new FilteringTextField();
        FilteringTextField serviceNameFieldFilter = new FilteringTextField();
        FilteringTextField sericeCountFieldFilter = new FilteringTextField();
        FilteringTextField serviceUnitPriceFieldFilter = new FilteringTextField();

        trainNumberFieldFilter.addValueChangeListener(event -> ((ListDataProvider<ServiceChargesPerTrainEntity>)
                this.getDataProvider()).addFilter(serviceChargesPerTrainEntity ->
                StringUtils.containsIgnoreCase(String.valueOf(serviceChargesPerTrainEntity.getTrainNumber()),
                        trainNumberFieldFilter.getValue())));

        stationFieldFilter.addValueChangeListener(event -> ((ListDataProvider<ServiceChargesPerTrainEntity>)
                this.getDataProvider()).addFilter(serviceChargesPerTrainEntity ->
                StringUtils.containsIgnoreCase(serviceChargesPerTrainEntity.getRailStationEntity().getStation(),
                        stationFieldFilter.getValue())));

        serviceNameFieldFilter.addValueChangeListener(event -> ((ListDataProvider<ServiceChargesPerTrainEntity>)
                this.getDataProvider()).addFilter(serviceChargesPerTrainEntity ->
                StringUtils.containsIgnoreCase(serviceChargesPerTrainEntity.getServiceEntity().getName(),
                        serviceNameFieldFilter.getValue())));

        sericeCountFieldFilter.addValueChangeListener(event -> ((ListDataProvider<ServiceChargesPerTrainEntity>)
                this.getDataProvider()).addFilter(serviceChargesPerTrainEntity ->
                StringUtils.containsIgnoreCase(String.valueOf(serviceChargesPerTrainEntity.getServiceCount()),
                        sericeCountFieldFilter.getValue())));

        serviceUnitPriceFieldFilter.addValueChangeListener(event -> ((ListDataProvider<ServiceChargesPerTrainEntity>)
                this.getDataProvider()).addFilter(serviceChargesPerTrainEntity ->
                StringUtils.containsIgnoreCase(String.valueOf(serviceChargesPerTrainEntity.getServiceEntity().getUnitPrice()),
                        serviceUnitPriceFieldFilter.getValue())));

        filterRow.getCell(trainNumberColumn).setComponent(trainNumberFieldFilter);
        filterRow.getCell(stationColumn).setComponent(stationFieldFilter);
        filterRow.getCell(serviceNameColumn).setComponent(serviceNameFieldFilter);
        filterRow.getCell(serviceCountColumn).setComponent(sericeCountFieldFilter);
        filterRow.getCell(serviceUnitPriceColumn).setComponent(serviceUnitPriceFieldFilter);
    }
}