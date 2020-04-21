package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.RailStationEntity;
import com.dp.trains.services.LineNumberService;
import com.dp.trains.services.RailStationService;
import com.dp.trains.ui.components.common.FilteringTextField;
import com.dp.trains.ui.components.dialogs.edit.EditRailStationDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@SuppressWarnings("unchecked")
public class RailStationsGrid extends SmartTACCalcGrid<RailStationEntity> {

    public RailStationsGrid(RailStationService railStationService, LineNumberService lineNumberService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(railStationService.fetch(0, 0)));

        Grid.Column<RailStationEntity> lineNumberColumn = this.addColumn(RailStationEntity::getLineNumber)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_LINE_NUMBER))
                .setSortable(true)
                .setResizable(true)
                .setFooter(String.format("%s %d", getTranslation(GRID_FOOTERS_TOTAL), railStationService.count()));

        Grid.Column<RailStationEntity> stationColumn = this.addColumn(RailStationEntity::getStation)
                .setHeader(getTranslation(GRID_RAIL_STATION_COLUMN_HEADER_STATION))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<RailStationEntity> isKeystationColumn = this.addColumn(
                new ComponentRenderer<>(railStationEntity -> {
                    Checkbox checkbox = new Checkbox();
                    checkbox.setValue(railStationEntity.getIsKeyStation() == null ? false : railStationEntity.getIsKeyStation());
                    checkbox.setEnabled(false);
                    checkbox.addValueChangeListener(event -> railStationEntity.setIsKeyStation(event.getValue()));
                    return checkbox;
                }))
                .setHeader(getTranslation(GRID_RAIL_STATION_COLUMN_HEADER_IS_KEY_STATION))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<RailStationEntity> typeColumn = this.addColumn(RailStationEntity::getType)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_TYPE))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<RailStationEntity> countryColumn = this.addColumn(RailStationEntity::getCountry)
                .setHeader(getTranslation(GRID_RAIL_STATION_COLUMN_HEADER_COUNTRY))
                .setSortable(true)
                .setResizable(true);

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), VaadinIcon.EDIT.create(), click -> {

            Dialog editDialog = new EditRailStationDialog(this, railStationService, lineNumberService, item);
            editDialog.open();
        }));

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE), new Icon(VaadinIcon.TRASH), click -> {

            ListDataProvider<RailStationEntity> dataProvider =
                    (ListDataProvider<RailStationEntity>) this.getDataProvider();

            railStationService.remove(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        }));

        HeaderRow filterRow = this.appendHeaderRow();

        FilteringTextField lineNumberFieldFilter = new FilteringTextField();
        FilteringTextField stationFieldFiler = new FilteringTextField();
        Checkbox isKeyStationFieldFilter = new Checkbox();
        FilteringTextField typeFieldFilter = new FilteringTextField();
        FilteringTextField countryFieldFilter = new FilteringTextField();

        lineNumberFieldFilter.addValueChangeListener(event -> ((ListDataProvider<RailStationEntity>)
                this.getDataProvider()).addFilter(railStationEntity ->
                StringUtils.containsIgnoreCase(String.valueOf(railStationEntity.getLineNumber()),
                        lineNumberFieldFilter.getValue())));

        stationFieldFiler.addValueChangeListener(event -> ((ListDataProvider<RailStationEntity>)
                this.getDataProvider()).addFilter(railStationEntity ->
                StringUtils.containsIgnoreCase(railStationEntity.getStation(), stationFieldFiler.getValue())));

        isKeyStationFieldFilter.addValueChangeListener(event -> ((ListDataProvider<RailStationEntity>)
                this.getDataProvider()).addFilter(railStationEntity ->
                railStationEntity.getIsKeyStation().equals(isKeyStationFieldFilter.getValue())));

        typeFieldFilter.addValueChangeListener(event -> ((ListDataProvider<RailStationEntity>)
                this.getDataProvider()).addFilter(railStationEntity ->
                StringUtils.containsIgnoreCase(railStationEntity.getType(), typeFieldFilter.getValue())));

        countryFieldFilter.addValueChangeListener(event -> ((ListDataProvider<RailStationEntity>)
                this.getDataProvider()).addFilter(railStationEntity ->
                StringUtils.containsIgnoreCase(railStationEntity.getCountry(), countryFieldFilter.getValue())));

        filterRow.getCell(lineNumberColumn).setComponent(lineNumberFieldFilter);
        filterRow.getCell(stationColumn).setComponent(stationFieldFiler);
        filterRow.getCell(isKeystationColumn).setComponent(isKeyStationFieldFilter);
        filterRow.getCell(typeColumn).setComponent(typeFieldFilter);
        filterRow.getCell(countryColumn).setComponent(countryFieldFilter);
    }
}