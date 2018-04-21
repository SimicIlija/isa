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
            toastr["error"]("Failed to approve.");
        }
    });
}