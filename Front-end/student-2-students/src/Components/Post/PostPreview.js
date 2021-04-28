import React, {useEffect, useState} from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import axios from 'axios'
import '../../css/PostPreview.css'
import { API_ENDPOINT } from '../Constants/Endpoints'
import Post from './Post'
import CommentModal from '../Modals/CommentModal'
import { useHistory } from "react-router-dom";
import Comment from '../Comment/Comment'

function PostPreview() {
    const history = useHistory();

    const [title, setTitle] = useState("");
    const [role, setRole] = useState("");
    const [post, setPost] = useState("");
    const [comments, setComments] = useState("");
    const [isCommentModalOpen, setIsCommentModalOpen] = useState(false);
    const [postId, setPostId] = useState(0);

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

    // Fetching post by title
    useEffect(() => {
        const url = window.location.href.split('/');
        setTitle(url[url.length-1].replace(/%20/g, ' '));
        
        if(title) {
            axios({
                url: API_ENDPOINT + '/posting/post/' + title,
                method: 'GET'
            })
            .then(response => response.data)
            .then(data => {
                setPost(data);
            }).catch(() => {
                console.log('Can\'t fetch ' + title);
                history.push('/');
            });
        }

    }, [title])

    // Fetching comments for post
    useEffect(() => {
        if(title) {
            axios({
                url: API_ENDPOINT + '/posting/comment/' + title,
                method: 'GET'
            })
            .then(response => response.data)
            .then(data => {
                console.log(data);
                setComments(data);
            })
            .catch(() => {
                console.log('Unable to fetch comments');
            });
        }
    }, [title])
    
    const handleCommentClick = currentPostId => {
        setIsCommentModalOpen(true);
        setPostId(currentPostId);
    }

    return (
        <>
          <Navbar role={role} />
          <div className="post-preview-wrapper">
            { post && <Post post={post} handleCommentClick={handleCommentClick} role={role} />}
            <div className="hr-separator"> <hr/> </div>
            <div className="comment-list-wrapper">
                { comments && comments.map(comment => <Comment comment={comment} />) }
            </div>
          </div>

          <CommentModal isModalOpen={isCommentModalOpen}
                        setIsModalOpen={setIsCommentModalOpen}
                        postId={postId} />
          <Footer />  
        </>
    )
}

export default PostPreview