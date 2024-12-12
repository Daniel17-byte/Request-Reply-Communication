package org.sd.monitoringcommunication.service;

import org.sd.monitoringcommunication.model.SimulationStep;
import org.sd.monitoringcommunication.repository.SimulationStepRepository;
import org.springframework.stereotype.Service;

@Service
public class SimulationStepService {
    private final SimulationStepRepository simulationStepRepository;

    public SimulationStepService(SimulationStepRepository simulationStepRepository) {
        this.simulationStepRepository = simulationStepRepository;
    }

    public void storeSimulationStep(SimulationStep simulationStep) {
        simulationStepRepository.save(simulationStep);
    }


}
