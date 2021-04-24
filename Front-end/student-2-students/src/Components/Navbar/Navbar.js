import React from 'react'
import '../../css/Navbar.css'
import { FRONT_END } from '../Constants/Endpoints';
import Hamburger from './Hamburger';
import LOGO from '../../images/logo.png';

function Navbar({role}) {
    
    const currentUrl = window.location.href
    console.log();

    return (
        <div className="container">
            <div id="heading">
                <a id="link" href="/"> <img id="logo" src={LOGO} alt="logo" /> </a>
            </div>
            {
               currentUrl !== FRONT_END &&
                <>
                    <Hamburger role={role} />
                </>
            }
        </div>
    )
}

export default Navbar
