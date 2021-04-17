import React, { useState, useEffect } from 'react'
import Navbar from '../Navbar/Navbar'
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints'
import { useHistory } from 'react-router-dom'
import '../../css/AdminDashboard.css'
import LineChart from '../Charts/LineChart'
import MajorTable from './MajorTable'


function AdminDashboard() {
    const [majorData, setMajorData] = useState([])

    const history = useHistory();

    // Restrict access if user doesn't have admin privileges
    // useEffect(() => {
    //     axios.get(API_ENDPOINT + '/authorization/admin', { withCredentials: true })
    //         .catch(err => {
    //             console.log(err);
    //             history.push('/');
    //         })
    // }, [history])
    
    useEffect(() => {     
        axios.get(API_ENDPOINT + '/manage/major')
        .then(response => {
            setMajorData(response.data);
        })
        .catch(err => {
            console.log('Error fetching majors');
            console.log(err);
        })
    }, [])

    const logout = () => {
        axios.get(API_ENDPOINT + '/authorization/logout', { withCredentials: true });
        history.push("/");
    }

    return (
        <div>
            <Navbar />
            <div className="content">
                <h1 id="title"> Dashboard </h1>
                <div className="chart">
                    <LineChart />
                </div>
                <div className="major">
                    <p> Majors </p>
                    <MajorTable majorData={majorData} />
                    
                </div>
                <button id="logout" onClick={logout} > Logout </button>
            </div>
        </div>
    )
}

export default AdminDashboard
