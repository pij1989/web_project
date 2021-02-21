const FORM_ID = 'changePasswordForm';
const SUBMIT = 'submit';
const INPUT = 'input';
const EMAIL = 'email';
const CHANGE_PASSWORD_SUCCESS = 'changePasswordSuccess';
const CHANGE_PASSWORD_ERROR = 'changePasswordError';
const WAS_VALIDATED = 'was-validated';

const form = document.getElementById(FORM_ID);
const formElements = form.elements;

Array.prototype.forEach.call(formElements, (element) => {
    element.addEventListener(INPUT, (event) => {
        let successCreateProduct = document.getElementById(CHANGE_PASSWORD_SUCCESS);
        let errorCreateProduct = document.getElementById(CHANGE_PASSWORD_ERROR);
        if (successCreateProduct != null) {
            successCreateProduct.hidden = true;
        }
        if (errorCreateProduct != null) {
            errorCreateProduct.hidden = true;
        }
    });
});

form.addEventListener(SUBMIT, (event) => {
    event.preventDefault();
    event.stopPropagation();
    form.classList.add(WAS_VALIDATED);
    if (form.checkValidity() === true) {
        HTMLFormElement.prototype.submit.call(form);
        form.classList.remove(WAS_VALIDATED);
    }
});
