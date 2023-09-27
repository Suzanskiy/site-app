function getQueryParam(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

const emailVerified = getQueryParam('emailVerified');
const error = getQueryParam('error');

if (emailVerified === 'true') {
    alert('Email has been successfully verified!');
} else if (error) {
    alert(`Помилка: ${error}`);
}
