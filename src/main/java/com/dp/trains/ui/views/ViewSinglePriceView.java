package com.dp.trains.ui.views;

import com.dp.trains.model.entities.UnitPriceEntity;
import com.dp.trains.services.UnitPriceService;
import com.dp.trains.ui.layout.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@UIScope
@SpringComponent
@Route(value = ViewSinglePriceView.NAV_VIEW_SINGLE_PRICE, layout = MainLayout.class)
public class ViewSinglePriceView extends Composite<Div> {

    @Autowired
    private UnitPriceService unitPriceService;

    static final String NAV_VIEW_SINGLE_PRICE = "view_single_price";

    public ViewSinglePriceView() {
        Grid<UnitPriceEntity> unitPriceGrid = new Grid<>();

        getContent().setHeightFull();

        CallbackDataProvider<UnitPriceEntity, Void> provider = DataProvider
                .fromCallbacks(query -> unitPriceService.fetch(query.getOffset(), query.getLimit()).stream(),
                        query -> unitPriceService.count());

        unitPriceGrid.setDataProvider(provider);

        unitPriceGrid.addColumn(UnitPriceEntity::getCode)
                .setHeader(getTranslation(CALCULATE_SINGLE_PRICE_GRID_COLUMN_HEADER_CODE))
                .setSortable(true)
                .setResizable(true);

        unitPriceGrid.addColumn(UnitPriceEntity::getName)
                .setHeader(getTranslation(CALCULATE_SINGLE_PRICE_GRID_COLUMN_HEADER_NAME))
                .setSortable(true)
                .setResizable(true);

        unitPriceGrid.addColumn(UnitPriceEntity::getMeasure)
                .setHeader(getTranslation(CALCULATE_SINGLE_PRICE_GRID_COLUMN_HEADER_MEASURE))
                .setSortable(true)
                .setResizable(true);

        unitPriceGrid.addColumn(UnitPriceEntity::getUnitPrice)
                .setHeader(getTranslation(CALCULATE_SINGLE_PRICE_GRID_COLUMN_HEADER_UNIT_PRICE))
                .setSortable(true)
                .setResizable(true);

        unitPriceGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_WRAP_CELL_CONTENT,
                GridVariant.MATERIAL_COLUMN_DIVIDERS, GridVariant.LUMO_COLUMN_BORDERS);

        unitPriceGrid.setSizeFull();

        getContent().add(unitPriceGrid);
    }
}
