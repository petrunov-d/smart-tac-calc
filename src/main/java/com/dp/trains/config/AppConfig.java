package com.dp.trains.config;

import com.dp.trains.model.dto.*;
import com.dp.trains.model.entities.*;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@RequiredArgsConstructor
@SuppressWarnings({"UnstableApiUsage"})
public class AppConfig {

    private ApplicationContext applicationContext;

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        methodInvokingFactoryBean.setArguments((Object) new String[]{SecurityContextHolder.MODE_INHERITABLETHREADLOCAL});
        return methodInvokingFactoryBean;
    }

    @Primary
    @Bean(name = "json-jackson")
    public ObjectMapper objectMapper() {

        ObjectMapper defaultObjectMapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
                .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .disable(SerializationFeature.WRAP_ROOT_VALUE)
                .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                .enable(JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION)
                .findAndRegisterModules();

        Hibernate5Module module = new Hibernate5Module();
        module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);

        defaultObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        defaultObjectMapper.disable(MapperFeature.USE_GETTERS_AS_SETTERS);
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        defaultObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        defaultObjectMapper.registerModule(module);

        return defaultObjectMapper;
    }

    @Bean("trainTypeMapper")
    public DefaultDtoEntityMapperService<TrainTypeDto, TrainTypeEntity> trainTypeMapper() {

        return new DefaultDtoEntityMapperService<>(TrainTypeDto.class, TrainTypeEntity.class);
    }

    @Bean("strategicCoefficientsMapper")
    public DefaultDtoEntityMapperService<StrategicCoefficientDto, StrategicCoefficientEntity> strategicCoefficientsMapper() {

        return new DefaultDtoEntityMapperService<>(StrategicCoefficientDto.class, StrategicCoefficientEntity.class);
    }

    @Bean("serviceMapper")
    public DefaultDtoEntityMapperService<ServiceDto, ServiceEntity> serviceMapper() {

        return new DefaultDtoEntityMapperService<>(ServiceDto.class, ServiceEntity.class);
    }

    @Bean("lineTypeMapper")
    public DefaultDtoEntityMapperService<LineTypeDto, LineTypeEntity> lineTypeMapper() {

        return new DefaultDtoEntityMapperService<>(LineTypeDto.class, LineTypeEntity.class);
    }

    @Bean("railStationMapper")
    public DefaultDtoEntityMapperService<RailStationDto, RailStationEntity> railStationMapper() {

        return new DefaultDtoEntityMapperService<>(RailStationDto.class, RailStationEntity.class);
    }

    @Bean("taxForServicesPerTrainMapper")
    public DefaultDtoEntityMapperService<TaxForServicesPerTrainDto, TaxForServicesPerTrainEntity> taxForServicesPerTrainMapper() {

        return new DefaultDtoEntityMapperService<>(TaxForServicesPerTrainDto.class, TaxForServicesPerTrainEntity.class);
    }

    @Bean("lineNumberMapper")
    public DefaultDtoEntityMapperService<LineNumberDto, LineNumberEntity> lineNumberMapper() {

        return new DefaultDtoEntityMapperService<>(LineNumberDto.class, LineNumberEntity.class);
    }

    @Bean("financialDataMapper")
    public DefaultDtoEntityMapperService<FinancialDataDto, FinancialDataEntity> financialDataMapper() {

        return new DefaultDtoEntityMapperService<>(FinancialDataDto.class, FinancialDataEntity.class);
    }

    @Bean("trafficDataMapper")
    public DefaultDtoEntityMapperService<TrafficDataDto, TrafficDataEntity> trafficDataMapper() {

        return new DefaultDtoEntityMapperService<>(TrafficDataDto.class, TrafficDataEntity.class);
    }

    @Bean("unitPriceMapper")
    public DefaultDtoEntityMapperService<UnitPriceDto, UnitPriceEntity> unitPriceMapper() {

        return new DefaultDtoEntityMapperService<>(UnitPriceDto.class, UnitPriceEntity.class);
    }

    @Bean("subSectionMapper")
    public DefaultDtoEntityMapperService<SubSectionDto, SubSectionEntity> subSectionMapper() {

        return new DefaultDtoEntityMapperService<>(SubSectionDto.class, SubSectionEntity.class);
    }

    @Bean("sectionMapper")
    public DefaultDtoEntityMapperService<SectionsDto, SectionEntity> sectionMapper() {

        return new DefaultDtoEntityMapperService<>(SectionsDto.class, SectionEntity.class);
    }

    @Bean("exceptionMapper")
    public DefaultDtoEntityMapperService<ExceptionDto, ExceptionEntity> exceptionMapper() {

        return new DefaultDtoEntityMapperService<>(ExceptionDto.class, ExceptionEntity.class);
    }

    @Bean("markupCoefficientMapper")
    public DefaultDtoEntityMapperService<MarkupCoefficientDto, MarkupCoefficientEntity> markupCoefficientMapper() {

        return new DefaultDtoEntityMapperService<>(MarkupCoefficientDto.class, MarkupCoefficientEntity.class);
    }

    @Bean("carrierCompanyMapper")
    public DefaultDtoEntityMapperService<CarrierCompanyDto, CarrierCompanyEntity> carrierCompanyMapper() {

        return new DefaultDtoEntityMapperService<>(CarrierCompanyDto.class, CarrierCompanyEntity.class);
    }
}