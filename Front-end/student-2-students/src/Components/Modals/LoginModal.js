import React, { useState } from 'react'
import axios from 'axios'
import Modal from 'react-modal'
import '../../css/LoginModal.css'
import { API_ENDPOINT as API }  from '../Constants/Endpoints'

function LoginModal({isModalOpen, setIsModalOpen}) {
    const [username, setUsername] = useState('')
    const [paassword, setPaassword] = useState('')

    
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

    const submitData = (e) => {
        e.preventDefault();
        let payload = {
            username: username,
            password: paassword
        }
        axios.post(API + '/login', payload, { withCredentials: true })
            .then(response => {
                console.log('Successful login');
            })
            .catch(err => {
                console.log(`Error logging in: ${err}`);
            })
    }


    return (
        <>
            <Modal isOpen={isModalOpen}
            ariaHideApp={false}
            onRequestClose={() => setIsModalOpen(false)}
            style={style}
            closeTimeoutMS={1000}
             >
                <h2 id="login-heading"> Login </h2>
                <form onSubmit={submitData} >
                    <label htmlFor="username"> Username </label>
                    <input type="text" id="usename"
                        value={username}
                        onChange={e => setUsername(e.target.value) } />

                    <label htmlFor="password"> Password </label>
                    <input type="password" id="password"
                    value={paassword}
                    onChange={e => setPaassword(e.target.value) } />

                    <div className="buttons">
                        <button onClick={() => setIsModalOpen(false)}
                         type="submit" id="confirm-login" > Login </button>
                        <button onClick={cancelForm} id="confirm-cancel"> Cancel </button>
                    </div>
                </form>
            </Modal>
        </>
    )
}

export default LoginModal
