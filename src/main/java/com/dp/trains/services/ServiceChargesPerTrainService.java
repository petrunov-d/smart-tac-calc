package com.dp.trains.services;

import com.dp.trains.model.entities.RailStationEntity;
import com.dp.trains.model.entities.ServiceChargesPerTrainEntity;
import com.dp.trains.repository.ServiceChargesPerTrainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceChargesPerTrainService {

    private final ServiceChargesPerTrainRepository serviceChargesPerTrainRepository;
    private final RailStationService railStationService;

    @Transactional
    public ServiceChargesPerTrainEntity save(ServiceChargesPerTrainEntity serviceChargesPerTrainEntity) {

        return this.serviceChargesPerTrainRepository.save(serviceChargesPerTrainEntity);
    }

    @Transactional(readOnly = true)
    public List<ServiceChargesPerTrainEntity> findByTrainNumberAndRailRoadStation(Integer trainNumber,
                                                                                  String railRoadStation) {

        List<RailStationEntity> railStationEntity = this.railStationService.getByRailStationName(railRoadStation);

        if (railStationEntity == null) {

            throw new IllegalStateException("Unknown Rail Station! " + railRoadStation);
        }

        return this.serviceChargesPerTrainRepository
                .findByTrainNumberAndRailStationEntity(trainNumber, railStationEntity.get(0));
    }

    public Double getTotalServiceChargesForTrainNumberAndRailStation(List<ServiceChargesPerTrainEntity>
                                                                             serviceChargesPerTrainEntities) {

        return serviceChargesPerTrainEntities
                .stream()
                .map(x -> x.getServiceEntity().getUnitPrice() * x.getServiceCount())
                .reduce(0.0, Double::sum);
    }

    @Transactional(readOnly = true)
    public int count() {

        int count = (int) serviceChargesPerTrainRepository.count();

        log.info(this.getClass().getSimpleName() + " count: " + count);

        return count;
    }

    @Transactional(readOnly = true)
    public Collection<ServiceChargesPerTrainEntity> fetch(int offset, int limit) {

        return serviceChargesPerTrainRepository.findAll();
    }

    @Transactional
    public void deleteAll() {

        serviceChargesPerTrainRepository.deleteAll();
    }

    @Transactional
    public void remove(ServiceChargesPerTrainEntity item) {

        log.info(this.getClass().getSimpleName() + " delete: " + item.toString());

        serviceChargesPerTrainRepository.delete(item);
    }

    @Transactional
    public ServiceChargesPerTrainEntity update(ServiceChargesPerTrainEntity serviceChargesPerTrainEntity) {

        Optional<ServiceChargesPerTrainEntity> optional = Optional.of(serviceChargesPerTrainRepository
                .findById(serviceChargesPerTrainEntity.getId())).orElseThrow(IllegalStateException::new);

        ServiceChargesPerTrainEntity serviceChargesPerTrainEntityFromDb = optional.get();

        log.info("About to update item " + serviceChargesPerTrainEntityFromDb.toString()
                + " to " + serviceChargesPerTrainEntity.toString());

        serviceChargesPerTrainEntityFromDb.setServiceCount(serviceChargesPerTrainEntity.getServiceCount());

        return serviceChargesPerTrainRepository.save(serviceChargesPerTrainEntityFromDb);
    }
}