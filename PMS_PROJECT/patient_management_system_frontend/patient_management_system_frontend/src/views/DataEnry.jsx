import React, {useEffect, useState} from "react";

// react-bootstrap components
import {

    Container,

} from "react-bootstrap";

import ProtectiveRoute from "../auth/ProtectiveRoute";
import FBS from "./reports/FBS";
import BP from "./reports/BP";
import DietPlan from "./reports/DietPlan";
import WBmi from "./reports/WBmi";
import Scr from "./reports/Scr";
import Hba1c6 from "./reports/Hba1c6";
import LipidProfile from "./reports/LipidProfile";
import ASTATL from "./reports/ASTATL";


function DataEnry() {
    ProtectiveRoute()

    const [filter , setFilter] = useState(null)

    const handleFilter = (filter) => {
        setFilter(filter)
    }

    const closeParent = () =>{
        document.getElementById("closeButton").click()
    }

    return (
        <>
            <Container fluid>
                <div className="row row-cols-3">

                    <div className="col mb-3">
                        <button type="button" data-target="#exampleModal" onClick={()=>handleFilter("FBS")} data-toggle="modal" className="btn btn-dark" style={{minWidth:"250px"}}>Fasting Blood Sugar</button>
                    </div>

                    <div className="col mb-3">
                        <button type="button" data-target="#exampleModal" onClick={()=>handleFilter("BP")} data-toggle="modal" className="btn btn-dark" style={{minWidth:"250px"}}>Blood Pressure</button>
                    </div>
                    {/*<div className="col mb-3">*/}
                    {/*    <button type="button" data-target="#exampleModal" onClick={()=>handleFilter("DietPlan")} data-toggle="modal" className="btn btn-dark" style={{minWidth:"250px"}}>Diet Plan</button>*/}
                    {/*</div>*/}
                    <div className="col mb-3">
                        <button type="button" data-target="#exampleModal" onClick={()=>handleFilter("WBmi")} data-toggle="modal" className="btn btn-dark" style={{minWidth:"250px"}}>BMI</button>
                    </div>
                    <div className="col mb-3">
                        <button type="button" data-target="#exampleModal" onClick={()=>handleFilter("Scr")} data-toggle="modal" className="btn btn-dark" style={{minWidth:"250px"}}>Serum Creatinine</button>
                    </div>
                    <div className="col mb-3">
                        <button type="button" data-target="#exampleModal" onClick={()=>handleFilter("Hba1c6")} data-toggle="modal" className="btn btn-dark" style={{minWidth:"250px"}}>HbA1c</button>
                    </div>
                    <div className="col mb-3">
                        <button type="button" data-target="#exampleModal" onClick={()=>handleFilter("LipidProfile")} data-toggle="modal" className="btn btn-dark" style={{minWidth:"250px"}}>Lipid Profile</button>
                    </div>
                    <div className="col mb-3">
                        <button type="button" data-target="#exampleModal" onClick={()=>handleFilter("ASTATL")} data-toggle="modal" className="btn btn-dark" style={{minWidth:"250px"}}>AST,ATL</button>
                    </div>
                </div>
            </Container>





            <div class="modal fade"  id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" style={{maxWidth:"80%"}}>
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Data Entry Form</h5>
                            <button type="button" class="close" onClick={()=>setFilter(null)} data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            {filter === "FBS" && <FBS closeParent={closeParent}></FBS>}
                            {filter === "BP" && <BP closeParent={closeParent}></BP>}
                            {filter === "DietPlan" && <DietPlan closeParent={closeParent}></DietPlan>}
                            {filter === "WBmi" && <WBmi closeParent={closeParent}></WBmi>}
                            {filter === "Scr" && <Scr closeParent={closeParent}></Scr>}
                            {filter === "Hba1c6" && <Hba1c6 closeParent={closeParent}></Hba1c6>}
                            {filter === "LipidProfile" && <LipidProfile closeParent={closeParent}></LipidProfile>}
                            {filter === "ASTATL" && <ASTATL closeParent={closeParent}></ASTATL>}
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" id="closeButton" onClick={()=>setFilter(null)} data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>

        </>
    );
}

export default DataEnry;
