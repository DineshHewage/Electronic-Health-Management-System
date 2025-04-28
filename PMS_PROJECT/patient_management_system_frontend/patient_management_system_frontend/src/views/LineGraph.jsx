import React, { useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';

const LineGraph = ({ labels, data }) => {
    const chartRef = useRef(null);

    useEffect(() => {
        // Cleanup previous chart
        const canvas = chartRef.current;
        const context = canvas.getContext('2d');
        context.clearRect(0, 0, canvas.width, canvas.height);

        // Initialize the new chart
        const newChart = new Chart(context, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: 'Data',
                        borderColor: 'rgba(75,192,192,1)',
                        data: data,
                    },
                ],
            },
            options: {
                scales: {
                    x: {
                        type: 'category', // Use category type for string labels
                        labels: labels, // Provide the labels array
                        position: 'bottom',
                    },
                    y: {
                        type: 'linear',
                        position: 'left',
                    },
                },
            },
        });

        // Save the new chart instance to the ref
        chartRef.current = canvas;

        // Cleanup on component unmount
        return () => {
            if (newChart) {
                newChart.destroy();
            }
        };
    }, [labels, data]);

    return <canvas ref={chartRef} />;
};

export default LineGraph;
