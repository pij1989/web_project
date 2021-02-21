const STATUS_SELECT = ".statusSelect";
const SUCCESS_CHANGE_STATUS = "successChangeStatus";
const ERROR_CHANGE_STATUS = "errorChangeStatus";
const CHANGE = 'change';

const statusSelect = document.querySelectorAll(STATUS_SELECT);

statusSelect.forEach(element => {
    element.addEventListener(CHANGE, event => {
        const currentTarget = event.currentTarget;
        HTMLFormElement.prototype.submit.call(currentTarget);
    })
});

setTimeout(() => {
    let successCreateProduct = document.getElementById(SUCCESS_CHANGE_STATUS);
    let errorCreateProduct = document.getElementById(ERROR_CHANGE_STATUS);
    if (successCreateProduct != null) {
        successCreateProduct.hidden = true;
    }
    if (errorCreateProduct != null) {
        errorCreateProduct.hidden = true;
    }
}, 1000);
