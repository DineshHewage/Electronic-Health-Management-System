import React, { useEffect } from 'react';
import './FlyInTextComponent.css'; // Import a separate CSS file for styling

const FlyInTextComponent = () => {
    useEffect(() => {
        setTimeout(() => {
            document.querySelector('.fly-in-text').classList.remove('hidden');
        }, 500);
    }, []);

    return (
        <div className="fly-in-text hidden" style={{width:"60%"}}>
            <div className="fly-in-text-li">W</div>
            <div className="fly-in-text-li">E</div>
            <div className="fly-in-text-li">L</div>
            <div className="fly-in-text-li">C</div>
            <div className="fly-in-text-li">O</div>
            <div className="fly-in-text-li">M</div>
            <div className="fly-in-text-li">E</div>
        </div>
    );
};

export default FlyInTextComponent;
