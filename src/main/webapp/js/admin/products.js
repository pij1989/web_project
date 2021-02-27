const PAGINATION_FORM = 'paginationForm';
const CHANGE = 'change';
const CLICK = 'click';
const BUTTON_TYPE = 'button';
const PAGE = 'page';
const NEXT = 'next';
const ACTIVE = 'active';
const PREVIOUS = 'previous';
const VALUE_ATTRIBUTE = 'value';
const pageNumber = 1;

const paginationForm = document.getElementById(PAGINATION_FORM);
const page = document.getElementById(PAGE);
const next = document.getElementById(NEXT);
const previous = document.getElementById(PREVIOUS);
const formElements = paginationForm.elements;
const currentPage = document.getElementById(ACTIVE);

Array.prototype.forEach.call(formElements, (element) => {
    if (element.type === BUTTON_TYPE) {
        element.addEventListener(CLICK, (event) => {
            const value = event.target.value;
            const name = event.target.name;
            console.log("Value: ", value);
            console.log("Name: ", name);
            page.setAttribute(VALUE_ATTRIBUTE, value);
            HTMLFormElement.prototype.submit.call(paginationForm);
        })
    }
});

if (!next.classList.contains('disabled')) {
    next.addEventListener(CLICK, () => {
        let value = parseInt(currentPage.value) + 1;
        page.setAttribute(VALUE_ATTRIBUTE, value);
        HTMLFormElement.prototype.submit.call(paginationForm);
    });
}

if (!previous.classList.contains('disabled')) {
    previous.addEventListener(CLICK, () => {
        let value = parseInt(currentPage.value) - 1;
        page.setAttribute(VALUE_ATTRIBUTE, value);
        HTMLFormElement.prototype.submit.call(paginationForm);
    });
}

paginationForm.addEventListener(CHANGE, (event) => {
    const value = event.target.value;
    const name = event.target.name;
    console.log("Value: ", value);
    console.log("Name: ", name);
    page.setAttribute(VALUE_ATTRIBUTE, pageNumber);
    const currentTarget = event.currentTarget;
    HTMLFormElement.prototype.submit.call(currentTarget);
});
