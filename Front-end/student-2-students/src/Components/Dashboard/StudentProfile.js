import React, { useState, useEffect } from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import DefaultPhoto from '../../images/profile.svg'
import '../../css/StudentProfile.css'
import { toast } from 'react-toastify'
import { API_ENDPOINT } from '../Constants/Endpoints'
import axios from 'axios';
import Select from 'react-select';
import Picker from 'emoji-picker-react';

toast.configure()
function StudentProfile() {
  const hiddenFileInput = React.useRef(null);
  const [imageStatus, setImageStatus] = useState(false);
  const [imgSrc, setImgSrc] = useState(DefaultPhoto);
  const [image, setImage] = useState({});

  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [country, setCountry] = useState("");
  const [countrySelection, setCountrySelection] = useState([]);
  const [city, setCity] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [newPasswordConfirm, setNewPasswordConfirm] = useState("");
  const [streetName, setStreetName] = useState("");
  const [streetNumber, setStreetNumber] = useState("");
  const [language, setLanguage] = useState("");
  const [languageSelection, setLanguageSelection] = useState("");
  const [major, setMajor] = useState("");
  const [majorSelection, setMajorSelection] = useState([]);
  const [biography, setBiography] = useState("");
  const [chosenEmoji, setChosenEmoji] = useState(null);
  const [emojiPicker, setEmojiPicker] = useState("emoji-picker hide");


  const handleClick = event => {
    hiddenFileInput.current.click();
  }

  // Fetching image
  useEffect(() => {
    axios({
      url: API_ENDPOINT + '/image/image-service/profile-image',
      method: 'GET',
      withCredentials: true
    }).then(response => {
      setImgSrc(response.data);
    }).catch(err => {
      console.log(err);
    });
  }, []);

  // Fetching countries
  useEffect(() => {
    axios.get(API_ENDPOINT + '/manage/country')
        .then(reponse => reponse.data)
        .then(response => 
          response.sort((a,b) => (a.country > b.country) ? 1 : ((b.country > a.country) ? -1 : 0))
        )
        .then(response => {
            setCountrySelection(response.map(current => {
                 return({ value: current.country, label: current.country })
            }))
        })
        .catch(() => {
            console.log('Error fetching countries');
        })
    }, [])

  // Fetching languages
  useEffect(() => {
      axios.get(API_ENDPOINT + '/manage/language')
      .then(reponse => reponse.data)
      .then(response => 
        response.sort((a,b) => (a.languageName > b.languageName) ? 1 : ((b.languageName > a.languageName) ? -1 : 0))
      )
      .then(response => {
          setLanguageSelection(response.map(current => {
              return({ value: current.languageCode, label: current.languageName + ` (${current.languageCode})` })
          }))
      })
      .catch(() => {
          console.log('Error fetching languages');
      })
  }, [])

  // Fetching majors
  useEffect(() => {
    axios.get(API_ENDPOINT + '/manage/major')
    .then(reponse => reponse.data)
    .then(response => 
      response.sort((a,b) => (a.majorName > b.majorName) ? 1 : ((b.majorName > a.majorName) ? -1 : 0))
    )
    .then(response => {
        setMajorSelection(response.map(current => {
            return({ value: current.majorName, label: current.majorName })
        }))
    })
    .catch(() => {
        console.log('Error fetching majors');
    })
  }, [])

  // Fetch username
  useEffect(() => {
    axios({
      url: API_ENDPOINT + '/authorization/username',
      method: 'GET',
      withCredentials: true
    }).then(response => {
      setUsername(response.data);
    }).catch(err => {
      console.log(err);
    });
  }, [])

  // Fetching student's data
  useEffect(() => {
    username !== '' && axios({
      url: API_ENDPOINT + '/manage/student/data/' + username,
      method: 'GET',
      withCredentials: true
    }).then(response => response.data)
      .then(response => {
        console.log(response);
      }).catch(err => {
        console.log(err);
      });
  }, [])

  const fileSelectedHandler = event => {
    if(event.target !== null) {
      setImage(event.target.files[0]);
      setImageStatus(true);
      const reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);

      reader.onload = () => {
          if(reader.readyState === 2) {
              setImgSrc(reader.result);
          }
      }
    }

}

