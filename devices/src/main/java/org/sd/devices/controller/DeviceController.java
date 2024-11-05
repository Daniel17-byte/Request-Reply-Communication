package org.sd.devices.controller;

import org.sd.devices.model.Device;
import org.sd.devices.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:8081"})
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.getAllDevices();
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Device> getDeviceById(@PathVariable UUID uuid) {
        Device device = deviceService.getDeviceByUuid(uuid);

        if (device == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        Device deviceResponse = deviceService.createDevice(device);
        return new ResponseEntity<>(deviceResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Device> updateDevice(@PathVariable UUID uuid, @RequestBody Device device) {
        try {
            Device updatedDevice = deviceService.updateDevice(device);
            return ResponseEntity.ok(updatedDevice);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteDevice(@PathVariable UUID uuid) {
        deviceService.deleteDevice(uuid);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<Void> deleteDeviceByUsername(@PathVariable String username) {
        System.out.println(username);
        deviceService.deleteDeviceByUsername(username);
        return ResponseEntity.noContent().build();
    }
}