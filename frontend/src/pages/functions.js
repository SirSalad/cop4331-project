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

document.addEventListener('DOMContentLoaded', updateLoginButton);