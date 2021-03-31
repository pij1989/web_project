const SUCCESS_DELETE_ORDER = 'successDeleteOrder';
const ERROR_DELETE_ORDER = 'errorDeleteOrder';
const SUCCESS_CHANGE_STATUS = "successChangeStatus";
const ERROR_CHANGE_STATUS = "errorChangeStatus";
const ORDER_STATUS_SELECT = '.orderStatusSelect';
const CHANGE = 'change';

const orderStatusSelect = document.querySelectorAll(ORDER_STATUS_SELECT);

orderStatusSelect.forEach(element => {
    element.addEventListener(CHANGE, event => {
        const currentTarget = event.currentTarget;
        HTMLFormElement.prototype.submit.call(currentTarget);
    })
});

setTimeout(() => {
    let successDeleteOrder = document.getElementById(SUCCESS_DELETE_ORDER);
    let errorDeleteOrder = document.getElementById(ERROR_DELETE_ORDER);
    let successChangeOrderStatus = document.getElementById(SUCCESS_CHANGE_STATUS);
    let errorChangeOrderStatus = document.getElementById(ERROR_CHANGE_STATUS);
    if (successDeleteOrder != null) {
        successDeleteOrder.hidden = true;
    }
    if (errorDeleteOrder != null) {
        errorDeleteOrder.hidden = true;
    }
    if (successChangeOrderStatus != null) {
        successChangeOrderStatus.hidden = true;
    }
    if (errorChangeOrderStatus != null) {
        errorChangeOrderStatus.hidden = true;
    }
}, 2000);