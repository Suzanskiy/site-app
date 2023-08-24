function checkUserNameAvailability() {
    clearAllMessages();
    var userName = $("#ReguserName").val();
    var validationForUserNameLabel = [
        {
            check: () => !userName,
            message: "Поле є обов'язковим для заповнення!",
            element: "#userNameMessage",
            color: "red" },
        {
            check: () => userName.length >= 10,
            message: "Довжина Вашого логіну повинна бути менша 10 символів",
            element: "#userNameLengthWarning",
            color: "red" },
        ];
    for (var i = 0; i < validationForUserNameLabel.length; i++) {
        if (validationForUserNameLabel[i].check()) {
            $(validationForUserNameLabel[i].element).text(validationForUserNameLabel[i].message).
            css("color", validationForUserNameLabel[i].color);
            return;
        }
    }
    $.get("/isUserNameAvailable", {userName: userName}, function (isAvailable){
        if(isAvailable){
            $("#userNameMessage").html("<strong" + userName + "</strong> Даний логін доступний для реєстрації")
                .css("color", "green");
        } else {
            $("#userNameMessage").html("<strong>" + userName + "</strong> Даний логін вже зайнятий").
                css("color", "red");
        }
    });
    function clearAllMessages() {
        $("#userNameMessage").text("");
        $("#userNameLengthWarning").text("");
    }

}