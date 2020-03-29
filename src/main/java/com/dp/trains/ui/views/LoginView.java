package com.dp.trains.ui.views;

import com.dp.trains.ui.layout.MainLayout;
import com.dp.trains.utils.CommonConstants;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

import static com.dp.trains.utils.LocaleKeys.SHARED_APP_TITLE;
import static com.dp.trains.utils.LocaleKeys.SHARED_VIEW_BACKGROUND_PICTURE_ALT;

@Slf4j
@PageTitle("Login")
@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
public class LoginView extends Composite<Div> implements BeforeEnterObserver {

    static final String ROUTE = "login";

    private LoginForm login = new LoginForm();

    public LoginView() {

        Image img = new Image(new StreamResource(CommonConstants.MAIN_PAGE_BACKGROUND,
                () -> MainLayout.class.getResourceAsStream("/" + CommonConstants.MAIN_PAGE_BACKGROUND)),
                getTranslation(SHARED_VIEW_BACKGROUND_PICTURE_ALT));

        img.setWidth("101%");
        img.setHeight("50%");

        login.setAction("login");
        login.setForgotPasswordButtonVisible(false);

        getContent().setSizeFull();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        H1 headerText = new H1(getTranslation(SHARED_APP_TITLE));

        verticalLayout.add(img);
        verticalLayout.add(headerText);
        verticalLayout.add(login);
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        getContent().add(verticalLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        if (!event.getLocation().getQueryParameters().getParameters().getOrDefault("error",
                Collections.emptyList()).isEmpty()) {

            login.setError(true);
        }
    }
}