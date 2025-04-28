import React, {useEffect, useState} from "react";

// react-bootstrap components
import {
    Button,
    Table,
    Container,

} from "react-bootstrap";
import axios from "axios";
import ProtectiveRoute from "../../auth/ProtectiveRoute";
import {AuthConfig} from "../../auth/AuthConfig";
import {BASE_URL} from "../../config/Config";
import DatePicker from "react-datepicker";
import LineGraph from "../LineGraph";
import {RoleConfig} from "../../auth/RoleConfig";
import {NicConfig} from "../../auth/NicConfig";

function BP({closeParent}) {
    ProtectiveRoute()
    const thArray = ["USER ID", "REPORT ID" , "PATENT NAME","NIC","REPORT DATE","SCR","ACTION"];
    const [tdArray , setTdArray] = useState([])


    const[refresh, setRefresh] = useState(false);
    const [patientNic , setPatientNic] = useState("")
    const [patientId , setPatientId] = useState("")
    const [file, setFile] = useState(null);
    const [preview, setPreview] = useState(null);
    const [reportedDate, setReportedDate] = useState('');

    const [scr, setScr] = useState(0);

    const [disabled, setDisabled] = useState(false);
    const [labels , setLabels] = useState([]);
    const [data,setData] = useState([]);

    const[patientName , setPatientName] = useState("");
    const[email , setEmail] = useState("");
    const[nic , setNic] = useState("");

    const [isPatient, setIsPatient] = useState(true)

    useEffect(()=>{
        if(RoleConfig() == "PATIENT"){
            setIsPatient(true)
            setPatientNic(NicConfig())
        }else {
            setIsPatient(false)
        }
    },[])


    const handleDateChange = (date) => {
        // Zero out the time components
        const newDate = new Date(date);
        newDate.setHours(0, 0, 0, 0);

        setReportedDate(newDate.toISOString().slice(0, 10));
    };

    const handlepatientNic = (event) => {
        setPatientNic(event.target.value);
    };

    const handleScr = (event) => {
        setScr(event.target.value);
    };

    const handleDisable = () =>{
        setScr(0)
        setFile(null)
        setPreview(null)
        setReportedDate("")
        setDisabled(false)
    }

    const handleFileChange = (e) => {
        const selectedFile = e.target.files[0];

        // Preview the selected file (for image or PDF)
        if (selectedFile && (selectedFile.type.startsWith('image/') || selectedFile.type === 'application/pdf')) {
            const reader = new FileReader();

            reader.onloadend = () => {
                setPreview(reader.result);
            };

            reader.readAsDataURL(selectedFile);
        } else {
            // Clear preview if the selected file is not an image or PDF
            setPreview(null);
        }

        setFile(selectedFile);
    };

    const loadReportData = () => {
        axios.get(`${BASE_URL}/api/v2/reports/scriReport?nic=${patientNic}`, AuthConfig())
            .then((res) => {
                setLabels(res.data.labels)
                setData(res.data.data)
            })
            .catch((e) => {
                if(e.response) {
                    console.log(e.response.data.message);
                    alert(e.response.data.message)
                }
            });
    }


    const handleSearch = (event) => {
        axios.get(`${BASE_URL}/api/v2/reports/findAllScrBy?nic=${patientNic}`, AuthConfig())
            .then((res) => {
                const tempArray = res.data.map((row) => [
                    row.patient.id,
                    row.id,
                    row.patient.user.firstName,
                    row.patient.nic,
                    row.reportedDate,
                    row.serumCreatinine,
                    <div>
                        <button type="button" className="btn btn-success mr-3" onClick={()=>handleView(row.id)}>
                            <i className="fa-regular fa-eye"></i></button>
                        <button type="button" className="btn btn-danger" disabled={isPatient} onClick={()=>handleDeletScr(row.id)}>
                            <i className="fa-regular fa-trash-can"></i></button>
                    </div>
                ]);
                setTdArray(tempArray);

                setPatientId(tempArray[0][0])

                res.data.map((row)=>{
                    setPatientName(row.patient.user.firstName)
                    setEmail(row.patient.user.email)
                    setNic(row.patient.nic)
                })

                loadReportData()
                setRefresh(true)
            })
            .catch((error) => {
                // Handle any errors here
                console.error("Error fetching data:", error);
            });

        if(!isPatient){
            setPatientNic("")
        }


    };

    const handleScrSave = (event) =>{
        event.preventDefault()
        const formData = new FormData();
        formData.append('serumCreatinine', scr);
        formData.append('patientId', patientId);
        formData.append('reportedDate', reportedDate);
        formData.append('report', file);

        axios.post(`${BASE_URL}/api/v2/reports/addScr`, formData, AuthConfig())
            .then((res) => {
                alert("SCR was successfully added")
                setRefresh(false)
                // window.location.reload();
                closeParent()
            })
            .catch((e) => {
                if(e.response) {
                    console.log(e.response.data.message);
                    alert(e.response.data.message)
                }
            });

    }

    const handleView = (id) => {
        setDisabled(true)
        document.getElementById("viewarea").click();

        axios.get(`${BASE_URL}/api/v2/reports/scr?id=${id}`, AuthConfig())
            .then((res) => {
                setScr(res.data.serumCreatinine)
                setReportedDate(res.data.reportedDate)
                setFile(res.data.fileDir)
                setPreview(res.data.fileDir)
            })
            .catch((e) => {
                if(e.response) {
                    console.log(e.response.data.message);
                    alert(e.response.data.message)
                }
            });

    }

    const handleDeletScr = (id) =>{
        axios.post(`${BASE_URL}/api/v2/reports/removeScr?scr=${id}`,{}, AuthConfig())
            .then((res) => {
                alert("Selected item was removed")
                closeParent()
            })
            .catch((e) => {
                if(e.response) {
                    console.log(e.response.data.message);
                    alert(e.response.data.message)
                }
            });
    }



    return (
        <>
            <Container fluid>

                <div>
                    <div className="form-group">
                        <div className="row">
                            <div className="col-4"><label htmlFor="patientNic">Patient nic</label></div>
                            <div className="col-5">                        <input
                                type="text"
                                className="form-control form-control-sm"
                                id="patientNic"
                                required
                                disabled={isPatient}
                                value={patientNic}
                                onChange={handlepatientNic}
                            /></div>
                            <div className="col-3">
                                <button type="button" className="btn btn-secondary" onClick={handleSearch}><i
                                    className="fa-solid fa-magnifying-glass"></i> Search</button></div>
                        </div>


                    </div>
                </div>

                {refresh && (
                    <div>

                        <div>
                            <span>Patient Name : <strong>{patientName}</strong> </span> <br/>
                            <span>Patient Email : <strong>{email}</strong> </span> <br/>
                            <span>Patient NIC : <strong>{nic}</strong> </span> <br/>
                            <hr/>
                            <hr/>
                        </div>


                        <div className="accordion" id="accordionExample">

                                <button className="btn btn-light btn-block text-left" type="button"
                                        data-toggle="collapse" data-target="#collapseOne" aria-expanded="true"
                                        aria-controls="collapseOne">
                                    SCR SUMMARY
                                </button>


                                <div id="collapseOne" className="collapse show" aria-labelledby="headingOne"
                                     data-parent="#accordionExample">

                                    <div>
                                        <LineGraph labels={labels} data={data} labelTest={"FBS"} />
                                    </div>


                                </div>

                                <br/>



                                <button className="btn btn-light btn-block text-left collapsed" type="button"
                                        data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false"
                                        aria-controls="collapseTwo">
                                    SCR TABLE VIEW
                                </button>

                                <div id="collapseTwo" className="collapse" aria-labelledby="headingTwo"
                                     data-parent="#accordionExample">

                                    <div>
                                        <Table striped hover>
                                            <thead>
                                            <tr>
                                                {thArray.map((prop, key) => (
                                                    <th key={key}>{prop}</th>
                                                ))}
                                            </tr>
                                            </thead>
                                            <tbody>
                                            {tdArray.map((prop, key) => (
                                                <tr key={key}>
                                                    {prop.map((prop, key) => (
                                                        <td key={key}>{prop}</td>
                                                    ))}
                                                </tr>
                                            ))}
                                            </tbody>
                                        </Table>
                                    </div>

                                </div>


                                <br/>


                                <button className="btn btn-light btn-block text-left collapsed" type="button" id = "viewarea"
                                        data-toggle="collapse" data-target="#collapseThree"
                                        aria-expanded="false" aria-controls="collapseThree">
                                    SCR FORM
                                </button>

                                <div id="collapseThree" className="collapse" aria-labelledby="headingThree"
                                     data-parent="#accordionExample">
                                    <div>
                                        <div>
                                            <div className="card" style={{ border: '1px solid rgba(135, 206, 250, 1)' }}>
                                                <div className="card-body">
                                                    <h5 className="card-title">SRC FORM</h5>
                                                    <hr/>
                                                    <form onSubmit={handleScrSave} >

                                                        <div className="row">

                                                            <div className="col-6">
                                                                <div className="form-group">
                                                                    <label htmlFor="fbs">BP value</label>
                                                                    <input disabled={disabled} type="number" required onChange={handleScr} value={scr} className="form-control" id="examplefbs"
                                                                           placeholder="Enter Serum Creatinine"></input>
                                                                </div>

                                                                <div className="form-group">
                                                                    <label htmlFor="file">Upload File</label>
                                                                    <input
                                                                        type="file"
                                                                        required
                                                                        className="form-control-file"
                                                                        disabled={disabled}
                                                                        id="file"
                                                                        onChange={handleFileChange}
                                                                    />
                                                                </div>

                                                                <div className="form-group row">
                                                                    <label htmlFor="dob" className="col-sm-3 col-form-label">Reported date</label>
                                                                    <div className="col-sm-7">
                                                                        <DatePicker
                                                                            selected={reportedDate}
                                                                            onChange={(date) => handleDateChange(date)}
                                                                            dateFormat="yyyy-MM-dd"
                                                                            disabled={disabled}
                                                                            className="form-control"
                                                                            placeholderText="Reported date"
                                                                            required
                                                                            isRequired
                                                                        />
                                                                    </div>
                                                                </div>

                                                                <button type="reset"
                                                                        className="btn btn-secondary mr-2" disabled={isPatient} onClick={()=>setPreview(null)}>RESET <i
                                                                    className="fa-solid fa-rotate-right"></i>
                                                                </button>
                                                                {disabled && (<>
                                                                    <button type="button" disabled={isPatient} onClick={handleDisable}
                                                                            className="btn btn-warning" >ADD NEW <i
                                                                        className="fa-solid fa-plus"></i>
                                                                    </button>
                                                                </>)}

                                                                {!disabled && (<>
                                                                    <button type="submit" disabled={isPatient}
                                                                            className="btn btn-success" >SAVE <i
                                                                        className="fa-solid fa-floppy-disk"></i>
                                                                    </button>
                                                                </>)}


                                                            </div>

                                                            <div className="col-6">
                                                                {preview && !disabled && (
                                                                    <div className="preview-container" style={{ maxWidth: '100%', maxHeight: '300px', overflow: 'auto' }}>
                                                                        <p>Preview:</p>
                                                                        {file.type.startsWith('image/') ? (
                                                                            <img src={preview} alt="Preview" className="preview-image" style={{ width: '100%', height: 'auto' }} />
                                                                        ) : (
                                                                            <embed src={preview} type={file.type} width="100%" height="300px" />
                                                                        )}
                                                                    </div>
                                                                )}

                                                                {preview && disabled && (
                                                                    <div className="preview-container" style={{ maxWidth: '100%', maxHeight: '300px', overflow: 'auto' }}>
                                                                        <div>
                                                                            <p>File:</p>
                                                                            <a href={`file:///${file.replace(/\\/g, '/')}`} download>
                                                                                Download File
                                                                            </a>
                                                                        </div>
                                                                    </div>
                                                                )}

                                                            </div>
                                                        </div>


                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                        </div>




                    </div>
                )}


            </Container>



        </>
    );
}

export default BP;
