import React, { useEffect } from 'react'
import Navbar from '../Navbar/Navbar'
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints'
import { useHistory } from 'react-router-dom'

function AdminDashboard() {
    const history = useHistory();

    useEffect(() => {
        axios.get(API_ENDPOINT + '/authorization/admin', { withCredentials: true })
            .catch(err => {
                console.log(err);
                history.push('/');
            })
    }, [])

    const logout = () => {
        axios.get(API_ENDPOINT + '/authorization/logout', { withCredentials: true });
        history.push("/");
    }

    return (
        <div>
            <Navbar />
            <h1> Admin Dashboard! </h1>
            <button onClick={logout} > Logout </button>
        </div>
    )
}

export default AdminDashboard
