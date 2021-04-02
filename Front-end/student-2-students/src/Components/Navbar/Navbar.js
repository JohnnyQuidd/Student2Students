import React from 'react'
import '../../css/Navbar.css'
import { FRONT_END } from '../Constants/Endpoints';
import Hamburger from './Hamburger'

function Navbar() {
    
    const currentUrl = window.location.href
    console.log();

    return (
        <div className="container">
            <div id="heading">
                <a id="heading-href" href="/">
                    <h1 className="brand" id="student">Student</h1>
                    <h1 className="brand" id="to" >2</h1>
                    <h1 className="brand" id="studens">Students</h1>
                </a>
            </div>
            {
               currentUrl !== FRONT_END &&
                <>
                    <Hamburger/>
                </>
            }
        </div>
    )
}

export default Navbar
