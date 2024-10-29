const API_URL = 'http://localhost:8081/api/users'; // Adjust the URL based on your backend

const getAllUsers = async () => {
    const response = await fetch(API_URL);
    if (!response.ok) {
        throw new Error('Failed to fetch users');
    }
    return response.json();
};

const getUserById = async (id) => {
    const response = await fetch(`${API_URL}/${id}`);
    if (!response.ok) {
        throw new Error('Failed to fetch user');
    }
    return response.json();
};

const createUser = async (user) => {
    const response = await fetch(API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
    });
    if (!response.ok) {
        throw new Error('Failed to create user');
    }
    return response.json();
};

const deleteUser = async (id) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: 'DELETE',
    });
    if (!response.ok) {
        throw new Error('Failed to delete user');
    }
};

export default {
    getAllUsers,
    getUserById,
    createUser,
    deleteUser,
};