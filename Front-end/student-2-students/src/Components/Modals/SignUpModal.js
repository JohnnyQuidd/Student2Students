import React, { useState, useEffect } from 'react'
import axios from 'axios'
import Modal from 'react-modal'
import '../../css/SignUpModal.css'
import { API_ENDPOINT as API, API_ENDPOINT }  from '../Constants/Endpoints'
import 'react-toastify/dist/ReactToastify.css'
import Select from 'react-select'
import { toast } from 'react-toastify'

toast.configure()
function SignUpModal({isModalOpen, setIsModalOpen}) {

    const [username, setUsername] = useState('')
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [countries, setCountries] = useState('')
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

    // useEffect(() => {
    //     axios.get(API_ENDPOINT + '/manage/country')
    //         .then(reponse => reponse.data)
    //         .then(response => {
    //             response.sort((a,b) => (a.country > b.country) ? 1 : ((b.country > a.country) ? -1 : 0));
    //             setCountries(response.map(current => {
    //                  return({ value: current.country, label: current.country })
    //             }))
    //         })
    //         .catch(() => {
    //             console.log('Error fetching countries');
    //         })
    // }, [])


    const cancelForm = (e) => {
        e.preventDefault();
        setIsModalOpen(false);
    }

    const selectCountryHandler = (event) => {
        if(event !== null) {
            setCountry(event.target);
        }
    }

    const submitData = (e) => {
        e.preventDefault();
        


        if(!isDataValid()) {
            toast.error(`All fields have to be populated!`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: false });
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

        axios.post(API_ENDPOINT + '/manage/registration/student', payload, { withCredentials: true })
            .then(response => {           
                toast.success('Successfull login', { position: toast.POSITION.BOTTOM_RIGHT, autoClose: 3000 });
                console.log(response);
            })
            .catch(err => {
                console.log(err);
            })
    }

    const isDataValid = () => {
        if(password !== passwordConfirm) {
            toast.error(`Passwords are not matching!`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: false });
            return false;
        }

        return username !== '' && password !== '' && firstName !== '' && lastName !== ''
            && country !== '' && city !== '' && email !== '';
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
                    <Select
                                closeMenuOnSelect={true}
                                className="basic-single"
                                classNamePrefix="select"
                                defaultValue={country}
                                isClearable={true}
                                isSearchable={true}
                                name="country"
                                isMulti={false}
                                options={countries}
                                onChange={selectCountryHandler}
                                />
                    
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
