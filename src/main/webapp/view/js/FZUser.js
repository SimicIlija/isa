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
                loadThemeProps();
                loadBought();
                loadMineUserProps();
            }

        }, error: function (jqxhr, textStatus, errorThrown) {
            top.location.href = "login.html";
        }
    });
};

var themeProps = null;

function loadThemeProps() {
    var list = $('#themeProps');
    $.ajax({
        url: "api/themeprops",
        type: "GET",
        dataType: "json",
        success: function (data, textStatus) {
            themeProps = data;
            list.empty();
            for (var i = 0; i < data.length; i++) {
                var tableRow = "<tr>";
                tableRow += "<td>" + data[i].name + "</td>";
                tableRow += "<td>" + data[i].description + "</td>";
                tableRow += "<td>" + data[i].amount + "</td>";
                tableRow += "<td>" + data[i].price + "</td>";
                tableRow += "<td>" + "<img src=\"" + data[i].imageUrl + "\" alt=\"No image\" width='150' height='150'>" + "</td>";
                tableRow += "<td>" + " <input type='text' class=\"form-control\" name=\"myAmount" + i + "\" id = \"myAmount" + i + "\" >" + "</td>";
                tableRow += "<td>" + " <input class=\"btn btn-lg btn-danger btn-block\" type=\"button\"\n" +
                    "                                           value=\"Buy\"\n" +
                    "                                           onclick=\"buyTP(" + i + ")\">" + "</td>";
                tableRow += "</tr>";
                list.append(tableRow);
            }
        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"]("Failed to load.");
        }
    });
}

function loadMineUserProps() {
    var list = $('#mineUserProps');
    $.ajax({
        url: "api/userprops/mine",
        type: "GET",
        dataType: "json",
        success: function (data, textStatus) {
            console.log(data);
            list.empty();
            for (var i = 0; i < data.length; i++) {
                var tableRow = "<tr>";
                tableRow += "<td>" + data[i].name + "</td>";
                tableRow += "<td>" + data[i].description + "</td>";
                tableRow += "<td>" + data[i].state + "</td>";
                var date = new Date(data[i].endDate);
                tableRow += "<td>" + date + "</td>";
                tableRow += "<td>" + "<img src=\"" + data[i].imageUrl + "\" alt=\"No image\" width='150' height='150'>" + "</td>";
                tableRow += "</tr>";
                list.append(tableRow);
            }
        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"]("Failed to load.");
        }
    });
}

function loadBought() {
    var list = $('#boughtTp');
    $.ajax({
        url: "api/themeprops/bought",
        type: "GET",
        dataType: "json",
        success: function (data, textStatus) {
            console.log(data);
            list.empty();
            for (var i = 0; i < data.length; i++) {
                var value = data[i].amount * data[i].price;
                var string = data[i].name + " : " + data[i].amount + "*" + data[i].price + " = " + value + "<br/>";
                list.append(string);
            }

        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"]("Failed to load.");
        }
    });
}

function buyTP(i) {
    var id = themeProps[i].id;
    console.log(id);
    var amount = $('#myAmount' + i).val();
    var json = JSON.stringify(amount);
    console.log(json);
    $.ajax({
        url: "api/themeprops/buy/" + id,
        type: "POST",
        dataType: "text",
        data: json,
        contentType: "application/json",
        success: function (data, textStatus) {
            toastr["success"]("Success.");
            loadThemeProps();
            loadBought();
        },
        error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"]("Failed to buy.");
        }
    });
}

function validateUPData() {
    var name = document.forms["addForm"]["name"].value;
    var description = document.forms["addForm"]["description"].value;
    var datepicker = document.forms["addForm"]["endDate"].value;
    if (name === null || name.trim() === '') {
        toastr["error"]("Enter name.");
        return false;
    }
    if (description === null || description.trim() === '') {
        toastr["error"]("Enter description.");
        return false;
    }
    if (datepicker === null || datepicker.trim() === '') {
        toastr["error"]("Enter datepicker.");
        return false;
    }
    return true;
}

function getFormData($form) {
    var unordered_array = $form.serializeArray();
    var ordered_array = {};

    $.map(unordered_array, function (n, i) {
        ordered_array[n['name']] = n['value'];
    });
    return ordered_array;
}

function addNewUP() {
    var bool = validateUPData();
    if (!bool) {
        return;
    }
    $form = $('#addForm');
    var data = getFormData($form);
    var date = new Date(data.endDate);
    data.endDate = date.getTime();
    var json = JSON.stringify(data);
    console.log(json);
    $.ajax({
        url: "api/userprops/add",
        type: "POST",
        dataType: "json",
        data: json,
        contentType: "application/json",
        success: function (data, textStatus) {
            toastr["success"]("Succesfully added new theme props");
            loadMineUserProps();
        }, error: function (jqxhr, textStatus, errorThrown) {
            console.log(jqxhr.status);
            toastr["error"]("Something failed");
        }
    });
}