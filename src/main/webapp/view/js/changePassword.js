function getFormData($form) {
    var unordered_array = $form.serializeArray();
    var ordered_array = {};

    $.map(unordered_array, function (n, i) {
        ordered_array[n['name']] = n['value'];
    });
    return ordered_array;
}

function changePassword() {
    $form = $("#changePasswordForm");
    var data = getFormData($form);
    var s = JSON.stringify(data);
    $.ajax({
        url: "user/changePassword",
        type: "POST",
        contentType: "application/json",
        data: s,
        success: function () {
            top.location.href = "login.html";
        }

    });

}