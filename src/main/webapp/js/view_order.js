const SUCCESS_DELETE_PRODUCT_FROM_ORDER = 'successDeleteProductFromOrder';
const ERROR_DELETE_PRODUCT_FROM_ORDER = 'errorDeleteProductFromOrder';
const SUCCESS_CONFIRM_ORDER = 'successConfirmOrder';
const ERROR_CONFIRM_ORDER = 'errorConfirmOrder';
const ERROR_CHANGE_AMOUNT = 'errorChangeAmount';
const CLICK = 'click';
const CHANGE = 'change';
const SUBMIT = 'submit';
const ADD_PRODUCT_TO_ORDER = '.add-product-to-order';

const forms = document.querySelectorAll(ADD_PRODUCT_TO_ORDER);

forms.forEach((form) => {
    let amountProduct = form.children[2].children[1].value;
    form.addEventListener(SUBMIT, (event) => {
        event.preventDefault();
        event.stopPropagation();
    });
    form.addEventListener(CHANGE, (event) => {
        let element = event.target;
        if (element.checkValidity()) {
            if (amountProduct !== element.value) {
                amountProduct = element.value;
                HTMLFormElement.prototype.submit.call(form);
            }
        } else {
            form.children[2].children[1].value = amountProduct;
        }
    });
    const minus = form.children[2].children[0];
    const plus = form.children[2].children[2];
    minus.addEventListener(CLICK, () => {
        console.log("Click minus...");
        let amount = Number.parseInt(form.children[2].children[1].value) - 1;
        if (amount < 1) {
            form.children[2].children[1].value = 1;
            amountProduct = 1;
        } else {
            form.children[2].children[1].value = amount;
            amountProduct = amount;
            HTMLFormElement.prototype.submit.call(form);
        }
    });
    plus.addEventListener(CLICK, () => {
        console.log("Click plus...");
        let amount = Number.parseInt(form.children[2].children[1].value) + 1;
        form.children[2].children[1].value = amount;
        amountProduct = amount;
        HTMLFormElement.prototype.submit.call(form);
    })
});

setTimeout(() => {
    let successDeleteProductFromOrder = document.getElementById(SUCCESS_DELETE_PRODUCT_FROM_ORDER);
    let errorDeleteProductFromOrder = document.getElementById(ERROR_DELETE_PRODUCT_FROM_ORDER);
    let successConfirmOrder = document.getElementById(SUCCESS_CONFIRM_ORDER);
    let errorConfirmOrder = document.getElementById(ERROR_CONFIRM_ORDER);
    let errorChangeAmount = document.getElementById(ERROR_CHANGE_AMOUNT);
    if (successDeleteProductFromOrder != null) {
        successDeleteProductFromOrder.hidden = true;
    }
    if (errorDeleteProductFromOrder != null) {
        errorDeleteProductFromOrder.hidden = true;
    }
    if (successConfirmOrder != null) {
        successConfirmOrder.hidden = true;
    }
    if (errorConfirmOrder != null) {
        errorConfirmOrder.hidden = true;
    }
    if (errorChangeAmount != null) {
        errorChangeAmount.hidden = true;
    }
}, 2000);
