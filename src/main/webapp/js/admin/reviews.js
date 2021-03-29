const SUCCESS_DELETE_REVIEW = 'successDeleteReview';
const ERROR_DELETE_REVIEW = 'errorDeleteReview';

setTimeout(() => {
    let successDeleteReview = document.getElementById(SUCCESS_DELETE_REVIEW);
    let errorDeleteReview = document.getElementById(ERROR_DELETE_REVIEW);
    if (successDeleteReview != null) {
        successDeleteReview.hidden = true;
    }
    if (errorDeleteReview != null) {
        errorDeleteReview.hidden = true;
    }
}, 2000);