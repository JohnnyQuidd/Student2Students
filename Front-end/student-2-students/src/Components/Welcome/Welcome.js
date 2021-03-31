import React, { useState } from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import '../../css/Welcome.css'
import LoginModal from '../Modals/LoginModal'
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints'

function Welcome() {
    const [isModalOpen, setIsModalOpen] = useState(false)

    const fetchStudents = () => {
        axios.get(API_ENDPOINT + '/admin', { withCredentials: true })
            .then(response => {
                console.log(response);
            })
            .catch(err => {
                console.log(err);
            })
    }

    return (
        <div>
            <Navbar />
            <div className="main-section">
                <h1 id="welcome"> Your favorite student platform </h1>
                <div className="auth-section">
                    <h2 id="member" > Become a member today! </h2>
                    <button id="login" onClick={() => setIsModalOpen(true)} > Login </button>
                    <button id="sign-up"
                    onClick={fetchStudents} > Sign Up </button>
                    <LoginModal isModalOpen={isModalOpen} setIsModalOpen={setIsModalOpen} />
                </div>
            </div>
            <Footer />
        </div>
    )
}

export default Welcome