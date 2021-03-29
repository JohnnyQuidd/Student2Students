import React from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import '../../css/Welcome.css'

function Welcome() {
    return (
        <div>
            <Navbar />
            <div className="main-section">
                <h1 id="welcome"> Your favorite student platform </h1>
                <div className="auth-section">
                    <h2 id="member" > Become a member today! </h2>
                    <button id="login"> Login </button>
                    <button id="sign-up"> Sign Up </button>
                </div>
            </div>
            <Footer />
        </div>
    )
}

export default Welcome