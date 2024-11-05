package org.sd.devices.repository;

import jakarta.transaction.Transactional;
import org.sd.devices.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device getDeviceByUuid(UUID uuid);

    Device findByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query("UPDATE Device d SET d.name = :#{#device.name}, d.type = :#{#device.type}, " +
            "d.model = :#{#device.model}, d.manufacturer = :#{#device.manufacturer}, " +
            "d.serialNumber = :#{#device.serialNumber}, d.username = :#{#device.username} " +
            "WHERE d.uuid = :uuid")
    void updateDeviceByUuid(UUID uuid, Device device);

    @Modifying
    @Transactional
    @Query("DELETE FROM Device d WHERE d.username = :username")
    void deleteDeviceByUsername(String username);
}
