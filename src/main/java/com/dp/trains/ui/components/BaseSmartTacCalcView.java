package com.dp.trains.ui.components;

import com.dp.trains.ui.layout.MainLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import lombok.extern.slf4j.Slf4j;

import static com.dp.trains.utils.CommonConstants.MAIN_PAGE_BACKGROUND;
import static com.dp.trains.utils.LocaleKeys.SHARED_VIEW_BACKGROUND_PICTURE_ALT;

@Slf4j
public class BaseSmartTacCalcView extends VerticalLayout {

    public BaseSmartTacCalcView() {
        setSizeFull();

        setMargin(false);
        setSpacing(false);
        setPadding(false);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
    }

    protected HorizontalLayout getFooter() {

        Image img = new Image(new StreamResource(MAIN_PAGE_BACKGROUND,
                () -> MainLayout.class.getResourceAsStream("/" + MAIN_PAGE_BACKGROUND)),
                getTranslation(SHARED_VIEW_BACKGROUND_PICTURE_ALT));

        img.setHeight("100%");

        HorizontalLayout footer = new HorizontalLayout(img);
        footer.setJustifyContentMode(JustifyContentMode.CENTER);
        footer.setWidth("100%");
        return footer;
    }
}
