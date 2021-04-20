import React, { useState, useEffect } from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import '../../css/AdminCountry.css'
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints'
import CountryTable from './CountryTable'
import { toast } from 'react-toastify'
import LanguageTable from './LanguageTable'

toast.configure()
function AdminCountry() {
    const [countryData, setCountryData] = useState([{label: 1, value: 'Serbia'}, {label: 1, value: 'Serbia'}]);
    const [countriesLoading, setCountriesLoading] = useState(true);
    const [newCountry, setNewCountry] = useState("");
    const [languageData, setLanguageData] = useState([]);
    const [languageLoading, setLanguageLoading] = useState(true);
    const [newLanguageName, setNewLanguageName] = useState("");
    const [newLanguageCode, setNewLanguageCode] = useState("");

    useEffect(() => {
        fetchCountries();
        fetchLanguages();
    }, [])

    const fetchCountries = () => {
        axios.get(API_ENDPOINT + '/manage/country')
            .then(response => response.data)
            .then(response => response.map(country => {
                return {
                    id: response.indexOf(country)+1,
                    country: country.country
                }} 
            ))
            .then((response) => {
                setCountryData(response);
                setCountriesLoading(false);
            })
            .catch(error => {
                console.log('Error fetching countries');
                setCountriesLoading(false);
            })
    }

    const fetchLanguages = () => {
        axios({
            url: API_ENDPOINT + '/manage/language',
            method: 'GET'
        }).then(response => response.data)
        .then(response => response.map(language => {
            return {
                id: response.indexOf(language)+1,
                languageName: language.languageName,
                languageCode: language.languageCode
            }
        }))
        .then(data => {
            setLanguageData(data);
            setLanguageLoading(false);
        })
        .catch(err => {
            console.log(err);
            setLanguageLoading(false);
        });
    }

    const deleteCountry = (countryName) => {
        axios({
            url: API_ENDPOINT + '/manage/country/' + countryName,
            method: 'DELETE'
        }).then(() => {
            toast.success(`${countryName} successfully deleted`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: 5000 });
            fetchCountries();
        }).catch(() => {
            toast.success(`Service temporary unavailable\nCouldn\'t delete ${countryName}`);
        });
    }

    const newCountryHandler = (event) => {
        setNewCountry(event.target.value);
    }

    const submitCountry = () => {
        axios({
            url: API_ENDPOINT + '/manage/country',
            method: 'POST',
            data: { country: newCountry }
        }).then(() => {
            setNewCountry("");
            fetchCountries();
            toast.success(`${newCountry} posted successfully`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: 5000 });
        }).catch((err) => {
            err.response.status === 403 ? toast.error(`${newCountry} is already supported`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: false }) :
            toast.error(`Service temporary unavailable`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: false });
        })
    }

    const deleteLanguage = (languageCode) => {
        axios({
            url: API_ENDPOINT + '/manage/language/' + languageCode,
            method: 'DELETE'
        }).then(() => {
            toast.success(`${languageCode} deleted successfully`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: 5000 });
            fetchLanguages();
        }).catch(() => {
            toast.error(`Service temporary unavailable`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: false });
        });
    }

    const languageNameHandler = event => {
        setNewLanguageName(event.target.value);
    }

    const languageCodeHandler = event => {
        setNewLanguageCode(event.target.value);
    }

    const submitLanguage = () => {
        if(languageDataValid()) {
            axios({
                url: API_ENDPOINT + '/manage/language',
                method: 'POST',
                data: {languageName: newLanguageName, languageCode: newLanguageCode}
            }).then(() => {
                toast.success(`${newLanguageName} added successfully`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: 5000 });
                setNewLanguageName("");
                setNewLanguageCode("");
                fetchLanguages();
            }).catch(() => {
                toast.error(`${newLanguageName} or ${newLanguageCode} already exist`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: false });
            })
        } else {
            toast.error(`Language name and language code have to be populated with unique values`, { position: toast.POSITION.BOTTOM_RIGHT, autoClose: false });
        }
    }

    const languageDataValid = () => {
        return newLanguageName !== '' && newLanguageCode !== '' && newLanguageName !== newLanguageCode;
    }

    return (
        <>
          <Navbar />
          <div className="admin-main-section">
            <h1> Supported countries </h1>
            <div id="country-table">

                { countriesLoading ? <div> <h1>Loading...</h1> </div> : <CountryTable countryData={countryData} deleteCountry={deleteCountry}/> }
            </div>

            <div id="posting-country">
                <h2 id="add-country-title"> Add new country </h2>
                <label id="country-label">Country name</label>
                <input id="country-input"
                    type="text"
                    value={newCountry}
                    onChange={newCountryHandler} />
                <button id="country-button" onClick={submitCountry}> Add country </button>
            </div>

            <h1 id="language-title"> Supported languages </h1>
            <div id="language-table">
                { languageLoading ? <div> <h1>Loading...</h1> </div> : <LanguageTable languageData={languageData} deleteLanguage={deleteLanguage} /> }
            </div>
            <h1 id="add-language-headline"> Add new language </h1>
            <div id="add-language">
                <div className="language-section">
                    <label className="language-label" > Language name </label>
                    <input className="language-input" value={newLanguageName}
                        placeholder="I.e. Spanish"
                        onChange={languageNameHandler} />
                </div>
                <div className="language-section">
                    <label className="language-label"> Language code </label>
                    <input className="language-input" value={newLanguageCode}
                        placeholder="I.e. es"
                        onChange={languageCodeHandler} />
                </div>
            </div>
            <button id="language-button" onClick={submitLanguage}> Add language </button>
          </div>
          <Footer />  
        </>
    )
}

export default AdminCountry