const sendImage = () => {
    let body = new FormData();
    body.append('image', image);
    axios({
        url: API_ENDPOINT + '/image/image-service/profile-image',
        method: "post",
        data: body,
        withCredentials: true
    }).then(() => {
        toast.success(`Image posted successfully`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: 3000 });
    }).catch(err => {
        toast.error(`Service temporary unavailable`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: false });
    });
}

  const selectedCountryHandler = event => {
    if(event !== null) {
      setCountry(event.value);
    }
  }

  const selectLanguageHandler = event => {
    if(event !== null) {
      setLanguage(event.value);
    }
  }

  const selectMajorHandler = event => {
    if(event !== null) {
      setMajor(event.value);
    }
  }

  const onEmojiClick = (event, emojiObject) => {
    setChosenEmoji(emojiObject);
    // const emoji = emojiObject.emoji;
    let text = biography;
    text = text + emojiObject.emoji;
    setBiography(text);
  };

  const toggleEmojiPicker = () => {
    emojiPicker === 'emoji-picker hide' ? setEmojiPicker('emoji-picker') : setEmojiPicker('emoji-picker hide');
  }

  const submitData = () => {
    console.log(languageSelection);
  }


  return (
      <>
        <Navbar role={"STUDENT"} />
          <div id="profile-panel">
            <div id="first-section">
                <div id="image-panel">
                  <img src={imgSrc} alt="profile image" id="profile-image"/>
                  <input type="file" ref={hiddenFileInput}
                      onChange={fileSelectedHandler}
                      style={{display: 'none'}}/>
                  
                  {
                    !imageStatus ? <button id="update-photo" onClick={handleClick}> Choose photo </button>
                    : <button id="update-photo" onClick={sendImage}> Upload photo </button>
                  }
                </div>
                <div>
                  <h3 id="username-display"> {username} </h3>
                  <div id="date-section">
                    <label id="member-label"> Member since </label>
                    <p id="member-since"> 23/04/2021 </p>
                  </div>
                </div>
            </div>
            <div id="data-panel">
                <div id="hr-div">
                    <hr/>
                </div>
                <h1 id="update-title"> Update personal data </h1>
                <div id="data-form">
                    <div className="left-data-panel">
                      <div className="form-group">
                        <label> Email </label>
                        <input type="email"
                          id="email-input"
                          disabled value={email} />
                      </div>

                      <div className="form-group">
                        <label> First name </label>
                        <input type="text"
                          value={firstName}
                          onChange={e => setFirstName(e.target.value) } />
                      </div>

                      <div className="form-group">
                        <label> Last name </label>
                        <input type="text"
                          value={lastName}
                          onChange={e => setLastName(e.target.value)} />
                      </div>

                      <div className="form-group">
                        <label> Country </label>
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

                      <div className="form-group">
                        <label> City </label>
                        <input type="text"
                          value={city}
                          onChange={e => setCity(e.target.value)} />
                      </div>

                      <div className="form-group">
                        <label> Street name </label>
                        <input type="text"
                          value={streetName}
                          onChange={e => setStreetName(e.target.value)} />
                      </div>

                      <div className="form-group">
                        <label> Street number </label>
                        <input type="text"
                          value={streetNumber}
                          onChange={e => setStreetNumber(e.target.value)} />
                      </div>
                    </div>

                    <div className="right-data-panel">
                    <div className="form-group">
                      <label> Language </label>
                      <Select
                        closeMenuOnSelect={true}
                        className="basic-single"
                        classNamePrefix="select"
                        defaultValue={language}
                        isClearable={true}
                        isSearchable={true}
                        name="language"
                        isMulti={false}
                        options={languageSelection}
                        onChange={selectLanguageHandler}
                        />
                    </div>

                    <div className="form-group">
                      <label> Your area of expertise </label>
                      <Select
                        closeMenuOnSelect={true}
                        className="basic-single"
                        classNamePrefix="select"
                        defaultValue={major}
                        isClearable={true}
                        isSearchable={true}
                        name="major"
                        isMulti={false}
                        options={majorSelection}
                        onChange={selectMajorHandler}
                        />
                    </div>

                    <div className="form-group">
                      <label> Biography </label>
                      <textarea id="biography"
                        placeholder="Tell us something about yourself"
                        value={biography}
                        onChange={e => setBiography(e.target.value)} />
                        <button onClick={toggleEmojiPicker} id="emoji-button"> Add emoji </button>
                        <div className={emojiPicker}>
                          <Picker onEmojiClick={onEmojiClick} />
                        </div>
                    </div>

                    <button id="submit" onClick={submitData}> Submit changes </button>
                    </div>
                </div>
            </div>
          </div>
        <Footer />  
      </>
  )
}

export default StudentProfile
