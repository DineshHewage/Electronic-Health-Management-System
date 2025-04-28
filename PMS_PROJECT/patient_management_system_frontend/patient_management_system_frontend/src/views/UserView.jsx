import React, {useEffect, useState} from "react";

import {
    Table,
    Container,
} from "react-bootstrap";
import axios from "axios";
import ProtectiveRoute from "../auth/ProtectiveRoute";
import {AuthConfig} from "../auth/AuthConfig";
import {BASE_URL} from "../config/Config";
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

function UserView() {
    ProtectiveRoute()
    const thArray = ["USER ID", "USER NAME", "FIRST NAME", "LAST NAME", "ROLE", "ACTION"];
    const [tdArray, setTdArray] = useState([])

    const [selectedUser, setSelectedUser] = useState(null);
    const [updateForm, setUpdateForm] = useState(false);
    const [id, setId] = useState(0);
    const [email, setEmail] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [password, setPassword] = useState('');
    const [registrationNumber, setRegistrationNumber] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [hospitalName, setHospitalName] = useState('');
    const [nic, setNic] = useState('');
    const [bloodGroup, setBloodGroup] = useState('');
    const [dob, setDob] = useState('');

    const handleUserSelection = (event) => {
        setSelectedUser(event.target.value);

        if (event.target.value == "doctor") {
            loadUsers("/api/v2/doctor/all")
        } else if (event.target.value == "patient") {
            loadUsers("/api/v2/patient/all")
        } else if (event.target.value == "admin") {
            loadUsers("/api/v2/admin/all")
        }

    };

    const handleSearch = () => {
        console.log('Selected User:', selectedUser);
    };

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handleFirstNameChange = (event) => {
        setFirstName(event.target.value);
    };

    const handleLastNameChange = (event) => {
        setLastName(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleRegistrationNumberChange = (event) => {
        setRegistrationNumber(event.target.value);
    };

    const handlePhoneNumberChange = (event) => {
        setPhoneNumber(event.target.value);
    };

    const handleHospitalNameChange = (event) => {
        setHospitalName(event.target.value);
    };

    const handleDoctorSAveForm = (event) => {
        event.preventDefault()

        if(!updateForm) {

            axios.post(`${BASE_URL}/api/v2/doctor/create`, {
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password,
                registrationNumber: registrationNumber,
                phoneNumber: phoneNumber,
                hospitalName: hospitalName
            }, AuthConfig())
                .then((res) => {
                    alert("New doctor was created")
                    handleDoctorReset(event)
                })
                .catch((e) => {
                    console.log(e.response.data.message);
                    alert(e.response.data.message)
                });

        }else {
            axios.put(`${BASE_URL}/api/v2/doctor/updateDoctor?doctorId=${id}`, {
                firstName: firstName,
                lastName: lastName,
                registrationNumber: registrationNumber,
                phoneNumber: phoneNumber,
                hospitalName: hospitalName
            }, AuthConfig())
                .then((res) => {
                    alert("Update doctor was success")
                    handleDoctorReset(event)
                })
                .catch((e) => {
                    console.log(e.response.data.message);
                    alert(e.response.data.message)
                });
        }


    };

    const handlePatientSaveForm = (event) => {
        event.preventDefault()

        if(!updateForm) {

            axios.post(`${BASE_URL}/api/v2/patient/create`, {
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password,
                nic: nic,
                phoneNumber: phoneNumber,
                dob: dob,
                bloodGroup: bloodGroup
            }, AuthConfig())
                .then((res) => {
                    alert("New patient was created")
                    handlePatientReset(event)
                })
                .catch((e) => {
                    console.log(e.response.data.message);
                    alert(e.response.data.message)
                });

        }else {
            axios.put(`${BASE_URL}/api/v2/patient/updatePatient?patientId=${id}`, {
                firstName: firstName,
                lastName: lastName,
                nic: nic,
                phoneNumber: phoneNumber,
                dob: dob,
                bloodGroup: bloodGroup
            }, AuthConfig())
                .then((res) => {
                    alert("Update patient was success")
                    handlePatientReset(event)
                })
                .catch((e) => {
                    console.log(e.response.data.message);
                    alert(e.response.data.message)
                });
        }


    };


    const handleAdminSaveForm = (event) => {
        event.preventDefault()

        if(!updateForm) {

            axios.post(`${BASE_URL}/api/v2/admin/create`, {
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password,
                nic: nic,
                phoneNumber: phoneNumber,
                hospitalName : hospitalName
            }, AuthConfig())
                .then((res) => {
                    alert("New admin was created")
                    handlePatientReset(event)
                })
                .catch((e) => {
                    console.log(e.response.data.message);
                    alert(e.response.data.message)
                });

        }else {
            axios.put(`${BASE_URL}/api/v2/admin/updateAdmin?adminId=${id}`, {
                firstName: firstName,
                lastName: lastName,
                nic: nic,
                phoneNumber: phoneNumber,
                hospitalName : hospitalName
            }, AuthConfig())
                .then((res) => {
                    alert("Update admin was success")
                    handlePatientReset(event)
                })
                .catch((e) => {
                    console.log(e.response.data.message);
                    alert(e.response.data.message)
                });
        }


    };

    const handleDoctorReset = (event) => {
        event.preventDefault()
        setEmail(null);
        setFirstName('');
        setLastName('');
        setPassword(null);
        setRegistrationNumber('');
        setPhoneNumber('');
        setHospitalName('');
        setUpdateForm(false)
    };

    const handlePatientReset = (event) => {
        event.preventDefault()
        setEmail(null);
        setFirstName('');
        setLastName('');
        setPassword(null);
        setNic('');
        setPhoneNumber('');
        setBloodGroup('');
        setDob('');
        setUpdateForm(false)
    };

    const handleAdminReset = (event) => {
        event.preventDefault()
        setEmail(null);
        setFirstName('');
        setLastName('');
        setPassword(null);
        setNic('');
        setPhoneNumber('');
        setHospitalName('');
        setUpdateForm(false)
    };


    const handleDeleteDoctor = (id , role) => {
        if (role == "DOCTOR") {
            axios.delete(`${BASE_URL}/api/v2/doctor/deleteDoctor?doctorId=${id}`, AuthConfig())
                .then((res) => {
                    console.log(res)
                    loadUsers("/api/v2/doctor/all")
                })
                .catch((e) => {
                    console.log(e);
                });
        } else if (role == "USER") {
            axios.delete(`${BASE_URL}/api/v2/patient/deletePatient?patientId=${id}`, AuthConfig())
                .then((res) => {
                    console.log(res)
                    loadUsers("/api/v2/patient/all")
                })
                .catch((e) => {
                    console.log(e);
                });
        } else if (role == "DATA") {
            axios.delete(`${BASE_URL}/api/v2/admin/deleteAdmin?adminId=${id}`, AuthConfig())
                .then((res) => {
                    console.log(res)
                    loadUsers("/api/v2/admin/all")
                })
                .catch((e) => {
                    console.log(e);
                });
        }
    }


    const loadUsers = (SUB_URL) => {
        axios.get(`${BASE_URL}${SUB_URL}`, AuthConfig())
            .then((res) => {
                const tempArray = res.data.map((row) => [
                    row.id,
                    row.user.email,
                    row.user.firstName,
                    row.user.lastName,
                    row.user.role.role,
                    <div>
                        <button type="button" className="btn btn-success mr-3" onClick={()=>handleUpdate(row.id , row.user.role.role)}>
                            <i className="fa-solid fa-wrench"></i></button>
                        <button type="button" className="btn btn-danger" onClick={() => handleDeleteDoctor(row.id, row.user.role.role)}>
                            <i className="fa-regular fa-trash-can"></i></button>
                    </div>
                ]);
                setTdArray(tempArray);
            })
            .catch((error) => {
                // Handle any errors here
                console.error("Error fetching data:", error);
            });
    }

    const resetUpdateForm =(event)=>{
        setUpdateForm(false);
        handleDoctorReset(event);
    }

    const handleUpdate = (id, role) => {
        setUpdateForm(true);
        if (role == "DOCTOR") {

            axios.get(`${BASE_URL}/api/v2/doctor?doctorId=${id}`, AuthConfig())
                .then((res) => {
                    setEmail(res.data.user.email)
                    setFirstName(res.data.user.firstName)
                    setLastName(res.data.user.lastName)
                    setRegistrationNumber(res.data.registrationNumber)
                    setPhoneNumber(res.data.phoneNumber)
                    setHospitalName(res.data.hospitalName)

                    setId(res.data.id)
                    document.getElementById("doctormodalopenbtn").click();

                })
                .catch((error) => {
                    // Handle any errors here
                    console.error("Error fetching data:", error);
                });

        } else if (role == "USER") {

            axios.get(`${BASE_URL}/api/v2/patient?patientId=${id}`, AuthConfig())
                .then((res) => {
                    setEmail(res.data.user.email)
                    setFirstName(res.data.user.firstName)
                    setLastName(res.data.user.lastName)
                    setNic(res.data.nic)
                    setPhoneNumber(res.data.phoneNumber)
                    setBloodGroup(res.data.bloodGroup)
                    setDob(res.data.dob)

                    setId(res.data.id)
                    document.getElementById("patientmodalopenbtn").click();

                })
                .catch((error) => {
                    // Handle any errors here
                    console.error("Error fetching data:", error);
                });

        } else if (role == "DATA") {
            axios.get(`${BASE_URL}/api/v2/admin?adminId=${id}`, AuthConfig())
                .then((res) => {
                    setEmail(res.data.user.email)
                    setFirstName(res.data.user.firstName)
                    setLastName(res.data.user.lastName)
                    setNic(res.data.nic)
                    setPhoneNumber(res.data.phoneNumber)
                    setHospitalName(res.data.hospitalName)

                    setId(res.data.id)
                    document.getElementById("adminmodalopenbtn").click();

                })
                .catch((error) => {
                    // Handle any errors here
                    console.error("Error fetching data:", error);
                });
        }
    };

    return (
        <>
            <Container fluid>

                <div>
                    <button type="button" id="doctormodalopenbtn" className="btn btn-primary mr-3" data-toggle="modal" data-target="#doctormodal">
                        <i className="fa-solid fa-user-doctor mr-3"></i>  ADD NEW DOCTOR
                    </button>

                    <button type="button" id="patientmodalopenbtn" className="btn btn-primary mr-3" data-toggle="modal" data-target="#patientmodal">
                        <i className="fa-solid fa-person-walking-with-cane mr-3"></i> ADD NEW PATIENT
                    </button>

                    <button type="button" id="adminmodalopenbtn" className="btn btn-primary mr-3" data-toggle="modal" data-target="#adminmodal">
                        <i className="fa-solid fa-user-tie mr-3"></i> ADD NEW ADMIN
                    </button>

                </div>

                <div className="container mt-3 p-0 m-0">
                    <div className="col-6">
                        <div className="row">
                            <div className="col-3">
                                <div className="form-check">
                                    <input
                                        className="form-check-input"
                                        type="radio"
                                        name="userR"
                                        id="doctorRadio"
                                        value="doctor"
                                        onChange={handleUserSelection}
                                    />
                                    <label className="form-check-label" htmlFor="doctorRadio">
                                        Doctors
                                    </label>
                                </div>
                            </div>
                            <div className="col-3">
                                <div className="form-check">
                                    <input
                                        className="form-check-input"
                                        type="radio"
                                        name="userR"
                                        id="patientRadio"
                                        value="patient"
                                        onChange={handleUserSelection}
                                    />
                                    <label className="form-check-label" htmlFor="patientRadio">
                                        Patients
                                    </label>
                                </div>
                            </div>
                            <div className="col-3">
                                <div className="form-check">
                                    <input
                                        className="form-check-input"
                                        type="radio"
                                        name="userR"
                                        id="adminRadio"
                                        value="admin"
                                        onChange={handleUserSelection}
                                    />
                                    <label className="form-check-label" htmlFor="adminRadio">
                                        Admin
                                    </label>
                                </div>
                            </div>
                            <div className="col-3">
                                {/*<button type="button" className="btn btn-sm btn-info" onClick={handleSearch}>*/}
                                {/*    Search*/}
                                {/*</button>*/}
                            </div>
                        </div>
                    </div>
                </div>


                <div className={`mt-3`}>
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
                        <tbody>
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
            </Container>


            <div className="modal fade" style={{maxHeight: "90vh"}} id="doctormodal" tabIndex="-1"
                 aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog modal-dialog-scrollable">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="exampleModalLabel">DOCTOR FORM</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close" onClick={resetUpdateForm}>
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>

                        <div className="modal-body">
                            <form onSubmit={handleDoctorSAveForm}>
                                <div className="form-group">
                                    <label htmlFor="email1">Email address</label>
                                    <input
                                        type="email"
                                        className="form-control form-control-sm"
                                        id="email1"
                                        required
                                        aria-describedby="emailHelp"
                                        value={email}
                                        onChange={handleEmailChange}
                                        disabled={updateForm}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="fname">First name</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="fname"
                                        required
                                        value={firstName}
                                        onChange={handleFirstNameChange}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="lname">Last name</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="lname"
                                        required
                                        value={lastName}
                                        onChange={handleLastNameChange}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="password">Password</label>
                                    <input
                                        type="password"
                                        className="form-control form-control-sm"
                                        id="password"
                                        required
                                        value={password}
                                        onChange={handlePasswordChange}
                                        disabled={updateForm}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="registrationNumber">Reg. number</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="registrationNumber"
                                        required
                                        value={registrationNumber}
                                        onChange={handleRegistrationNumberChange}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="phoneNumber">Phone number</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="phoneNumber"
                                        required
                                        value={phoneNumber}
                                        onChange={handlePhoneNumberChange}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="hospitalName">Hospital name</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="hospitalName"
                                        required
                                        value={hospitalName}
                                        onChange={handleHospitalNameChange}
                                    />
                                </div>

                                {!updateForm && (
                                    <div className="modal-footer">
                                        <button type="reset" onClick={handleDoctorReset} className="btn btn-secondary" data-dismiss="modal">
                                            Close
                                        </button>
                                        <button type="submit" className="btn btn-primary">
                                            Save changes
                                        </button>
                                    </div>
                                )}

                                {updateForm && (
                                    <div className="modal-footer">
                                        <button type="reset" onClick={handleDoctorReset} className="btn btn-secondary" data-dismiss="modal">
                                            Close
                                        </button>
                                        <button type="submit" className="btn btn-primary">
                                            Update changes
                                        </button>
                                    </div>
                                )}
                            </form>
                        </div>


                    </div>
                </div>
            </div>

            <div className="modal fade" id="patientmodal" style={{maxHeight: "90vh"}} tabIndex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="exampleModalLabel">PATIENT FORM</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>

                        <div className="modal-body">
                            <form onSubmit={handlePatientSaveForm}>
                                <div className="form-group">
                                    <label htmlFor="email1">Email address</label>
                                    <input
                                        type="email"
                                        className="form-control form-control-sm"
                                        id="email1"
                                        required
                                        aria-describedby="emailHelp"
                                        value={email}
                                        onChange={handleEmailChange}
                                        disabled={updateForm}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="fname">First name</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="fname"
                                        required
                                        value={firstName}
                                        onChange={handleFirstNameChange}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="lname">Last name</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="lname"
                                        required
                                        value={lastName}
                                        onChange={handleLastNameChange}
                                    />
                                </div>

                                <div className="form-group row">
                                    <label htmlFor="dob" className="col-sm-5 col-form-label">Date of Birth</label>
                                    <div className="col-sm-7">
                                        <DatePicker
                                            selected={dob}
                                            onChange={(date) => setDob(date)}
                                            dateFormat="yyyy-MM-dd"
                                            className="form-control"
                                            placeholderText="Select Date of Birth"
                                            required
                                            isRequired
                                        />
                                    </div>
                                </div>

                                <div className="form-group">
                                    <label htmlFor="password">Password</label>
                                    <input
                                        type="password"
                                        className="form-control form-control-sm"
                                        id="password"
                                        required
                                        value={password}
                                        onChange={handlePasswordChange}
                                        disabled={updateForm}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="nic">NIC</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="nic"
                                        placeholder="Enter NIC"
                                        value={nic}
                                        onChange={(e) => setNic(e.target.value)}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="phoneNumber">Phone number</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="phoneNumber"
                                        required
                                        value={phoneNumber}
                                        onChange={handlePhoneNumberChange}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="bloodGroup">Blood Group</label>
                                    <select
                                        className="form-control"
                                        id="bloodGroup"
                                        value={bloodGroup}
                                        onChange={(e) => setBloodGroup(e.target.value)}
                                    >
                                        <option value="">Select Blood Group</option>
                                        <option value="AP">A+</option>
                                        <option value="AN">A-</option>
                                        <option value="BP">B+</option>
                                        <option value="BN">B-</option>
                                        <option value="ABP">AB+</option>
                                        <option value="ABN">AB-</option>
                                        <option value="OP">O+</option>
                                        <option value="ON">O-</option>
                                    </select>
                                </div>

                                {!updateForm && (
                                    <div className="modal-footer">
                                        <button type="reset" onClick={handlePatientReset} className="btn btn-secondary" data-dismiss="modal">
                                            Close
                                        </button>
                                        <button type="submit" className="btn btn-primary">
                                            Save changes
                                        </button>
                                    </div>
                                )}

                                {updateForm && (
                                    <div className="modal-footer">
                                        <button type="reset" onClick={handlePatientReset} className="btn btn-secondary" data-dismiss="modal">
                                            Close
                                        </button>
                                        <button type="submit" className="btn btn-primary">
                                            Update changes
                                        </button>
                                    </div>
                                )}
                            </form>
                        </div>

                    </div>
                </div>
            </div>

            <div className="modal fade" id="adminmodal" style={{maxHeight: "90vh"}} tabIndex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="exampleModalLabel">ADMIN FORM</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>

                        <div className="modal-body">
                            <form onSubmit={handleAdminSaveForm}>
                                <div className="form-group">
                                    <label htmlFor="email1">Email address</label>
                                    <input
                                        type="email"
                                        className="form-control form-control-sm"
                                        id="email1"
                                        required
                                        aria-describedby="emailHelp"
                                        value={email}
                                        onChange={handleEmailChange}
                                        disabled={updateForm}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="fname">First name</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="fname"
                                        required
                                        value={firstName}
                                        onChange={handleFirstNameChange}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="lname">Last name</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="lname"
                                        required
                                        value={lastName}
                                        onChange={handleLastNameChange}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="password">Password</label>
                                    <input
                                        type="password"
                                        className="form-control form-control-sm"
                                        id="password"
                                        required
                                        value={password}
                                        onChange={handlePasswordChange}
                                        disabled={updateForm}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="nic">NIC</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="nic"
                                        placeholder="Enter NIC"
                                        value={nic}
                                        onChange={(e) => setNic(e.target.value)}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="phoneNumber">Phone number</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="phoneNumber"
                                        required
                                        value={phoneNumber}
                                        onChange={handlePhoneNumberChange}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="hospitalName">Hospital name</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="hospitalName"
                                        required
                                        value={hospitalName}
                                        onChange={handleHospitalNameChange}
                                    />
                                </div>

                                {!updateForm && (
                                    <div className="modal-footer">
                                        <button type="reset" onClick={handleAdminReset} className="btn btn-secondary" data-dismiss="modal">
                                            Close
                                        </button>
                                        <button type="submit" className="btn btn-primary">
                                            Save changes
                                        </button>
                                    </div>
                                )}

                                {updateForm && (
                                    <div className="modal-footer">
                                        <button type="reset" onClick={handleAdminReset} className="btn btn-secondary" data-dismiss="modal">
                                            Close
                                        </button>
                                        <button type="submit" className="btn btn-primary">
                                            Update changes
                                        </button>
                                    </div>
                                )}
                            </form>
                        </div>

                    </div>
                </div>
            </div>

        </>
    );
}

export default UserView;
