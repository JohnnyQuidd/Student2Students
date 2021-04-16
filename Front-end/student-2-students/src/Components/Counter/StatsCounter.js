import React from 'react'
import CountryCounter from './CountryCounter'
import StudentCounter from './StudentCounter'
import UniversityCounter from './UniversityCounter'

function StatsCounter({isVisible}) {

    return (
        <>
            <div className="counter-div">
                <StudentCounter isVisible={isVisible} />
                <h3> Students </h3>
            </div>
            <div className="counter-div" id="middle-div" >
                <UniversityCounter isVisible={isVisible} />
                <h3> Universities </h3>
            </div>
            <div className="counter-div">
                <CountryCounter isVisible={isVisible} />
                <h3> Countries </h3>
            </div>
        </>
    )
}

export default StatsCounter
