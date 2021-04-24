import React, { useState, useEffect } from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import Post from '../Post/Post'
import '../../css/HomePage.css'
import PostFilter from '../Post/PostFilter'
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints'

function HomePage() {
    // selectedMajor, majors, selectedMajorHandler, selectedTopic, topics, selectTopicHandler, fliterPosts
    const [majors, setMajors] = useState([]);
    const [selectedMajor, setSelectedMajor] = useState("");
    const [topics, setTopics] = useState([]);
    const [selectedTopics, setSelectedTopics] = useState([]);

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
                setMajors(data);
            })
            .catch(() => {
                console.log('Error fetching majors');
            })
    }, []);

    useEffect(() => {
        if(selectedMajor === '') {
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
                    setTopics(data);
                })
                .catch((err) => {
                    console.log(err);
                })
        } else {
            axios({
                url: API_ENDPOINT + '/manage/topic/' + selectedMajor,
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
                    setTopics(data);
                })
                .catch((err) => {
                    console.log(err);
                })
        }
    }, [selectedMajor]);

    const selectedMajorHandler = event => {
        if(event !== null) {
            setSelectedMajor(event.value);
        }
        else
            setSelectedMajor('');
    }

    const selectedTopicHandler = event => {
        if(event !== undefined) {
            console.log(event);
            let index = selectedTopics.length;
            if(index > 0 && event[index] !== undefined) {
                setSelectedTopics(prevState => 
                    [...prevState, event[index].value]
                );
            } else {
                if(event[0] !== undefined)
                    setSelectedTopics([event[0].value]);
            }

        }
    }

    const fliterPosts = () => {
        console.log(selectedMajor);
        console.log(selectedTopics);
    }

    return (
        <>
            <Navbar />
            <div className="homepage-main-section">
                <div className="post-filtering">
                    <PostFilter
                        selectedMajor={selectedMajor}
                        majors={majors}
                        selectedMajorHandler={selectedMajorHandler}
                        selectedTopics={selectedTopics}
                        topics={topics}
                        selectTopicHandler={selectedTopicHandler}
                        fliterPosts={fliterPosts} />
                </div>
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
