package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.LineNumberEntity;
import com.dp.trains.services.LineNumberService;
import com.dp.trains.ui.components.dialogs.EditLineNumberDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import lombok.extern.slf4j.Slf4j;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@SuppressWarnings("unchecked")
public class LineNumberGrid extends SmartTACCalcGrid<LineNumberEntity> {

    public LineNumberGrid(LineNumberService lineNumberService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(lineNumberService.fetch(0, 0)));

        Grid.Column<LineNumberEntity> lineNumberColumn = this.addColumn(LineNumberEntity::getLineNumber)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_LINE_NUMBER))
                .setSortable(true)
                .setResizable(true);

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
    }
}