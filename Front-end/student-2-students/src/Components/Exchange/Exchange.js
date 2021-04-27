import React, { useState, useEffect } from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import { useHistory } from "react-router-dom";
import axios from 'axios';
import { API_ENDPOINT } from '../Constants/Endpoints'
import '../../css/Exchange.css';
import Select from 'react-select'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'
import { toast } from 'react-toastify';
import Picker from 'emoji-picker-react';


toast.configure()
function Exchange() {
    // country, city, university, minNumberOfAttendees, maxNumberOfAttendees, exchangeStart
    // exchangeEnding, price
    const [role, setRole] = useState(null);
    const [country, setCountry] = useState(null);
    const [countrySelection, setCountrySelection] = useState([]);
    const [city, setCity] = useState("");
    const [university, setUniversity] = useState(null);
    const [universitySelection, setUniversitySelection] = useState([]);
    const [minNumberOfAttendees, setMinNumberOfAttendees] = useState(0);
    const [maxNumberOfAttendees, setMaxNumberOfAttendees] = useState(0);
    const [exchangeStart, setExchangeStart] = useState(null);
    const [exchangeEnding, setExchangeEnding] = useState(null);
    const [price, setPrice] = useState(0);
    const [body, setBody] = useState("");

    const [emojiPicker, setEmojiPicker] = useState("emoji-picker hide");
    const [chosenEmoji, setChosenEmoji] = useState(null);

    const history = useHistory();

    useEffect(() => {
        axios.get(API_ENDPOINT + '/authorization/student', { withCredentials: true })
            .then(() => {
                setRole("STUDENT");
            })
            .catch(err => {
                console.log(err);
                history.push('/login');
            })
    }, [history]);

    useEffect(() => {
        axios.get(API_ENDPOINT + '/manage/country')
            .then(reponse => reponse.data)
            .then(data => {
                return data.sort((a,b) => (a.country > b.country) ? 1 : ((b.country > a.country) ? -1 : 0))
            } )
            .then(response => {
                setCountrySelection(response.map(current => {
                     return({ value: current.country, label: current.country })
                }))
            })
            .catch(err => {
                console.log('Error fetching countries');
                console.log(err);
            })
    }, []);

    useEffect(() => {
        axios.get(API_ENDPOINT + '/manage/university/pagination')
            .then(reponse => reponse.data)
            .then(data => {
                return data.sort((a,b) => (a.universityName > b.universityName) ? 1 : ((b.universityName > a.universityName) ? -1 : 0))
            } )
            .then(response => {
                setUniversitySelection(response.map(current => {
                     return({ value: current.universityName, label: current.universityName })
                }))
            })
            .catch(err => {
                console.log('Error fetching universities');
                console.log(err);
            })
    }, [])

    const toggleEmojiPicker = () => {
        emojiPicker === 'emoji-picker hide' ? setEmojiPicker('emoji-picker') : setEmojiPicker('emoji-picker hide');
    }

    const onEmojiClick = (event, emojiObject) => {
        setChosenEmoji(emojiObject);
        let text = body;
        text = text + emojiObject.emoji;
        setBody(text);
      };

    const selectedCountryHandler = event => {
        if(event !== null) {
            setCountry(event.value);
        }
    }

    const selectedUniversityHandler = event => {
        if(event !== null) {
            setUniversity(event.value);
        }
    }

    const handleMinAttendees = event => {
        setMinNumberOfAttendees(event.target.value);
    }

    const handleMaxAttendees = event => {
        setMaxNumberOfAttendees(event.target.value);
    }

    const submitExchange = () => {
        const payload = {country, city, university, minNumberOfAttendees, maxNumberOfAttendees, exchangeStart, exchangeEnding, price, body};

        axios({
            url: API_ENDPOINT + '/posting/exchange',
            method: 'POST',
            data: payload,
            withCredentials: true
        }).then(() => {
            toast.success('Success!', { position: toast.POSITION.BOTTOM_RIGHT, autoClose: 3000 });
        }).catch(err => {
            toast.error(`Service temporary unavailable`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: false });
        });
    }

    return (
        <>
          <Navbar role={role} />
            <div className="new-exchange-wrapper">
              <div className="new-exchange-title">
                <h1> Have an exchange to offer? </h1>
              </div>
              <div className="exchange-content">
                <div className="exchange-left-panel">
                    <div className="new-exchange-form">
                        <label className="new-exchange-label">Country</label>
                        <Select
                            closeMenuOnSelect={true}
                            className="basic-single"
                            classNamePrefix="select"
                            defaultValue={country}
                            isClearable={true}
                            isSearchable={true}
                            name="country"
                            isMulti={false}
                            options={countrySelection}
                            onChange={selectedCountryHandler}
                            />
                    </div>
                        <div className="new-exchange-form">
                            <label className="new-exchange-label">City</label>
                            <input className="exchange-input" type="text" value={city} onChange={e => setCity(e.target.value)} />
                        </div>
                    <div className="new-exchange-form">
                        <label className="new-exchange-label">Afilliated University</label>
                        <Select
                            closeMenuOnSelect={true}
                            className="basic-single"
                            classNamePrefix="select"
                            defaultValue={university}
                            isClearable={true}
                            isSearchable={true}
                            name="university"
                            isMulti={false}
                            options={universitySelection}
                            onChange={selectedUniversityHandler}
                            />
                    </div>
                    <div className="new-exchange-form">
                        <label className="new-exchange-label">Min number of Attendees ({minNumberOfAttendees}) </label>
                        <input 
                            className="exchange-input"
                            id="minNumberOfAttendees" 
                            type="range" 
                            min="0" max="100" 
                            value={minNumberOfAttendees} 
                            onChange={handleMinAttendees}
                            step="1"/>
                    </div>

                    <div className="new-exchange-form">
                        <label className="new-exchange-label">Max number of Attendees ({maxNumberOfAttendees})</label>
                        <input 
                            className="exchange-input"
                            id="maxNumberOfAttendees" 
                            type="range" 
                            min="0" max="100" 
                            value={maxNumberOfAttendees} 
                            onChange={handleMaxAttendees}
                            step="1"/>
                    </div>
                </div>

                <div className="exchange-right-panel">
                    <div className="new-exchange-form">
                        <label className="new-exchange-label"> From </label>
                        <DatePicker
                            className="exchange-date"
                            selected={exchangeStart}
                            onChange={date => setExchangeStart(date)}
                            isClearable
                            dateFormat="dd/MM/yyyy"
                            minDate={new Date()} />
                    </div>

                    <div className="new-exchange-form">
                        <label className="new-exchange-label"> Untill </label>
                        <DatePicker
                            className="exchange-date"
                            selected={exchangeEnding}
                            onChange={date => setExchangeEnding(date)}
                            isClearable
                            dateFormat="dd/MM/yyyy"
                            minDate={exchangeStart} />
                    </div>

                    <div className="new-exchange-form">
                        <label className="new-exchange-label"> Price ($) </label>
                        <input className="exchange-input" type="number" value={price} onChange={e => setPrice(e.target.value)} />
                    </div>
                    
                    <div className="new-exchange-form">
                        <label className="new-exchange-label"> Exchange description </label>
                        <textarea className="exchange-text-area" value={body} onChange={e => setBody(e.target.value)} />
                        <button onClick={toggleEmojiPicker} id="exchange-emoji-button"> Add emoji </button>
                            <div className={emojiPicker}>
                                <Picker onEmojiClick={onEmojiClick} />
                            </div>
                    </div>

                    <div className="new-exchange-form">
                        <button className="offer-exchange-button"  onClick={submitExchange}> Offer an exchange </button>
                    </div>
                </div>
              </div>


            </div>
          <Footer />  
        </>
    )
}

export default Exchange
