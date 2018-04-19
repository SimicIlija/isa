window.onload = function () {
    $.ajax({
        url: "user/authRole",
        type: "GET",
        dataType: "json",
        success: function (data, textStatus) {
            console.log(data);
            if (textStatus !== "success") {
                toastr["error"]("Something failed");

            } else {
                if (data.role === 'ADMIN_SYS') {
                    console.log('ok');
                    var mapProp = {
                        center: new google.maps.LatLng(45.2508742, 19.836),
                        zoom: 10
                    };
                    var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
                    google.maps.event.addListener(map, 'click', function (event) {
                        placeMarker(map, event.latLng);
                    });
                } else {
                    top.location.href = "login.html";
                }
            }

        }, error: function (jqxhr, textStatus, errorThrown) {
            top.location.href = "login.html";
        }
    });
};

function getFormData($form) {
    var unordered_array = $form.serializeArray();
    var ordered_array = {};

    $.map(unordered_array, function (n, i) {
        ordered_array[n['name']] = n['value'];
    });
    return ordered_array;
}

var role = null;
var isCinema = null;
var marker = null;

function registerAdmin() {
    var bool = validateData();
    if (!bool) {
        return;
    }
    $form = $("#registracijaAdmina");
    var data = getFormData($form);
    var json = JSON.stringify(data);
    if (role === 'fz') {
        $.ajax({
            url: "users/registerFanZoneAdmin",
            type: "POST",
            data: json,
            contentType: "application/json",
            dataType: "text",
            success: function (data, textStatus) {
                if (textStatus !== "success") {
                    toastr["error"]("Registration failed");

                } else {
                    toastr["success"]("Registration successfull");
                    top.location.href = "login.html";
                }

            }, error: function (jqxhr, textStatus, errorThrown) {
                toastr["error"]("Registration failed");
            }
        });
    } else if (role === 'sys') {
        $.ajax({
            url: "users/registerSystemAdmin",
            type: "POST",
            data: json,
            contentType: "application/json",
            dataType: "text",
            success: function (data, textStatus) {
                if (textStatus !== "success") {
                    toastr["error"]("Registration failed");

                } else {
                    toastr["success"]("Registration successfull");
                    top.location.href = "login.html";
                }

            }, error: function (jqxhr, textStatus, errorThrown) {
                toastr["error"]("Registration failed");
            }
        });
    } else if (role === 'ins') {
        $.ajax({
            url: "users/registerInstitutionAdmin",
            type: "POST",
            data: json,
            contentType: "application/json",
            dataType: "text",
            success: function (data, textStatus) {
                if (textStatus !== "success") {
                    toastr["error"]("Registration failed");

                } else {
                    toastr["success"]("Registration successfull");
                    top.location.href = "login.html";
                }

            }, error: function (jqxhr, textStatus, errorThrown) {
                toastr["error"]("Registration failed");
            }
        });
    } else {
        console.error("some ridicolous mistake");
    }

}

function changeRole(tempRole) {
    role = tempRole;
}

function changeType(newType) {
    isCinema = newType;
}

function validateData() {

    var z = document.forms["registracijaAdmina"]["email"].value;
    var phone = document.forms["registracijaAdmina"]["phoneNumber"].value;
    var FN = document.forms["registracijaAdmina"]["firstName"].value;
    var LN = document.forms["registracijaAdmina"]["lastName"].value;

    if (role === null) {
        toastr["error"]("You have to choose role", "Registration failed");
        return false;
    }

    if ((z == "" || phone == "" || FN == "" || LN == "") || (z == "" && phone == "" && FN == "" && LN == "")) {
        toastr["error"]("You have to fill out all input fields", "Registration failed");
        return false;
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

            return false;
        }
    }
    return true;
}

function validateInsData() {
    if (isCinema === null) {
        toastr["error"]("You must choose type");
        return false;
    }
    var name = document.forms["instAdd"]["name"].value;
    var description = document.forms["instAdd"]["description"].value;
    if (name.trim() === '') {
        toastr["error"]("You must enter name");
        return false;
    }
    if (description.trim() === '') {
        toastr["error"]("You must enter description");
        return false;
    }
    if (marker === null) {
        toastr["error"]("Pin location on map");
        return false;
    }
    return true;
}

function createNewInstitution() {
    var bool = validateInsData();
    if (!bool) {
        return;
    }
    var dataObj = {};
    dataObj.isCinema = isCinema;
    dataObj.name = document.forms["instAdd"]["name"].value;
    dataObj.description = document.forms["instAdd"]["description"].value;
    dataObj.longitude = marker.position.lng();
    dataObj.latitude = marker.position.lat();
    var json = JSON.stringify(dataObj);
    console.log(json);

    $.ajax({
        url: "institution",
        type: "POST",
        data: json,
        contentType: "application/json",
        dataType: "json",
        success: function (data, textStatus) {
            if (textStatus !== "success") {
                toastr["error"]("Submission failed");

            } else {
                toastr["success"]("Added new instituion");
            }

        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"]("Adding failed");
        }
    });
}

function placeMarker(map, location) {
    if (marker) {
        marker.setPosition(location);
    } else {
        marker = new google.maps.Marker({
            position: location,
            map: map
        });
    }
    // alert(marker.position.lat());
    // alert(marker.position.lng());
}
