import React, { useEffect } from "react";
import { Outlet, useLocation, useNavigate, Route, Routes } from "react-router-dom";

import AdminNavbar from "components/Navbars/AdminNavbar";
import Footer from "components/Footer/Footer";
import Sidebar from "components/Sidebar/Sidebar";
import FixedPlugin from "components/FixedPlugin/FixedPlugin.js";

import routes from "routes.js";

import sidebarImage from "assets/img/sidebar-3.jpg";

function Admin() {
  const [image, setImage] = React.useState(sidebarImage);
  const [color, setColor] = React.useState("black");
  const [hasImage, setHasImage] = React.useState(true);
  const location = useLocation();
  const navigate = useNavigate();

  const watermarkStyle = {
    position: 'fixed',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    opacity: 0.08, // Set the desired opacity (0 to 1)
    pointerEvents: 'none',
  };

  useEffect(() => {
    document.documentElement.scrollTop = 0;
    document.scrollingElement.scrollTop = 0;

    if (
        window.innerWidth < 993 &&
        document.documentElement.className.indexOf("nav-open") !== -1
    ) {
      document.documentElement.classList.toggle("nav-open");
      var element = document.getElementById("bodyClick");
      element.parentNode.removeChild(element);
    }
  }, [location]);

  const getRoutes = (routes) => {
    return routes.map((prop, key) => {
      if (prop.layout === "/admin") {
        return (
            <Route
                key={key}
                path={prop.path}
                element={<prop.component />}
            />
        );
      } else {
        return null;
      }
    });
  };

  return (
      <>
        <div className="wrapper">
          <Sidebar color={color} image={hasImage ? image : ""} routes={routes} />
          <div className="main-panel">
            <img src={sidebarImage} alt="Watermark" style={watermarkStyle} />
            <AdminNavbar />
            <div className="content">
              <Routes>{getRoutes(routes)}</Routes>
              <Outlet />
            </div>
            <Footer />
          </div>
        </div>
        <FixedPlugin
            hasImage={hasImage}
            setHasImage={() => setHasImage(!hasImage)}
            color={color}
            setColor={(color) => setColor(color)}
            image={image}
            setImage={(image) => setImage(image)}
        />
      </>
  );
}

export default Admin;
