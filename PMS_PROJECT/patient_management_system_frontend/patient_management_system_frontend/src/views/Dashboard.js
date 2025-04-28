import React, {useEffect, useState} from "react";

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
    Tooltip, Spinner,
} from "react-bootstrap";
import ProtectiveRoute from "../auth/ProtectiveRoute";
import axios from "axios";
import {AuthConfig} from "../auth/AuthConfig";
import {BASE_URL} from "../config/Config";
import FlyInTextComponent from "./FlyInTextComponent";

function Dashboard() {
    ProtectiveRoute()

    const [dashboardData, setDashboardData] = useState();
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        if(isLoading) {
            axios.get(`${BASE_URL}/garden/dashboard/dashboard_view` , AuthConfig())
                .then((res) => {
                    setDashboardData(res.data)
                    setIsLoading(false)
                }).catch((e) => {
                console.log(e)
            })
        }
    }, [])

    if(isLoading){
        return (
            <FlyInTextComponent></FlyInTextComponent>
        )
    }


}

export default Dashboard;
