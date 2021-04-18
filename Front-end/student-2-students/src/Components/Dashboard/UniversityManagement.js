import React, { useState, useEffect } from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import '../../css/UniversityManagement.css'
import Select from 'react-select'
import makeAnimated from 'react-select/animated';
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints'
import UniversityLogo from '../../images/university-logo.png'
import { toast } from 'react-toastify'

toast.configure()
function UniversityManagement() {

    const [image, setImage] = useState({})
    const [imgSrc, setImgSrc] = useState(UniversityLogo)
    const [countries, setCountries] = useState([])

    const [universityName, setUniversityName] = useState("")
    const [universityEmail, setUniversityEmail] = useState("")
    const [selectedCountry, setSelectedCoontry] = useState("")
    const [city, setCity] = useState("")
    const [street, setStreet] = useState("")
    const [streetNumber, setStreetNumber] = useState("")

    const [imageStatus, setImageStatus] = useState(false)
    const [universityStatus, setUniversityStatus] = useState(false)

    const animatedComponents = makeAnimated();


    useEffect(() => {
        axios.get(API_ENDPOINT + '/manage/country')
            .then(reponse => reponse.data)
            .then(response => {
                setCountries(response.map(country => {
                     return({ value: country.country, label: country.country })
                }))
            })
            .catch(err => {
                console.log('Error fetching countries');
            })
    }, [])



    const fileSelectedHandler = event => {
        setImage(event.target.files[0]);
        
        const reader = new FileReader();
        reader.readAsDataURL(event.target.files[0]);

        reader.onload = () => {
            if(reader.readyState === 2) {
                setImgSrc(reader.result);
            }
        }
    }

    const submitData = (event) => {
        event.preventDefault();
        let body = new FormData();
        body.append('image', image);

        axios({
            url: API_ENDPOINT + '/image/image-service/university/' + universityName,
            method: "post",
            data: body
        }).then(response => {
            console.log(`Image posted successfully!`);
            setImageStatus(true);
        }).catch(err => {
            setImageStatus(false);
        });

        const payload = {
            universityName, universityEmail, country: selectedCountry, city, streetName: street, streetNumber
        }
        
        axios({
            url: API_ENDPOINT + '/manage/university',
            method: 'post',
            data: payload
        }).then(response => {
            setUniversityStatus(true);
        }).catch(err => {
            setUniversityStatus(false);
        })

        if(imageStatus && universityStatus) {
            toast.success('Successfull registration', {position: toast.POSITION.BOTTOM_RIGHT, autoClose: 3000});
        } else {
            toast.error('University name or email is already taken', {position: toast.POSITION.BOTTOM_RIGHT, autoClose: false});
        }
    }

    const universityNameHandler = event => {
        setUniversityName(event.target.value);
    }

    const universityEmailHandler = event => {
        setUniversityEmail(event.target.value);
    }

    const selectedCountryHandler = event => {
        setSelectedCoontry(event.value);
    }

    const cityHandler = event => {
        setCity(event.target.value);
    }

    const streetHandler = event => {
        setStreet(event.target.value);
    }

    const streetNumberHandler = event => {
        setStreetNumber(event.target.value);
    }

    return (
        <>
            <Navbar />
            <div id="university-section">
                <h1 id="title"> University management </h1>
                <form id="university-form" onSubmit={submitData} >
                    <div className="left-section">
                        <div className="form-group">
                            <label className="uni-label">University name</label>
                            <input className="uni-input" type="text"
                            id="universityName" placeholder="I.e. University of engineering"
                            onChange={universityNameHandler} />
                        </div>
                        <div className="form-group">
                            <label className="uni-label">University email</label>
                            <input className="uni-input" type="email"
                                id="email"
                                placeholder="example@gmail.com"
                                onChange={universityEmailHandler}
                                value={universityEmail} />
                        </div>

                        <div className="form-group">
                            <label className="uni-label">Country</label>
                            <Select
                                closeMenuOnSelect={true}
                                className="basic-single"
                                classNamePrefix="select"
                                components={animatedComponents}
                                defaultValue={selectedCountry}
                                isClearable={true}
                                isSearchable={true}
                                name="country"
                                isMulti={false}
                                options={countries}
                                onChange={selectedCountryHandler}
                                />
                        </div>

                    </div>
                    <div className="right-section">
                        <div className="form-group">
                            <label className="uni-label">City</label>
                            <input className="uni-input"
                                type="text"
                                id="city"
                                placeholder="I.e. Oxford"
                                value={city}
                                onChange={cityHandler} />
                        </div>

                        <div className="form-group">
                            <label className="uni-label">Street</label>
                            <input className="uni-input"
                                type="text"
                                id="streetName"
                                placeholder="I.e. Oxford street"
                                value={street}
                                onChange={streetHandler} />
                        </div>

                        <div className="form-group">
                            <label className="uni-label">Street number</label>
                            <input className="uni-input"
                                type="text"
                                id="streetNumber"
                                placeholder="i.e. 2A"
                                value={streetNumber}
                                onChange={streetNumberHandler}/>
                        </div>
                        
                        <div className="form-group">
                            <label className="uni-label">Choose an image</label>
                            <input id="image-select" type="file" onChange={fileSelectedHandler} />
                        </div>

                        <div id="img-preview-div">
                            <img id="img-preview" src={imgSrc} />
                        </div>

                        <div id="btn-div">
                            <button id="add-university" type="submit"> Add university </button>
                        </div>
                    </div>

                </form>
            </div>
            <Footer/>            
        </>
    )
}

export default UniversityManagement
