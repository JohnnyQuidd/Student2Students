import React, { useState, useEffect } from 'react'
import Navbar from '../Navbar/Navbar'
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints'
import { useHistory } from 'react-router-dom'
import '../../css/AdminDashboard.css'
import LineChart from '../Charts/LineChart'
import MajorTable from './MajorTable'
import { HashLoader } from 'react-spinners'
import Footer from '../Footer/Footer'



function AdminDashboard() {
    const [majorData, setMajorData] = useState([])
    const [majorLoading, setMajorLoading] = useState(true)


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
            setMajorLoading(false);
        })
        .catch(err => {
            setMajorData(false);
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
                    { majorLoading && <div className='major-loader'> <HashLoader size={100} color="blue" /> </div> }
                    { !majorLoading && <MajorTable majorData={majorData} /> }
                    
                </div>
            </div>

            <Footer />
        </div>
    )
}

export default AdminDashboard
