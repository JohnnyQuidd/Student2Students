import React, { useState } from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import DefaultPhoto from '../../images/profile.svg'
import '../../css/StudentProfile.css'
import { toast } from 'react-toastify'
import { API_ENDPOINT } from '../Constants/Endpoints'
import axios from 'axios';

toast.configure()
function StudentProfile() {
  const hiddenFileInput = React.useRef(null);
  const [imageStatus, setImageStatus] = useState(false);
  const [imgSrc, setImgSrc] = useState(DefaultPhoto);
  const [image, setImage] = useState({});

  const handleClick = event => {
    hiddenFileInput.current.click();
  }

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

    // Debugging
    // axios({
    //   url: API_ENDPOINT + '/manage/data/SkinnyPete',
    //   method: 'GET',
    //   withCredentials: true
    // }).then(response => {
    //   console.log(response);
    // }).catch(err => {
    //   console.log(err);
    // })
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
                <div id="data-panel">
                  <p id="info">Username: <strong> SkinnyPete </strong> </p>
                  <p id="info"> Email: <strong> petar.kovacevic0088@gmail.com </strong> </p>
                </div>
            </div>
          </div>
        <Footer />  
      </>
  )
}

export default StudentProfile
