import React from 'react'
import '../../css/Comment.css'

function Comment({comment}) {
    const formatDate = (date) => {
        const dateFormat = Date.parse(date);
        const newDate = new Date(dateFormat);
        return newDate.getDate() + '.' + (newDate.getMonth() + 1) + '.' + newDate.getFullYear() + '.';
    }
    return (
        <div className="comment-wrapper">
            <div className="comment-body">
                <p className="comment-body-p">" {comment.body} " </p>
            </div>
            <div className="comment-footer">
                <p className="comment-author"> By: {comment.username} </p>
                <p className="comment-date"> {formatDate(comment.createdAt)} </p>
            </div>
        </div>
    )
}

export default Comment
