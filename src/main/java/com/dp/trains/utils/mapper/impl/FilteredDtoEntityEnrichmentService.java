package com.dp.trains.utils.mapper.impl;

import com.dp.trains.utils.mapper.DtoEntityEnrichmentService;
import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.enums.MappingType;

import java.util.Objects;

/**
 * Filtered dto/entity enrichment mapper. The data is filtered using the provided {@link MappingType}.
 *
 * @param <D> type of DTOs to use for mapping
 * @param <E> type of Entities to use for mapping
 */
public class FilteredDtoEntityEnrichmentService<D, E> implements DtoEntityEnrichmentService<D, E> {

    private final MappingType dtoMappingType;
    private final MappingType entityMTDestination;
    private final MappingType entityMTSource;
    private JMapper<D, E> dtoMapper;
    private JMapper<E, D> entityMapper;

    /**
     * Creates new dto/entity mapper service using the given classes.
     *
     * @param dtoClass                     The dto class.
     * @param entityClass                  The entity class.
     * @param dtoMappingType               Dto mapping type.
     * @param entityMappingTypeDestination Entity mapping type destination.
     * @param entityMappingTypeSource      Entity mapping type source.
     */
    public FilteredDtoEntityEnrichmentService(final Class<D> dtoClass, final Class<E> entityClass, final MappingType
            dtoMappingType, final MappingType entityMappingTypeDestination, final MappingType entityMappingTypeSource) {
        Objects.requireNonNull(dtoMappingType, "Dto mapping type must not be null!");
        Objects.requireNonNull(entityMappingTypeDestination, "Entity mapping type destination must not be null!");
        Objects.requireNonNull(entityMappingTypeSource, "Entity mapping type source must not be null!");
        this.dtoMappingType = dtoMappingType;
        this.entityMTDestination = entityMappingTypeDestination;
        this.entityMTSource = entityMappingTypeSource;
        this.dtoMapper = new JMapper<>(dtoClass, entityClass);
        this.entityMapper = new JMapper<>(entityClass, dtoClass);
    }

    @Override
    public void enrichDtoFromEntity(final E entity, final D dto) {
        this.dtoMapper.getDestination(dto, entity, this.dtoMappingType, this.dtoMappingType);
    }

    @Override
    public void enrichEntityFromDto(final D dto, final E entity) {
        this.entityMapper.getDestination(entity, dto, this.entityMTDestination, this.entityMTSource);
    }
}
