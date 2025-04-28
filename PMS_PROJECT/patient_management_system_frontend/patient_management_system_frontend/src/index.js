import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route , Navigate } from "react-router-dom";
import reportWebVitals from './reportWebVitals';
import "bootstrap/dist/css/bootstrap.min.css";
import "./assets/css/animate.min.css";
import "./assets/scss/light-bootstrap-dashboard-react.scss";
import "./assets/css/demo.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import AdminLayout from "layouts/Admin.js";
import Login from "./views/Login";
const root = ReactDOM.createRoot(document.getElementById('root'));



root.render(
  <React.StrictMode>
      <BrowserRouter>
          <Routes>

              <Route path="/login" element={<Login/>} />
              <Route path="/admin/*" element={<AdminLayout />} />
              <Route path="/" element={<Navigate to="/admin/dashboard" />} />

          </Routes>
      </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();
