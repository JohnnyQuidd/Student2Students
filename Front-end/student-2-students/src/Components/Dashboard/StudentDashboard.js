import React, { useState, useEffect } from 'react'
import axios from 'axios';
import { API_ENDPOINT } from '../Constants/Endpoints';
import { useHistory } from "react-router-dom";
import Navbar from '../Navbar/Navbar'
import '../../css/Dashboard.css'
import Footer from '../Footer/Footer';
import '../../css/StudentDashboard.css'
import Exchange from '../Exchange/Exchange';

function StudentDashboard() {
    const [role, setRole] = useState(null);
    const [exchanges, setExchanges] = useState([]);
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

    useEffect(() => {
        axios({
            url: API_ENDPOINT + '/posting/exchange',
            method: 'GET',
            withCredentials: true
        })
        .then(response => response.data)
        .then(data => setExchanges(data))
        .catch(err => console.log('Couldn\'t fetch exchanges'));
    }, [])

    return (
        <div>
            <Navbar role={role} />
            <div id="student-main-panel">
                <div className="home-exchange">
                    <h1> Looking for an exchange? </h1>
                </div>
                <div className="exchange-list">
                    {/* <Exchange /> */}
                    {
                        exchanges.map(exchange => <Exchange exchange={exchange} />)
                    }
                </div>
                
            </div>
            <Footer />
        </div>
    )
}

export default StudentDashboard