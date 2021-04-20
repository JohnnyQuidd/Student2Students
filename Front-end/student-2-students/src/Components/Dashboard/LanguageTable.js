import React, { useMemo } from 'react'
import { useTable } from 'react-table'
import '../../css/MajorTable.css'
import {BsFillTrashFill} from 'react-icons/bs'; 

function LanguageTable({languageData, deleteLanguage}) {

    const COLUMNS = [
        {
            Header: 'Id',
            accessor: 'id'
        },
        {
            Header: 'Name',
            accessor: 'languageName'
        },
        {
            Header: 'Code',
            accessor: 'languageCode'
        },
        {
            Header: "Delete",
            id: "delete",
            accessor: (str) => "delete",
    
            Cell: (tableProps) => (
              <span
                title={`Delete ${tableProps.row.original.languageName}`} 
                className="delete-major"
                onClick={() => {
                  deleteLanguage(tableProps.row.original.languageCode);
                }}
              >
                <BsFillTrashFill />
              </span>
            )
          }]

    const cols = useMemo(() => COLUMNS, [])

    const table = useTable({
        columns: cols,
        data: languageData
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

export default LanguageTable