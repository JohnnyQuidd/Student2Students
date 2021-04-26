import React, { useState, useEffect } from 'react'
import '../../css/Post.css'

function Post({post, handleCommentClick, role}) {
    const [buttonDetails, setButtonDetails] = useState("");
    const [buttonText, setButtonText] = useState("Show details");
    const [detailsState, setDetailsState] = useState("post-header hide");
    const [topicState, setTopicState] = useState("topic-section hide");

    useEffect(() => {
        detailsState === 'post-header' ? setButtonText('Hide details') : setButtonText('Show details');
    }, [detailsState]);

    useEffect(() => {
        if(window.innerWidth > 700) {
            setButtonDetails("post-details hide");
            setDetailsState("post-header");
            setTopicState("topic-section");
        } else {
            setButtonDetails("post-details");
            setDetailsState("post-header hide");
            setTopicState("topic-section hide");
        }
    }, [window])

    const toggleDetailsState = () => {
        detailsState === 'post-header' ? setDetailsState('post-header hide') : setDetailsState('post-header');
        topicState === 'topic-section' ? setTopicState('topic-section hide') : setTopicState('topic-section');
    }

    const formatDate = (date) => {
        const dateFormat = Date.parse(date);
        const newDate = new Date(dateFormat);
        return newDate.getDate() + '.' + (newDate.getMonth() + 1) + '.' + newDate.getFullYear() + '.';
    }

    return (
        <div className="post">
            <button className={buttonDetails} onClick={toggleDetailsState}> {buttonText} </button>
            <div className={detailsState}>
                <div className="post-major">
                    <a href="#"> {post.majorName} </a>
                </div>
                <div className="post-author">
                    <p> Author: <a> {post.username} </a> </p>
                </div>
                <div className="post-date">
                    <p className="date-paragraph"> {formatDate(post.createdAt)} </p>
                </div>
            </div>
            <div className={topicState}>
                <div className="topic-wrapper">
                    <ul className="ul-class">
                        {post.topics.map(topic => <li key={topic} className="li-class"> {topic} </li> )}     
                    </ul>
                </div>
            </div>
            <div className="post-body-section">
                <div className="post-title">
                    <h3 className="post-title-h2"> <a className="post-link" href={`/post/${post.headline}`}> {post.headline} </a> </h3>
                </div>
                <div className="post-body">
                    <p className="post-body-paragraph"> {post.body} </p> </div>
            </div>
            {
                (role === 'STUDENT' || role === 'ADMIN') &&
                
                <div className="post-footer">
                <div className="post-comment">
                    <button className="comment-button" onClick={() => handleCommentClick(post.id)}> Comment </button>
                </div>
                <div className="post-mark">
                    <button className="rate-button"> Rate post </button>
                </div>
                <div className="post-rating">
                    <p className="current-rate"> 8.25/10 </p>
                </div>
            </div>
            }
        </div>
    )
}

export default Post
