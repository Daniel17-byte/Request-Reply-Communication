// src/UserManagement.js
import React, { useState, useEffect } from 'react';
import UserService from './UserService';

const UserManagement = () => {
    const [users, setUsers] = useState([]);
    const [user, setUser] = useState({
        username: '',
        password: '',
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        role: '',
    });
    const [editMode, setEditMode] = useState(false);
    const [userId, setUserId] = useState(null);

    const fetchUsers = async () => {
        try {
            const userList = await UserService.getAllUsers();
            setUsers(userList);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        fetchUsers();
    }, []);

    const handleChange = (e) => {
        setUser({ ...user, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (editMode) {
            // You can implement the update logic here
            // Example: await UserService.updateUser(userId, user);
        } else {
            try {
                await UserService.createUser(user);
                setUser({
                    username: '',
                    password: '',
                    firstName: '',
                    lastName: '',
                    email: '',
                    phone: '',
                    role: '',
                });
                fetchUsers(); // Refresh the user list after creation
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleEdit = (user) => {
        setUser(user);
        setEditMode(true);
        setUserId(user.uuid); // Assuming uuid is used as the ID
    };

    const handleDelete = async (id) => {
        try {
            await UserService.deleteUser(id);
            fetchUsers(); // Refresh the user list after deletion
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div>
            <h1>User Management</h1>
            <form onSubmit={handleSubmit}>
                <input type="text" name="username" placeholder="Username" value={user.username} onChange={handleChange} required />
                <input type="password" name="password" placeholder="Password" value={user.password} onChange={handleChange} required />
                <input type="text" name="firstName" placeholder="First Name" value={user.firstName} onChange={handleChange} required />
                <input type="text" name="lastName" placeholder="Last Name" value={user.lastName} onChange={handleChange} required />
                <input type="email" name="email" placeholder="Email" value={user.email} onChange={handleChange} required />
                <input type="text" name="phone" placeholder="Phone" value={user.phone} onChange={handleChange} required />
                <input type="text" name="role" placeholder="Role" value={user.role} onChange={handleChange} required />
                <button type="submit">{editMode ? 'Update User' : 'Create User'}</button>
            </form>
            <ul>
                {users.map((user) => (
                    <li key={user.uuid}>
                        {user.username} - {user.email}
                        <button onClick={() => handleEdit(user)}>Edit</button>
                        <button onClick={() => handleDelete(user.uuid)}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default UserManagement;