window.onload = function () {
    $("#spanPasswordConfirm").empty();
    $("#spanFirstName").empty();
    $("#spanSurname").empty();
    $("#spanEmail").empty();
    $("#spanPhone").empty();
    $("#spanPassword").empty();

}

function getFormData($form) {
    var unordered_array = $form.serializeArray();
    var ordered_array = {};

    $.map(unordered_array, function (n, i) {
        ordered_array[n['name']] = n['value'];
    });
    return ordered_array;
}


function registerUser() {

    var x = document.forms["registracija"]["passwordConfirm"].value;
    var y = document.forms["registracija"]["password"].value;
    var z = document.forms["registracija"]["email"].value;
    var phone = document.forms["registracija"]["phoneNumber"].value;
    var FN = document.forms["registracija"]["firstName"].value;
    var LN = document.forms["registracija"]["lastName"].value;


    if ((z == "" || phone == "" || FN == "" || LN == "") || (x == "" && y == "" && z == "" && phone == "" && FN == "" && LN == "")) {
        toastr["error"]("You have to fill out all input fields", "Registration failed");

    } else {
        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        var ok = re.test(z);

        var reUs = new RegExp("^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$");
        // var okUs = reUs.test(x);


        var reFN = /^[A-Z][a-zA-Z]+[']*[0-9]{0}/;
        var reLN = /^[A-Z][a-zA-Z]+[']*[0-9]{0}/;
        var okFN = reFN.test(FN);
        var okLN = reLN.test(LN);

        var rePass = new RegExp("(?=.*[a-z0-9])[0-9a-zA-Z!@#$%]{8,}");
        // var okPass = rePass.test(y);


        var rePh = new RegExp("[(]{1}[0-9]{3}[)]{1}[0-9]{3,4}[-]{1}[0-9]{3}");
        var okPh = rePh.test(phone);

        if (ok == false || okFN == false || okLN == false || okPh == false) {
            if (ok == false) {
                $("#spanEmail").html("<label style=\"color:red\">Invalid input for email field</label>");
            }
            /* if (okUs == false) {
                 $("#spanPasswordConfirm").html("<label style=\"color:red\">Invalid input for username field</label>");

             }*/
            if (okFN == false) {
                $("#spanFirstName").html("<label style=\"color:red\">Invalid input for first name field</label>");

            }
            if (okLN == false) {
                $("#spanLastName").html("<label style=\"color:red\">Invalid input for last name field</label>");

            }
            /*if (okPass == false) {
                $("#spanPassword").html("<label style=\"color:red\">Invalid input for password field</label>");

            }*/
            if (okPh == false) {
                $("#spanPhoneNumber").html("<label style=\"color:red\">Invalid input for phone number field</label>");

            }


        } else {
            $("#spanPasswordConfirm").empty();
            $("#spanFirstame").empty();
            $("#spanLastName").empty();
            $("#spanEmail").empty();
            $("#spanPhoneNumber").empty();
            $("#spanPassword").empty();

            $form = $("#registracija");
            var data = getFormData($form);
            var s = JSON.stringify(data);
            $.ajax({
                url: "user/registerUser",
                type: "POST",
                data: s,
                contentType: "application/json",
                dataType: "json",
                success: function (data) {
                    if (data == false) {
                        toastr["error"]("Registration failed");

                    } else {
                        toastr["success"]("Registration successfull");
                        top.location.href = "login.html";
                    }

                }, error: function (jqxhr, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });


        }

    }

}