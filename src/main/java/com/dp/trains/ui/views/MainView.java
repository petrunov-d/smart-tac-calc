package com.dp.trains.ui.views;

import com.dp.trains.ui.layout.MainLayout;
import com.dp.trains.utils.CommonConstants;
import com.dp.trains.utils.SelectedYearPerUserHolder;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@UIScope
@SpringComponent
@Route(value = MainView.NAV_MAIN_VIEW)
public class MainView extends Composite<Div> {

    static final String NAV_MAIN_VIEW = "main";

    public MainView() {

        Image img = new Image(new StreamResource(CommonConstants.MAIN_PAGE_BACKGROUND,
                () -> MainLayout.class.getResourceAsStream("/" + CommonConstants.MAIN_PAGE_BACKGROUND)),
                getTranslation(SHARED_VIEW_BACKGROUND_PICTURE_ALT));

        img.setWidth("101%");
        img.setHeight("50%");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        H1 headerText = new H1(getTranslation(MAIN_VIEW_H1_TITLE));
        H3 subTitle = new H3(getTranslation(MAIN_VIEW_H3_SUBTITLE));

        List<Integer> ints = IntStream.range(2015, Calendar.getInstance().get(Calendar.YEAR) + 1)
                .boxed().collect(Collectors.toList());

        ComboBox<Integer> comboBox = new ComboBox<>(getTranslation(MAIN_VIEW_YEAR_LABEL));
        comboBox.setItems(ints);
        comboBox.setClearButtonVisible(true);
        comboBox.addValueChangeListener(event -> SelectedYearPerUserHolder.addOrUpdate(SecurityContextHolder
                .getContext().getAuthentication().getName(), event.getValue()));

        Button button = new Button(getTranslation(MAIN_VIEW_BUTTON_CONTINUE_TEXT));

        button.addClickListener(event -> {

            if (comboBox.getValue() == null || SelectedYearPerUserHolder.getForUser(SecurityContextHolder
                    .getContext().getAuthentication().getName()) == null) {

                Dialog d = getErrorDialog();
                d.open();

            } else {

                UI.getCurrent().navigate(EditDataView.class);
            }
        });

        verticalLayout.add(img);
        verticalLayout.add(headerText);
        verticalLayout.add(subTitle);
        verticalLayout.add(comboBox);
        verticalLayout.add(button);

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        getContent().add(verticalLayout);
    }

    Dialog getErrorDialog() {

        Dialog dialog = new Dialog();
        Div content = new Div();
        content.addClassName("error-style");

        content.setText(getTranslation(MAIN_VIEW_DIALOG_ERROR_YEAR_REQUIRED));
        dialog.add(content);

        String styles = ".error-style { "
                + "  color: red;"
                + " }";

        StreamRegistration resource = UI.getCurrent().getSession()
                .getResourceRegistry()
                .registerResource(new StreamResource("styles.css", () -> {
                    byte[] bytes = styles.getBytes(StandardCharsets.UTF_8);
                    return new ByteArrayInputStream(bytes);
                }));

        UI.getCurrent().getPage().addStyleSheet("base://" + resource.getResourceUri().toString());

        return dialog;
    }
}