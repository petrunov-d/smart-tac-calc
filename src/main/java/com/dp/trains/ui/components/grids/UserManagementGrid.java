package com.dp.trains.ui.components.grids;

import com.dp.trains.model.dto.Authority;
import com.dp.trains.model.entities.AuthorityEntity;
import com.dp.trains.model.entities.UserEntity;
import com.dp.trains.services.TrainsUserDetailService;
import com.dp.trains.ui.components.common.FilteringTextField;
import com.dp.trains.ui.components.dialogs.edit.EditUserDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@SuppressWarnings("unchecked")
public class UserManagementGrid extends SmartTACCalcGrid<UserEntity> {

    public UserManagementGrid(TrainsUserDetailService trainsUserDetailService) {
        super();

        this.setDataProvider(DataProvider.ofCollection(trainsUserDetailService.fetch(0, 0)));

        Grid.Column<UserEntity> userNameColumn = this.addColumn(UserEntity::getUsername)
                .setHeader(getTranslation(GRID_USER_MANAGEMENT_COLUMN_HEADER_USERNAME))
                .setSortable(true)
                .setResizable(true)
                .setFooter(String.format("%s %d", getTranslation(GRID_FOOTERS_TOTAL), trainsUserDetailService.count()));

        Grid.Column<UserEntity> isAdminColumn = this.addColumn(new ComponentRenderer<>(userEntity -> {

            Collection<AuthorityEntity> authorityEntities = (Collection<AuthorityEntity>) userEntity.getAuthorities();

            boolean isAdmin = authorityEntities.stream().anyMatch(x -> x.getAuthority().equals(Authority.ADMIN.getName()));

            Checkbox isAdminCheckbox = new Checkbox();
            isAdminCheckbox.setValue(isAdmin);
            isAdminCheckbox.setEnabled(false);
            return isAdminCheckbox;

        })).setHeader(getTranslation(GRID_USER_MANAGEMENT_COLUMN_HEADER_IS_ADMIN))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<UserEntity> isUserColumn = this.addColumn(new ComponentRenderer<>(userEntity -> {

            Collection<AuthorityEntity> authorityEntities = (Collection<AuthorityEntity>) userEntity.getAuthorities();

            boolean isUser = authorityEntities.stream().anyMatch(x -> x.getAuthority().equals(Authority.USER.getName()));

            Checkbox isUserCheckbox = new Checkbox();
            isUserCheckbox.setValue(isUser);
            isUserCheckbox.setEnabled(false);
            return isUserCheckbox;

        })).setHeader(getTranslation(GRID_USER_MANAGEMENT_COLUMN_HEADER_IS_USER))
                .setSortable(true)
                .setResizable(true);

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), new Icon(VaadinIcon.EDIT),
                click -> {

                    ListDataProvider<UserEntity> dataProvider = (ListDataProvider<UserEntity>) this.getDataProvider();
                    Dialog editUserDialog = new EditUserDialog(item, dataProvider, trainsUserDetailService);
                    editUserDialog.open();
                }));

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE), new Icon(VaadinIcon.TRASH),
                click -> {

                    ListDataProvider<UserEntity> dataProvider = (ListDataProvider<UserEntity>) this.getDataProvider();

                    trainsUserDetailService.deleteByUserName(item.getUsername());

                    dataProvider.getItems().remove(item);
                    dataProvider.refreshAll();
                }));

        HeaderRow filterRow = this.appendHeaderRow();

        FilteringTextField usernameFieldFilter = new FilteringTextField();

        usernameFieldFilter.addValueChangeListener(event -> ((ListDataProvider<UserEntity>)
                this.getDataProvider()).addFilter(userEntity ->
                StringUtils.containsIgnoreCase(userEntity.getUsername(), usernameFieldFilter.getValue())));

        filterRow.getCell(userNameColumn).setComponent(usernameFieldFilter);
    }
}
