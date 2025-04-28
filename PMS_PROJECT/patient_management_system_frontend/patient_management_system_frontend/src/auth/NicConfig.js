export const NicConfig = () => {
    const nic = localStorage.getItem("nic"); // Replace with your actual access token
    console.log("Access Token:", nic);
    return nic
};