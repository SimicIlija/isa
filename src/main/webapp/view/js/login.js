function getFormData($form) {
    var unordered_array = $form.serializeArray();
    var ordered_array = {};

    $.map(unordered_array, function (n, i) {
        ordered_array[n['name']] = n['value'];
    });
    return ordered_array;
}


function logInUser() {
    $form = $("#loginForm");
    var data = getFormData($form);
    var s = JSON.stringify(data);
    $.ajax({
        url: "user/login",
        type: "POST",
        data: s,
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            if (data == false) {
                toastr["error"]("Username/password is incorrect,doesn't exist or empty");

            } else {
                top.location.href = "homeForLoggedIn.html";
                alert('uspeo!');
            }
        }, error: function (jqxhr, textStatus, errorThrown) {
            alert(errorThrown);
        }

    });

}