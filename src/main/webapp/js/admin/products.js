const PAGINATION_FORM = "paginationForm";
const CHANGE = 'change';
const CLICK = 'click';
const BUTTON_TYPE = 'button';
const PAGE = 'page';
const VALUE_ATTRIBUTE = 'value';
let pageNumber = 1;

const paginationForm = document.getElementById(PAGINATION_FORM);
const page = document.getElementById(PAGE);
const formElements = paginationForm.elements;

Array.prototype.forEach.call(formElements, (element) => {
    if (element.type === BUTTON_TYPE) {
        element.addEventListener(CLICK, (event) => {
            const value = event.target.value;
            const name = event.target.name;
            console.log("Value: ", value);
            console.log("Name: ", name);
            pageNumber = value;
            page.setAttribute(VALUE_ATTRIBUTE, value);
            HTMLFormElement.prototype.submit.call(paginationForm);
        })
    }
});

paginationForm.addEventListener(CHANGE, (event) => {
    const value = event.target.value;
    const name = event.target.name;
    console.log("Value: ", value);
    console.log("Name: ", name);
    page.setAttribute(VALUE_ATTRIBUTE, pageNumber);
    const currentTarget = event.currentTarget;
    HTMLFormElement.prototype.submit.call(currentTarget);
});
