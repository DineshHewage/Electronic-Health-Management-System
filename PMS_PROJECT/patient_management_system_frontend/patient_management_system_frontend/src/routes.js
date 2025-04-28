import Dashboard from "views/Dashboard.js";
import Recommendation from "views/Recommendation.jsx";
import DataEnry from "views/DataEnry.jsx";
import UserView from "views/UserView.jsx";

const dashboardRoutes = [
    {
        path: "/dashboard",
        name: "Dashboard",
        icon: "nc-icon nc-chart-pie-35",
        component: Dashboard,
        layout: "/admin",
        view: ["ADMIN", "USER"]
    },
    {
        path: "/recommendations",
        name: "Recommendations",
        icon: "nc-icon nc-circle-09",
        component: Recommendation,
        layout: "/admin",
        view: "ADMIN"
    },
    {
        path: "/data-entry",
        name: "Data entry",
        icon: "nc-icon nc-notes",
        component: DataEnry,
        layout: "/admin",
        view: ["ADMIN", "USER"]
    },
    {
        path: "/user-view",
        name: "User Manager",
        icon: "nc-icon nc-atom",
        component: UserView,
        layout: "/admin",
        view: "ADMIN"

    }
];

export default dashboardRoutes;
