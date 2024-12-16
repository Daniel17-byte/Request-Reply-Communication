const BASE_URL = 'http://localhost:8080/api/devices';

const getAuthHeaders = () => {
    const token = localStorage.getItem('token');
    return {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
    };
};

export const fetchDevices = async () => {
    try {
        const response = await fetch(BASE_URL, {
            method: 'GET',
            headers: getAuthHeaders(),
        });
        if (!response.ok) {
            const errorDetails = await response.text();
            throw new Error(`Failed to fetch devices: ${errorDetails}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching devices:', error);
        throw error;
    }
};

export const addDevice = async (newDevice) => {
    try {
        const response = await fetch(BASE_URL, {
            method: 'POST',
            headers: getAuthHeaders(),
            body: JSON.stringify(newDevice),
        });
        if (!response.ok) {
            const errorDetails = await response.text();
            throw new Error(`Failed to add device: ${errorDetails}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error adding device:', error);
        throw error;
    }
};

export const updateDevice = async (id, updatedDevice) => {
    try {
        const response = await fetch(`${BASE_URL}/${id}`, {
            method: 'PUT',
            headers: getAuthHeaders(),
            body: JSON.stringify(updatedDevice),
        });
        if (!response.ok) {
            const errorDetails = await response.text();
            throw new Error(`Failed to update device: ${errorDetails}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error updating device:', error);
        throw error;
    }
};

export const deleteDevice = async (id) => {
    try {
        const response = await fetch(`${BASE_URL}/${id}`, {
            method: 'DELETE',
            headers: getAuthHeaders(),
        });
        if (!response.ok) {
            const errorDetails = await response.text();
            throw new Error(`Failed to delete device: ${errorDetails}`);
        }
        return { message: 'Device deleted successfully' };
    } catch (error) {
        console.error('Error deleting device:', error);
        throw error;
    }
};