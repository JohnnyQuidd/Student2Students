import axios from 'axios';
import React, {useState, useEffect} from 'react'
import '../../css/Authenticate.css'
import { API_ENDPOINT } from '../Constants/Endpoints';
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import {toast} from 'react-toastify'
import { useHistory, useParams } from 'react-router-dom'
import { FaRobot } from 'react-icons/fa'
import { GiMagnifyingGlass } from 'react-icons/gi'


toast.configure()
function Authenticate() {
    const [message, setMessage] = useState("Validating token");
    const history = useHistory();
    const { token } = useParams();


    useEffect(() => {
        setTimeout(() => {
            axios({
                url: API_ENDPOINT + '/registration?token=' + token,
                method: 'GET'
            })
            .then(() => {
                setMessage("Redirecting to Login page");
                toast.success('Successfull registration', { position: toast.POSITION.BOTTOM_RIGHT, autoClose: 2000});
                setTimeout(() => {
                    history.push('/login');
                }, 2000);
            })
            .catch(() => {
                setMessage('Something went wrong');
                toast.error('Service temporary unavailable', { position:  toast.POSITION.BOTTOM_RIGHT, autoClose: false})
            });
        }, 1500);
    }, [])

    return (
        <>
          <Navbar />
            <div className="autheticate-div">
                <h1 className="authenticate-message"> {message} </h1>
                <div className="authenticate-icons">
                    <FaRobot size="5rem" color="#46473E" className="robot" />
                    <GiMagnifyingGlass size="3rem" color="#46473E" className="magnifier" />
                </div>
            </div>
          <Footer />  
        </>
    )
}

export default Authenticate
