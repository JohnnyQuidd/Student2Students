import React, { useEffect } from 'react'
import axios from 'axios';
import { API_ENDPOINT } from '../Constants/Endpoints';
import { useHistory } from "react-router-dom";
import Navbar from '../Navbar/Navbar'
import '../../css/Dashboard.css'
import Footer from '../Footer/Footer';
import '../../css/StudentDashboard.css'

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

    const testCredentials = () => {
        axios.get(API_ENDPOINT + '/manage/data/SkinnyPete', { withCredentials: true })
            .then(response => {
                console.log(response);
            })
            .catch(err => {
                console.log(err);
            });
    }



    return (
        <div>
            <Navbar role={"STUDENT"}/>
            <div id="student-main-panel">
                <h1> Student Dashboard </h1>
                <button onClick={testCredentials} > Test credentials </button>
            </div>
            <Footer />
        </div>
    )
}

export default StudentDashboard