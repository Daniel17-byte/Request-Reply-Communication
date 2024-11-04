import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import UserService from '../user/UserService';
import './Login.css';

const Login = () => {
    const [credentials, setCredentials] = useState({ username: '', password: '' });
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleChange = (e) => {
        setCredentials({ ...credentials, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        try {
            const user = await UserService.authenticate(credentials);
            console.log(user);
            if (user.role === 'ADMIN') {
                navigate('/admin');
            } else {
                navigate('/client');
            }
        } catch (error) {
            setError('Invalid username or password.');
        }
    };

    return (
        <div className="login-container">
            <div className="login-card">
                <h1>Login</h1>
                {error && <p className="error-message">{error}</p>}
                <form onSubmit={handleSubmit}>
                    <input
                        type="text"
                        name="username"
                        placeholder="Username"
                        value={credentials.username}
                        onChange={handleChange}
                        required
                    />
                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        value={credentials.password}
                        onChange={handleChange}
                        required
                    />
                    <button type="submit">Login</button>
                </form>
            </div>
        </div>
    );
};

export default Login;
