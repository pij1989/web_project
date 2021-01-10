const locale = {
    'en': {
        'invalid.email': 'Invalid email',
        'invalid.password': 'Invalid password',
        'valid': 'Looks good!'
    },
    'ru': {
        'invalid.email': 'Невалидный адрес электронной почты',
        'invalid.password': 'Невалидный пароль',
        'valid': 'Валидные данные'
    }
};
const LOGIN_FORM = 'loginForm';
const SUBMIT = 'submit';
const CHANGE = 'change';
const WAS_VALIDATED = 'was-validated';
const EMAIL_VALIDATION_MESSAGE = 'emailValidationMessage';
const PASSWORD_VALIDATION_MESSAGE = 'passwordValidationMessage';
const VALID_FEEDBACK = 'valid-feedback';
const INVALID_FEEDBACK = 'invalid-feedback';

let loginForm = document.getElementById(LOGIN_FORM);
let passwordValidationMessage = document.getElementById(PASSWORD_VALIDATION_MESSAGE);
let emailValidationMessage = document.getElementById(EMAIL_VALIDATION_MESSAGE);
let alert = document.querySelector('.alert');
let language = 'en';

const setLocale = (lang) => {
    if (lang.trim() !== '') {
        console.log("Language: " + lang);
        language = lang;
    }
};

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

loginForm.email.addEventListener(CHANGE, () => {
    console.log("Email change...");
    if (alert != null) {
        alert.setAttribute('hidden', 'hidden');
    }
    if (loginForm.email.checkValidity() === true) {
        addValidFeedback(emailValidationMessage, locale[language]["valid"])
    } else {
        addInvalidFeedback(emailValidationMessage, locale[language]["invalid.email"])
    }
});

loginForm.password.addEventListener(CHANGE, () => {
    console.log("Password change...");
    if (alert != null) {
        alert.setAttribute('hidden', 'hidden');
    }
    if (loginForm.password.checkValidity() === true) {
        addValidFeedback(passwordValidationMessage, locale[language]["valid"])
    } else {
        addInvalidFeedback(passwordValidationMessage, locale[language]["invalid.password"])
    }
});

loginForm.addEventListener(SUBMIT, (event) => {
    event.preventDefault();
    event.stopPropagation();
    if (loginForm.email.checkValidity() === true) {
        addValidFeedback(emailValidationMessage, locale[language]["valid"])
    } else {
        addInvalidFeedback(emailValidationMessage, locale[language]["invalid.email"])
    }
    if (loginForm.password.checkValidity() === true) {
        addValidFeedback(passwordValidationMessage, locale[language]["valid"])
    } else {
        addInvalidFeedback(passwordValidationMessage, locale[language]["invalid.password"])
    }
    loginForm.classList.add(WAS_VALIDATED);
    if (loginForm.checkValidity() === true) {
        HTMLFormElement.prototype.submit.call(loginForm);
        loginForm.classList.remove(WAS_VALIDATED);
    }
});


