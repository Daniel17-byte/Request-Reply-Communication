package org.sd.monitoringcommunication.repository;

import org.sd.monitoringcommunication.model.SimulationStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationStepRepository extends JpaRepository<SimulationStep, Long> {
}
