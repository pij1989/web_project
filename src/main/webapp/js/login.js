const LOGIN_FORM = 'loginForm';
const SUBMIT = 'submit';
const CHANGE = 'change';
const HIDDEN = 'hidden';
const WAS_VALIDATED = 'was-validated';
const EMAIL_VALID_MESSAGE = 'emailValidMessage';
const EMAIL_INVALID_MESSAGE = 'emailInvalidMessage';
const PASSWORD_VALID_MESSAGE = 'passwordValidMessage';
const PASSWORD_INVALID_MESSAGE = 'passwordInvalidMessage';

let loginForm = document.getElementById(LOGIN_FORM);
let emailValidMessage = document.getElementById(EMAIL_VALID_MESSAGE);
let emailInvalidMessage = document.getElementById(EMAIL_INVALID_MESSAGE);
let passwordValidMessage = document.getElementById(PASSWORD_VALID_MESSAGE);
let passwordInvalidMessage = document.getElementById(PASSWORD_INVALID_MESSAGE);

let alert = document.querySelector('.alert');

const showValidationMessage = (element1, element2) => {
    if(element1.getAttribute(HIDDEN) != null){
        element1.removeAttribute(HIDDEN);
    }
    if(element2.getAttribute(HIDDEN) == null){
        element2.setAttribute(HIDDEN,HIDDEN);
    }
};

loginForm.email.addEventListener(CHANGE, () => {
    console.log("Email change...");
    if (alert != null) {
        alert.setAttribute(HIDDEN, HIDDEN);
    }
    if (loginForm.email.checkValidity() === true) {
        showValidationMessage(emailValidMessage, emailInvalidMessage);
    } else {
        showValidationMessage(emailInvalidMessage, emailValidMessage);
    }
});

loginForm.password.addEventListener(CHANGE, () => {
    console.log("Password change...");
    if (alert != null) {
        alert.setAttribute(HIDDEN, HIDDEN);
    }
    if (loginForm.password.checkValidity() === true) {
        showValidationMessage(passwordValidMessage, passwordInvalidMessage);
    } else {
        showValidationMessage(passwordInvalidMessage,passwordValidMessage);
    }
});

loginForm.addEventListener(SUBMIT, (event) => {
    event.preventDefault();
    event.stopPropagation();
    if (loginForm.email.checkValidity() === true) {
        showValidationMessage(emailValidMessage, emailInvalidMessage);
    } else {
        showValidationMessage(emailInvalidMessage, emailValidMessage);
    }
    if (loginForm.password.checkValidity() === true) {
        showValidationMessage(passwordValidMessage, passwordInvalidMessage);
    } else {
        showValidationMessage(passwordInvalidMessage,passwordValidMessage);
    }
    loginForm.classList.add(WAS_VALIDATED);
    if (loginForm.checkValidity() === true) {
        HTMLFormElement.prototype.submit.call(loginForm);
        loginForm.classList.remove(WAS_VALIDATED);
    }
});
