package org.sd.devices.service;

import org.sd.devices.model.Device;
import org.sd.devices.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Device> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    public Device updateDevice(Long id, Device device) {
        if (deviceRepository.findById(id).isPresent()){
            return deviceRepository.save(device);
        }
        return null;
    }

    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }
}
