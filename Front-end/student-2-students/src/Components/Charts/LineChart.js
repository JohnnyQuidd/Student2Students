import React from 'react'
import { Line } from 'react-chartjs-2'

function LineChart() {
    const studentData = {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'June', 'July', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        datasets: [
            {
                label: 'Income ($)',
                data: [3, 50, 2363, 57, -2379, -550, 456, 972, 1670, 3670, 11230, 5555],
                borderColor: ['rgba(54, 162, 235, 0.2)'],
                backgroundColor: ['rgba(54, 162, 235, 0.2)'],
                pointBackgroundColor: 'rgba(54,162,235, 0.5)',
                pointBorderColor: 'rgba(54,162,235, 0.5)'
            },
            {
                label: 'Taxes ($)',
                data: [323, 50, 213, 22, 213, 550, 23, 26, 231, 22, 111, 2323],
                borderColor: ['rgba(255, 206, 86, 0.2)'],
                backgroundColor: ['rgba(255, 206, 86, 0.2)'],
                pointBackgroundColor: 'rgba(255, 206, 86, 0.5)',
                pointBorderColor: 'rgba(255, 206, 86, 0.5)'
            }
        ]
    }

    const options = {
        title: {
            display: true,
            text: "Income"
        }
    }

    return (
        <Line data={studentData} options={options} />
    )
}

export default LineChart
