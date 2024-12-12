package org.sd.monitoringcommunication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sd.monitoringcommunication.model.SimulationStep;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {
    private final SimulationStepService simulationStepService;

    public MessageConsumer(SimulationStepService simulationStepService) {
        this.simulationStepService = simulationStepService;
    }


    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessage(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(message);
            SimulationStep receivedData = objectMapper.readValue(message, SimulationStep.class);

            simulationStepService.storeSimulationStep(receivedData);

            System.out.println("Processed JSON message: " + message);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}