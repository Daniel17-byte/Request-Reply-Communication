import React, { useEffect, useState } from 'react';
import {fetchDevices, addDevice, updateDevice, deleteDevice, deleteDeviceByUsername} from './DeviceService';
import './DeviceManager.css';

const DeviceManager = () => {
    const [devices, setDevices] = useState([]);
    const [currentDevice, setCurrentDevice] = useState({
        username: '',
        name: '',
        type: '',
        model: '',
        manufacturer: '',
        serialNumber: ''
    });
    const [editMode, setEditMode] = useState(false);
    const [currentDeviceId, setCurrentDeviceId] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const loadDevices = async () => {
            setLoading(true);
            try {
                const data = await fetchDevices();
                setDevices(data);
            } catch (error) {
                setError('Failed to load devices. Please try again later.');
            } finally {
                setLoading(false);
            }
        };
        loadDevices();
    }, []);

    const handleAddOrUpdateDevice = async (e) => {
        e.preventDefault();
        setError(null);
        try {
            if (editMode) {
                const updatedDevice = await updateDevice(currentDeviceId, currentDevice);
                setDevices(prevDevices =>
                    prevDevices.map(device =>
                        device.uuid === currentDeviceId ? updatedDevice : device
                    )
                );
                setEditMode(false);
                setCurrentDeviceId(null);
            } else {
                const device = await addDevice(currentDevice);
                setDevices(prevDevices => [...prevDevices, device]);
            }
            resetForm();
        } catch (error) {
            setError('Failed to save device. Please try again.');
        }
    };


    const handleEditDevice = (device) => {
        setCurrentDevice(device);
        setEditMode(true);
        setCurrentDeviceId(device.uuid);
    };

    const handleDeleteDevice = async (id) => {
        setError(null);
        try {
            await deleteDevice(id);
            setDevices(prevDevices => prevDevices.filter(device => device.uuid !== id));
        } catch (error) {
            setError('Failed to delete device. Please try again.');
        }
    };

    const resetForm = () => {
        setCurrentDevice({ username: '', name: '', type: '', model: '', manufacturer: '', serialNumber: '' });
    };

    return (
        <div>
            <h1>Device Manager</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {loading ? (
                <p>Loading devices...</p>
            ) : (
                <>
                    <form onSubmit={handleAddOrUpdateDevice}>
                        <input
                            type="text"
                            placeholder="Username"
                            value={currentDevice.username}
                            onChange={(e) => setCurrentDevice({...currentDevice, username: e.target.value})}
                            required
                        />
                        <input
                            type="text"
                            placeholder="Name"
                            value={currentDevice.name}
                            onChange={(e) => setCurrentDevice({...currentDevice, name: e.target.value})}
                            required
                        />
                        <input
                            type="text"
                            placeholder="Type"
                            value={currentDevice.type}
                            onChange={(e) => setCurrentDevice({...currentDevice, type: e.target.value})}
                            required
                        />
                        <input
                            type="text"
                            placeholder="Model"
                            value={currentDevice.model}
                            onChange={(e) => setCurrentDevice({...currentDevice, model: e.target.value})}
                            required
                        />
                        <input
                            type="text"
                            placeholder="Manufacturer"
                            value={currentDevice.manufacturer}
                            onChange={(e) => setCurrentDevice({...currentDevice, manufacturer: e.target.value})}
                            required
                        />
                        <input
                            type="text"
                            placeholder="Serial Number"
                            value={currentDevice.serialNumber}
                            onChange={(e) => setCurrentDevice({...currentDevice, serialNumber: e.target.value})}
                            required
                        />
                        <button type="submit">{editMode ? 'Update Device' : 'Add Device'}</button>
                    </form>

                    <table className="device-table">
                        <thead>
                        <tr>
                            <th>Username</th>
                            <th>Name</th>
                            <th>Type</th>
                            <th>Model</th>
                            <th>Manufacturer</th>
                            <th>Serial Number</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {devices.map(device => (
                            <tr key={device.uuid}>
                                <td>{device.username}</td>
                                <td>{device.name}</td>
                                <td>{device.type}</td>
                                <td>{device.model}</td>
                                <td>{device.manufacturer}</td>
                                <td>{device.serialNumber}</td>
                                <td>
                                    <button onClick={() => handleEditDevice(device)}>Edit</button>
                                    <button onClick={() => handleDeleteDevice(device.uuid)}>Delete</button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </>
            )}
        </div>
    );
};

export default DeviceManager;
