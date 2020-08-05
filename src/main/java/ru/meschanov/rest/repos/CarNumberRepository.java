package ru.meschanov.rest.repos;

import org.springframework.data.repository.CrudRepository;
import ru.meschanov.rest.models.CarNumberModel;

public interface CarNumberRepository extends CrudRepository<CarNumberModel, Long> {
}
