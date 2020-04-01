package com.dp.trains.ui;

import com.dp.trains.ui.layout.MainLayout;
import com.dp.trains.ui.views.LoginView;
import com.dp.trains.ui.views.MainView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@UIScope
@SpringComponent
@Route(value = VaadinApp.NAVIGATION_ROOT, layout = MainLayout.class)
public class VaadinApp extends Composite<Div> {

    public static final String NAVIGATION_ROOT = "";

    @Override
    protected void onAttach(AttachEvent attachEvent) {

        super.onAttach(attachEvent);

        log.info("Navigated to ROOT, now redirecting.");

        if (SecurityContextHolder.getContext() == null ||
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UI.getCurrent().navigate(LoginView.class);
        } else {
            UI.getCurrent().navigate(MainView.class);
        }
    }
}