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
    let successChangeStatus = document.getElementById(SUCCESS_CHANGE_STATUS);
    if (successChangeStatus != null) {
        successChangeStatus.hidden = true;
    }
}, 1000);

setTimeout(() => {
    let errorChangeStatus = document.getElementById(ERROR_CHANGE_STATUS);
    if (errorChangeStatus != null) {
        errorChangeStatus.hidden = true;
    }
}, 1000);
