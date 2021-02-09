const SUCCESS_CREATE_PRODUCT = 'successCreateProduct';
const ERROR_CREATE_PRODUCT = 'errorCreateProduct';
const FORM_ID = 'createProductForm';
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

setTimeout(() => {
    let successCreateProduct = document.getElementById(SUCCESS_CREATE_PRODUCT);
    let errorCreateProduct = document.getElementById(ERROR_CREATE_PRODUCT);
    if (successCreateProduct != null) {
        successCreateProduct.hidden = true;
    }
    if (errorCreateProduct != null) {
        errorCreateProduct.hidden = true;
    }
}, 1000);
