import React, { useState, useEffect, useMemo } from 'react'
import { useTable } from 'react-table'
import axios from 'axios'
import { API_ENDPOINT } from '../Constants/Endpoints'

function MajorTable() {
    const [majorData, setMajorData] = useState({})

    useEffect(() => {
        axios.get(API_ENDPOINT + '/manage/major')
            .then(response => {
                setMajorData(response.data);
            })
            .catch(err => {
                console.log('Error fetching majors');
            })
    }, [])

    const COLUMNS = [
        {
            Header: 'Id',
            accessor: 'id'
        },
        {
            Header: 'Major Name',
            accessor: 'majorName'
        }]

    const dummyData = [
        {id: '1', majorName: 'SD'},
        {id: '2', majorName: 'asd'},
        {id: '3', majorName: 'S123'},
        {id: '4', majorName: 'Sasdad'},
        {id: '5', majorName: 'S213'},
        {id: '6', majorName: 'asdasdD'},
        {id: '7', majorName: 'SsssssD'},
    ]

    const cols = useMemo(() => COLUMNS, [])
    const data = useMemo(() => dummyData, [])

    const table = useTable({
        columns: cols,
        data: data
    })

    const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } = table

    const showData = () => {
        console.log(majorData);
    }

    if(!majorData.length) {
        return(<h2> Loading </h2>)
    } else {
        //return(<h3> OK </h3>)
        
        return (
            <>
                <table {...getTableProps()}>
                    <thead>
                        {
                            headerGroups.map(headerGroup => (
                                <tr {...headerGroup.getHeaderGroupProps()}>
                                    {
                                        headerGroup.headers.map(column => (
                                            <th {...column.getHeaderProps()}> {column.render('Header')} </th>
                                        ))
                                    }
                                    
                                </tr>
                            ))
                        }
    
                    </thead>
                    <tbody {...getTableBodyProps()}>
                       {
                           rows.map(row => {
                               prepareRow(row)
                               return (
                                    <tr {...row.getRowProps()}>
                                        {
                                            row.cells.map(cell => {
                                                return <td { ...cell.getCellProps() }> {cell.render('Cell')} </td>
                                            })
                                        }
                                    </tr>
                               )
                            })
                       }
                    </tbody>
                </table>
                <button onClick={showData}>Data</button>
            </>
        )
    }

}

export default MajorTable
