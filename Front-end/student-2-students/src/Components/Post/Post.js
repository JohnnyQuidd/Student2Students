import React, { useState, useEffect } from 'react'
import '../../css/Post.css'

function Post({post}) {
    const [buttonText, setButtonText] = useState("Show details");
    const [detailsState, setDetailsState] = useState("post-header hide");

    useEffect(() => {
        detailsState === 'post-header' ? setButtonText('Hide details') : setButtonText('Show details');
    }, [detailsState]);

    const toggleDetailsState = () => {
        detailsState === 'post-header' ? setDetailsState('post-header hide') : setDetailsState('post-header');
    }

    return (
        <div className="post">
            <button className="post-details" onClick={toggleDetailsState}> {buttonText} </button>
            <div className={detailsState}>
                <div className="post-major">
                    <a href="#"> Computer Science </a>
                </div>
                <div className="post-author">
                    <p> Author: <a> SkinnyPete </a> </p>
                </div>
                <div className="post-date">
                    <p className="date-paragraph"> 04/04/2021 </p>
                </div>
            </div>
            <div className="post-body-section">
                <div className="post-title">
                    <h2 className="post-title-h2"> Configuring Spring Boot </h2>
                </div>
                <div className="post-body">
                    <p className="post-body-paragraph"> Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.

The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "de Finibus Bonorum et Malorum" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham. </p>
                </div>
            </div>
            <div className="post-footer">
                <div className="post-comment">
                    <button className="comment-button"> Comment </button>
                </div>
                <div className="post-mark">
                    <button className="rate-button"> Rate post </button>
                </div>
                <div className="post-rating">
                    <p className="current-rate"> 8.25/10 </p>
                </div>
            </div>
        </div>
    )
}

export default Post
