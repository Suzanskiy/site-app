function checkEmailAvailability() {
    var userEmail = $("#email").val();
    clearMessage();
var validationForUserEmailLabel = [
    {
        check: () => !userEmail,
        message: "Дане поле є обов'язковим для заповнення",
        element: "emailMessage",
        color: "red" },
    {
        check: () => userEmail.length >= 20,
        message: "Довжина Вашого email повинна бути менша 20 символів",
        element: "#userEmailLengthWarning",
        color: "red" }
]
    for (var i = 0; i < validationForUserEmailLabel.length; i++) {
        if (validationForUserEmailLabel[i].check()) {
            $(validationForUserEmailLabel[i].element).text(validationForUserEmailLabel[i].message).
            css("color", validationForUserEmailLabel[i].color);
            return;
        }
    }
    function clearMessage() {
        $("#emailMessage").text("");
        $("#userEmailLengthWarning").text("");
    }
}