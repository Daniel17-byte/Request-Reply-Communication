import React, { useState, useEffect } from 'react';
import UserService from './UserService';
import './UserManagement.css';

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
            try {
                await UserService.updateUser(userId, user);
                setEditMode(false);
                setUserId(null);
            } catch (error) {
                console.error('Failed to update user', error);
            }
        } else {
            try {
                await UserService.createUser(user);
            } catch (error) {
                console.error('Failed to create user', error);
            }
        }

        setUser({
            username: '',
            password: '',
            firstName: '',
            lastName: '',
            email: '',
            phone: '',
            role: '',
        });

        fetchUsers();
    };

    const handleEdit = (user) => {
        setUser(user);
        setEditMode(true);
        setUserId(user.uuid);
    };

    const handleDelete = async (id) => {
        try {
            await UserService.deleteUser(id);
            fetchUsers();
        } catch (error) {
            console.error('Failed to delete user', error);
        }
    };

    return (
        <div>
            <h1>User Management</h1>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="username"
                    placeholder="Username"
                    value={user.username}
                    onChange={handleChange}
                    required
                />
                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={user.password}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="firstName"
                    placeholder="First Name"
                    value={user.firstName}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="lastName"
                    placeholder="Last Name"
                    value={user.lastName}
                    onChange={handleChange}
                    required
                />
                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={user.email}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="phone"
                    placeholder="Phone"
                    value={user.phone}
                    onChange={handleChange}
                    required
                />
                <select
                    name="role"
                    value={user.role}
                    onChange={handleChange}
                    required
                >
                    <option value="" disabled>Select Role</option>
                    <option value="ADMIN">ADMIN</option>
                    <option value="CLIENT">CLIENT</option>
                </select>
                <button type="submit">{editMode ? 'Update User' : 'Create User'}</button>
            </form>

            <table className="user-table">
                <thead>
                <tr>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {users.map((user) => (
                    <tr key={user.uuid} className={`user-row ${user.role.toLowerCase()}`}>
                        <td>{user.username}</td>
                        <td>{user.email}</td>
                        <td>{user.phone}</td>
                        <td className={`role-cell ${user.role.toLowerCase()}`}>{user.role}</td>
                        <td>
                            <button onClick={() => handleEdit(user)}>Edit</button>
                            <button onClick={() => handleDelete(user.uuid)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default UserManagement;
