const SUCCESS_ADD_REVIEW = 'successAddReview';
const ERROR_ADD_REVIEW = 'errorAddReview';

setTimeout(() => {
    let successAddReview = document.getElementById(SUCCESS_ADD_REVIEW);
    let errorAddReview = document.getElementById(ERROR_ADD_REVIEW);
    if (successAddReview != null) {
        successAddReview.hidden = true;
    }
    if (errorAddReview != null) {
        errorAddReview.hidden = true;
    }
}, 2000);
