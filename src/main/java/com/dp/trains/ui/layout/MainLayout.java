package com.dp.trains.ui.layout;

import com.dp.trains.exception.CodeNotFoundException;
import com.dp.trains.services.vaadin.I18NProviderImpl;
import com.dp.trains.ui.components.common.LanguageSelect;
import com.dp.trains.ui.views.*;
import com.dp.trains.utils.EventBusHolder;
import com.dp.trains.utils.SelectedYearPerUserHolder;
import com.dp.trains.utils.SmartTacCalcContext;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.applayout.LeftLayouts;
import com.github.appreciated.app.layout.component.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.items.LeftClickableItem;
import com.github.appreciated.app.layout.component.router.AppLayoutRouterLayout;
import com.github.appreciated.app.layout.entity.Section;
import com.google.common.collect.Maps;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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

@Theme(Lumo.class)
public class MainLayout extends AppLayoutRouterLayout<LeftLayouts.LeftResponsiveHybrid> {

    //  private Map<Class<? extends Throwable>, String> errorsDescriptions;

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

        //errorsDescriptions = getDescriptions();
        init(appLayout);
    }

//    @Subscribe
//    public void listenForException(ExceptionRaisedEvent exceptionRaisedEvent) {
//
//        String errorMessage = errorsDescriptions.get(exceptionRaisedEvent.getThrowable().getClass());
//
//        if (errorMessage == null) {
//
//            errorMessage = "";
//        }
//
//        Dialog dialog = new BasicInfoDialog(getTranslation(MAIN_LAYOUT_GENERIC_ERROR_MESSAGE) + "\n Hint: " + errorMessage);
//        dialog.open();
//    }

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
                .add(new Span(String.format("%s %s ", getTranslation(MAIN_LAYOUT_SPAN_VERSION_LABEL),
                        SmartTacCalcContext.getSmartTACCalcContext().getModel().getVersion())))
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
                .add(getTranslation(MAIN_LAYOUT_ADD_SERVICE_CHARGES_FOR_TRAIN_NUMBER), SPLINE_CHART.create(), AddServiceChargesForTrainNumberView.class)
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

        map.put(IllegalStateException.class, "General Error");
        map.put(ConstraintViolationException.class, "Data Integrity Problem. Please fix the data before continuing");
        map.put(NullPointerException.class, "General Error");
        map.put(CodeNotFoundException.class, "Code not set! Please make sure all data with codes has a code!");
        map.put(GenericJDBCException.class, "General Error");
        map.put(JDBCConnectionException.class, "Connection error, contact an admin.");
        map.put(LockAcquisitionException.class, "Try again later");
        map.put(LockTimeoutException.class, "Try again later");
        map.put(SQLGrammarException.class, "Contact an admin, or refresh the page.");
        map.put(ArithmeticException.class, "Contact an admin or make sure calculation is a legal arithmetic expression");
        map.put(ClassNotFoundException.class, "Contact an admin, or refresh the page.");
        map.put(FileNotFoundException.class, "Contact an admin, or refresh the page.");
        map.put(IOException.class, "Contact an admin, or refresh the page.");
        map.put(InterruptedException.class, "Contact an admin, or refresh the page.");
        map.put(NoSuchFieldException.class, "Contact an admin, or refresh the page.");
        map.put(NoSuchMethodException.class, "Contact an admin, or refresh the page.");
        map.put(RuntimeException.class, "Contact an admin, or refresh the page.");
        map.put(StringIndexOutOfBoundsException.class, "Contact an admin, or refresh the page.");
        map.put(SecurityException.class, "Contact an admin, or refresh the page.");

        return map;
    }
}