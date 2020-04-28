package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.MarkupCoefficientEntity;
import com.dp.trains.services.MarkupCoefficientService;
import com.dp.trains.ui.components.common.FilteringTextField;
import com.dp.trains.ui.components.dialogs.edit.EditMarkupCoefficientDialog;
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
@SuppressWarnings("unchecked")
public class MarkupCoefficientGrid extends SmartTACCalcGrid<MarkupCoefficientEntity> {

    public MarkupCoefficientGrid(MarkupCoefficientService markupCoefficientService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(markupCoefficientService.fetch(0, 0)));

        Grid.Column<MarkupCoefficientEntity> nameColumn = this.addColumn(MarkupCoefficientEntity::getName)
                .setHeader(getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_NAME))
                .setSortable(true)
                .setResizable(true)
                .setFooter(String.format("%s %d", getTranslation(GRID_FOOTERS_TOTAL), markupCoefficientService.count()));

        Grid.Column<MarkupCoefficientEntity> codeColumn = this.addColumn(MarkupCoefficientEntity::getCode)
                .setHeader(getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_CODE))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<MarkupCoefficientEntity> coefficientColumn = this.addColumn(MarkupCoefficientEntity::getCoefficient)
                .setHeader(getTranslation(GRID_STRATEGIC_COEFFICIENT_COLUMN_HEADER_COEFFICIENT))
                .setSortable(true)
                .setResizable(true);

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT),
                VaadinIcon.EDIT.create(), click -> {

            Dialog editDialog = new EditMarkupCoefficientDialog(this, markupCoefficientService, item);
            editDialog.open();
        }));

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE),
                VaadinIcon.TRASH.create(), click -> {

            ListDataProvider<MarkupCoefficientEntity> dataProvider = (ListDataProvider<MarkupCoefficientEntity>) this
                    .getDataProvider();

            markupCoefficientService.remove(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        }));

        HeaderRow filterRow = this.appendHeaderRow();

        FilteringTextField nameFieldFilter = new FilteringTextField();
        FilteringTextField codeFieldFilter = new FilteringTextField();
        FilteringTextField coefficientFieldFilter = new FilteringTextField();

        nameFieldFilter.addValueChangeListener(event -> ((ListDataProvider<MarkupCoefficientEntity>)
                this.getDataProvider()).addFilter(markupCoefficientEntity ->
                StringUtils.containsIgnoreCase(markupCoefficientEntity.getName(), nameFieldFilter.getValue())));

        codeFieldFilter.addValueChangeListener(event -> ((ListDataProvider<MarkupCoefficientEntity>)
                this.getDataProvider()).addFilter(markupCoefficientEntity ->
                StringUtils.containsIgnoreCase(markupCoefficientEntity.getCode(), codeFieldFilter.getValue())));

        coefficientFieldFilter.addValueChangeListener(event -> ((ListDataProvider<MarkupCoefficientEntity>)
                this.getDataProvider()).addFilter(markupCoefficientEntity ->
                StringUtils.containsIgnoreCase(String.valueOf(markupCoefficientEntity.getCoefficient()),
                        coefficientFieldFilter.getValue())));

        filterRow.getCell(nameColumn).setComponent(nameFieldFilter);
        filterRow.getCell(codeColumn).setComponent(codeFieldFilter);
        filterRow.getCell(coefficientColumn).setComponent(coefficientFieldFilter);
    }
}