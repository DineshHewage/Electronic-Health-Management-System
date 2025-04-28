//


import React, { useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";
import {AuthConfig} from "./AuthConfig";
import {BASE_URL} from "../config/Config";

export default function ProtectiveRoute() {
    const token = localStorage.getItem("token");
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        if (token == null || token === "") {
            navigate("/login");
        } else {
            axios.post(`${BASE_URL}/api/v2/auth/validateToken`,{},AuthConfig())
                .then((res) => {
                    if (res.status === 200) {
                        // Token is valid, navigate to the requested URL
                        navigate(location.pathname);
                    } else {
                        console.log("Token validation failed with status code:", res.status);
                        navigate("/login");
                    }
                })
                .catch((error) => {
                    console.error(error);
                    navigate("/login");
                });
        }
    }, [token, navigate, location.pathname]);

    return null;
}
