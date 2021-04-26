import React, { useState, useEffect } from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import Post from '../Post/Post'
import '../../css/HomePage.css'
import PostFilter from '../Post/PostFilter'
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints'
import CommentModal from '../Modals/CommentModal'

function HomePage({props}) {
    const [role, setRole] = useState("");
    const [majors, setMajors] = useState([]);
    const [selectedMajor, setSelectedMajor] = useState("");
    const [topics, setTopics] = useState([]);
    const [selectedTopics, setSelectedTopics] = useState([]);
    const [posts, setPosts] = useState([]);
    const [isCommentModalOpen, setIsCommentModalOpen] = useState(false);
    const [postId, setPostId] = useState(0);

    // Fetching posts
    useEffect(() => {
        axios({
            url: API_ENDPOINT + '/posting/post',
            method: 'GET'
        })
        .then(response => response.data)
        .then(data => {
            setPosts(data);
        })
        .catch(() => {
            console.log('Unable to fetch posts');
        })
    }, [])


    // Check if logged in user is student
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
        const payload = {majorName: selectedMajor, topics: selectedTopics};
        let urlEndpoint = API_ENDPOINT + '/posting/post/filter' +'?majorName=' + selectedMajor + "&topics=";

        for(let i=0; i<selectedTopics.length; i++) {
            if(i<selectedTopics.length-1){
                urlEndpoint += selectedTopics[i] + ',';
            } else {
                urlEndpoint += selectedTopics[i];
            }
        }

        console.log(urlEndpoint);

        axios({
            url: urlEndpoint,
            method: 'GET',
            data: payload
        })
        .then(response => response.data)
        .then(response => {
            console.log(response);
            setPosts(response);
        }).catch(() => {
            console.log('Unable to filter now');
        });

    }

    const handleCommentClick = currentPostId => {
        setIsCommentModalOpen(true);
        setPostId(currentPostId);
    }

    return (
        <>
            <Navbar role={role} />
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
                    {
                        posts.map(post => <Post post={post} handleCommentClick={handleCommentClick} role={role} />)
                    }
                </div>
            </div>

            <CommentModal isModalOpen={isCommentModalOpen}
                        setIsModalOpen={setIsCommentModalOpen}
                        postId={postId} />
            <Footer />  
        </>
    )
}

export default HomePage
