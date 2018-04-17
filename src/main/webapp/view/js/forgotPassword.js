function getFormData($form) {
    var unordered_array = $form.serializeArray();
    var ordered_array = {};

    $.map(unordered_array, function (n, i) {
        ordered_array[n['name']] = n['value'];
    });
    return ordered_array;
}

function changePassword() {
    $form = $("#forgotPasswordForm");
    var data = getFormData($form);
    var s = JSON.stringify(data);
    $.ajax({
        url: "user/sendEmail",
        type: "POST",
        contentType: "application/json",
        data: s,
        success: function () {
            top.location.href = "login.html";
        }

    });

}