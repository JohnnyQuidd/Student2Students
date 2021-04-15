import React, { useEffect } from 'react'
import axios from 'axios';
import { API_ENDPOINT } from '../Constants/Endpoints';
import { useHistory } from "react-router-dom";
import Navbar from '../Navbar/Navbar'
import '../../css/Dashboard.css'

function StudentDashboard() {
    const history = useHistory();

    useEffect(() => {
        axios.get(API_ENDPOINT + '/authorization/student', { withCredentials: true })
            .then(response => {
                console.log('student-role');
            })
            .catch(err => {
                console.log(err);
                history.push('/');
            })
    }, [history])

    const fetchData = () => {
        axios.get(API_ENDPOINT + '/student/data', { withCredentials: true })
            .then(response => {
                console.log(response);
            })
            .catch(err => {
                console.log(err);
            });
    }

    const logout = () => {
        axios.get(API_ENDPOINT + '/authorization/logout', { withCredentials: true });
        history.push("/");
    }

    return (
        <div>
            <Navbar />
            <h1> Student Dashboard </h1>
            <button onClick={fetchData} > Fetch data </button>
            <button onClick={logout} > Logout </button>
        </div>
    )
}

export default StudentDashboard