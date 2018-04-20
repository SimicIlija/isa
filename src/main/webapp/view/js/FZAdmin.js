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
                if (data.role === 'ADMIN_FUN') {
                    console.log('ok');
                    loadProps();
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

//GLOBALS
var themeProps = null;
var editedId = null;

function loadProps() {
    var editDiv = $('#editTP');
    editDiv.empty();
    var list = $('#themeProps');
    $.ajax({
        url: "api/themeprops/all",
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
                tableRow += "<td>" + " <input class=\"btn btn-lg btn-primary btn-block\" type=\"button\"\n" +
                    "                                           value=\"Edit\"\n" +
                    "                                           onclick=\"editThemeProps(" + i + ")\">" + "</td>";
                tableRow += "<td>" + " <input class=\"btn btn-lg btn-danger btn-block\" type=\"button\"\n" +
                    "                                           value=\"Delete\"\n" +
                    "                                           onclick=\"deleteThemeProps(" + i + ")\">" + "</td>";
                tableRow += "</tr>";
                list.append(tableRow);
            }
        }, error: function (jqxhr, textStatus, errorThrown) {
            top.location.href = "login.html";
        }
    });

}

function editThemeProps(i) {
    editedId = themeProps[i].id;
    var editDiv = $('#editTP');
    editDiv.empty();
    var realValues = editDivStr
        .replace("vrednostImena", themeProps[i].name)
        .replace("vrednostOpisa", themeProps[i].description)
        .replace("vrednostAm", themeProps[i].amount)
        .replace("vrednostPr", themeProps[i].price)
        .replace("vrednostUrl", themeProps[i].imageUrl);
    editDiv.append(realValues);
}

function deleteThemeProps(i) {
    var editDiv = $('#editTP');
    editDiv.empty();
    console.log(themeProps[i].id);
    $.ajax({
        url: "api/themeprops/" + themeProps[i].id,
        type: "DELETE",
        dataType: "text",
        success: function (data, textStatus) {
            loadProps();
        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"]("Something failed");
        }
    });
}

function validateTPData(formName) {
    var name = document.forms[formName]["name"].value;
    var description = document.forms[formName]["description"].value;
    var amount = document.forms[formName]["amount"].value;
    var price = document.forms[formName]["price"].value;
    var parsedAmount = parseInt(amount);
    price = parseFloat(price);
    if (name === null || name.trim() === '') {
        toastr["error"]("Enter name");
        return false;
    }
    if (description === undefined || description.trim() === '') {
        toastr["error"]("Enter description");
        return false;
    }
    if (isNaN(amount) || (parsedAmount != amount) || amount < 0) {
        toastr["error"]("Enter positive integer for amount");
        return false;
    }
    if (isNaN(price) || price < 0) {
        toastr["error"]("Enter positive number for price");
        return false;
    }
    return true;
}


function addNewTP() {
    var bool = validateTPData("addForm");
    if (!bool) {
        return;
    }
    $form = $('#addForm');
    var data = getFormData($form);
    var json = JSON.stringify(data);
    console.log(json);
    $.ajax({
        url: "api/themeprops/add",
        type: "POST",
        dataType: "json",
        data: json,
        contentType: "application/json",
        success: function (data, textStatus) {
            toastr["success"]("Succesfully added new theme props");
            loadProps();
        }, error: function (jqxhr, textStatus, errorThrown) {
            console.log(jqxhr.status);
            toastr["error"]("Something failed");
        }
    });
}

function sendEditData() {
    var bool = validateTPData("editForm");
    if (!bool) {
        return;
    }
    if (editedId === null) {
        return;
    }
    $form = $('#editForm');
    var data = getFormData($form);
    var json = JSON.stringify(data);
    console.log(json);
    console.log(editedId);
    $.ajax({
        url: "api/themeprops/" + editedId,
        type: "PUT",
        dataType: "text",
        data: json,
        contentType: "application/json",
        success: function (data, textStatus) {
            toastr["success"]("Succesfully edited theme props");
            loadProps();
        }, error: function (jqxhr, textStatus, errorThrown) {
            console.log(jqxhr.status);
            toastr["error"]("Something failed");
        }
    });
}


var editDivStr = " <div class=\"modal-content well\">\n" +
    "                    <form method=\"post\" id=\"editForm\">\n" +
    "                        <h1>Edit theme props</h1>\n" +
    "                        <fieldset>\n" +
    "                            <div class=\"form-group\">\n" +
    "                                <label for=\"name\">Enter name:</label>\n" +
    "                                <input type=\"text\" class=\"form-control\" value= \"vrednostImena\" name=\"name\" id=\"name\">\n" +
    "                            </div>\n" +
    "                            <div class=\"form-group\">\n" +
    "                                <label for=\"description\">Enter description:</label>\n" +
    "                                <input type=\"text\" class=\"form-control\"\n" +
    "                                       name=\"description\" value= \"vrednostOpisa\" id=\"description\">\n" +
    "                            </div>\n" +
    "\n" +
    "                            <div class=\"form-group\">\n" +
    "                                <label for=\"amount\">Enter amount:</label>\n" +
    "                                <input type=\"number\" min=\"0\" step=\"1\"\n" +
    "                                       class=\"form-control\" value= \"vrednostAm\" name=\"amount\" id=\"amount\">\n" +
    "                            </div>\n" +
    "\n" +
    "                            <div class=\"form-group\">\n" +
    "                                <label for=\"amount\">Enter price:</label>\n" +
    "                                <input type=\"number\" min=\"0\" step=\"0.01\"\n" +
    "                                       class=\"form-control\" name=\"price\" value= \"vrednostPr\" id=\"price\">\n" +
    "                            </div>\n" +
    "\n" +
    "                            <div class=\"form-group\">\n" +
    "                                <label for=\"imageUrl\">Enter image url:</label>\n" +
    "                                <input type=\"text\" class=\"form-control\" name=\"imageUrl\" value= \"vrednostUrl\" id=\"imageUrl\">\n" +
    "                            </div>\n" +
    "\n" +
    "                            <input class=\"btn btn-lg btn-primary btn-block\" type=\"button\"\n" +
    "                                   value=\"Edit theme props\"\n" +
    "                                   onclick=\"sendEditData()\">\n" +
    "                        </fieldset>\n" +
    "                    </form>\n" +
    "                </div>";