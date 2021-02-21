const SUCCESS_UPDATE_CATEGORY = 'successUpdateCategory';
const ERROR_UPDATE_CATEGORY = 'errorUpdateCategory';
const FORM_ID = 'updateCategoryForm';
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
    let successCreateProduct = document.getElementById(SUCCESS_UPDATE_CATEGORY);
    let errorCreateProduct = document.getElementById(ERROR_UPDATE_CATEGORY);
    if (successCreateProduct != null) {
        successCreateProduct.hidden = true;
    }
    if (errorCreateProduct != null) {
        errorCreateProduct.hidden = true;
    }
}, 1000);
