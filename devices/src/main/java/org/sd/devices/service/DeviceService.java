package org.sd.devices.service;

import org.sd.devices.model.Device;
import org.sd.devices.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    public Device getDeviceByUuid(UUID uuid) {
        return deviceRepository.findByUuid(uuid);
    }

    public Device updateDevice(Device device) {
        deviceRepository.updateDeviceByUuid(device.getUuid(), device);
        return device;
    }

    public void deleteDevice(UUID uuid) {
        Device device = deviceRepository.getDeviceByUuid(uuid);
        deviceRepository.delete(device);
    }
}
