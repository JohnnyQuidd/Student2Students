import React, { useState, useEffect } from 'react'
import '../../css/NotFound.css'
import Navbar from '../Navbar/Navbar'
import { useHistory } from "react-router-dom";
import { FaRobot } from 'react-icons/fa'

function NotFound() {
    const [counter, setCounter] = useState(5)
    const history = useHistory();
    let i = 5;
    useEffect(() => {
        const interval = setInterval(function(){
            setCounter(prev => prev - 1)            
            
            // NOTE: This is a simple hack because counter variable is not updating inside interval
            // This should be improved in further commits
            // eslint-disable-next-line
            i = i-1;
            if(i<= 0) {
                console.log(`Clearing interval: i ${i}`);
                clearInterval(interval);
                history.push('/');
            }

            console.log('Counting');
            
        }, 1000);       
        
    }, [])

    return (
        <div id="main-warning">
            <Navbar />
            <h1 id="warning"> Error: 404 Page not found </h1>
            <span> <FaRobot size="5rem" color="#46473E" /> </span>
            <h3 id="redirecting-h3"> Redirecting in {counter} seconds </h3>
        </div>
    )
}

export default NotFound
