$(document).ready(function () {
    $.ajax({
        url: "institution/getCinemas",
        dataType: "json",
        success: function (data) {
            var cinemaDiv = $('#cinemaDiv');
            for (i = 0; i < data.length; i++) {
                newCinema =
                    "<div>"
                    + "<h2>" + data[i].name + "</h2>"
                    + "<p>" + data[i].description + "</p>"
                    + "</div>";
                cinemaDiv.append(newCinema);
            }
        }
    });
});