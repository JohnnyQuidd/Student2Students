import React, { useEffect } from 'react'
import { useCountUp } from 'react-countup'


function StudentCounter({isVisible}) {
    const { countUp, start, reset } = useCountUp({duration: 2, end: 1000})

    useEffect(() => {
        isVisible ? start() : reset();
        
    }, [isVisible])

    return (
        <>
            <h3>{countUp} +</h3>
        </>
    )
}

export default StudentCounter
