import React from 'react'
import '../../css/Navbar.css'
import { FRONT_END } from '../Constants/Endpoints';
import Hamburger from './Hamburger';
import LOGO from '../../images/logo.png';

function Navbar({role}) {
    
    const currentUrl = window.location.href;

    if(currentUrl !== FRONT_END + '/login' && (role === 'ADMIN' || role === 'STUDENT')) {
        return (
            <div className="container">
                <div id="heading">
                    <a id="link" href="/"> <img id="logo" src={LOGO} alt="logo" /> </a>
                </div>
                <>
                    <Hamburger role={role} />
                </>       
            </div>
        )
    }
    else {
        return(
            <div className="container">
                <div id="heading">
                    <a id="link" href="/"> <img id="logo" src={LOGO} alt="logo" /> </a>
                </div>
                { currentUrl !== FRONT_END + '/login' &&  <div className="join-us">
                        <a id="link" href="/login"> Join Us </a>
                    </div>}
            </div>
        )
    }


}

export default Navbar
