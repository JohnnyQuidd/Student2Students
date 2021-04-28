import React, { useState } from 'react'
import Modal from 'react-modal'
import Picker from 'emoji-picker-react'
import '../../css/CommentModal.css'
import axios, { useEffect } from 'axios';
import { API_ENDPOINT } from '../Constants/Endpoints';
import { toast } from 'react-toastify'

toast.configure()
function CommentModal({isModalOpen, setIsModalOpen, postId}) {
    const [body, setBody] = useState("");
    const [emojiPicker, setEmojiPicker] = useState("emoji-picker hide");
    const [chosenEmoji, setChosenEmoji] = useState(null);


    const style = {
        overlay: {
            background: 'rgba(44, 42, 42, 0.95)'
        },
        content: {
            top : '30%',
            left : '50%',
            right : 'auto',
            bottom : 'auto',
            marginRight : '-50%',
            transform : 'translate(-50%, -50%)'
        }
    }

    const submitData = event => {
        event.preventDefault();
        const payload = {postId, body};
        
        axios({
            url: API_ENDPOINT + '/posting/comment',
            method: 'POST',
            data: payload,
            withCredentials: true
        })
        .then(() => {
            setBody("");
            setIsModalOpen(false);
            toast.success('Comment sent successfully!', { position: toast.POSITION.BOTTOM_RIGHT, autoClose: 3000 });
        })
        .catch(() => {
            toast.error('Service temporary unavailable!', { position: toast.POSITION.BOTTOM_RIGHT, autoClose: false });
        });
    }

    const onEmojiClick = (event, emojiObject) => {
        setChosenEmoji(emojiObject);
        let text = body;
        text = text + emojiObject.emoji;
        setBody(text);
      };

    const toggleEmojiPicker = () => {
        emojiPicker === 'emoji-picker hide' ? setEmojiPicker('emoji-picker') : setEmojiPicker('emoji-picker hide');
    }

    return (
        <>
        <Modal isOpen={isModalOpen}
            ariaHideApp={false}
            onRequestClose={() => setIsModalOpen(false)}
            style={style}
            closeTimeoutMS={1000}
            >
                <div className="new-comment-wrapper">
                    <h1 id="comment-heading"> Comment </h1>
                    <form onSubmit={submitData} >
                        <textarea id="comment-text-area"
                        value={body}
                        placeholder="Please remember to be nice to others..."
                        onChange={e => setBody(e.target.value) } />

                        <div id="emoji-div">
                            <button type="button" onClick={toggleEmojiPicker} id="comment-emoji-button"> Add emoji </button>
                        </div>
                        <div className={emojiPicker}>
                            <Picker onEmojiClick={onEmojiClick} />
                        </div>

                        <div className="buttons">
                            <button onClick={() => setIsModalOpen(false)}
                            type="submit" id="comment-submit" > Send comment </button>
                        </div>
                    </form>
                </div>
            </Modal>
        </>
    )
}

export default CommentModal
