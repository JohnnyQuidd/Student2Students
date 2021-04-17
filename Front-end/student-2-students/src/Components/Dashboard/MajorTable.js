import React, { useMemo } from 'react'
import { useTable } from 'react-table'
import '../../css/MajorTable.css'


require("es6-promise").polyfill();
require("isomorphic-fetch");
function MajorTable({majorData}) {

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

    // I'm just going to use Memo on columns, since it doesn't work when data is memoized
    // It can cause performance issues!
    const cols = useMemo(() => COLUMNS, [])

    const table = useTable({
        columns: cols,
        data: majorData
    })

    const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } = table



    if(!majorData.length) {
        // Show spinner

        return(<h2> Loading </h2>)
    
    } else {
        return (
            <div className="table-div">
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
            </div>
        )
    }

}

export default MajorTable
