import React from 'react'
import '../../css/Footer.css'
import { AiFillGithub } from 'react-icons/ai';
import { SiGmail } from 'react-icons/si';

function Footer() {
    return (
        <div className="footer">
            <h2 id='company'> Copyright <span id="rights">®</span> 2021 Student2Students. All rights reserved </h2>
            <div id="data-section">
                <div id="left-section">
                    <h1 id="address-heading"> Address </h1>
                    <p className="address"> 21000, Novi Sad</p>
                    <p className="address"> Serbia</p>
                </div>
                <div id="right-section">
                    <h1 id="contact-heading"> Contact </h1>
                    <a className="contact" href="https://github.com/JohnnyQuidd"
                        target="_blank">
                        <span className="icon">
                            <AiFillGithub style={{ height: '2.5rem', width: '2.5rem' }} />
                            </span> <span id="data-span"> github@JohnnyQuidd</span></a>
                    <a className="contact" href="mailto:petar.kovacevic0099@gmail.com"> <span className="icon"> <SiGmail style={{height: '1.5rem', width: '1.5rem'}} /> </span>
                         <span id="data-span"> petar.kovacevic0099@gmail.com </span></a>
                </div>
            </div>
        </div>
    )
}

export default Footer
