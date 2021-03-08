const SUCCESS_CREATE_USER = 'successCreateUser';
const ERROR_CREATE_USER = 'errorCreateUser';
const FORM_ID = 'createUserForm';
const SUBMIT = 'submit';
const WAS_VALIDATED = 'was-validated';
const PASSWORD = 'password';

const form = document.getElementById(FORM_ID);

form.addEventListener(SUBMIT, (event) => {
    event.preventDefault();
    event.stopPropagation();
    form.classList.add(WAS_VALIDATED);
    if (confirmPassword.value === password.value) {
        console.log("Confirmed");
        if (form.checkValidity() === true) {
            HTMLFormElement.prototype.submit.call(form);
            form.classList.remove(WAS_VALIDATED);
        }
    } else {
       alert("Password not confirmed");
    }
});

setTimeout(() => {
    let successCreateUser = document.getElementById(SUCCESS_CREATE_USER);
    let errorCreateUser = document.getElementById(ERROR_CREATE_USER);
    if (successCreateUser != null) {
        successCreateUser.hidden = true;
    }
    if (errorCreateUser != null) {
        errorCreateUser.hidden = true;
    }
}, 2000);
