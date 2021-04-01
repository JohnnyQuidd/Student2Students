import React, { useEffect, useRef } from 'react'
import '../../css/Hamburger.css'

function Hamburger() {
    const menuIconRef = useRef(null)
    const navBarRef = useRef(null)

    useEffect(() => {
        console.log('Clicking menuIcon');
        menuIconRef.current.addEventListener('click', () => {
            navBarRef.current.classList.toggle('change');
        })
        
    }, [])

    return (
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
                        <a href="/messages" className="nav-link">Messages</a>
                    </li>
                    <li className="nav-item">
                        <a href="/connections" className="nav-link">Connections</a>
                    </li>
                    <li className="nav-item">
                        <a href="/logout" className="nav-link">Logout</a>
                    </li>
                </ul>

            </div>
        </>
    )
}

export default Hamburger
