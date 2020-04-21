package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.ServiceEntity;
import com.dp.trains.services.ServiceService;
import com.dp.trains.ui.components.common.FilteringTextField;
import com.dp.trains.ui.components.dialogs.edit.EditServiceDialog;
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
public class ServiceGrid extends SmartTACCalcGrid<ServiceEntity> {

    public ServiceGrid(ServiceService serviceService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(serviceService.fetch(0, 0)));

        Grid.Column<ServiceEntity> nameColumn = this.addColumn(ServiceEntity::getName)
                .setHeader(getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_NAME))
                .setSortable(true)
                .setResizable(true)
                .setFooter(String.format("%s %d", getTranslation(GRID_FOOTERS_TOTAL), serviceService.count()));

        Grid.Column<ServiceEntity> metricColumn = this.addColumn(ServiceEntity::getMetric)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_METRIC))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<ServiceEntity> typeColumn = this.addColumn(ServiceEntity::getType)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_TYPE))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<ServiceEntity> unitPriceColumn = this.addColumn(ServiceEntity::getUnitPrice)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_UNIT_PRICE))
                .setSortable(true)
                .setResizable(true);

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), VaadinIcon.EDIT.create(), click -> {

            Dialog editDialog = new EditServiceDialog(this, serviceService, item);
            editDialog.open();
        }));

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE), new Icon(VaadinIcon.TRASH), click -> {

            ListDataProvider<ServiceEntity> dataProvider = (ListDataProvider<ServiceEntity>) this.getDataProvider();

            serviceService.remove(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        }));

        HeaderRow filterRow = this.appendHeaderRow();

        FilteringTextField nameFieldFilter = new FilteringTextField();
        FilteringTextField metricFieldFilter = new FilteringTextField();
        FilteringTextField typeFieldFilter = new FilteringTextField();
        FilteringTextField unitPriceFieldFilter = new FilteringTextField();

        nameFieldFilter.addValueChangeListener(event -> ((ListDataProvider<ServiceEntity>)
                this.getDataProvider()).addFilter(serviceEntity ->
                StringUtils.containsIgnoreCase(serviceEntity.getName(), nameFieldFilter.getValue())));

        metricFieldFilter.addValueChangeListener(event -> ((ListDataProvider<ServiceEntity>)
                this.getDataProvider()).addFilter(serviceEntity ->
                StringUtils.containsIgnoreCase(serviceEntity.getMetric(), metricFieldFilter.getValue())));

        typeFieldFilter.addValueChangeListener(event -> ((ListDataProvider<ServiceEntity>)
                this.getDataProvider()).addFilter(serviceEntity ->
                StringUtils.containsIgnoreCase(serviceEntity.getType(), typeFieldFilter.getValue())));

        unitPriceFieldFilter.addValueChangeListener(event -> ((ListDataProvider<ServiceEntity>)
                this.getDataProvider()).addFilter(serviceEntity ->
                StringUtils.containsIgnoreCase(String.valueOf(serviceEntity.getUnitPrice()), unitPriceFieldFilter.getValue())));

        filterRow.getCell(nameColumn).setComponent(nameFieldFilter);
        filterRow.getCell(metricColumn).setComponent(metricFieldFilter);
        filterRow.getCell(typeColumn).setComponent(typeFieldFilter);
        filterRow.getCell(unitPriceColumn).setComponent(unitPriceFieldFilter);

    }
}