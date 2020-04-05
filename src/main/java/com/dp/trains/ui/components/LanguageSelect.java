package com.dp.trains.ui.components;

import com.dp.trains.event.LocaleChangedEvent;
import com.dp.trains.ui.layout.MainLayout;
import com.dp.trains.utils.EventBusHolder;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.server.StreamResource;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;


@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
public class LanguageSelect extends Select<Locale> implements LocaleChangeObserver {

    private ComponentRenderer languageRenderer = new ComponentRenderer((item) -> {

        Locale l = (Locale) item;
        HorizontalLayout hLayout = new HorizontalLayout();

        Image languageFlag = new Image(new StreamResource(l.getLanguage() + ".png",
                () -> MainLayout.class.getResourceAsStream("/" + l.getLanguage() + ".png")),
                getTranslation("language flag" + l.getLanguage()));

        languageFlag.setHeight("30px");
        hLayout.add(languageFlag);
        hLayout.add(new Span(l.getDisplayName()));
        hLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        return hLayout;
    });

    public LanguageSelect(Locale... items) {

        super(items);
        this.setEmptySelectionAllowed(false);
        this.setRenderer(this.languageRenderer);
        log.info("Creating Language Select, current locale is:" + UI.getCurrent().getLocale());
        this.setValue(UI.getCurrent().getLocale());
        this.addValueChangeListener((change) -> {
            UI.getCurrent().getSession().setLocale(change.getValue());
            log.info("Changed locale to:" + change.getValue().toString());
            UI.getCurrent().getPage().reload();
        });
    }

    public void refresh() {

        this.setRenderer(this.languageRenderer);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {

        EventBusHolder.getEventBus().post(LocaleChangedEvent.builder().locale(event.getLocale()).build());
    }
}
