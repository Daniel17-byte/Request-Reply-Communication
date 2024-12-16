import { useEffect, useState } from 'react';
import UserService from '../user/UserService';

const useAuth = () => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const loggedInUser = await UserService.getLoggedInUser();
                console.log("USER : " + loggedInUser);
                setUser(loggedInUser);
            } catch (err) {
                setError(err);
            } finally {
                setLoading(false);
            }
        };

        fetchUser();
    }, []);

    return { user, loading, error };
};

export default useAuth;
