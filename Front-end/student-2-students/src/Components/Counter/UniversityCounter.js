import React, { useEffect } from 'react'
import { useCountUp } from 'react-countup'


function UniversityCounter({isVisible}) {
    const { countUp, start, reset } = useCountUp({duration: 5, end: 50})

    useEffect(() => {
        isVisible ? start() : reset();
        
    }, [isVisible])

    return (
        <>
            <h3>{countUp} +</h3>
        </>
    )
}

export default UniversityCounter
