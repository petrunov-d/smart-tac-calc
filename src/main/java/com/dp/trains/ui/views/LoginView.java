package com.dp.trains.ui.views;

import com.dp.trains.services.vaadin.I18NProviderImpl;
import com.dp.trains.ui.components.common.LanguageSelect;
import com.dp.trains.ui.layout.MainLayout;
import com.dp.trains.utils.CommonConstants;
import com.dp.trains.utils.EventBusHolder;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Locale;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@PageTitle("Login")
@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
public class LoginView extends Composite<Div> implements BeforeEnterObserver {

    static final String ROUTE = "login";

    private final LoginForm login = new LoginForm();

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

        LanguageSelect languageSelect = new LanguageSelect(Locale.ENGLISH, I18NProviderImpl.LOCALE_SERBIAN);

        HorizontalLayout horizontalLayout = new HorizontalLayout(new H5(getTranslation(MAIN_LAYOUT_SELECT_LANGUAGE)), languageSelect);

        verticalLayout.add(img, headerText, horizontalLayout, login);

        login.setI18n(getLocalizedLogin());

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        getContent().add(verticalLayout);

        EventBusHolder.getEventBus();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        if (!event.getLocation().getQueryParameters().getParameters().getOrDefault("error",
                Collections.emptyList()).isEmpty()) {

            login.setError(true);
        }
    }

    private LoginI18n getLocalizedLogin() {

        final LoginI18n i18n = LoginI18n.createDefault();

        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle(getTranslation(LOGIN_FORM_LOGIN));
        i18n.getHeader().setDescription(getTranslation(LOGIN_FORM_DESCRIPTION));
        i18n.getForm().setUsername(getTranslation(LOGIN_FORM_USERNAME));
        i18n.getForm().setTitle(getTranslation(LOGIN_FORM_LOG_IN));
        i18n.getForm().setSubmit(getTranslation(LOGIN_FORM_SUBMIT));
        i18n.getForm().setPassword(getTranslation(LOGIN_FORM_PASSWORD));
        i18n.getErrorMessage().setTitle(getTranslation(LOGIN_FORM_ERROR_MESSAGE_TITLE));
        i18n.getErrorMessage().setMessage(getTranslation(LOGIN_FORM_ERROR_MESSAGE_MESSAGE));
        i18n.setAdditionalInformation(getTranslation(LOGIN_FORM_ADDITIONAL_INFORMATION));

        return i18n;
    }
}