package com.dp.trains.services.vaadin;


import com.dp.trains.event.LocaleChangedEvent;
import com.dp.trains.utils.EventBusHolder;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.i18n.I18NProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.ROOT;
import static java.util.ResourceBundle.getBundle;

@Slf4j
@Service
@RequiredArgsConstructor
public class I18NProviderImpl implements I18NProvider {

    public static final Locale LOCALE_SERBIAN = new Locale("sr", "RS");

    public static final String RESOURCE_BUNDLE_NAME = "labels";

    private static final ResourceBundle RESOURCE_BUNDLE_ROOT = getBundle(RESOURCE_BUNDLE_NAME, ROOT);
    private static final ResourceBundle RESOURCE_BUNDLE_ENGLISH = getBundle(RESOURCE_BUNDLE_NAME, ENGLISH);
    private static final ResourceBundle RESOURCE_BUNDLE_SERBIAN = getBundle(RESOURCE_BUNDLE_NAME, LOCALE_SERBIAN);

    private Locale locale;

    @Override
    public List<Locale> getProvidedLocales() {

        return Lists.newArrayList(ROOT, ENGLISH, LOCALE_SERBIAN);
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {

        ResourceBundle resourceBundle = null;

        if (this.locale != null) {

            log.debug("Get Translation for key:" + key + " locale = " + locale.toString());

            if (locale.equals(ENGLISH)) {

                resourceBundle = RESOURCE_BUNDLE_ENGLISH;
            } else if (locale.equals(LOCALE_SERBIAN)) {
                resourceBundle = RESOURCE_BUNDLE_SERBIAN;
            }
        } else {
            resourceBundle = RESOURCE_BUNDLE_ROOT;
        }

        assert resourceBundle != null;

        return (resourceBundle.containsKey(key)) ? new String(resourceBundle.getString(key)
                .getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8) : key;
    }

    @PostConstruct
    public void postInit() {

        EventBusHolder.getEventBus().register(this);
    }

    @Subscribe
    public void localeChanged(LocaleChangedEvent localeChangedEvent) {

        locale = localeChangedEvent.getLocale();

        log.debug("Locale changed: " + locale.toString());
    }
}