package com.dp.trains.utils.mapper;

/**
 * Provides functionality for enriching a dto from an entity and vice versa.
 *
 * @param <D> the type of the dto
 * @param <E> the type of the entity
 */
public interface DtoEntityEnrichmentService<D, E> {

    /**
     * Enriches the supplied {@link D} with data from {@link E}.
     *
     * @param entity the entity
     * @param dto    the dto
     */
    void enrichDtoFromEntity(E entity, D dto);

    /**
     * Enriches the supplied {@link E} with data from {@link D}.
     *
     * @param dto    the dto
     * @param entity the entity
     */
    void enrichEntityFromDto(D dto, E entity);

}
