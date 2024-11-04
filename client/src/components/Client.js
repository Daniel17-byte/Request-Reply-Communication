import React, { useEffect, useState } from 'react';
import { fetchDevices } from '../device/DeviceService';
import UserService from '../user/UserService';
import { useNavigate } from 'react-router-dom';
import '../device/DeviceManager.css';

const Client = () => {
    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [userRole, setUserRole] = useState(null);
    const navigate = useNavigate();

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

        const checkUserRole = async () => {
            try {
                const user = await UserService.getLoggedInUser();
                setUserRole(user.role);
                if (user.role !== 'CLIENT') {
                    navigate('/');
                }
            } catch (error) {
                setError('Failed to fetch user information.');
                navigate('/');
            }
        };

        checkUserRole();
        loadDevices();
    }, [navigate]);

    return (
        <div>
            <h1>Available Devices</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {loading ? (
                <p>Loading devices...</p>
            ) : (
                <table className="device-table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Model</th>
                        <th>Manufacturer</th>
                        <th>Serial Number</th>
                    </tr>
                    </thead>
                    <tbody>
                    {devices.map(device => (
                        <tr key={device.uuid}>
                            <td>{device.name}</td>
                            <td>{device.type}</td>
                            <td>{device.model}</td>
                            <td>{device.manufacturer}</td>
                            <td>{device.serialNumber}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default Client;
