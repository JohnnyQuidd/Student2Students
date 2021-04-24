import React, { useState, useEffect } from 'react'
import { API_ENDPOINT } from '../Constants/Endpoints';
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import axios from 'axios'
import '../../css/NewPost.css'
import Select from 'react-select'
import makeAnimated from 'react-select/animated';
import { toast } from 'react-toastify';

toast.configure()
function NewPost() {
    const animatedComponents = makeAnimated();

    const [role, setRole] = useState("");
    const [headline, setHeadline] = useState("");
    const [body, setBody] = useState("");
    const [majorName, setMajorName] = useState("");
    const [topics, setTopics] = useState([]);

    const [majorSelection, setMajorSelection] = useState([]);
    const [topicSelection, setTopicSelection] = useState([]);

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

    useEffect(() => {
        axios({
            url: API_ENDPOINT + '/manage/major',
            method: 'GET'
        }).then(response => response.data)
            .then(response => response.map(data => {
                {
                    return ({
                        label: data.majorName,
                        value: data.majorName
                    })
                }
            }))
            .then((data) => {
                setMajorSelection(data);
            })
            .catch(() => {
                console.log('Error fetching majors');
            })
    }, []);

    useEffect(() => {
        axios({
            url: API_ENDPOINT + '/manage/topic',
            method: 'GET'
        }).then(response => response.data)
            .then(response => response.map(data => {
                {
                    return ({
                        label: data.topicName,
                        value: data.topicName
                    })
                }
            }))
            .then((data) => {
                setTopicSelection(data);
            })
            .catch((err) => {
                console.log(err);
            })
        
    }, []);

    const selectedMajorHandler = event => {
        if(event !== null) {
            setMajorName(event.value);
        }
    }

    const selectedTopicHandler = event => {
        if(event !== undefined) {
            let index = topics.length;
            if(index > 0 && event[index] !== undefined) {
                setTopics(prevState => 
                    [...prevState, event[index].value]
                );
            } else {
                if(event[0] !== undefined)
                    setTopics([event[0].value]);
            }

        }
    }

    const submitPost = () => {
        const payload = { headline, body, majorName, topics };
        axios({
            url: API_ENDPOINT + '/posting/post',
            method: 'POST',
            data: payload,
            withCredentials: true
        }).then(() => {
            toast.success('Success!', { position: toast.POSITION.BOTTOM_RIGHT, autoClose: 3000 });
        }).catch(() => {
            toast.error('Service temporary unavailable', { position: toast.POSITION.BOTTOM_RIGHT, autoClose: false });
        });
    }

    return (
        <>
            <Navbar role={role}/>
            <div className="new-post">
                <div className="new-post-title">
                    <h1> Create new post </h1>
                </div>
                <div className="new-post-wrapper">
                <div className="new-post-form">
                    <label className="new-post-label"> Title </label>
                    <input value={headline}
                        className="new-post-input"
                        onChange={e => setHeadline(e.target.value)} />
                </div>
                <div className="new-post-form">
                    <label className="new-post-label"> Body </label>
                    <textarea value={body}
                        className="new-post-text-area"
                        onChange={e => setBody(e.target.value)} />
                </div>
                <div className="new-post-form">
                    <label className="new-post-label" > Major </label>
                    <Select
                        closeMenuOnSelect={true}
                        className="basic-single"
                        classNamePrefix="select"
                        defaultValue={majorName}
                        isClearable={true}
                        isSearchable={true}
                        name="major"
                        isMulti={false}
                        options={majorSelection}
                        onChange={selectedMajorHandler}
                        className="new-post-select"
                        />
                </div>
                <div className="new-post-form">
                    <label className="new-post-label"> Keywords </label>
                    <Select
                        closeMenuOnSelect={true}
                        className="basic-single"
                        classNamePrefix="select"
                        components={animatedComponents}
                        defaultValue={topics}
                        isClearable={true}
                        isSearchable={true}
                        name="topic"
                        isMulti={true}
                        options={topicSelection}
                        onChange={selectedTopicHandler}
                        className="new-post-select"
                        />
                </div>
                </div>
                <button className="new-post-submit" onClick={submitPost}> Submit post </button>
            </div>
            <Footer />  
        </>
    )
}

export default NewPost
