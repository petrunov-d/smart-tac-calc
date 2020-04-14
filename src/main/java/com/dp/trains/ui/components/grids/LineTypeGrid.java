package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.LineTypeEntity;
import com.dp.trains.services.LineTypeService;
import com.dp.trains.ui.components.common.FilteringTextField;
import com.dp.trains.ui.components.dialogs.EditLineTypeDialog;
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
public class LineTypeGrid extends SmartTACCalcGrid<LineTypeEntity> {

    public LineTypeGrid(LineTypeService lineTypeService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(lineTypeService.fetch(0, 0)));

        Grid.Column<LineTypeEntity> nameColumn = this.addColumn(LineTypeEntity::getName)
                .setHeader(getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_NAME))
                .setSortable(true)
                .setResizable(true)
                .setFooter(String.format("%s %d", getTranslation(GRID_FOOTERS_TOTAL), lineTypeService.count()));

        Grid.Column<LineTypeEntity> lineTypeColumn = this.addColumn(LineTypeEntity::getLineType)
                .setHeader(getTranslation(GRID_LINE_TYPE_COLUMN_HEADER_LINE_TYPE))
                .setSortable(true)
                .setResizable(true);

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), VaadinIcon.EDIT.create(), click -> {

            Dialog editDialog = new EditLineTypeDialog(this, lineTypeService, item);
            editDialog.open();
        }));

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE), new Icon(VaadinIcon.TRASH), click -> {

            ListDataProvider<LineTypeEntity> dataProvider = (ListDataProvider<LineTypeEntity>) this
                    .getDataProvider();

            lineTypeService.remove(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        }));

        HeaderRow filterRow = this.appendHeaderRow();

        FilteringTextField nameFieldFilter = new FilteringTextField();
        FilteringTextField lineTypeFieldFilter = new FilteringTextField();

        nameFieldFilter.addValueChangeListener(event -> ((ListDataProvider<LineTypeEntity>)
                this.getDataProvider()).addFilter(lineTypeEntity ->
                StringUtils.containsIgnoreCase(lineTypeEntity.getName(), nameFieldFilter.getValue())));

        lineTypeFieldFilter.addValueChangeListener(event -> ((ListDataProvider<LineTypeEntity>)
                this.getDataProvider()).addFilter(lineTypeEntity ->
                StringUtils.containsIgnoreCase(lineTypeEntity.getLineType(), lineTypeFieldFilter.getValue())));

        filterRow.getCell(nameColumn).setComponent(nameFieldFilter);
        filterRow.getCell(lineTypeColumn).setComponent(lineTypeFieldFilter);
    }
}