$(document).ready(function () {
    $.ajax({
        url: "institution/getTheatres",
        dataType: "json",
        success: function (data) {
            var theatreDiv = $('#theatreDiv');
            for (i = 0; i < data.length; i++) {
                newTheatre =
                    "<div class='container'>"
                    + "<div class=\"p-3 mb-2 bg-light text-dark\">"
                    + "<h2>" + data[i].name + "</h2>"
                    + "<p>" + data[i].description + "</p>"
                    + "<a class=\"btn btn-link\" href=\"register.html\" role=\"button\">Repertoar</a>"
                    + "</div>"
                    + "</div>";
                theatreDiv.append(newTheatre);
            }
        }
    });
});