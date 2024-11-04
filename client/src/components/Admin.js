import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import UserManagement from "../user/UserManagement";
import DeviceManager from "../device/DeviceManager";
import useAuth from '../hooks/useAuth';

const Admin = () => {
    const navigate = useNavigate();
    const { user, loading } = useAuth();

    useEffect(() => {
        if (loading) return;
        if (!user || user.role !== 'ADMIN') {
            navigate('/');
        }
    }, [loading, user, navigate]);

    return (
        <>
            <h1>Admin Panel</h1>
            <UserManagement />
            <DeviceManager />
        </>
    );
}

export default Admin;
