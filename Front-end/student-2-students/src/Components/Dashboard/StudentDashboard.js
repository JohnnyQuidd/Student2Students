import React, { useState, useEffect } from 'react'
import axios from 'axios';
import { API_ENDPOINT } from '../Constants/Endpoints';
import { useHistory } from "react-router-dom";
import Navbar from '../Navbar/Navbar'
import '../../css/Dashboard.css'
import Footer from '../Footer/Footer';
import '../../css/StudentDashboard.css'

function StudentDashboard() {
    const [role, setRole] = useState(null);
    const history = useHistory();

    useEffect(() => {
        axios.get(API_ENDPOINT + '/authorization/student', { withCredentials: true })
            .then(() => {
                setRole("STUDENT");
            })
            .catch(err => {
                console.log(err);
                history.push('/login');
            })
    }, [history])



    return (
        <div>
            <Navbar role={role} />
            <div id="student-main-panel">
                <div className="home-exchange">
                    <h1> Looking for an exchange? </h1>
                </div>
                
            </div>
            <Footer />
        </div>
    )
}

export default StudentDashboard