package com.dp.trains.ui.components.common;

import com.dp.trains.model.dto.Authority;
import com.dp.trains.model.entities.user.UserAccess;
import com.dp.trains.model.entities.user.UserAccessEntitiy;
import com.dp.trains.model.entities.user.UserEntity;
import com.dp.trains.services.TrainsUserDetailService;
import com.dp.trains.ui.components.dialogs.ConfirmDialog;
import com.dp.trains.ui.views.UserManagementView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dp.trains.utils.LocaleKeys.MESSAGE_ERROR_CANT_VIEW_PAGE;

@Slf4j
@UIScope
@SpringComponent
public abstract class UserPermissionAwareView extends BaseSmartTacCalcView implements BeforeEnterObserver {

    @Autowired
    private TrainsUserDetailService trainsUserDetailService;

    public UserPermissionAwareView() {

        super();
    }

    public abstract UserAccess getViewUserAccessPermission();

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        UserEntity userEntity = (UserEntity) trainsUserDetailService
                .loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        Set<String> grantedAuthorities = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        List<String> userAccess = userEntity.getUserAccesses()
                .stream()
                .map(UserAccessEntitiy::getUserAccess)
                .collect(Collectors.toList());

        if (!grantedAuthorities.contains(Authority.ADMIN.getName()) && !userAccess.contains(getViewUserAccessPermission().name())) {

            ConfirmDialog confirmDialog = new ConfirmDialog.Builder()
                    .withTitle(getTranslation(MESSAGE_ERROR_CANT_VIEW_PAGE))
                    .withOkButtonListener(e -> UI.getCurrent().navigate(UserManagementView.class))
                    .withCancelButtonListener(e -> UI.getCurrent().navigate(UserManagementView.class))
                    .build();

            confirmDialog.open();
        }
    }
}