const SUCCESS_DELETE_PRODUCT_FROM_ORDER = 'successDeleteProductFromOrder';
const ERROR_DELETE_PRODUCT_FROM_ORDER = 'errorDeleteProductFromOrder';
const CLICK = 'click';
const CHANGE = 'change';
const ADD_PRODUCT_TO_ORDER = '.add-product-to-order';

const forms = document.querySelectorAll(ADD_PRODUCT_TO_ORDER);

forms.forEach((form) => {
    let amountProduct = form.children[2].children[1].value;
    form.addEventListener(CHANGE, (event) => {
        let element = event.target;
        if (element.checkValidity()) {
            amountProduct = element.value;
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
        }
    });
    plus.addEventListener(CLICK, () => {
        console.log("Click plus...");
        let amount = Number.parseInt(form.children[2].children[1].value) + 1;
        form.children[2].children[1].value = amount;
        amountProduct = amount;
    })
});

/*loginForm.addEventListener(SUBMIT, (event) => {
    event.preventDefault();
    event.stopPropagation();
    if (loginForm.email.checkValidity() === true) {
        showValidationMessage(emailValidMessage, emailInvalidMessage);
    } else {
        showValidationMessage(emailInvalidMessage, emailValidMessage);
    }
    if (loginForm.password.checkValidity() === true) {
        showValidationMessage(passwordValidMessage, passwordInvalidMessage);
    } else {
        showValidationMessage(passwordInvalidMessage, passwordValidMessage);
    }
    loginForm.classList.add(WAS_VALIDATED);
    if (loginForm.checkValidity() === true) {
        HTMLFormElement.prototype.submit.call(loginForm);
        loginForm.classList.remove(WAS_VALIDATED);
    }
});*/

setTimeout(() => {
    let successDeleteProductFromOrder = document.getElementById(SUCCESS_DELETE_PRODUCT_FROM_ORDER);
    let errorDeleteProductFromOrder = document.getElementById(ERROR_DELETE_PRODUCT_FROM_ORDER);
    if (successDeleteProductFromOrder != null) {
        successDeleteProductFromOrder.hidden = true;
    }
    if (errorDeleteProductFromOrder != null) {
        errorDeleteProductFromOrder.hidden = true;
    }
}, 2000);
