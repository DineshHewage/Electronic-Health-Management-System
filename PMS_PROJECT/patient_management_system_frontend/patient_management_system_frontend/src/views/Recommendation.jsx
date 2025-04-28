import React, {useState, useEffect} from "react";

// react-bootstrap components
import {
    Badge,
    Button,
    Card,
    Navbar,
    Nav,
    Table,
    Container,
    Row,
    Col,
    Form,
    OverlayTrigger,
    Tooltip, Modal,
} from "react-bootstrap";
import axios from "axios";
import ProtectiveRoute from "../auth/ProtectiveRoute";
import {AuthConfig} from "../auth/AuthConfig";
import {BASE_URL} from "../config/Config";
import {RoleConfig} from "../auth/RoleConfig";
import {NicConfig} from "../auth/NicConfig";

function Recommendation() {
    ProtectiveRoute()
    const thArray = ["PATIENT", "REPORTED DATE", "OCCURRENCE", "TOPIC", "RECOMMENDATION", "RECOMMENDATION TYPE", "NEXT FIRE", "STATUS", "ACTIONS"];
    const [tdArray, setTdArray] = useState([])

    const [patientNic, setPatientNic] = useState("")
    const [occurrence, setOccurrence] = useState("")
    const [recommendationType, setRecommendationType] = useState("")
    const [topic, setTopic] = useState("")
    const [patientNicAddForm, setPatientNicAddForm] = useState("")
    const [occurrenceAddForm, setOccurrenceAddForm] = useState("")
    const [recommendation, setRecommendation] = useState("")
    const [isPatient, setIsPatient] = useState(true)

    useEffect(()=>{
        if(RoleConfig() == "PATIENT"){
            setIsPatient(true)
            setPatientNic(NicConfig())
        }else {
            setIsPatient(false)
        }
    },[])

    const handlepatientNic = (event) => {
        setPatientNic(event.target.value);
    };

    const handleOccurrence = (event) => {
        setOccurrence(event.target.value);
    };

    const handlepatientNicAddForm = (event) => {
        setPatientNicAddForm(event.target.value);
    };

    const handleOccurrenceAddForm = (event) => {
        setOccurrenceAddForm(event.target.value);
    };

    const handleRecommendationType = (event) => {
        setRecommendationType(event.target.value);
    };

    const handleTopic = (event) => {
        setTopic(event.target.value);
    };

    const handleRecommendation = (event) => {
        setRecommendation(event.target.value);
    };

    const resetForm = () => {
        setRecommendationType("");
        setTopic("");
        setPatientNicAddForm("");
        setOccurrenceAddForm("");
        setRecommendation("");
    };

    const handleSearch = (event) => {
        event.preventDefault()
        axios.get(`${BASE_URL}/api/v2/recommendation/findByPatientNicAndOccurrence?nic=${patientNic}&occurrence=${occurrence}`, AuthConfig())
            .then((res) => {
                const tempArray = res.data.map((row) => [
                    row.patient.user.firstName,
                    row.reportedDate,
                    row.occurrence,
                    row.topic,
                    row.recommendation,
                    row.recommendationType,
                    row.nextFire,
                    row.active ? "ACTIVE" : "DE ACTIVE",
                    <div>
                        {row.active && (
                            <Button disabled={isPatient} variant="success" onClick={() => handleDeActive(row.id)} className="mr-2"><i className="fa-solid fa-lock-open"></i></Button>
                        )}
                        {!row.active && (
                            <Button disabled={isPatient} variant="warning" onClick={() => handleActive(row.id)} className="mr-2"><i className="fa-solid fa-lock"></i></Button>
                        )}
                    </div>
                ]);
                setTdArray(tempArray);
            })
            .catch((error) => {
                // Handle any errors here
                console.error("Error fetching data:", error);
            });
    }

    const handleSaveRecommendations = (event) => {
        event.preventDefault()

        const data = {
            patientNic: patientNicAddForm,
            occurrence: occurrenceAddForm,
            topic: topic,
            recommendation: recommendation,
            recommendationType: recommendationType
        }
        axios.post(`${BASE_URL}/api/v2/recommendation/create`, data, AuthConfig()).then((res) => {
            setPatientNic(patientNicAddForm)
            setOccurrence(occurrenceAddForm)
            alert("New recommendations was added")
            handleSearch(event)
            resetForm();
        }).catch((e) => {
            if (e.response) {
                console.log(e.response.data.message);
                alert(e.response.data.message)
            }
        })
    }

    const handleActive = (id) =>{
        axios.get(`${BASE_URL}/api/v2/recommendation/activeRecommendation?id=${id}`, AuthConfig())
            .then((res) => {
                alert("Activated")
                const syntheticEvent = {
                    target: {},
                    currentTarget: {},
                    preventDefault: () => {},
                };
                handleSearch(syntheticEvent)
            })
            .catch((error) => {
                // Handle any errors here
                console.error("Error fetching data:", error);
            });
    }

    const handleDeActive = (id) =>{
        axios.get(`${BASE_URL}/api/v2/recommendation/deActiveRecommendation?id=${id}`, AuthConfig())
            .then((res) => {
                alert("De Activated")
                const syntheticEvent = {
                    target: {},
                    currentTarget: {},
                    preventDefault: () => {},
                };
                handleSearch(syntheticEvent)
            })
            .catch((error) => {
                // Handle any errors here
                console.error("Error fetching data:", error);
            });
    }


    return (
        <>
            <Container fluid>

                <form onSubmit={handleSearch}>
                    <div className="form-group">
                        <div className="row">
                            <div className="col-4"><label htmlFor="patientNic">Patient nic</label></div>
                            <div className="col-5"><input
                                disabled={isPatient}
                                type="text"
                                className="form-control form-control-sm"
                                id="patientNic"
                                required
                                value={patientNic}
                                onChange={handlepatientNic}
                            /></div>
                            <div className="col-3">
                                <button type="submit" className="btn btn-secondary"><i
                                    className="fa-solid fa-magnifying-glass"></i> Search
                                </button>
                            </div>
                        </div>

                        <div className="form-group">
                            <div className="row">
                                <div className="col-4">
                                    <label htmlFor="Occurrence">Occurrence</label>
                                </div>
                                <div className="col-5">
                                    <select
                                        className="form-control"
                                        id="Occurrence"
                                        required
                                        value={occurrence}
                                        onChange={handleOccurrence}
                                    >
                                        <option value="">Select Occurrence</option>
                                        <option value="DAILY">DAILY</option>
                                        <option value="WEEKLY">WEEKLY</option>
                                        <option value="MONTHLY">MONTHLY</option>
                                        <option value="YEARLY">YEARLY</option>
                                    </select>
                                </div>
                            </div>
                        </div>


                    </div>
                </form>


                <div>
                    <Table striped hover>
                        <thead>
                        <tr>
                            {
                                thArray.map((prop, key) => {
                                    return (
                                        <th key={key}>{prop}</th>
                                    );
                                })
                            }
                        </tr>
                        </thead>
                        <tbody style={{fontSize: "0.7rem"}}>
                        {
                            tdArray.map((prop, key) => {
                                return (
                                    <tr key={key}>{
                                        prop.map((prop, key) => {
                                            return (
                                                <td key={key}>{prop}</td>
                                            );
                                        })
                                    }</tr>
                                )
                            })
                        }
                        </tbody>
                    </Table>

                </div>

                {!isPatient && (

                    <div className="card mt-5">
                        <div className="card-body" style={{display:"flex", justifyContent:"space-around"}}>
                            <form className={`col-9`} onSubmit={handleSaveRecommendations}>
                                <div className="row">
                                    <div className="col-3"><label htmlFor="patientNic">Patient nic</label></div>
                                    <div className="col-9"><input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="patientNic"
                                        required
                                        value={patientNicAddForm}
                                        onChange={handlepatientNicAddForm}
                                    /></div>
                                </div>

                                <div className="form-group mt-2">
                                    <div className="row">
                                        <div className="col-3">
                                            <label htmlFor="Occurrence">Occurrence</label>
                                        </div>
                                        <div className="col-9">
                                            <select
                                                className="form-control"
                                                id="Occurrence"
                                                required
                                                value={occurrenceAddForm}
                                                onChange={handleOccurrenceAddForm}
                                            >
                                                <option value="">Select Occurrence</option>
                                                <option value="DAILY">DAILY</option>
                                                <option value="WEEKLY">WEEKLY</option>
                                                <option value="MONTHLY">MONTHLY</option>
                                                <option value="YEARLY">YEARLY</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div className="form-group">
                                    <div className="row">
                                        <div className="col-3">
                                            <label htmlFor="Occurrence">Recommendation Type</label>
                                        </div>
                                        <div className="col-9">
                                            <select
                                                className="form-control"
                                                id="Occurrence"
                                                required
                                                value={recommendationType}
                                                onChange={handleRecommendationType}
                                            >
                                                <option value="">Select Recommendation Type</option>
                                                <option value="DIABETIC_FOOT_CARE">DIABETIC FOOT CARE</option>
                                                <option value="DIABETIC_RETINOPATHY_SCREENING">DIABETIC RETINOPATHY SCREENING
                                                </option>
                                                <option value="DENTAL_SCREENING">DENTAL SCREENING</option>
                                                <option value="FAMILY_SCREENING">FAMILY SCREENING</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div className="row">
                                    <div className="col-3"><label htmlFor="Topic">Topic</label></div>
                                    <div className="col-9"><input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="Topic"
                                        required
                                        value={topic}
                                        onChange={handleTopic}
                                    /></div>
                                </div>

                                <div className="form-group mt-2">
                                    <div className="row">
                                        <div className="col-3"><label htmlFor="exampleFormControlTextarea1">Recommendation</label>
                                        </div>
                                        <div className="col-9"><textarea onChange={handleRecommendation} className="form-control"
                                                                         id="exampleFormControlTextarea1"
                                                                         rows="3"></textarea></div>
                                    </div>
                                </div>

                                <div className="form-group mt-2 float-right">
                                    <button type="reset" onClick={resetForm}
                                            className="btn btn-danger mr-1">RESET <i
                                        className="fa-solid fa-rotate-right"></i>
                                    </button>
                                    <button type="submit"
                                            className="btn btn-success">SAVE <i className="fa-solid fa-floppy-disk"></i>
                                    </button>
                                </div>

                            </form>
                        </div>
                    </div>


                )}


            </Container>


        </>
    );
}

export default Recommendation;
