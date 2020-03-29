package com.dp.trains.config;

import com.dp.trains.ui.views.LoginView;
import com.dp.trains.ui.views.MainView;
import com.dp.trains.utils.SecurityUtils;
import com.dp.trains.utils.SelectedYearPerUserHolder;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.AfterNavigationListener;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {

        event.getSource().addUIInitListener(uiEvent -> {

            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::beforeEnter);
            ui.addAfterNavigationListener((AfterNavigationListener) event1 -> {

                if (SecurityContextHolder.getContext() != null &&
                        SecurityContextHolder.getContext().getAuthentication() != null) {

                    if (SelectedYearPerUserHolder.getForUser(SecurityContextHolder
                            .getContext().getAuthentication().getName()) == null) {

                        log.info("Got to a view without having selected a year, redirecting to select year view... "
                                + UI.getCurrent().getUIId());

                        ui.navigate(MainView.class);
                    }
                }
            });
        });
    }

    /**
     * Reroutes the user if (s)he is not authorized to access the view.
     *
     * @param event before navigation event with event details
     */
    private void beforeEnter(BeforeEnterEvent event) {

        if (!LoginView.class.equals(event.getNavigationTarget()) && !SecurityUtils.isUserLoggedIn()) {

            log.info("Routing to Login view user is unauthorized... " + UI.getCurrent().getUIId());

            event.rerouteTo(LoginView.class);
        }
    }
}