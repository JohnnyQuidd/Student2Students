import React, { useEffect } from 'react'
import Navbar from '../Navbar/Navbar'
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints'
import { useHistory } from 'react-router-dom'
import '../../css/AdminDashboard.css'
import LineChart from '../Charts/LineChart'

function AdminDashboard() {
    const history = useHistory();

    // useEffect(() => {
    //     axios.get(API_ENDPOINT + '/authorization/admin', { withCredentials: true })
    //         .catch(err => {
    //             console.log(err);
    //             history.push('/');
    //         })
    // }, [history])

    const logout = () => {
        axios.get(API_ENDPOINT + '/authorization/logout', { withCredentials: true });
        history.push("/");
    }

    return (
        <div>
            <Navbar />
            <div className="content">
                <h1 id="title"> Admin Dashboard </h1>
                <div className="chart">
                    <LineChart />
                </div>
                <button id="logout" onClick={logout} > Logout </button>
            </div>
        </div>
    )
}

export default AdminDashboard
