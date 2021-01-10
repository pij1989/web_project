const REGISTER_FORM = 'registerForm';
const SUBMIT = 'submit';
const WAS_VALIDATED = 'was-validated';
const EMAIL_VALIDATION_MESSAGE = 'emailValidationMessage';
const PASSWORD_VALIDATION_MESSAGE = 'passwordValidationMessage';
const VALID_FEEDBACK = 'valid-feedback';
const INVALID_FEEDBACK = 'invalid-feedback';
const CONFIRM_PASSWORD = 'confirmPassword';

let loginForm = document.getElementById(REGISTER_FORM);
let confirmPassword = document.getElementById(CONFIRM_PASSWORD);
let passwordValidationMessage = document.getElementById(PASSWORD_VALIDATION_MESSAGE);
let emailValidationMessage = document.getElementById(EMAIL_VALIDATION_MESSAGE);

const addValidFeedback = (element, validMessage) => {
    if (element.classList.contains(INVALID_FEEDBACK)) {
        element.classList.remove(INVALID_FEEDBACK);
    }
    element.classList.add(VALID_FEEDBACK);
    element.innerText = validMessage;
};

const addInvalidFeedback = (element, invalidMessage) => {
    if (element.classList.contains(VALID_FEEDBACK)) {
        element.classList.remove(VALID_FEEDBACK);
    }
    element.classList.add(INVALID_FEEDBACK);
    element.innerText = invalidMessage;
};

loginForm.addEventListener(SUBMIT, (event) => {
    event.preventDefault();
    event.stopPropagation();
    if (loginForm.email.checkValidity() === true) {
        addValidFeedback(emailValidationMessage, 'Looks good!')
    } else {
        addInvalidFeedback(emailValidationMessage, 'Invalid email')
    }
    if (loginForm.password.checkValidity() === true) {
        addValidFeedback(passwordValidationMessage, 'Looks good!')
    } else {
        addInvalidFeedback(passwordValidationMessage, 'Invalid password')
    }
    loginForm.classList.add(WAS_VALIDATED);
    if (loginForm.checkValidity() === true && confirmPassword.value === loginForm.password.value) {
        HTMLFormElement.prototype.submit.call(loginForm);
        loginForm.classList.remove(WAS_VALIDATED);
    }
});


