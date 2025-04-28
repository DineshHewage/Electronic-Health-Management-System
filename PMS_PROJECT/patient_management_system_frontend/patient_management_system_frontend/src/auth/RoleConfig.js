export const RoleConfig = () => {
    const role = localStorage.getItem("role"); // Replace with your actual access token
    console.log("Access Token:", role);

    if(role === "DOCTOR"){
        return "ADMIN"
    }else if(role === "ADMIN"){
        return "ADMIN"
    }else if(role === "USER"){
        return "PATIENT"
    }
};
