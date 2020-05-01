package com.dp.trains.ui.views;

import com.dp.trains.model.entities.UnitPriceEntity;
import com.dp.trains.services.UnitPriceService;
import com.dp.trains.ui.components.common.BaseSmartTacCalcView;
import com.dp.trains.ui.components.common.FilteringTextField;
import com.dp.trains.ui.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@UIScope
@SpringComponent
@SuppressWarnings("unchecked")
@Route(value = ViewSinglePriceView.NAV_VIEW_SINGLE_PRICE, layout = MainLayout.class)
public class ViewSinglePriceView extends BaseSmartTacCalcView {

    @Autowired
    private UnitPriceService unitPriceService;

    static final String NAV_VIEW_SINGLE_PRICE = "view_single_price";

    Grid.Column<UnitPriceEntity> codeColumn;

    public ViewSinglePriceView() {

        Grid<UnitPriceEntity> unitPriceGrid = new Grid<>();

        this.setHeightFull();

        CallbackDataProvider<UnitPriceEntity, Void> provider = DataProvider
                .fromCallbacks(query -> unitPriceService.fetch(query.getOffset(), query.getLimit()).stream(),
                        query -> unitPriceService.count());

        unitPriceGrid.setDataProvider(provider);

        codeColumn = unitPriceGrid.addColumn(UnitPriceEntity::getCode)
                .setHeader(getTranslation(CALCULATE_SINGLE_PRICE_GRID_COLUMN_HEADER_CODE))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<UnitPriceEntity> nameColumn = unitPriceGrid.addColumn(UnitPriceEntity::getName)
                .setHeader(getTranslation(CALCULATE_SINGLE_PRICE_GRID_COLUMN_HEADER_NAME))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<UnitPriceEntity> measureColumn = unitPriceGrid.addColumn(UnitPriceEntity::getMeasure)
                .setHeader(getTranslation(CALCULATE_SINGLE_PRICE_GRID_COLUMN_HEADER_MEASURE))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<UnitPriceEntity> unitPriceColumn = unitPriceGrid.addColumn(UnitPriceEntity::getUnitPrice)
                .setHeader(getTranslation(CALCULATE_SINGLE_PRICE_GRID_COLUMN_HEADER_UNIT_PRICE))
                .setSortable(true)
                .setResizable(true);

        unitPriceGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_WRAP_CELL_CONTENT,
                GridVariant.MATERIAL_COLUMN_DIVIDERS, GridVariant.LUMO_COLUMN_BORDERS);

        unitPriceGrid.setSizeFull();

        HeaderRow filterRow = unitPriceGrid.appendHeaderRow();

        FilteringTextField codeFieldFilter = new FilteringTextField();
        FilteringTextField nameFieldFilter = new FilteringTextField();
        FilteringTextField measureFieldFilter = new FilteringTextField();
        FilteringTextField unitPriceFieldFilter = new FilteringTextField();

        codeFieldFilter.addValueChangeListener(event -> ((ListDataProvider<UnitPriceEntity>)
                unitPriceGrid.getDataProvider()).addFilter(unitPriceEntity ->
                StringUtils.containsIgnoreCase(unitPriceEntity.getCode(), codeFieldFilter.getValue())));

        nameFieldFilter.addValueChangeListener(event -> ((ListDataProvider<UnitPriceEntity>)
                unitPriceGrid.getDataProvider()).addFilter(unitPriceEntity ->
                StringUtils.containsIgnoreCase(unitPriceEntity.getName(), nameFieldFilter.getValue())));

        measureFieldFilter.addValueChangeListener(event -> ((ListDataProvider<UnitPriceEntity>)
                unitPriceGrid.getDataProvider()).addFilter(unitPriceEntity ->
                StringUtils.containsIgnoreCase(unitPriceEntity.getMeasure(), measureFieldFilter.getValue())));

        unitPriceFieldFilter.addValueChangeListener(event -> ((ListDataProvider<UnitPriceEntity>)
                unitPriceGrid.getDataProvider()).addFilter(unitPriceEntity ->
                StringUtils.containsIgnoreCase(String.valueOf(unitPriceEntity.getUnitPrice()),
                        unitPriceFieldFilter.getValue())));

        filterRow.getCell(codeColumn).setComponent(codeFieldFilter);
        filterRow.getCell(nameColumn).setComponent(nameFieldFilter);
        filterRow.getCell(measureColumn).setComponent(measureFieldFilter);
        filterRow.getCell(unitPriceColumn).setComponent(unitPriceFieldFilter);

        Button clearButton = new Button(getTranslation(CLEAR_UNIT_PRICE_DATA), VaadinIcon.CLOSE_CIRCLE.create());
        clearButton.addClickListener(event -> this.unitPriceService.deleteAll());

        VerticalLayout verticalLayout = new VerticalLayout(clearButton);
        verticalLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

       this.add(clearButton);
       this.add(unitPriceGrid);
    }

    @PostConstruct
    public void init() {

        this.codeColumn.setFooter(String.format("%s %d", getTranslation(GRID_FOOTERS_TOTAL), unitPriceService.count()));
    }
}