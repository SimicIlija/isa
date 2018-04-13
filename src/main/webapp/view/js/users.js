$(document).ready(function () {
    $.ajax({
        url: "users/getAllUsers",
        dataType: "json",
        success: function (data) {
            var userTable = $('#userTable');
            for (i = 0; i < data.length; i++) {
                user =
                    "<tr>"
                    + "<td>" + data[i].firstName + "</td>"
                    + "<td>" + data[i].lastName + "</td>"
                    + "<td>" + data[i].email + "</td>"
                    + "</tr>";
                userTable.append(user);
            }
        }
    });
});