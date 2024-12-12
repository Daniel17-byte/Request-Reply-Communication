package org.sd.monitoringcommunication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SimulationStep {
    @Id
    private UUID id = UUID.randomUUID();
    private long timestamp;
    private UUID deviceId;
    private Double measurementValue;
}
