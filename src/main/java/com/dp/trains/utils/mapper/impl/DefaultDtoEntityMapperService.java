package com.dp.trains.utils.mapper.impl;

import com.dp.trains.utils.mapper.DtoEntityMapperService;
import com.googlecode.jmapper.api.enums.MappingType;

/**
 * Default implementation for {@link DtoEntityMapperService}. It uses {@link MappingType#ALL_FIELDS} for dto mapping
 * and {@link MappingType#ONLY_VALUED_FIELDS} for entity mapping.
 *
 * @param <D> type of DTOs to use for mapping
 * @param <E> type of Entities to use for mapping
 */

@SuppressWarnings({"rawtypes", "unchecked"})
public class DefaultDtoEntityMapperService<D, E> implements DtoEntityMapperService<D, E> {

    private FilteredDtoEntityMapperService<D, E> origin;

    /**
     * Creates new dto/entity mapper service using the given classes.
     *
     * @param dtoClass    The dto class.
     * @param entityClass The entity class.
     */
    public DefaultDtoEntityMapperService(final Class<D> dtoClass, final Class<E> entityClass) {
        this.origin = new FilteredDtoEntityMapperService<>(dtoClass, entityClass, MappingType
                .ALL_FIELDS, MappingType.ONLY_VALUED_FIELDS);
    }

    @Override
    public D mapDto(final E entity) {
        return this.origin.mapDto(entity);
    }

    @Override
    public E mapEntity(final D dto) {
        return this.origin.mapEntity(dto);
    }

    public static DefaultDtoEntityMapperService create(final Class dtoClass, final Class entityClass) {

        return new DefaultDtoEntityMapperService(dtoClass, entityClass);
    }
}
