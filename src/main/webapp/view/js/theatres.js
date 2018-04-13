$(document).ready(function () {
    $.ajax({
        url: "institution/getTheatres",
        dataType: "json",
        success: function (data) {
            var theatreDiv = $('#theatreDiv');
            for (i = 0; i < data.length; i++) {
                newTheatre =
                    "<div>"
                    + "<h2>" + data[i].name + "</h2>"
                    + "<p>" + data[i].description + "</p>"
                    + "</div>";
                theatreDiv.append(newTheatre);
            }
        }
    });
});