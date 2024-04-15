//login value: 0 not logged in, 1 logged in, 2 premium member
var loginValue = 0;

function updateLoginValue() {
    loginValue++;
    updateLoginButton();
}

function updateLoginButton(){
    const loginButton = document.getElementById('loginButton');
    const premiumButton = document.getElementById('premiumButton');

    switch(loginValue){
        case 0:
            loginButton.style.display = 'block';
            premiumButton.style.display = 'none';
            break;
        case 1:
            loginButton.style.display = 'none';
            premiumButton.style.display = 'block';
            break;
        case 2:
            loginButton.style.display = 'none';
            premiumButton.style.display = 'none';
            break;
    }
}

async function checkLoginStatus() {
    try {
        const response = await fetch("/* Address in Backend */", {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include'
        });

        if(!response.ok){
            throw new Error('Failed to fetch login status');
        }

        const data = await response.json();
        switch(data.status){
            case 'guest':
                loginValue = 0;
                break;
            case 'user':
                loginValue = 1;
                break;
            case 'premium':
                loginValue = 2;
                break;
            default:
                console.log("Invalid Status");
        }
        updateLoginButton();

    } catch (error) {
        console.error('Error Fetching Login', error);
    }
}



document.addEventListener('DOMContentLoaded', checkLoginStatus);