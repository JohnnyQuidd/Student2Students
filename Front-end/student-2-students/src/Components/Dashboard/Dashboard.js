import React, { useEffect } from 'react'
// import axios from 'axios';
// import { API_ENDPOINT } from '../Constants/Endpoints';
// import { useHistory } from "react-router-dom";
import Navbar from '../Navbar/Navbar'
import '../../css/Dashboard.css'
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints'

function Dashboard() {
    // const history = useHistory();

    // useEffect(() =>{
    //     console.log('Use effect');
    //     axios.get(API_ENDPOINT + '/students')
    //             .then(response => {
    //                 console.log(response);
    //             })
    //             .catch(err => { 
    //                 console.log('Redirecting');
    //                 history.push('/');                   
    //             })
    // }, [history]);

    // Not protected Endpoint
    useEffect(() => {
        axios.get(API_ENDPOINT + '/student/no-role', { withCredentials: true })
            .then(response => {
                console.log('no-role WORKS');
            })
            .catch(err => {
                console.log('no-role error');
            })
    }, [])
    useEffect(() => {
        axios.get(API_ENDPOINT + '/student/student-role', { withCredentials: true })
            .then(response => {
                console.log('student-role');
            })
            .catch(err => {
                console.log('student-role error');
            })
    }, [])
    useEffect(() => {
        axios.get(API_ENDPOINT + '/student/admin-role', { withCredentials: true })
            .then(response => {
                console.log('admin-role');
            })
            .catch(err => {
                console.log('admin-role error');
            })
    }, [])

    return (
        <div>
            <Navbar />
            <h1> Dashboard </h1>
        </div>
    )
}

export default Dashboard