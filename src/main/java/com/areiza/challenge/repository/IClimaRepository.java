package com.areiza.challenge.repository;

import com.areiza.challenge.model.enums.Clima;
import com.areiza.challenge.model.entity.ClimaCondition;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface IClimaRepository extends CrudRepository<ClimaCondition, Long> {

    ClimaCondition findByFecha(LocalDate fecha);
    List<ClimaCondition> findByClima(Clima clima);
}
