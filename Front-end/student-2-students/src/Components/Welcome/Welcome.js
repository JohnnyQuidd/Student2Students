import React, { useState, useEffect } from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import '../../css/Welcome.css'
import LoginModal from '../Modals/LoginModal'
// import axios from 'axios'
// import { API_ENDPOINT } from '../Constants/Endpoints'
import SignUpModal from '../Modals/SignUpModal'
import CountUp from 'react-countup'
import { HashLoader } from 'react-spinners'


function Welcome() {
    const [loading, setLoading] = useState(false)
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


    if(loading) {
        return(
            <>
                <div id="spinner-div">
                    <HashLoader loading={loading} size={100} color="blue" />
                </div>
                <div className="loading">
                    <Navbar />
                    <div className="main-section">
                        
                    </div>

                    <Footer />
                </div>
            </>
        )
    } else {
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
                        <LoginModal isModalOpen={isLoginModalOpen}
                        setIsModalOpen={setIsLoginModalOpen}
                        setLoading={setLoading} />
                        <SignUpModal isModalOpen={isSignUpModalOpen} setIsModalOpen={setIsSignUpModalOpen} />
                    </div>
    
                   <div className="stats">
                        <div className="counter-div">
                            <h3> <CountUp end={500} duration={7}  /> + </h3>
                            <h3> Students </h3>
                        </div>
                        <div className="counter-div" id="middle-div" >
                            <h3> <CountUp end={50} duration={7} /> + </h3>
                            <h3> Universities </h3>
                        </div>
                        <div className="counter-div">
                            <h3> <CountUp end={20} duration={7}  /> + </h3>
                            <h3> Countries </h3>
                        </div>
    
                        {/* <HashLoader loading color="white" /> */}
                        {/* <PropagateLoader loading={true} color="white" /> */}
                        {/* <SyncLoader loading={true} color="white" /> */}
                   </div>
                </div>
    
                <Footer />
            </div>
        )
    }
}

export default Welcome