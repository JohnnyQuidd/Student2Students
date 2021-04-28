import React from 'react'
import { Line } from 'react-chartjs-2'

function LineChart() {
    const studentData = {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'June', 'July', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        datasets: [
            {
                label: 'Number of Posts',
                data: [3, 50, 75, 157, 379, 550, 456, 972, 1670, 3670, 3000, 7500],
                borderColor: ['rgba(54, 162, 235, 0.2)'],
                backgroundColor: ['rgba(54, 162, 235, 0.4)'],
                pointBackgroundColor: 'rgba(54,162,235, 0.5)',
                pointBorderColor: 'rgba(54,162,235, 0.5)'
            },
            {
                label: 'Number of Exchanges',
                data: [5, 70, 45, 30, 120, 250, 200, 150, 450, 4300, 2000, 1200],
                borderColor: ['rgba(255, 206, 86, 0.2)'],
                backgroundColor: ['rgba(255, 206, 86, 0.4)'],
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
        <div style={{width: '100%'}}>
            <Line data={studentData} options={options} />
        </div>
    )
}

export default LineChart
