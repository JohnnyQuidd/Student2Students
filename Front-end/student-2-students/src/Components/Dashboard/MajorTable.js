import React, { useMemo } from 'react'
import { useTable } from 'react-table'
import '../../css/MajorTable.css'

function MajorTable({majorData}) {

    const COLUMNS = [
        {
            Header: 'Id',
            accessor: 'id'
        },
        {
            Header: 'Major Name',
            accessor: 'majorName'
        },
        {
            Header: 'Action',
            accessor: 'action',
            Cell: ({ cell }) => (
                <button value={cell} onClick={handleDeleteMajor}>
                  Delete
                </button>
            )
        }]

    const handleDeleteMajor = (event) => {
        let obj = event.target.value;
        console.log(JSON.parse(JSON.stringify(obj)));
        //console.log(event.target.value);
    }

    // I'm just going to use Memo on columns, since it doesn't work when data is memoized
    // It can cause performance issues!
    // eslint-disable-next-line
    const cols = useMemo(() => COLUMNS, [])

    const table = useTable({
        columns: cols,
        data: majorData
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

export default MajorTable
