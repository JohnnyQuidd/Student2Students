import React from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import '../../css/UniversityManagement.css'
import Select from 'react-select'
import makeAnimated from 'react-select/animated';


function UniversityManagement() {

    const animatedComponents = makeAnimated();

    const countryOptions = [
        { value: "Serbia", label: "Serbia"},
        { value: "Italy", label: "Italy"},
        { value: "France", label: "France"},
        { value: "Germany", label: "Germany"}
    ]

    return (
        <>
            <Navbar />
            <div id="university-section">
                <h1 id="title"> University management </h1>
                <div id="university-form">
                    <div className="left-section">
                        <div className="form-group">
                            <label className="uni-label">University name</label>
                            <input className="uni-input" type="text" id="universityName" placeholder="I.e. University of engineering" />
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
                        <div id="btn-div">
                            <button id="add-university"> Add university </button>
                        </div>
                    </div>

                </div>
            </div>
            <Footer/>            
        </>
    )
}

export default UniversityManagement
