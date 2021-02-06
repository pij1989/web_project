const STATUS_SELECT = ".statusSelect";
const SUCCESS_CHANGE_STATUS = "successChangeStatus";
const CHANGE = 'change';
const STATUS = 'status';
const USER_ID = 'userId';
const URL = 'http://localhost:8080/web_project_war/changestatus';

const statusSelect = document.querySelectorAll(STATUS_SELECT);

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
                document.getElementById(SUCCESS_CHANGE_STATUS).hidden = false;
                setTimeout(() => {
                    document.getElementById(SUCCESS_CHANGE_STATUS).hidden = true;
                }, 1000);
                return status;
            }
        })
        .then(value => console.log('Success:', value))
        .catch(err => console.log('Error:', err));
};

statusSelect.forEach(element => {
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
});
