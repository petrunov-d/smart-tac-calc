package com.dp.trains.ui.components.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmartTACCalcGrid<T> extends Grid<T> {

    public SmartTACCalcGrid() {

        this.addThemeVariants(GridVariant.LUMO_ROW_STRIPES,
                GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.MATERIAL_COLUMN_DIVIDERS,
                GridVariant.LUMO_COLUMN_BORDERS);

        this.setSelectionMode(Grid.SelectionMode.SINGLE);
        this.setColumnReorderingAllowed(true);

        this.setSizeFull();
    }
}
