import React, { useState } from 'react'
import Modal from 'react-modal'
import '../../css/CommentModal.css'

function CommentModal({isModalOpen, setIsModalOpen, postId}) {
    const [body, setBody] = useState("");

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

    const cancelForm = (e) => {
        e.preventDefault();
        setIsModalOpen(false);
    }

    const submitData = event => {
        event.preventDefault();
        let payload = {postId, body};
        console.log(payload);
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
