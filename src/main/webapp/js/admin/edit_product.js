const SUCCESS_UPDATE_PRODUCT = 'successUpdateProduct';
const ERROR_UPDATE_PRODUCT = 'errorUpdateProduct';
const PRODUCT_NOT_EXIST = 'productNotExist';
const FORM_ID = 'updateProductForm';
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
    let successCreateProduct = document.getElementById(SUCCESS_UPDATE_PRODUCT);
    let errorCreateProduct = document.getElementById(ERROR_UPDATE_PRODUCT);
    let productNotExist = document.getElementById(PRODUCT_NOT_EXIST);
    if (successCreateProduct != null) {
        successCreateProduct.hidden = true;
    }
    if (errorCreateProduct != null) {
        errorCreateProduct.hidden = true;
    }
    if (productNotExist != null) {
        productNotExist.hidden = true;
    }
}, 2000);
