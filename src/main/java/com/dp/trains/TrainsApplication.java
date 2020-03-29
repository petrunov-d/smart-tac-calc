package com.dp.trains;

import com.dp.trains.services.vaadin.I18NProviderImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

import static java.lang.System.setProperty;

@EnableAsync
@EnableConfigurationProperties
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class TrainsApplication {

    public static void main(String[] args) {

        setProperty("vaadin.i18n.provider", I18NProviderImpl.class.getName());

        SpringApplication.run(TrainsApplication.class, args);
    }
}