const DELIVERY_FORM = 'deliveryForm';
const CONFIRM_ORDER = 'confirmOrder';
const CLICK = 'click';
const WAS_VALIDATED = 'was-validated';
const ERROR_CONFIRM_ORDER = 'errorConfirmOrder';

const deliveryForm = document.getElementById(DELIVERY_FORM);
const confirmOrder = document.getElementById(CONFIRM_ORDER);

confirmOrder.addEventListener(CLICK, () => {
    deliveryForm.classList.add(WAS_VALIDATED);
    if (deliveryForm.checkValidity() === true) {
        HTMLFormElement.prototype.submit.call(deliveryForm);
        deliveryForm.classList.remove(WAS_VALIDATED);
    }
});

setTimeout(() => {
    let errorConfirmOrder = document.getElementById(ERROR_CONFIRM_ORDER);
    if (errorConfirmOrder != null) {
        errorConfirmOrder.hidden = true;
    }
}, 2000);
