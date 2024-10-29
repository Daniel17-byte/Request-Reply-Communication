import React, { useEffect, useState } from 'react';

const DeviceManager = () => {
    const [devices, setDevices] = useState([]);
    const [newDevice, setNewDevice] = useState({ name: '', type: '', model: '', manufacturer: '', serialNumber: '' });
    const [error, setError] = useState(null);

    // Fetch devices from the API
    useEffect(() => {
        fetch('http://localhost:8082/devices')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => setDevices(data))
            .catch(error => setError(error.message));
    }, []);

    // Handle adding a new device
    const handleAddDevice = () => {
        fetch('http://localhost:8082/devices', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newDevice),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to add device');
                }
                return response.json();
            })
            .then(device => {
                setDevices([...devices, device]);
                setNewDevice({ name: '', type: '', model: '', manufacturer: '', serialNumber: '' }); // Reset form
            })
            .catch(error => setError(error.message));
    };

    // Handle deleting a device
    const handleDeleteDevice = (id) => {
        fetch(`http://localhost:8082/devices/${id}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to delete device');
                }
                setDevices(devices.filter(device => device.uuid !== id)); // Update local state
            })
            .catch(error => setError(error.message));
    };

    return (
        <div>
            <h1>Device Manager</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <ul>
                {devices.map(device => (
                    <li key={device.uuid}>
                        {device.name} - {device.type}
                        <button onClick={() => handleDeleteDevice(device.uuid)}>Delete</button>
                    </li>
                ))}
            </ul>
            <h2>Add New Device</h2>
            <input
                type="text"
                placeholder="Name"
                value={newDevice.name}
                onChange={(e) => setNewDevice({ ...newDevice, name: e.target.value })}
            />
            <input
                type="text"
                placeholder="Type"
                value={newDevice.type}
                onChange={(e) => setNewDevice({ ...newDevice, type: e.target.value })}
            />
            <input
                type="text"
                placeholder="Model"
                value={newDevice.model}
                onChange={(e) => setNewDevice({ ...newDevice, model: e.target.value })}
            />
            <input
                type="text"
                placeholder="Manufacturer"
                value={newDevice.manufacturer}
                onChange={(e) => setNewDevice({ ...newDevice, manufacturer: e.target.value })}
            />
            <input
                type="text"
                placeholder="Serial Number"
                value={newDevice.serialNumber}
                onChange={(e) => setNewDevice({ ...newDevice, serialNumber: e.target.value })}
            />
            <button onClick={handleAddDevice}>Add Device</button>
        </div>
    );
};

export default DeviceManager;