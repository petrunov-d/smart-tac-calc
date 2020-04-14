package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.LineNumberEntity;
import com.dp.trains.services.LineNumberService;
import com.dp.trains.ui.components.common.FilteringTextField;
import com.dp.trains.ui.components.dialogs.EditLineNumberDialog;
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
@SuppressWarnings({"unchecked", "rawtypes"})
public class LineNumberGrid extends SmartTACCalcGrid<LineNumberEntity> {

    public LineNumberGrid(LineNumberService lineNumberService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(lineNumberService.fetch(0, 0)));

        Grid.Column<LineNumberEntity> lineNumberColumn = this.addColumn(LineNumberEntity::getLineNumber)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_LINE_NUMBER))
                .setSortable(true)
                .setResizable(true)
                .setFooter(String.format("%s %d", getTranslation(GRID_FOOTERS_TOTAL), lineNumberService.count()));

        Grid.Column<LineNumberEntity> descriptionColumn = this.addColumn(LineNumberEntity::getDescription)
                .setHeader(getTranslation(DIALOG_ADD_LINE_NUMBER_FORM_ITEM_DESCRIPTION))
                .setSortable(true)
                .setResizable(true);

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), VaadinIcon.EDIT.create(), click -> {

            Dialog editDialog = new EditLineNumberDialog(this, lineNumberService, item);
            editDialog.open();
        }));

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE), VaadinIcon.TRASH.create(), click -> {

            ListDataProvider<LineNumberEntity> dataProvider = (ListDataProvider<LineNumberEntity>) this
                    .getDataProvider();

            lineNumberService.remove(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        }));

        HeaderRow filterRow = this.appendHeaderRow();

        FilteringTextField lineNumberFieldFilter = new FilteringTextField();
        FilteringTextField descriptionFieldFilter = new FilteringTextField();

        lineNumberFieldFilter.addValueChangeListener(event -> ((ListDataProvider<LineNumberEntity>)
                this.getDataProvider()).addFilter(lineNumber ->
                StringUtils.containsIgnoreCase(String.valueOf(lineNumber.getLineNumber()),
                        lineNumberFieldFilter.getValue())));

        descriptionFieldFilter.addValueChangeListener(event -> ((ListDataProvider<LineNumberEntity>)
                this.getDataProvider()).addFilter(lineNumber ->
                StringUtils.containsIgnoreCase(lineNumber.getDescription(), descriptionFieldFilter.getValue())));

        filterRow.getCell(lineNumberColumn).setComponent(lineNumberFieldFilter);
        filterRow.getCell(descriptionColumn).setComponent(descriptionFieldFilter);
    }
}