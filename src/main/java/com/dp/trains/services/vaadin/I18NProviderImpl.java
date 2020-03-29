package com.dp.trains.services.vaadin;


import com.google.common.base.Joiner;
import com.vaadin.flow.i18n.I18NProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.util.Locale.ROOT;
import static java.util.Objects.isNull;
import static org.rapidpm.frp.matcher.Case.match;
import static org.rapidpm.frp.matcher.Case.matchCase;
import static org.rapidpm.frp.model.Result.failure;
import static org.rapidpm.frp.model.Result.success;

@Slf4j
@Service
@RequiredArgsConstructor
public class I18NProviderImpl implements I18NProvider {

    @Autowired
    private final ResourceBundleService resourceBundleService;

    public static final String NULL_KEY = "###-NULL-KEY-###";
    public static final String EMPTY_KEY = "###-EMPTY-KEY-###";

    @Override
    public List<Locale> getProvidedLocales() {

        List<Locale> locales = resourceBundleService.providedLocalesAsList();

        log.info("Provided locales: " + Joiner.on(",").join(locales.stream()
                .map(Locale::getISO3Country)
                .collect(Collectors.toSet())));

        return locales;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {

        log.debug("VaadinI18NProvider translation for key : " + key + " - " + locale);

        final ResourceBundle resourceBundle = resourceBundleService
                .resourceBundleToUse()
                .apply(locale != null ? locale : ROOT);


        return match(
                matchCase(() -> failure("###-" + key + "-###-" + locale)),
                matchCase(() -> isNull(key), () -> failure(NULL_KEY)),
                matchCase(key::isEmpty, () -> failure(EMPTY_KEY)),
                matchCase(() -> resourceBundle.containsKey(key), () ->
                        success(new String(resourceBundle.getString(key)
                                .getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8)))
        ).ifFailed(log::warn).getOrElse(() -> "Key not found: " + key + " - " + locale + " .");
    }
}