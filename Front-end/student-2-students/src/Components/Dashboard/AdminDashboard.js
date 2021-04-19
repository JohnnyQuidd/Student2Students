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
import { toast } from 'react-toastify'


toast.configure()
function AdminDashboard() {
    const [majorData, setMajorData] = useState([]);
    const [majorLoading, setMajorLoading] = useState(true);
    const [newMajorName, setNewMajorName] = useState("")


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
        fetchMajors();
    }, [])

    const fetchMajors = () => {
        axios.get(API_ENDPOINT + '/manage/major')
        .then(response => response.data)
        .then(response => {
            setMajorData(response.map(major => { return {id: response.indexOf(major)+1, majorName: major.majorName} }));
            setMajorLoading(false);
        })
        .catch(err => {
            setMajorData(false);
        })
    }

    const majorHandler = (event) => {
        setNewMajorName(event.target.value);
    }

    const submitMajor = () => {
        axios({
            url: API_ENDPOINT + '/manage/major',
            method: 'post',
            data: {majorName : newMajorName},
            withCredentials: true
        }).then(() => {
            toast.success(`Major ${newMajorName} posted successfully`, {position: toast.POSITION.BOTTOM_RIGHT, autoClose: 5000});
            fetchMajors();
            setNewMajorName("");
        }).catch(err => {
            toast.error(`Major ${newMajorName} already exists`, {position: toast.POSITION.BOTTOM_RIGHT, autoClose: false});
        })
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

                <div id="add-major">
                    <div id="major-fields">
                        <label id="label-major"> Add new Major </label>
                        <input id="input-major"
                            type="text"
                            placeholder="I.e. Statistics"
                            value={newMajorName}
                            onChange={majorHandler} />
                        </div>
                    <button id="add-major-button" onClick={submitMajor}> Add Major </button>
                </div>
            </div>

            <Footer />
        </div>
    )
}

export default AdminDashboard
