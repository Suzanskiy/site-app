$(document).ready(function() {
    $("#register_form").submit(function(e) {
        e.preventDefault();

        var form = $(this);
        var url = form.attr('action');

        $.ajax({
            type: "POST",
            url: url,
            data: form.serialize(),
            success: function(data) {
                if (data.success) {
                    alert(data.message);
                    form.find('input[type="text"], input[type="email"], input[type="password"]').val("");
                    form.find('span').text("");
                } else {
                    alert('Error: ' + data.message);
                }
            },
            error: function(error) {
                alert('Error: ' + error.responseText);
            }
        });
    });
});
