import React, {useEffect, useState} from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import axios from 'axios'
import '../../css/PostPreview.css'
import { API_ENDPOINT } from '../Constants/Endpoints'

function PostPreview() {
    const [role, setRole] = useState("");

    useEffect(() => {
        axios.get(API_ENDPOINT + '/authorization/student', { withCredentials: true })
            .then(() => {
                setRole("STUDENT");
            });
    }, [])

    useEffect(() => {
        axios.get(API_ENDPOINT + '/authorization/admin', { withCredentials: true })
            .then(() => {
                setRole("ADMIN");
            });
    }, [])

    // Fetching title
    useEffect(() => {
        const url = window.location.href.split('/');
        const title = url[url.length-1];
        console.log(title);
    }, [])
    
    return (
        <>
          <Navbar role={role} />
          <div className="post-preview-wrapper">
            
          </div>
          <Footer />  
        </>
    )
}

export default PostPreview
