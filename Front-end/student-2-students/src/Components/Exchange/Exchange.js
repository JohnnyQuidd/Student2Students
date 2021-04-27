import React from 'react'
import '../../css/Exchange.css'

function Exchange({exchange}) {

    const formatDate = (date) => {
        const dateFormat = Date.parse(date);
        const newDate = new Date(dateFormat);
        return newDate.getDate() + '.' + (newDate.getMonth() + 1) + '.' + newDate.getFullYear() + '.';
    }

    return (
        <div className="exchange-wrapper">
            <div className="exchange-location">
                <p className="exchange-country-city">{exchange.country}, {exchange.city}</p>
            </div>
            <div className="exchange-body">
                <p> {exchange.body} </p>
            </div>
            <hr/>
            <div className="exchange-details">
                <div className="exchange-details-left">
                    <p> Affiliated University: {exchange.university} </p>
                    <p> Number of attendees: {exchange.minNumberOfAttendees} - {exchange.maxNumberOfAttendees} </p>
                    <p> Price: {exchange.price}$ </p>
                </div>
                <div className="exchange-details-right">
                    <p> Posted: {formatDate(exchange.createdAt)} </p>
                    <p> Taking place from {formatDate(exchange.exchangeStart)} to {formatDate(exchange.exchangeEnding)}</p>
                    <p> Exchange host: <a href={`/member/${exchange.studentUsername}`}>{exchange.studentUsername}</a></p>
                </div>
            </div>
        </div>
    )
}

export default Exchange
