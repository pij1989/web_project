const SUCCESS_CREATE_CATEGORY = 'successCreateCategory';
const ERROR_CREATE_CATEGORY = 'errorCreateCategory';
const CATEGORY_NOT_EXIST = 'categoryNotExist';
const FORM_ID = 'createCategoryForm';
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
    let successCreateProduct = document.getElementById(SUCCESS_CREATE_CATEGORY);
    let errorCreateProduct = document.getElementById(ERROR_CREATE_CATEGORY);
    let categoryNotExist = document.getElementById(CATEGORY_NOT_EXIST);
    if (successCreateProduct != null) {
        successCreateProduct.hidden = true;
    }
    if (errorCreateProduct != null) {
        errorCreateProduct.hidden = true;
    }
    if (categoryNotExist != null) {
        categoryNotExist.hidden = true;
    }
}, 2000);
