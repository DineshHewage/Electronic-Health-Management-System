// Your function for Axios request configuration
export const AuthConfig = () => {
    const accessToken = localStorage.getItem("token"); // Replace with your actual access token
    console.log("Access Token:", accessToken);
    return {
        headers: {
            'Authorization': `Bearer ${accessToken}`,
        },
    };
};