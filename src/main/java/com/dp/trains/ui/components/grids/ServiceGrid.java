package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.ServiceEntity;
import com.dp.trains.services.LineNumberService;
import com.dp.trains.services.ServiceService;
import com.dp.trains.ui.components.dialogs.EditServiceDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import lombok.extern.slf4j.Slf4j;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@SuppressWarnings("unchecked")
public class ServiceGrid extends SmartTACCalcGrid<ServiceEntity> {

    public ServiceGrid(ServiceService serviceService, LineNumberService lineNumberService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(serviceService.fetch(0, 0)));

        Grid.Column<ServiceEntity> nameColumn = this.addColumn(ServiceEntity::getName)
                .setHeader(getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_NAME))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<ServiceEntity> lineNumberColumn = this.addColumn(ServiceEntity::getCode)
                .setHeader(getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_CODE))
                .setSortable(true)
                .setResizable(true);

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
    }
}