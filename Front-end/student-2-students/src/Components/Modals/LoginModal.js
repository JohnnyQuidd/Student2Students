import React from 'react'
import Modal from 'react-modal'
import '../../css/LoginModal.css'

function LoginModal({isModalOpen, setIsModalOpen}) {
    
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

    return (
        <>
            <Modal isOpen={isModalOpen}
            ariaHideApp={false}
            onRequestClose={() => setIsModalOpen(false)}
            style={style}
            closeTimeoutMS={1000}
             >
                <h2 id="login-heaading"> Login </h2>
                <form>
                    <label htmlFor="username"> Username </label>
                    <input type="text" id="usename" />
                    <label htmlFor="password"> Password </label>
                    <input type="password" id="password" />
                    <div className="buttons">
                        <button onClick={() => setIsModalOpen(false)} type="submit" id="confirm-login"> Login </button>
                        <button onClick={cancelForm} id="confirm-cancel" > Cancel </button>
                    </div>
                </form>
            </Modal>
        </>
    )
}

export default LoginModal
