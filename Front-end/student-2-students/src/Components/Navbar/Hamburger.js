import React, { useEffect, useRef } from 'react'
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints';
import { useHistory } from "react-router-dom";

import '../../css/Hamburger.css'

function Hamburger({role}) {
    const menuIconRef = useRef(null)
    const navBarRef = useRef(null)
    const history = useHistory();


    useEffect(() => {
        menuIconRef.current.addEventListener('click', () => {
            navBarRef.current.classList.toggle('change');
        })
        
    }, [])
    
    const logout = () => {
        axios.get(API_ENDPOINT + '/authorization/logout', { withCredentials: true });
        const currentUrl = window.location.href;
        history.push('/login');
    }

    if(role === 'ADMIN') {
        return(
        <>
            <div className="navbar" ref={navBarRef}>
                <div className="hamburger-menu" ref={menuIconRef} >
                    <div className="line line-1"></div>
                    <div className="line line-2"></div>
                    <div className="line line-3"></div>
                </div>
            
                {/* Actual links */}
                <ul className="nav-list">
                    <li className="nav-item">
                        <a href="/profile" className="nav-link">My profile</a>
                    </li>
                    <li className="nav-item">
                        <a href="/admin/university" className="nav-link">University Management</a>
                    </li>
                    <li className="nav-item">
                        <button id="logout-btn" className="nav-link" onClick={logout} > Logout </button>
                    </li>
                </ul>

            </div>
        </>
        )
    }
    else {
        return(
            <>
                <div className="navbar" ref={navBarRef}>
                    <div className="hamburger-menu" ref={menuIconRef} >
                        <div className="line line-1"></div>
                        <div className="line line-2"></div>
                        <div className="line line-3"></div>
                    </div>
                
                    {/* Actual links */}
                    <ul className="nav-list">
                        <li className="nav-item">
                            <a href="/home" className="nav-link">Home</a>
                        </li>
                        <li className="nav-item">
                            <a href="/profile" className="nav-link">My profile</a>
                        </li>
                        <li className="nav-item">
                            <a href="/post" className="nav-link">New Post</a>
                        </li>
                        <li className="nav-item">
                            <a href="/new-exchange" className="nav-link">New Exchange</a>
                        </li>
                        <li className="nav-item">
                            <button id="logout-btn" className="nav-link" onClick={logout}> Logout </button>
                        </li>
                    </ul>

                </div>
             </>
        )
    }

}

export default Hamburger
