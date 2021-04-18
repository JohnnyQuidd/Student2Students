import React, { useState } from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import '../../css/UniversityManagement.css'
import Select from 'react-select'
import makeAnimated from 'react-select/animated';
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints'


function UniversityManagement() {

    const [image, setImage] = useState({})
    const [universityName, setUniversityName] = useState("")

    const animatedComponents = makeAnimated();

    const countryOptions = [
        { value: "Serbia", label: "Serbia"},
        { value: "Italy", label: "Italy"},
        { value: "France", label: "France"},
        { value: "Germany", label: "Germany"}
    ]

    const universityNameHandler = event => {
        setUniversityName(event.target.value);
    }

    const fileSelectedHandler = event => {
        setImage(event.target.files[0]);
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
        }).catch(err => {
            console.log('Error');
        });
        
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
                            <input className="uni-input" type="email" id="email" placeholder="example@gmail.com" />
                        </div>

                        <div className="form-group">
                            <label className="uni-label">Country</label>
                            <Select
                                closeMenuOnSelect={true}
                                className="basic-single"
                                classNamePrefix="select"
                                components={animatedComponents}
                                defaultValue={""}
                                isClearable={true}
                                isSearchable={true}
                                name="country"
                                isMulti={false}
                                options={countryOptions}
                                />
                        </div>
                    </div>
                    <div className="right-section">
                        <div className="form-group">
                            <label className="uni-label">City</label>
                            <input className="uni-input" type="text" id="city" placeholder="I.e. Oxford" />
                        </div>

                        <div className="form-group">
                            <label className="uni-label">Street</label>
                            <input className="uni-input" type="text" id="streetName" placeholder="I.e. Oxford street" />
                        </div>

                        <div className="form-group">
                            <label className="uni-label">Street number</label>
                            <input className="uni-input" type="text" id="streetNumber" placeholder="i.e. 2A" />
                        </div>

                        <div className="form-group">
                            <label className="uni-label">Choose an image</label>
                            <input id="image-select" type="file" onChange={fileSelectedHandler} />
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
