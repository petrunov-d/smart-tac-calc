package com.dp.trains.ui.views;

import com.dp.trains.model.dto.Authority;
import com.dp.trains.services.TrainsUserDetailService;
import com.dp.trains.ui.components.common.BaseSmartTacCalcView;
import com.dp.trains.ui.components.dialogs.ChangePasswordDialog;
import com.dp.trains.ui.components.dialogs.add.AddUserDialog;
import com.dp.trains.ui.components.factories.EditableDataGridFactory;
import com.dp.trains.ui.layout.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@UIScope
@SpringComponent
@Route(value = UserManagementView.NAV_USER_MANAGEMENT_VIEW, layout = MainLayout.class)
public class UserManagementView extends BaseSmartTacCalcView {

    static final String NAV_USER_MANAGEMENT_VIEW = "user_management_view";

    @Autowired
    private TrainsUserDetailService trainsUserDetailService;

    @Autowired
    private EditableDataGridFactory editableDataGridFactory;

    private Grid userGrid;

    public UserManagementView() {

        VerticalLayout verticalLayout;

        Set<String> grantedAuthorities = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        if (grantedAuthorities.contains(Authority.ADMIN.getName())) {

            verticalLayout = loadAdminUI();

        } else {

            verticalLayout = loadUserUI();
        }

        this.add(verticalLayout);
        this.setHeightFull();
    }

    @PostConstruct
    public void init() {

        Set<String> grantedAuthorities = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        if (grantedAuthorities.contains(Authority.ADMIN.getName())) {

            this.userGrid = editableDataGridFactory.getUsersGrid();
            this.userGrid.setSizeFull();
            this.add(userGrid);
        }
    }

    private VerticalLayout loadUserUI() {

        VerticalLayout verticalLayout = new VerticalLayout();

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        H3 label = new H3(getTranslation(USER_MANAGEMENT_VIEW_TITLE_CURRENTLY_LOGGED_IN_AS) + loggedInUser);

        Button button = new Button(new Icon(VaadinIcon.PASSWORD));
        button.setText(getTranslation(USER_MANAGEMENT_VIEW_BUTTON_TEXT_CHANGE_PASSWORD));
        button.setWidth("50%");
        button.setHeight("100%");
        button.setHeightFull();
        button.addClickListener(event -> {

            Dialog changePasswordDialog = new ChangePasswordDialog(loggedInUser, trainsUserDetailService);

            changePasswordDialog.open();
        });

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        verticalLayout.add(label);
        verticalLayout.add(button);

        return verticalLayout;
    }

    private VerticalLayout loadAdminUI() {

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        VerticalLayout verticalLayout = new VerticalLayout();

        H3 label = new H3(getTranslation(USER_MANAGEMENT_VIEW_ADMIN_TITLE));

        Button button = new Button(new Icon(VaadinIcon.PLUS));
        button.setText(getTranslation(USER_MANAGEMENT_VIEW_BUTTON_TEXT_ADD_USER));
        button.setWidth("50%");
        button.setHeight("100%");
        button.setHeightFull();
        button.addClickListener(event -> {

            Dialog dialog = new AddUserDialog(loggedInUser, trainsUserDetailService);

            dialog.open();
        });

        verticalLayout.add(label);
        verticalLayout.add(button);

        return verticalLayout;
    }
}