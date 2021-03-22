const PAGINATION_FORM = 'paginationForm';
const CHANGE = 'change';
const CLICK = 'click';
const SUBMIT = 'submit';
const WAS_VALIDATED = 'was-validated';
const BUTTON_TYPE = 'button';
const PAGE = 'page';
const NEXT = 'next';
const ACTIVE = 'active';
const PREVIOUS = 'previous';
const VALUE_ATTRIBUTE = 'value';
const PAGE_NUMBER = 1;
const SELECT_CATEGORY_FORM = 'selectCategoryForm';
const SORT_FORM = 'sortForm';
const FILTER_FORM = 'filterForm';

const paginationForm = document.getElementById(PAGINATION_FORM);
const page = document.getElementById(PAGE);
const next = document.getElementById(NEXT);
const previous = document.getElementById(PREVIOUS);
const currentPage = document.getElementById(ACTIVE);
const selectCategoryForm = document.getElementById(SELECT_CATEGORY_FORM);
const sortForm = document.getElementById(SORT_FORM);
const filterForm = document.getElementById(FILTER_FORM);

const addChangeEventToForm = form => {
    if (form != null) {
        form.addEventListener(CHANGE, (event) => {
            const currentTarget = event.currentTarget;
            HTMLFormElement.prototype.submit.call(currentTarget);
        });
    }
};

addChangeEventToForm(selectCategoryForm);

addChangeEventToForm(sortForm);

if(filterForm != null){
    filterForm.addEventListener(SUBMIT,event =>{
        if (filterForm.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        filterForm.classList.add(WAS_VALIDATED);
    })
}

if (paginationForm != null) {
    const formElements = paginationForm.elements;
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

    paginationForm.addEventListener(CHANGE, (event) => {
        const value = event.target.value;
        const name = event.target.name;
        console.log("Value: ", value);
        console.log("Name: ", name);
        page.setAttribute(VALUE_ATTRIBUTE, PAGE_NUMBER);
        const currentTarget = event.currentTarget;
        HTMLFormElement.prototype.submit.call(currentTarget);
    });
}

if (next != null && previous != null) {
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
}
