const REGISTER_FORM = 'registrationForm';
const FIRST_NAME = 'firstName';
const LAST_NAME = 'lastName';
const USERNAME = 'username';
const EMAIL = 'email';
const PASSWORD = 'password';
const CONFIRM_PASSWORD = 'confirmPassword';
const SUBMIT = 'submit';
const CHANGE = 'change';
const HIDDEN = 'hidden';
const WAS_VALIDATED = 'was-validated';
const FIRST_NAME_VALID_MESSAGE = 'firstNameValidMessage';
const FIRST_NAME_INVALID_MESSAGE = 'firstNameInvalidMessage';
const LAST_NAME_VALID_MESSAGE = 'lastNameValidMessage';
const LAST_NAME_INVALID_MESSAGE = 'lastNameInvalidMessage';
const USERNAME_VALID_MESSAGE = 'usernameValidMessage';
const USERNAME_INVALID_MESSAGE = 'usernameInvalidMessage';
const EMAIL_VALID_MESSAGE = 'emailValidMessage';
const EMAIL_INVALID_MESSAGE = 'emailInvalidMessage';
const PASSWORD_VALID_MESSAGE = 'passwordValidMessage';
const PASSWORD_INVALID_MESSAGE = 'passwordInvalidMessage';
const CONFIRM_PASSWORD_VALID_MESSAGE = 'confirmPasswordValidMessage';
const CONFIRM_PASSWORD_INVALID_MESSAGE = 'confirmPasswordInvalidMessage';
const CONFIRM_PASSWORD_NOT_EQUAL_MESSAGE = 'confirmPasswordNotEqualMessage';

let registrationForm = document.getElementById(REGISTER_FORM);
let firstName = document.getElementById(FIRST_NAME);
let lastName = document.getElementById(LAST_NAME);
let username = document.getElementById(USERNAME);
let email = document.getElementById(EMAIL);
let password = document.getElementById(PASSWORD);
let confirmPassword = document.getElementById(CONFIRM_PASSWORD);

let firstNameValidMessage = document.getElementById(FIRST_NAME_VALID_MESSAGE);
let firstNameInvalidMessage = document.getElementById(FIRST_NAME_INVALID_MESSAGE);
let lastNameValidMessage = document.getElementById(LAST_NAME_VALID_MESSAGE);
let lastNameInvalidMessage = document.getElementById(LAST_NAME_INVALID_MESSAGE);
let usernameValidMessage = document.getElementById(USERNAME_VALID_MESSAGE);
let usernameInvalidMessage = document.getElementById(USERNAME_INVALID_MESSAGE);
let emailValidMessage = document.getElementById(EMAIL_VALID_MESSAGE);
let emailInvalidMessage = document.getElementById(EMAIL_INVALID_MESSAGE);
let passwordValidMessage = document.getElementById(PASSWORD_VALID_MESSAGE);
let passwordInvalidMessage = document.getElementById(PASSWORD_INVALID_MESSAGE);
let confirmPasswordValidMessage = document.getElementById(CONFIRM_PASSWORD_VALID_MESSAGE);
let confirmPasswordInvalidMessage = document.getElementById(CONFIRM_PASSWORD_INVALID_MESSAGE);
let confirmPasswordNotEqualMessage = document.getElementById(CONFIRM_PASSWORD_NOT_EQUAL_MESSAGE);

const showValidationMessage = (element1, element2) => {
    if (element1.getAttribute(HIDDEN) != null) {
        element1.removeAttribute(HIDDEN);
    }
    if (element2.getAttribute(HIDDEN) == null) {
        element2.setAttribute(HIDDEN, HIDDEN);
    }
};

const checkValid = (formElement, validElement, invalidElement) => {
    if (formElement.checkValidity() === true) {
        showValidationMessage(validElement, invalidElement);
    } else {
        showValidationMessage(invalidElement, validElement);
    }
};

firstName.addEventListener(CHANGE, () => {
    checkValid(firstName, firstNameValidMessage, firstNameInvalidMessage);
});

lastName.addEventListener(CHANGE, () => {
    checkValid(lastName, lastNameValidMessage, lastNameInvalidMessage);
});

username.addEventListener(CHANGE, () => {
    checkValid(username, usernameValidMessage, usernameInvalidMessage);
});

email.addEventListener(CHANGE, () => {
    checkValid(email, emailValidMessage, emailInvalidMessage);
});

password.addEventListener(CHANGE, () => {
    if (confirmPasswordNotEqualMessage.getAttribute(HIDDEN) == null) {
        confirmPasswordNotEqualMessage.setAttribute(HIDDEN, HIDDEN);
    }
    checkValid(password, passwordValidMessage, passwordInvalidMessage);
});

confirmPassword.addEventListener(CHANGE, () => {
    if (confirmPasswordNotEqualMessage.getAttribute(HIDDEN) == null) {
        confirmPasswordNotEqualMessage.setAttribute(HIDDEN, HIDDEN);
    }
    checkValid(confirmPassword, confirmPasswordValidMessage, confirmPasswordInvalidMessage);
});

registrationForm.addEventListener(SUBMIT, (event) => {
    event.preventDefault();
    event.stopPropagation();
    checkValid(firstName, firstNameValidMessage, firstNameInvalidMessage);
    checkValid(lastName, lastNameValidMessage, lastNameInvalidMessage);
    checkValid(username, usernameValidMessage, usernameInvalidMessage);
    checkValid(email, emailValidMessage, emailInvalidMessage);
    checkValid(password, passwordValidMessage, passwordInvalidMessage);
    checkValid(confirmPassword, confirmPasswordValidMessage, confirmPasswordInvalidMessage);
    registrationForm.classList.add(WAS_VALIDATED);
    if (confirmPassword.value === password.value) {
        console.log("Confirmed");
        if (registrationForm.checkValidity() === true) {
            HTMLFormElement.prototype.submit.call(registrationForm);
            registrationForm.classList.remove(WAS_VALIDATED);
        }
    } else {
        console.log("Not confirmed");
        if (confirmPasswordNotEqualMessage.getAttribute(HIDDEN) != null) {
            confirmPasswordNotEqualMessage.removeAttribute(HIDDEN);
        }
    }
});


