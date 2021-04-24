import React, { useState, useEffect } from 'react'
import Navbar from '../Navbar/Navbar'
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints'
import { useHistory } from 'react-router-dom'
import '../../css/AdminDashboard.css'
import LineChart from '../Charts/LineChart'
import MajorTable from './MajorTable'
import { HashLoader } from 'react-spinners'
import Footer from '../Footer/Footer'
import { toast } from 'react-toastify'
import Select from 'react-select'



toast.configure()
function AdminDashboard() {
    const [majorData, setMajorData] = useState([]);
    const [majorLoading, setMajorLoading] = useState(true);
    const [newMajorName, setNewMajorName] = useState("");
    const [selectedMajor, setSelectedMajor] = useState("");
    const [majorDataSelection, setMajorDataSelection] = useState([]);
    const [newTopicName, setNewTopicName] = useState("")


    const history = useHistory();

    // Restrict access if user doesn't have admin privileges
    // useEffect(() => {
    //     axios.get(API_ENDPOINT + '/authorization/admin', { withCredentials: true })
    //         .catch(err => {
    //             console.log(err);
    //             history.push('/login');
    //         })
    // }, [history])
    
    useEffect(() => {     
        fetchMajors();
        fetchMajorSelection();
    }, [])

    const fetchMajors = () => {
        axios.get(API_ENDPOINT + '/manage/major')
        .then(response => response.data)
        .then(response => {
            setMajorData(response.map(major => { 
                return {
                    id: response.indexOf(major)+1,
                    majorName: major.majorName
                } 
            }));
        }).catch(err => {
            setMajorData(false);
        })
    }

    const fetchMajorSelection = () => {
        axios.get(API_ENDPOINT + '/manage/major')
            .then(response => response.data)
            .then(response => {
                setMajorDataSelection(response.map(major => { 
                    return {
                        value: major.majorName,
                        label: major.majorName
                    } 
                }));
            }).then(() => {
                setMajorLoading(false);
            }).catch(err => {
                console.log(err);
                setMajorLoading(false);
            })
    }

    const majorHandler = (event) => {
        setNewMajorName(event.target.value);
    }

    const submitMajor = () => {
        axios({
            url: API_ENDPOINT + '/manage/major',
            method: 'post',
            data: {majorName : newMajorName},
            withCredentials: true
        }).then(() => {
            toast.success(`Major ${newMajorName} posted successfully`, {position: toast.POSITION.BOTTOM_RIGHT, autoClose: 5000});
            fetchMajors();
            setNewMajorName("");
        }).catch(err => {
            toast.error(`Major ${newMajorName} already exists`, {position: toast.POSITION.BOTTOM_RIGHT, autoClose: false});
        })
    }

    const deleteMajor = (majorName) => {
        axios({
            url: API_ENDPOINT + '/manage/major/' + majorName,
            method: 'delete',
            withCredentials: true
        }).then(() => {
            toast.success(`${majorName} successfully deleted`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: 5000 });
            fetchMajors();
            fetchMajorSelection();
        }).catch(() => {
            toast.error(`Service temporary unavailable. \n${majorName} cannot be deleted`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: false });
        })
    }

    const majorDataHandler = (event) => {
        setSelectedMajor(event.value);
    }

    const topicNameHandler = (event) => {
        setNewTopicName(event.target.value);
    }

    const submitTopic = () => {
        if(topicDataValid()) {
            const payload = {topicName: newTopicName, majorName: selectedMajor};
            axios({
                url: API_ENDPOINT + '/manage/topic',
                method: 'POST',
                data: payload
            }).then(() => {
                toast.success(`${newTopicName} added to ${selectedMajor}`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: 5000 });
                setNewTopicName("");
            }).catch((err) => {
                console.log(err);
                toast.error('Service temporary unavailable', { position: toast.POSITION.BOTTOM_RIGHT, autoClose: false });
            })
        } else {
            toast.error('Major and Topic name have to be specified', { position: toast.POSITION.BOTTOM_RIGHT, autoClose: false });
        }
    }

    const topicDataValid = () => {
        return newTopicName !== '' && selectedMajor !== '';
    }

    return (
        <div>
            <Navbar />
            <div className="content">
                <h1 id="title"> Dashboard </h1>
                <div className="chart">
                    <LineChart />
                </div>
                <div className="major">
                    <p> Majors </p>
                    { majorLoading && <div className='major-loader'> <HashLoader size={100} color="blue" /> </div> }
                    { !majorLoading && <MajorTable majorData={majorData} deleteMajor={deleteMajor}/> }
                </div>

                <div id="add-major">
                    <div id="major-fields">
                        <label id="label-major"> Add new Major </label>
                        <input id="input-major"
                            type="text"
                            placeholder="I.e. Statistics"
                            value={newMajorName}
                            onChange={majorHandler} />
                    </div>
                    <button id="add-major-button" onClick={submitMajor}> Add Major </button>
                </div>

                <div className="add-topic">
                    <h1 id="add-topic-headline"> Add a topic to corresponding major </h1>
                    <div id="topic-div">
                        <div className="form-group">
                                <label id="major-label">Major</label>
                                <Select
                                closeMenuOnSelect={true}
                                className="basic-single"
                                classNamePrefix="select"
                                isClearable={true}
                                isSearchable={true}
                                name="major"
                                isMulti={false}
                                options={majorDataSelection}
                                onChange={majorDataHandler}
                                /> 
                                
                        </div>
                        <div id="topic-field">
                            <label id="topic-label"> Topic name </label>
                            <input id="input-topic"
                                type="text"
                                placeholder="I.e. Front end, Back end, React..."
                                value={newTopicName}
                                onChange={topicNameHandler} />
                        </div>
                    </div>
                    <button id="add-topic-button" onClick={submitTopic}> Add Topic </button>
                </div>

            </div>
            
            <Footer />
        </div>
    )
}

export default AdminDashboard
