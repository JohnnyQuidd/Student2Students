import React from 'react'
import Navbar from '../Navbar/Navbar'
import Footer from '../Footer/Footer'
import '../../css/Member.css'
import DefaultPhoto from '../../images/profile.svg'

function Member() {
    return (
        <>
            {/* email, firstName, lastName, username, createdAt, image, his/her posts */}
            <Navbar />
            <div className="member-wrapper">
                <div className="member-image">
                    <img id="member-profile" src={DefaultPhoto} />
                </div>
                <div className="member-username">
                        <p className="username-paragraph"> SkinnyPete </p>
                    </div>
                <div className="name-contact-div">          
                    <div className="member-full-name">
                        <p className="last-name-paragraph"> Petar Kovacevic </p>
                    </div>
                    <div className="member-email">
                        <p className="email-paragraph"> Contact: petar.kovacevic0088@gmail.com </p>
                    </div>
                </div>

                <div className="member-since">
                    <p className="member-since-p"> Member since 04.04.1997. </p>
                </div>
                <div className="member-biography">
                    <p> Biography </p>
                    <p className="member-biography-p"> "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)." </p>
                </div>
                <div className="member-address">
                    <p className="member-country-label"> Country & Language: </p>
                    <p className="member-country">Serbia</p>
                    <p className="member-dash"> - </p>
                    <p className="member-language">Serbian (rs)</p>
                </div>
                <div className="member-posts-wrapper">
                    <h1 className="member-posts-heading"> SkinnyPete's activity </h1>
                    <div className="member-posts">
                        {/* TODO: Fetch posts for user */}
                    </div>
                </div>
            </div>
            <Footer />
        </>
    )
}

export default Member
