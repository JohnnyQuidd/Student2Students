import React, { useState } from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import '../../css/Welcome.css'
import LoginModal from '../Modals/LoginModal'
// import axios from 'axios'
// import { API_ENDPOINT } from '../Constants/Endpoints'
import SignUpModal from '../Modals/SignUpModal'

function Welcome() {
    const [isLoginModalOpen, setIsLoginModalOpen] = useState(false)
    const [isSignUpModalOpen, setIsSignUpModalOpen] = useState(false)

    // const fetchStudents = () => {
    //     axios.get(API_ENDPOINT + '/student', { withCredentials: true })
    //         .then(response => {
    //             console.log(response);
    //         })
    //         .catch(err => {
    //             console.log(err);
    //         })
    // }

    return (
        <div>
            <Navbar />
            <div className="main-section">
                <h1 id="welcome"> Your favorite student platform </h1>
                <div className="auth-section">
                    <h2 id="member" > Become a member today! </h2>
                    <button id="login" onClick={() => setIsLoginModalOpen(true)} > Login </button>
                    <button id="sign-up"
                    onClick={() => setIsSignUpModalOpen(true)} > Sign Up </button>
                    <LoginModal isModalOpen={isLoginModalOpen} setIsModalOpen={setIsLoginModalOpen} />
                    <SignUpModal isModalOpen={isSignUpModalOpen} setIsModalOpen={setIsSignUpModalOpen} />
                </div>
            </div>
            <Footer />
        </div>
    )
}

export default Welcome