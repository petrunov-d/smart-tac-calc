package com.dp.trains.ui.layout;

import com.dp.trains.event.ExceptionRaisedEvent;
import com.dp.trains.exception.CodeNotFoundException;
import com.dp.trains.services.vaadin.I18NProviderImpl;
import com.dp.trains.ui.components.common.DialogRegistry;
import com.dp.trains.ui.components.common.LanguageSelect;
import com.dp.trains.ui.components.dialogs.BasicInfoDialog;
import com.dp.trains.ui.views.*;
import com.dp.trains.utils.EventBusHolder;
import com.dp.trains.utils.SelectedYearPerUserHolder;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.applayout.LeftLayouts;
import com.github.appreciated.app.layout.component.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.items.LeftClickableItem;
import com.github.appreciated.app.layout.component.router.AppLayoutRouterLayout;
import com.github.appreciated.app.layout.entity.Section;
import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.impl.ImmutableEmptyStyle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import static com.dp.trains.utils.CommonConstants.*;
import static com.dp.trains.utils.LocaleKeys.*;
import static com.vaadin.flow.component.icon.VaadinIcon.*;

@Slf4j
@Theme(Lumo.class)
public class MainLayout extends AppLayoutRouterLayout<LeftLayouts.LeftResponsiveHybrid> {

    private Map<Class<? extends Throwable>, String> errorsDescriptions;

