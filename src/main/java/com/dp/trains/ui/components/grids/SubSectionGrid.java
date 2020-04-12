package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.SectionEntity;
import com.dp.trains.model.entities.SubSectionEntity;
import com.dp.trains.services.RailStationService;
import com.dp.trains.services.SubSectionService;
import com.dp.trains.ui.components.dialogs.EditSubSectionDialog;
import com.google.common.collect.Lists;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@SuppressWarnings("unchecked")
public class SubSectionGrid extends SmartTACCalcGrid<SubSectionEntity> {

    public SubSectionGrid(SectionEntity sectionEntity, SubSectionService subSectionService, RailStationService railStationService) {

        super();

        List<SubSectionEntity> subSectionEntityList = sectionEntity.getSubSectionEntities();

        if (subSectionEntityList == null) {

            subSectionEntityList = Lists.newArrayList();
        }
        this.setDataProvider(DataProvider.ofCollection(subSectionEntityList));

        Grid.Column<SubSectionEntity> kilometersColumn = this.addColumn(SubSectionEntity::getKilometers)
                .setHeader(getTranslation(GRID_SUB_SECTION_COLUMN_HEADER_KILOMETERS))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<SubSectionEntity> nonKeyStationColumn = this
                .addColumn(SubSectionEntity::getNonKeyStation)
                .setHeader(getTranslation(GRID_SUB_SECTION_COLUMN_HEADER_NON_KEY_STATION))
                .setSortable(true)
                .setResizable(true);

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), VaadinIcon.EDIT.create(), click -> {

            Dialog editDialog = new EditSubSectionDialog(this, subSectionService, item, railStationService);
            editDialog.open();
        }));

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE), new Icon(VaadinIcon.TRASH), click -> {

            ListDataProvider<SubSectionEntity> dataProvider = (ListDataProvider<SubSectionEntity>) this
                    .getDataProvider();

            subSectionService.remove(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        }));
    }
}
