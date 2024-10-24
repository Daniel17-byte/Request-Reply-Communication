package org.sd.devices.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Device {
    @Id
    private UUID uuid;
    private String name;
    private String type;
    private String model;
    private String manufacturer;
    private String serialNumber;

    public Device() {
        this.uuid = UUID.randomUUID();
    }
}
