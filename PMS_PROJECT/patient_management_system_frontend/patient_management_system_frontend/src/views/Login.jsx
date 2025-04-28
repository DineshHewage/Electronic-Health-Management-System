import React , {useState , useEffect} from "react";
import sideBarImage1 from "assets/img/sidebar-1.jpg";

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
import {useNavigate} from "react-router-dom";
import {BASE_URL} from "../config/Config";

export default function Login() {

    const divStyle = {
        backgroundImage: "url(" + sideBarImage1 + ")",
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
        width: '100vw',
        height: '100vh',
    };
    const navigate = useNavigate();
    const [email , setEmail] = useState("")
    const [password , setPassword] = useState("")

    const handleSubmit = (e) =>{
        e.preventDefault()
        if(email == null || email == "" ){
            alert("Please provide emaila")
            return;
        }else if( password == null || password == ""){
            alert("Please provide password")
            return;
        }

        const data = {
            email : email,
            password : password
        }
        axios.post(`${BASE_URL}/api/v1/auth/login` , data).then((res)=>{
            const u = {
                username:res.data.email,
                token:res.data.token,
                // is_super_user:res.data.is_super_user,
                role:res.data.role
            }
            console.log(u)
            // Convert the object to a JSON string
            const uString = JSON.stringify(u);
            localStorage.setItem("loged_user" , res.data.email)
            localStorage.setItem("loged_user_obj" , uString)
            localStorage.setItem("token" , res.data.token)
            localStorage.setItem("role" , res.data.role)
            localStorage.setItem("nic" , res.data.nic)
            navigate("/admin/dashboard");
        }).catch((e)=>{
            alert("Login Field")
        })
    }

    return (
        <>
            <div style={divStyle}>
                <div style={{display:"flex" , justifyContent:"space-around" , width:"100%" , height:"100%"}}>
                    <div style={{display:"flex",flexDirection : "column" , justifyContent:"space-around"}}>
                <Card style={{ width: '50vw' , height:"50vh" }}>
                    <Card.Body>
                        <Card.Title><h3>Patient Management System</h3>Login</Card.Title>
                        <hr/>
                        <Card.Body>
                            <Form onSubmit={handleSubmit}>
                                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                    <Form.Label>Email address</Form.Label>
                                    <Form.Control type="email" onChange={(e)=>setEmail(e.target.value)} placeholder="name@example.com" />
                                </Form.Group>

                                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                    <Form.Label>Password</Form.Label>
                                    <Form.Control type="password" onChange={(e)=>setPassword(e.target.value)} placeholder="password" />
                                </Form.Group>

                                <Form.Group className="mb-3" style={{float:"right"}} controlId="exampleForm.ControlInput1">
                                    <Button variant="danger" className="mr-3" type="reset">
                                        RESET
                                    </Button>
                                    <Button variant="success" type="submit">
                                        LOGIN
                                    </Button>
                                </Form.Group>

                            </Form>
                        </Card.Body>
                    </Card.Body>
                </Card>
                    </div>
                </div>
            </div>
        </>
    )
}

