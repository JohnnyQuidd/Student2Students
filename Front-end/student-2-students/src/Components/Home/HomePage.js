import React from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import Post from '../Post/Post'
import '../../css/HomePage.css'

function HomePage() {
    return (
        <>
            <Navbar />
            <div className="homepage-main-section">
                <div className="post-section">
                    <Post post={""} />
                    <Post post={""} />
                    <Post post={""} />
                </div>
            </div>
            <Footer />  
        </>
    )
}

export default HomePage
