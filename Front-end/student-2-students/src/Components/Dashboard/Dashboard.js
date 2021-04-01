import React from 'react'
// import axios from 'axios';
// import { API_ENDPOINT } from '../Constants/Endpoints';
// import { useHistory } from "react-router-dom";
import Navbar from '../Navbar/Navbar'
import '../../css/Dashboard.css'

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

    return (
        <div>
            <Navbar />
            <h1> Dashboard </h1>
        </div>
    )
}

export default Dashboard