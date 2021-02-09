const STATUS_SELECT = ".statusSelect";
const SUCCESS_CHANGE_STATUS = "successChangeStatus";
const ERROR_CHANGE_STATUS = "errorChangeStatus";
const CHANGE = 'change';
const STATUS = 'status';
const USER_ID = 'userId';
const URL = 'http://localhost:8080/web_project_war/changestatus';

const statusSelect = document.querySelectorAll(STATUS_SELECT);

const successAlert = () => {
    document.getElementById(SUCCESS_CHANGE_STATUS).hidden = false;
    setTimeout(() => {
        document.getElementById(SUCCESS_CHANGE_STATUS).hidden = true;
    }, 1000);
};

const errorAlert = () => {
    document.getElementById(ERROR_CHANGE_STATUS).hidden = false;
    setTimeout(() => {
        document.getElementById(ERROR_CHANGE_STATUS).hidden = true;
    }, 1000);
};

const send = (url, urlSearchParams) => {
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: urlSearchParams,
    })
        .then((response) => {
            let status = response.status;
            if (status === 204) {
                successAlert();
                return status;
            } else {
                errorAlert();
            }
        })
        .then(value => console.log('Success:', value))
        .catch(err => {
            console.log('Error:', err);
            errorAlert();
        });
};

/*statusSelect.forEach(element => {
    element.addEventListener(CHANGE, event => {
        const target = event.target;
        const status = target.value;
        const userId = target.attributes[2].nodeValue;
        console.log("Status: ", status);
        console.log("User id: ", userId);
        const urlSearchParams = new URLSearchParams();
        urlSearchParams.append(USER_ID, userId);
        urlSearchParams.append(STATUS, status);
        send(URL, urlSearchParams);
    })
});*/
statusSelect.forEach(element => {
    element.addEventListener(CHANGE, event => {
        const currentTarget = event.currentTarget;
        HTMLFormElement.prototype.submit.call(currentTarget);
    })
});

setTimeout(() => {
    let successChangeStatus = document.getElementById(SUCCESS_CHANGE_STATUS);
    if(successChangeStatus != null){
        successChangeStatus.hidden = true;
    }
}, 1000);

setTimeout(() => {
    let errorChangeStatus = document.getElementById(ERROR_CHANGE_STATUS);
    if(errorChangeStatus != null){
        errorChangeStatus.hidden = true;
    }
}, 1000);
