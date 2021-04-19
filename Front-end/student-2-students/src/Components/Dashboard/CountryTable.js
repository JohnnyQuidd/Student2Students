import React, { useMemo } from 'react'
import { useTable } from 'react-table'
import '../../css/MajorTable.css'
import {BsFillTrashFill} from 'react-icons/bs'; 

function CountryTable({countryData, deleteCountry}) {

    const COLUMNS = [
        {
            Header: 'Id',
            accessor: 'id'
        },
        {
            Header: 'Country',
            accessor: 'country'
        },
        {
            Header: "Delete",
            id: "delete",
            accessor: (str) => "delete",
    
            Cell: (tableProps) => (
              <span
                title={`Delete ${tableProps.row.original.country}`} 
                className="delete-major"
                onClick={() => {
                  deleteCountry(tableProps.row.original.country);
                }}
              >
                <BsFillTrashFill />
              </span>
            )
          }]



    // I'm just going to use Memo on columns, since it doesn't work when data is memoized
    // It can cause performance issues!
    // eslint-disable-next-line
    const cols = useMemo(() => COLUMNS, [])

    const table = useTable({
        columns: cols,
        data: countryData
    })

    const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } = table


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

export default CountryTable