    public MainLayout() {

        Image img = new Image(new StreamResource(SERBIAN_RAILROADS_LOGO,
                () -> MainLayout.class.getResourceAsStream("/" + SERBIAN_RAILROADS_LOGO)),
                getTranslation(MAIN_LAYOUT_IMG_ALT_SERBIAN_RAILROADS_LOGO));

        img.setHeight("var(--app-layout-menu-button-height)");

        final LeftLayouts.LeftResponsiveHybrid appLayout = AppLayoutBuilder.get(LeftLayouts.LeftResponsiveHybrid.class)
                .withTitle(getTranslation(SHARED_APP_TITLE) + " " + getTranslation(MAIN_LAYOUT_YEAR_SELECTOR_LABEL)
                        + " " + SelectedYearPerUserHolder
                        .getForUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .withIconComponent(img)
                .withAppMenu(appMenu())
                .withAppBar(getAppBar())
                .build();

        EventBusHolder.getEventBus().register(this);

        this.errorsDescriptions = getDescriptions();
        init(appLayout);
    }

    @Subscribe
    public void listenForException(ExceptionRaisedEvent exceptionRaisedEvent) {

        log.info("Got exception event: " + exceptionRaisedEvent.toString());

        String errorMessage = this.errorsDescriptions.get(exceptionRaisedEvent.getThrowable().getClass());

        if (errorMessage == null) {

            errorMessage = "";
        }

        DialogRegistry.get().closeAllDialogs();

        Dialog dialog = new BasicInfoDialog(String.format("%s %s %s", getTranslation(MAIN_LAYOUT_GENERIC_ERROR_MESSAGE),
                " => ", errorMessage));

        dialog.open();
    }

    private Component getAppBar() {

        Image img = new Image(new StreamResource(KPMG_LOGO,
                () -> MainLayout.class.getResourceAsStream("/" + KPMG_LOGO)),
                getTranslation(MAIN_LAYOUT_IMG_ALT_KPMG_LOGO));

        LanguageSelect languageSelect = new LanguageSelect(Locale.ENGLISH, I18NProviderImpl.LOCALE_SERBIAN);

        HorizontalLayout horizontalLayout = new HorizontalLayout(new H5(getTranslation(MAIN_LAYOUT_SELECT_LANGUAGE)), languageSelect);
        horizontalLayout.setPadding(true);
        horizontalLayout.setSpacing(true);
        horizontalLayout.setMargin(true);

        return AppBarBuilder.get()
                .add(new Span(String.format("%s %s ", getTranslation(MAIN_LAYOUT_SPAN_VERSION_LABEL), "0.1.2")))
                .add(horizontalLayout)
                .add(img)
                .build();
    }

    private Component appMenu() {

        Image img = new Image(new StreamResource(EU_LOGO,
                () -> MainLayout.class.getResourceAsStream("/" + EU_LOGO)),
                getTranslation(MAIN_LAYOUT_IMG_ALT_EU_LOGO));

        img.setHeight("auto");
        img.setWidth("100%");

        Component leftAppMenu = LeftAppMenuBuilder
                .get()
                .withStickyFooter()
                .addToSection(Section.FOOTER, img)
                .add(getTranslation(MAIN_LAYOUT_MENU_ITEM_LOAD_DATA), DATABASE.create(), ImportDataView.class)
                .add(getTranslation(MAIN_LAYOUT_MENU_ITEM_EDIT_DATA), EDIT.create(), EditDataView.class)
                .add(getTranslation(MAIN_LAYOUT_MENU_ITEM_CALCULATE_SINGLE_PRICE), CALC_BOOK.create(), CalculateSinglePriceView.class)
                .add(getTranslation(MAIN_LAYOUT_VIEW_SINGLE_PRICE), INFO_CIRCLE_O.create(), ViewSinglePriceView.class)
                .add(getTranslation(MAIN_LAYOUT_ADD_SERVICE_CHARGES_FOR_TRAIN_NUMBER), SPLINE_CHART.create(), ServiceChargesForTrainNumberView.class)
                .add(getTranslation(MAIN_LAYOUT_MENU_ITEM_CALCULATE_PRICE_PER_TRAIN), TRAIN.create(), CalculatePricePerTrainView.class)
                .add(getTranslation(MAIN_LAYOUT_MENU_ITEM_REPORTS), CHART_GRID.create(), ReportsView.class)
                .add(getTranslation(MAIN_LAYOUT_MENU_ITEM_USERS), USER.create(), UserManagementView.class)
                .add(new LeftClickableItem(getTranslation(MAIN_LAYOUT_MENU_ITEM_LOGOUT), SIGN_OUT.create(), e -> logout())).build();

        leftAppMenu.getElement().setAttribute("background", " #32b0f3");
        leftAppMenu.getElement().getStyle().set("background", "#32b0f3");

        applyFontColor(leftAppMenu);

        return leftAppMenu;
    }

    private void applyFontColor(Component leftAppMenu) {

        Stack<Element> stack = new Stack<>();

        stack.addAll(leftAppMenu.getElement().getChildren().collect(Collectors.toList()));

        while (!stack.isEmpty()) {

            Element element = stack.pop();

            if (!(element.getStyle() instanceof ImmutableEmptyStyle)) {

                element.getStyle().set("color", "white");
            }

            stack.addAll(element.getChildren().collect(Collectors.toList()));
        }
    }

    private void logout() {

        UI ui = UI.getCurrent();

        SelectedYearPerUserHolder.remove(SecurityContextHolder.getContext().getAuthentication().getName());
        SecurityContextHolder.clearContext();

        VaadinSession session = UI.getCurrent().getSession();
        session.close();

        ui.navigate(LoginView.class);
    }

    private Map<Class<? extends Throwable>, String> getDescriptions() {

        Map<Class<? extends Throwable>, String> map = Maps.newHashMap();

        map.put(IllegalStateException.class, getTranslation(GENERAL_ERROR_HINT_DEFAULT));
        map.put(ConstraintViolationException.class, getTranslation(GENERAL_ERROR_HINT_DATA_INTEGRITY_PROBLEM));
        map.put(NullPointerException.class, getTranslation(GENERAL_ERROR_HINT_DEFAULT));
        map.put(CodeNotFoundException.class, getTranslation(GENERAL_ERROR_HINT_CODE_NOT_SET));
        map.put(GenericJDBCException.class, getTranslation(GENERAL_ERROR_HINT_DEFAULT));
        map.put(JDBCConnectionException.class, getTranslation(GENERAL_ERROR_HINT_CONNECTION_ERROR));
        map.put(LockAcquisitionException.class, getTranslation(GENERAL_ERROR_HINT_TRY_AGAIN_LATER));
        map.put(LockTimeoutException.class, getTranslation(GENERAL_ERROR_HINT_TRY_AGAIN_LATER));
        map.put(SQLGrammarException.class, getTranslation(GENERAL_ERROR_HINT_CONTACT_ADMIN));
        map.put(ArithmeticException.class, getTranslation(GENERAL_ERROR_HINT_ILLEGAL_ARITHMETICS));
        map.put(ClassNotFoundException.class, getTranslation(GENERAL_ERROR_HINT_CONTACT_ADMIN));
        map.put(FileNotFoundException.class, getTranslation(GENERAL_ERROR_HINT_CONTACT_ADMIN));
        map.put(IOException.class, getTranslation(GENERAL_ERROR_HINT_CONTACT_ADMIN));
        map.put(InterruptedException.class, getTranslation(GENERAL_ERROR_HINT_CONTACT_ADMIN));
        map.put(NoSuchFieldException.class, getTranslation(GENERAL_ERROR_HINT_CONTACT_ADMIN));
        map.put(NoSuchMethodException.class, getTranslation(GENERAL_ERROR_HINT_CONTACT_ADMIN));
        map.put(RuntimeException.class, getTranslation(GENERAL_ERROR_HINT_CONTACT_ADMIN));
        map.put(StringIndexOutOfBoundsException.class, getTranslation(GENERAL_ERROR_HINT_CONTACT_ADMIN));
        map.put(SecurityException.class, getTranslation(GENERAL_ERROR_HINT_CONTACT_ADMIN));

        return map;
    }
}