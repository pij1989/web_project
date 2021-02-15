const FORM_ID = 'changePasswordForm';
const SUBMIT = 'submit';
const WAS_VALIDATED = 'was-validated';

const form = document.getElementById(FORM_ID);

form.addEventListener(SUBMIT, (event) => {
    event.preventDefault();
    event.stopPropagation();
    form.classList.add(WAS_VALIDATED);
    if (form.checkValidity() === true) {
        HTMLFormElement.prototype.submit.call(form);
        form.classList.remove(WAS_VALIDATED);
    }
});
