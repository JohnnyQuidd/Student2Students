import React, { useState, useEffect } from 'react'
import Navbar from '../Navbar/Navbar'
import Footer from '../Footer/Footer'
import '../../css/Member.css'
import DefaultPhoto from '../../images/profile.svg'
import axios from 'axios'
import { useHistory } from "react-router-dom";
import { API_ENDPOINT } from '../Constants/Endpoints'
import Post from '../Post/Post'

function Member() {
    const history = useHistory();
    const [role, setRole] = useState("");
    const [username, setUsername] = useState("");
    const [student, setStudent] = useState({});
    const [imgSrc, setImgSrc] = useState(DefaultPhoto);
    const [posts, setPosts] = useState([]);

    // Setting role
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

    // Fetching User by username
    useEffect(() => {
        const url = window.location.href.split('/');
        setUsername(url[url.length-1].replace(/%20/g, ' '));
        
        if(username) {
            // TODO: Fetch user by username
            axios({
                url: API_ENDPOINT + '/manage/student/data/' + username,
                method: 'GET'
            })
            .then(response => response.data)
            .then(data => {
                console.log(data);
                setStudent(data);
            })
            .catch(() => {
                history.push('/');
            });
            console.log(username);
        }

    }, [username])

    // Fetching profile photo
    useEffect(() => {
        if(username) {
            axios({
                url: API_ENDPOINT + '/image/image-service/profile-image/' + username,
                method: 'GET',
              }).then(response => {
                setImgSrc(response.data);
              }).catch(err => {
                console.log('Couldn\'t fetch photo');
              });
        }
      }, [username]);

    // Fetching posts
    useEffect(() => {
        if(username) {
            axios({
                url: API_ENDPOINT + '/posting/post/student/' + username,
                method: 'GET'
            })
            .then(response => response.data)
            .then(data => {
                setPosts(data);
            })
            .catch(() => {
                console.log('Couldn\'t fetch posts');
            })
        }
    }, [username])

    const formatDate = (date) => {
        const dateFormat = Date.parse(date);
        const newDate = new Date(dateFormat);
        return newDate.getDate() + '.' + (newDate.getMonth() + 1) + '.' + newDate.getFullYear() + '.';
    }

    return (
        <>
            {/* email, firstName, lastName, username, createdAt, image, his/her posts */}
            <Navbar />
            <div className="member-wrapper">
                <div className="member-image">
                    <img id="member-profile" src={imgSrc} />
                </div>
                <div className="member-username">
                        <p className="username-paragraph"> {student.username} </p>
                    </div>
                <div className="name-contact-div">          
                    <div className="member-full-name">
                        <p className="last-name-paragraph"> {student.firstName} {student.lastName} </p>
                    </div>
                    <div className="member-email">
                        <p className="email-paragraph"> Contact: {student.email} </p>
                    </div>
                </div>

                <div className="member-since">
                    <p className="member-since-p"> Member since {formatDate(student.createdAt)} </p>
                </div>
                <div className="member-biography">
                    <p> Biography </p>
                    <p className="member-biography-p"> "{student.biography}" </p>
                </div>
                <div className="member-address">
                    <p className="member-country-label"> Country & Language: </p>
                    <p className="member-country">{student.country}</p>
                    <p className="member-dash"> - </p>
                    <p className="member-language">{student.language}</p>
                </div>
                <div className="member-posts-wrapper">
                    <h1 className="member-posts-heading"> {student.username}'s activity </h1>
                    <div className="member-posts">
                        {
                            posts.map(post => { return <Post post={post} role={role} /> })
                        }
                    </div>
                </div>
            </div>
            <Footer />
        </>
    )
}

export default Member
