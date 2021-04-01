import React, { useState, useEffect } from 'react'
import axios from 'axios'
import '../../css/Navbar.css'
import Hamburger from './Hamburger'
import { API_ENDPOINT } from '../Constants/Endpoints'

function Navbar() {
    const [loggedIn, setLoggedIn] = useState(true)

    useEffect(() => {
        // TODO: Hit relevant API to see if user is logged in
        axios.get(API_ENDPOINT + '/students')
            .then(() => {
                setLoggedIn(true)
            })
            .catch(() => {
                // TODO: Set this to false to authenticate
                setLoggedIn(true)
            })
    })

    return (
        <div className="container">
            <div id="heading">
                <h1 className="brand" id="student">Student</h1>
                <h1 className="brand" id="to" >2</h1>
                <h1 className="brand" id="studens">Students</h1>
            </div>
            {
                loggedIn && 
                <>
                    <Hamburger/>
                </>
            }
        </div>
    )
}

export default Navbar
