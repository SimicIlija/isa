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

//GLOBALS
var themeProps = null;
var addShowId = null;

function loadProps() {
    var list = $('#themeProps')
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
    console.log(themeProps[i]);
}

function deleteThemeProps(i) {
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

function validateTPData() {

}


function addNewTP() {
    var bool = validateTPData();
    if (!bool) {
        return;
    }
    toastr["error"]("Sve ok");
}