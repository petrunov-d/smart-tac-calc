package com.dp.trains.utils.mapper.impl;

import com.dp.trains.utils.mapper.DtoEntityMapperService;
import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.enums.MappingType;
import com.googlecode.jmapper.api.enums.NullPointerControl;

import java.util.Objects;

/**
 * Filtered dto/entity mapper. The data is filtered using the provided {@link MappingType}.
 *
 * @param <D> type of DTOs to use for mapping
 * @param <E> type of Entities to use for mapping
 */
public class FilteredDtoEntityMapperService<D, E> implements DtoEntityMapperService<D, E> {

    private final MappingType dtoMappingType;
    private final MappingType entityMappingType;
    private JMapper<D, E> dtoMapper;
    private JMapper<E, D> entityMapper;

    /**
     * Creates new dto/entity mapper service using the given classes.
     *
     * @param dtoClass          The dto class.
     * @param entityClass       The entity class.
     * @param dtoMappingType    Dto mapping type.
     * @param entityMappingType Entity mapping type.
     */
    public FilteredDtoEntityMapperService(final Class<D> dtoClass, final Class<E> entityClass, final MappingType
            dtoMappingType, final MappingType entityMappingType) {
        Objects.requireNonNull(dtoMappingType, "Dto mapping type must not be null!");
        Objects.requireNonNull(entityMappingType, "Entity mapping type must not be null!");
        this.dtoMappingType = dtoMappingType;
        this.entityMappingType = entityMappingType;
        this.dtoMapper = new JMapper<>(dtoClass, entityClass);
        this.entityMapper = new JMapper<>(entityClass, dtoClass);
    }

    @Override
    public D mapDto(final E entity) {
        return this.dtoMapper.getDestination(entity, NullPointerControl.ALL, this.dtoMappingType);
    }

    @Override
    public E mapEntity(final D dto) {
        return this.entityMapper.getDestination(dto, NullPointerControl.ALL, this.entityMappingType);
    }
}
