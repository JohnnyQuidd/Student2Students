import React, { useState, useEffect } from 'react'
import Footer from '../Footer/Footer'
import Navbar from '../Navbar/Navbar'
import '../../css/AdminCountry.css'
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints'
import CountryTable from './CountryTable'
import { toast } from 'react-toastify'

toast.configure()
function AdminCountry() {
    const [countryData, setCountryData] = useState([{label: 1, value: 'Serbia'}, {label: 1, value: 'Serbia'}])
    const [countriesLoading, setCountriesLoading] = useState(true)

    useEffect(() => {
        fetchCountries();
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



    return (
        <>
          <Navbar />
          <div className="admin-main-section">
            <h1> Supported countries </h1>
            <div id="country-table">

                { countriesLoading ? <div> <h1>Loading...</h1> </div> : <CountryTable countryData={countryData} deleteCountry={deleteCountry}/> }
            </div>
          </div>
          <Footer />  
        </>
    )
}

export default AdminCountry
