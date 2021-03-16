const SUCCESS_DELETE_PRODUCT_FROM_ORDER = 'successDeleteProductFromOrder';
const ERROR_DELETE_PRODUCT_FROM_ORDER = 'errorDeleteProductFromOrder';

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
