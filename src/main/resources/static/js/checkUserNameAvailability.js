function checkUserNameAvailability() {
    clearAllMessages();
    var userName = $("#RegisterName").val();
    if (!userName) {
        $("#userNameMessage").text("Поле є обов'язковим для заповнення!").css("color", "red");
        return;
    }
    if (userName.length >= 10) {
        $("#userNameLengthWarning").text("Довжина Вашого логіну повинна бути менша 10 символів").css("color", "red");
        return;
    }
    $.get("/api/isUserNameAvailable", {userName: userName}, function (isAvailable) {
        if (isAvailable) {
            $("#userNameMessage").html("Даний логін доступний для реєстрації").css("color", "green");
        } else {
            $("#userNameMessage").html("</strong> Даний логін вже зайнятий").css("color", "red");
        }
    });
}

function clearAllMessages() {
    $("#userNameMessage").text("");
    $("#userNameLengthWarning").text("");
}
