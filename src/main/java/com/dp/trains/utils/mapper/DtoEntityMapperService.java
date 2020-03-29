package com.dp.trains.utils.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface for classes that need to map DTO to Entity and vice versa.
 *
 * @param <D> type of DTOs to use for mapping
 * @param <E> type of Entities to use for mapping
 */
public interface DtoEntityMapperService<D, E> {

    /**
     * Maps collection of entities to collection of dtos.
     *
     * @param entities The entities used as a source.
     * @return {@code List<D>} list of dtos
     */
    default List<D> mapDtos(final List<E> entities) {
        return entities.stream().map(this::mapDto).collect(Collectors.toList());
    }

    /**
     * Maps collection of dtos to collection of entities.
     *
     * @param dtos The dtos used as a source.
     * @return {@code List<E>} list of entities
     */
    default Collection<E> mapEntities(final Collection<D> dtos) {
        return dtos.stream().map(this::mapEntity).collect(Collectors.toList());
    }

    /**
     * Maps the provided Entity to DTO.
     *
     * @param entity - the entity
     * @return the DTO
     */
    D mapDto(E entity);

    /**
     * Maps the provided DTO to Entity.
     *
     * @param dto - the DTO
     * @return the Entity
     */
    E mapEntity(D dto);
}
