package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.StrategicCoefficientEntity;
import com.dp.trains.services.StrategicCoefficientService;
import com.dp.trains.ui.components.dialogs.EditStrategicCoefficientDialog;
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
public class StrategicCoefficientsGrid extends SmartTACCalcGrid<StrategicCoefficientEntity> {

    public StrategicCoefficientsGrid(StrategicCoefficientService strategicCoefficientService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(strategicCoefficientService.fetch(0, 0)));

        Grid.Column<StrategicCoefficientEntity> codeColumn = this
                .addColumn(StrategicCoefficientEntity::getCode)
                .setHeader(getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_CODE))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<StrategicCoefficientEntity> nameColumn = this
                .addColumn(StrategicCoefficientEntity::getName)
                .setHeader(getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_NAME))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<StrategicCoefficientEntity> coefficientColumn = this
                .addColumn(StrategicCoefficientEntity::getCoefficient)
                .setHeader(getTranslation(GRID_STRATEGIC_COEFFICIENT_COLUMN_HEADER_COEFFICIENT))
                .setSortable(true)
                .setResizable(true);

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), VaadinIcon.EDIT.create(), click -> {

            Dialog editDialog = new EditStrategicCoefficientDialog(this, strategicCoefficientService, item);
            editDialog.open();
        }));

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE), new Icon(VaadinIcon.TRASH),
                click -> {

                    ListDataProvider<StrategicCoefficientEntity> dataProvider =
                            (ListDataProvider<StrategicCoefficientEntity>) this.getDataProvider();

                    strategicCoefficientService.remove(item);
                    dataProvider.getItems().remove(item);
                    dataProvider.refreshAll();
                }));
    }
}
