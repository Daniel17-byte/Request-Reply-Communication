const API_URL = 'http://localhost:8080/api/users';

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

const updateUser = async (id, user) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
    });
    if (!response.ok) {
        throw new Error('Failed to update user');
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

const authenticate = async (credentials) => {
    const response = await fetch(`${API_URL}/authenticate`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(credentials),
    });

    if (!response.ok) {
        throw new Error('Failed to authenticate');
    }
    return response.json();
};

const getLoggedInUser = async () => {
    const response = await fetch(`${API_URL}/getLoggedInUser`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'include',
    });

    if (!response.ok) {
        throw new Error('Failed to get logged in user');
    }
    return response.json();
};

export default {
    getAllUsers,
    getUserById,
    createUser,
    updateUser,
    deleteUser,
    authenticate,
    getLoggedInUser,
};