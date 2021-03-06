package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.SectionEntity;
import com.dp.trains.services.*;
import com.dp.trains.ui.components.common.FilteringTextField;
import com.dp.trains.ui.components.dialogs.edit.EditSectionDialog;
import com.dp.trains.ui.components.dialogs.edit.EditSubSectionsDialog;
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
public class SectionGrid extends SmartTACCalcGrid<SectionEntity> {

    public SectionGrid(SectionsService sectionsService, SubSectionService subSectionService,
                       LineNumberService lineNumberService, LineTypeService lineTypeService, RailStationService railStationService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(sectionsService.fetch(0, 0)));

        Grid.Column<SectionEntity> lineNumberColumn = this.addColumn(SectionEntity::getLineNumber)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_LINE_NUMBER))
                .setSortable(true)
                .setResizable(true)
                .setFooter(String.format("%s %d", getTranslation(GRID_FOOTERS_TOTAL), sectionsService.count()));

        Grid.Column<SectionEntity> firstKeyPointColumn = this.addColumn(SectionEntity::getFirstKeyPoint)
                .setHeader(getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_FIRST_KEY_POINT))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<SectionEntity> lastKeyPointColumn = this.addColumn(SectionEntity::getLastKeyPoint)
                .setHeader(getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_LAST_KEY_POINT))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<SectionEntity> isElectrifiedColumn = this.addColumn(new ComponentRenderer<>(userEntity -> {

            Checkbox isElectrifiedCheckBox = new Checkbox();
            isElectrifiedCheckBox.setValue(userEntity.getIsElectrified() == null ? false : userEntity.getIsElectrified());
            isElectrifiedCheckBox.setEnabled(false);
            return isElectrifiedCheckBox;

        })).setHeader(getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_IS_ELECTRIFED))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<SectionEntity> kilometersBetweenStationsColumn = this.addColumn(SectionEntity::getKilometersBetweenStations)
                .setHeader(getTranslation(GRID_SECTION_COLUMN_HEADER_KILOMETERS_BETWEEN_STATIONS))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<SectionEntity> lineTypeColumn = this.addColumn(SectionEntity::getLineType)
                .setHeader(getTranslation(GRID_LINE_TYPE_COLUMN_HEADER_LINE_TYPE))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<SectionEntity> unitPriceColumn = this.addColumn(SectionEntity::getUnitPrice)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_UNIT_PRICE))
                .setSortable(true)
                .setResizable(true);


        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), VaadinIcon.EDIT.create(), click -> {

            Dialog editDialog = new EditSectionDialog(this, sectionsService, lineTypeService,
                    lineNumberService, item, railStationService);
            editDialog.open();
        }));

        this.addComponentColumn(item -> new Button(getTranslation(GRID_SECTION_BUTTON_VIEW_SUB_SECTIONS_LABEL),
                new Icon(VaadinIcon.EYE), click -> {

            Dialog editUserDialog = new EditSubSectionsDialog(item, subSectionService, railStationService);
            editUserDialog.open();
        }));

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE), new Icon(VaadinIcon.TRASH),
                click -> {

                    ListDataProvider<SectionEntity> dataProvider =
                            (ListDataProvider<SectionEntity>) this.getDataProvider();

                    sectionsService.remove(item);
                    dataProvider.getItems().remove(item);
                    dataProvider.refreshAll();
                }));

        HeaderRow filterRow = this.appendHeaderRow();

        FilteringTextField lineNumberFieldFilter = new FilteringTextField();
        FilteringTextField firstKeyPointFieldFiler = new FilteringTextField();
        FilteringTextField lastKeyPointFieldFilter = new FilteringTextField();
        Checkbox isElectrifiedFieldFilter = new Checkbox();
        FilteringTextField kilometersBetweenStationsFieldFilter = new FilteringTextField();
        FilteringTextField lineTypeFieldFilter = new FilteringTextField();
        FilteringTextField unitPriceFieldFilter = new FilteringTextField();

        lineNumberFieldFilter.addValueChangeListener(event -> ((ListDataProvider<SectionEntity>)
                this.getDataProvider()).addFilter(sectionEntity ->
                StringUtils.containsIgnoreCase(String.valueOf(sectionEntity.getLineNumber()),
                        lineNumberFieldFilter.getValue())));

        firstKeyPointFieldFiler.addValueChangeListener(event -> ((ListDataProvider<SectionEntity>)
                this.getDataProvider()).addFilter(sectionEntity ->
                StringUtils.containsIgnoreCase(sectionEntity.getFirstKeyPoint(), firstKeyPointFieldFiler.getValue())));

        lastKeyPointFieldFilter.addValueChangeListener(event -> ((ListDataProvider<SectionEntity>)
                this.getDataProvider()).addFilter(sectionEntity ->
                StringUtils.containsIgnoreCase(sectionEntity.getLastKeyPoint(), lastKeyPointFieldFilter.getValue())));

        isElectrifiedFieldFilter.addValueChangeListener(event -> ((ListDataProvider<SectionEntity>)
                this.getDataProvider()).addFilter(sectionEntity ->
                sectionEntity.getIsElectrified().equals(isElectrifiedFieldFilter.getValue())));

        kilometersBetweenStationsFieldFilter.addValueChangeListener(event -> ((ListDataProvider<SectionEntity>)
                this.getDataProvider()).addFilter(sectionEntity ->
                StringUtils.containsIgnoreCase(String.valueOf(sectionEntity.getKilometersBetweenStations()),
                        kilometersBetweenStationsFieldFilter.getValue())));

        lineTypeFieldFilter.addValueChangeListener(event -> ((ListDataProvider<SectionEntity>)
                this.getDataProvider()).addFilter(sectionEntity ->
                StringUtils.containsIgnoreCase(sectionEntity.getLineType(), lineTypeFieldFilter.getValue())));

        unitPriceFieldFilter.addValueChangeListener(event -> ((ListDataProvider<SectionEntity>)
                this.getDataProvider()).addFilter(sectionEntity ->
                StringUtils.containsIgnoreCase(String.valueOf(sectionEntity.getUnitPrice()), unitPriceFieldFilter.getValue())));

        filterRow.getCell(lineNumberColumn).setComponent(lineNumberFieldFilter);
        filterRow.getCell(firstKeyPointColumn).setComponent(firstKeyPointFieldFiler);
        filterRow.getCell(lastKeyPointColumn).setComponent(lastKeyPointFieldFilter);
        filterRow.getCell(isElectrifiedColumn).setComponent(isElectrifiedFieldFilter);
        filterRow.getCell(kilometersBetweenStationsColumn).setComponent(kilometersBetweenStationsFieldFilter);
        filterRow.getCell(lineTypeColumn).setComponent(lineTypeFieldFilter);
        filterRow.getCell(unitPriceColumn).setComponent(unitPriceFieldFilter);
    }
}