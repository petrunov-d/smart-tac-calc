package com.dp.trains.services;

import com.dp.trains.model.dto.SubSectionDto;
import com.dp.trains.model.entities.SectionEntity;
import com.dp.trains.model.entities.SubSectionEntity;
import com.dp.trains.repository.SubSectionRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@ToString
@RequiredArgsConstructor
public class SubSectionService {

    @Qualifier("subSectionMapper")
    private final DefaultDtoEntityMapperService<SubSectionDto, SubSectionEntity> subSectionMapper;

    private final SubSectionRepository subSectionRepository;

    @Transactional(readOnly = true)
    public int count() {

        int count = (int) subSectionRepository.count();

        log.info(this.getClass().getSimpleName() + " count: " + count);

        return count;
    }

    @Transactional(readOnly = true)
    public Collection<SubSectionEntity> fetch(int offset, int limit) {

        return subSectionRepository.findAll();
    }

    @Transactional
    public void remove(SubSectionEntity item) {

        log.info(this.getClass().getSimpleName() + " delete: " + item.toString());

        subSectionRepository.delete(item);
    }

    @Transactional
    public SubSectionEntity add(SubSectionDto subSectionDto, SectionEntity sectionEntity) {

        SubSectionEntity subSectionEntity = subSectionMapper.mapEntity(subSectionDto);
        subSectionEntity.setSectionEntity(sectionEntity);

        log.info(this.getClass().getSimpleName() + " add: " + subSectionEntity.toString());

        return subSectionRepository.save(subSectionEntity);
    }

    @Transactional
    public List<SubSectionEntity> add(Collection<SubSectionDto> subSectionDtos) {

        Collection<SubSectionEntity> subSectionEntities = subSectionMapper.mapEntities(subSectionDtos);

        return subSectionRepository.saveAll(subSectionEntities);
    }

    @Transactional
    public SubSectionEntity update(SubSectionEntity item) {

        Optional<SubSectionEntity> optional = Optional.of(subSectionRepository
                .findById(item.getId())).orElseThrow(IllegalStateException::new);

        SubSectionEntity subSectionEntityFromDb = optional.get();

        log.info("About to update item " + subSectionEntityFromDb.toString() + " to " + item.toString());

        subSectionEntityFromDb.setKilometers(item.getKilometers());
        subSectionEntityFromDb.setNonKeyStation(item.getNonKeyStation());

        return subSectionRepository.save(subSectionEntityFromDb);
    }

    @Transactional
    public SubSectionEntity update(SubSectionDto subSectionDto, Long id) {

        Optional<SubSectionEntity> optional = Optional.of(subSectionRepository
                .findById(id)).orElseThrow(IllegalStateException::new);

        SubSectionEntity subSectionEntityFromDb = optional.get();

        log.info("About to update item " + subSectionEntityFromDb.toString() + " to " + subSectionDto.toString());

        subSectionEntityFromDb.setKilometers(subSectionDto.getKilometers());
        subSectionEntityFromDb.setNonKeyStation(subSectionDto.getNonKeyStation());

        return subSectionRepository.save(subSectionEntityFromDb);
    }
}