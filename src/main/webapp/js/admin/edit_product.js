const SUCCESS_UPDATE_PRODUCT = 'successUpdateProduct';
const ERROR_UPDATE_PRODUCT = 'errorUpdateProduct';
const PRODUCT_NOT_EXIST = 'productNotExist';
const FORM_ID = 'updateProductForm';
const SUBMIT = 'submit';
const WAS_VALIDATED = 'was-validated';
const EXTENSION_REGEX = /\.(jpe?g|png)$/;
const UPLOAD_IMAGE = "uploadImage";

const form = document.getElementById(FORM_ID);

form.addEventListener(SUBMIT, (event) => {
    event.preventDefault();
    event.stopPropagation();
    let isValidImage;
    let image = document.getElementById(UPLOAD_IMAGE).value;
    if (image.trim() === "") {
        isValidImage = true;
    } else {
        let extension = image.substring(image.lastIndexOf('.'));
        isValidImage = EXTENSION_REGEX.test(extension);
    }
    form.classList.add(WAS_VALIDATED);
    if (form.checkValidity() === true && isValidImage) {
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
