// $(document).ready(function() {
//     $('#login_form').on('submit', function(event) {
//         event.preventDefault();
//         const formData = {
//             userName: $("#userName").val(),
//             password: $("#password").val()
//         };
//
//         var token = $("input[name='_csrf']").val();
//         var header = "X-CSRF-TOKEN";
//
//         $.ajax({
//             type: "POST",
//             url: "/api/login",
//             data: formData,
//             dataType: "json",
//             headers: {
//                 [header]: token
//             },
//             success: function(response, textStatus, xhr) {
//                 const locationHeader = xhr.getResponseHeader("Location");
//                 if (locationHeader) {
//                     window.location.href = locationHeader;
//                 } else {
//                     alert("Authentication failed!");
//                 }
//             },
//             error: function(xhr, status, error) {
//                 alert("Error: " + error);
//             }
//         });
//     });
// });
