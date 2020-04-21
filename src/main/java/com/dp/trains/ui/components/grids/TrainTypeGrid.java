package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.TrainTypeEntity;
import com.dp.trains.services.TrainTypeService;
import com.dp.trains.ui.components.common.FilteringTextField;
import com.dp.trains.ui.components.dialogs.edit.EditTrainTypeDialog;
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
public class TrainTypeGrid extends SmartTACCalcGrid<TrainTypeEntity> {

    public TrainTypeGrid(TrainTypeService trainTypeService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(trainTypeService.fetch(0, 0)));

        Grid.Column<TrainTypeEntity> codeColumn = this.addColumn(TrainTypeEntity::getCode)
                .setHeader(getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_CODE))
                .setSortable(true)
                .setResizable(true)
                .setFooter(String.format("%s %d", getTranslation(GRID_FOOTERS_TOTAL), trainTypeService.count()));

        Grid.Column<TrainTypeEntity> nameColumn = this.addColumn(TrainTypeEntity::getName)
                .setHeader(getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_NAME))
                .setSortable(true)
                .setResizable(true);

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), VaadinIcon.EDIT.create(), click -> {

            Dialog editDialog = new EditTrainTypeDialog(this, trainTypeService, item);
            editDialog.open();
        }));

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE), new Icon(VaadinIcon.TRASH), click -> {

            ListDataProvider<TrainTypeEntity> dataProvider = (ListDataProvider<TrainTypeEntity>) this.getDataProvider();

            trainTypeService.remove(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        }));

        HeaderRow filterRow = this.appendHeaderRow();

        FilteringTextField codeFieldFilter = new FilteringTextField();
        FilteringTextField nameFieldFilter = new FilteringTextField();

        codeFieldFilter.addValueChangeListener(event -> ((ListDataProvider<TrainTypeEntity>)
                this.getDataProvider()).addFilter(trainTypeEntity ->
                StringUtils.containsIgnoreCase(String.valueOf(trainTypeEntity.getCode()), codeFieldFilter.getValue())));

        nameFieldFilter.addValueChangeListener(event -> ((ListDataProvider<TrainTypeEntity>)
                this.getDataProvider()).addFilter(trainTypeEntity ->
                StringUtils.containsIgnoreCase(trainTypeEntity.getName(), nameFieldFilter.getValue())));

        filterRow.getCell(codeColumn).setComponent(codeFieldFilter);
        filterRow.getCell(nameColumn).setComponent(nameFieldFilter);
    }
}