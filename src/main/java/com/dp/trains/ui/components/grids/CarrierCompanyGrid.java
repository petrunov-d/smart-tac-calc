package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.CarrierCompanyEntity;
import com.dp.trains.services.CarrierCompanyService;
import com.dp.trains.ui.components.common.FilteringTextField;
import com.dp.trains.ui.components.dialogs.edit.EditCarrierCompanyDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@SuppressWarnings({"unchecked"})
public class CarrierCompanyGrid extends SmartTACCalcGrid<CarrierCompanyEntity> {

    public CarrierCompanyGrid(CarrierCompanyService carrierCompanyService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(carrierCompanyService.fetch(0, 0)));

        Grid.Column<CarrierCompanyEntity> carrierNameColumn = this.addColumn(CarrierCompanyEntity::getCarrierName)
                .setHeader(getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_NAME))
                .setSortable(true)
                .setResizable(true)
                .setFooter(String.format("%s %d", getTranslation(GRID_FOOTERS_TOTAL), carrierCompanyService.count()));

        Grid.Column<CarrierCompanyEntity> locomotiveSeriesColumn = this.addColumn(CarrierCompanyEntity::getLocomotiveSeries)
                .setHeader(getTranslation(getTranslation(GRID_CARRIER_COMPANY_LOCOMOTIVE_SERIES)))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<CarrierCompanyEntity> locomotiveTypeColumn = this.addColumn(CarrierCompanyEntity::getLocomotiveType)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_TYPE))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<CarrierCompanyEntity> locomotiveWeightColumn = this.addColumn(CarrierCompanyEntity::getLocomotiveWeight)
                .setHeader(getTranslation(getTranslation(GRID_CARRIER_COMPANY_LOCOMOTIVE_WEIGHT)))
                .setSortable(true)
                .setResizable(true);

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT),
                VaadinIcon.EDIT.create(), click -> {

            Dialog editDialog = new EditCarrierCompanyDialog(this, carrierCompanyService, item);
            editDialog.open();
        }));

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE),
                VaadinIcon.TRASH.create(), click -> {

            ListDataProvider<CarrierCompanyEntity> dataProvider = (ListDataProvider<CarrierCompanyEntity>) this
                    .getDataProvider();

            carrierCompanyService.remove(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        }));

        HeaderRow filterRow = this.appendHeaderRow();

        FilteringTextField carrierNameFieldFilter = new FilteringTextField();
        FilteringTextField locomotiveSeriesFieldFilter = new FilteringTextField();
        FilteringTextField locomotiveTypeFieldFilter = new FilteringTextField();
        FilteringTextField locomotiveWeightFieldFilter = new FilteringTextField();

        carrierNameFieldFilter.addValueChangeListener(event -> ((ListDataProvider<CarrierCompanyEntity>)
                this.getDataProvider()).addFilter(carrierCompanyEntity ->
                StringUtils.containsIgnoreCase(carrierCompanyEntity.getCarrierName(),
                        carrierNameFieldFilter.getValue())));

        locomotiveSeriesFieldFilter.addValueChangeListener(event -> ((ListDataProvider<CarrierCompanyEntity>)
                this.getDataProvider()).addFilter(carrierCompanyEntity ->
                StringUtils.containsIgnoreCase(carrierCompanyEntity.getLocomotiveSeries(),
                        locomotiveSeriesFieldFilter.getValue())));

        locomotiveTypeFieldFilter.addValueChangeListener(event -> ((ListDataProvider<CarrierCompanyEntity>)
                this.getDataProvider()).addFilter(carrierCompanyEntity ->
                StringUtils.containsIgnoreCase(carrierCompanyEntity.getLocomotiveType(),
                        locomotiveTypeFieldFilter.getValue())));

        locomotiveWeightFieldFilter.addValueChangeListener(event -> ((ListDataProvider<CarrierCompanyEntity>)
                this.getDataProvider()).addFilter(carrierCompanyEntity ->
                StringUtils.containsIgnoreCase(String.valueOf(carrierCompanyEntity.getLocomotiveWeight()),
                        locomotiveWeightFieldFilter.getValue())));

        filterRow.getCell(carrierNameColumn).setComponent(carrierNameFieldFilter);
        filterRow.getCell(locomotiveSeriesColumn).setComponent(locomotiveSeriesFieldFilter);
        filterRow.getCell(locomotiveTypeColumn).setComponent(locomotiveTypeFieldFilter);
        filterRow.getCell(locomotiveWeightColumn).setComponent(locomotiveWeightFieldFilter);
    }
}