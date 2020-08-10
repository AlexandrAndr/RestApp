package ru.meschanov.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.meschanov.rest.domains.CarNumberEntity;

@Repository
public interface CarNumberRepository extends CrudRepository<CarNumberEntity, Long> {
}
