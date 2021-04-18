import React, { useState } from 'react'
import axios from 'axios'
import Modal from 'react-modal'
import '../../css/SignUpModal.css'
import { API_ENDPOINT as API }  from '../Constants/Endpoints'
import 'react-toastify/dist/ReactToastify.css'

function SignUpModal({isModalOpen, setIsModalOpen}) {

    const [username, setUsername] = useState('')
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [country, setCountry] = useState('')
    const [city, setCity] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [passwordConfirm, setPasswordConfirm] = useState('')

    
    const style = {
        overlay: {
            background: 'rgba(44, 42, 42, 0.95)'
        },
        content: {
            top : '40%',
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
        
        if(password !== passwordConfirm) {
            alert('Passwords have to be the same!')
            return;
        }
        
        let payload = {
            username: username,
            firstName: firstName,
            lastName: lastName,
            country: country,
            city: city,
            email: email,
            password: password
        }

        axios.post(API + '/student', payload, { withCredentials: true })
            .then(response => {           
                console.log('Successful signing');
                console.log(response);
            })
            .catch(err => {
                console.log(`Error signing up: ${err}`);
            })
    }

    return (
        <div>
            <Modal isOpen={isModalOpen}
            ariaHideApp={false}
            onRequestClose={() => setIsModalOpen(false)}
            style={style}
            closeTimeoutMS={1000}
             >
                <h2 id="sign-up-heading"> Sign Up! </h2>
                <form onSubmit={submitData} >
                    <label htmlFor="username"> Username </label>
                    <input type="text" id="username"
                        value={username}
                        onChange={e => setUsername(e.target.value) } />
                    
                    <label htmlFor="firstName"> First name </label>
                    <input type="text" id="firsName"
                        value={firstName}
                        onChange={e => setFirstName(e.target.value) } />

                    <label htmlFor="username"> Last name </label>
                    <input type="text" id="lastName"
                        value={lastName}
                        onChange={e => setLastName(e.target.value) } />

                    <label htmlFor="country"> Country </label>
                    <input type="text" id="country"
                        value={country}
                        onChange={e => setCountry(e.target.value) } />
                    
                    <label htmlFor="city"> City </label>
                    <input type="text" id="city"
                        value={city}
                        onChange={e => setCity(e.target.value) } />

                    <label htmlFor="email"> Email </label>
                    <input type="email" id="email"
                        value={email}
                        onChange={e => setEmail(e.target.value) } />

                    <label htmlFor="password"> Password </label>
                    <input type="password" id="password"
                    value={password}
                    onChange={e => setPassword(e.target.value) } />

                    <label htmlFor="passwordConfirm"> Confirm password </label>
                    <input type="password" id="passwordConfirm"
                    value={passwordConfirm}
                    onChange={e => setPasswordConfirm(e.target.value) } />


                    <div className="buttons">
                        <button onClick={() => setIsModalOpen(false)}
                         type="submit" id="confirm-sign-up" > Sign up </button>
                        <button onClick={cancelForm} id="confirm-cancel"> Cancel </button>
                    </div>
                </form>
            </Modal>
        </div>
    )
}

export default SignUpModal
