import React, { useState } from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import '../../css/Welcome.css'
import LoginModal from '../Modals/LoginModal'

function Welcome() {
    const [isModalOpen, setIsModalOpen] = useState(false)

    return (
        <div>
            <Navbar />
            <div className="main-section">
                <h1 id="welcome"> Your favorite student platform </h1>
                <div className="auth-section">
                    <h2 id="member" > Become a member today! </h2>
                    <button id="login" onClick={() => setIsModalOpen(true)} > Login </button>
                    <button id="sign-up"> Sign Up </button>
                    <LoginModal isModalOpen={isModalOpen} setIsModalOpen={setIsModalOpen} />
                </div>
            </div>
            <Footer />
        </div>
    )
}

export default Welcome