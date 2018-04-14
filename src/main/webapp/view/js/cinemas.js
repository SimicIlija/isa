$(document).ready(function () {
    cinemasHome();
});

function cinemasHome() {
    $.ajax({
        url: "institution/getCinemas",
        dataType: "json",
        success: function (data) {
            var cinemaDiv = $('#cinemaDiv');
            cinemaDiv.empty();
            for (i = 0; i < data.length; i++) {
                newCinema =
                    "<div class='container'>"
                    + "<div class=\"p-3 mb-2 bg-light text-dark\">"
                    + "<h2>" + data[i].name + "</h2>"
                    + "<p>" + data[i].description + "</p>"
                    + "<button type=\"button\" class=\"btn btn-link\" onclick=\"cinemaRepertoar(" + data[i].id + ")\">Repertoar</button>"
                    + "</div>"
                    + "</div>";
                cinemaDiv.append(newCinema);
            }
        }
    });
}

function cinemaRepertoar(id) {
    $.ajax({
        url: "projections/getCinemas",
        dataType: "json",
        success: function (data) {
            var cinemaDiv = $('#cinemaDiv');
            cinemaDiv.empty();
            for (i = 0; i < data.length; i++) {
                newCinema =
                    "<div class='container'>"
                    + "<div class=\"p-3 mb-2 bg-light text-dark\">"
                    + "<h2>" + data[i].name + "</h2>"
                    + "<p>" + data[i].description + "</p>"
                    + "<button type=\"button\" class=\"btn btn-link\" onclick=\"cinemaRepertoar(" + data[i].id + ")\">Repertoar</button>"
                    + "</div>"
                    + "</div>";
                cinemaDiv.append(newCinema);
            }
        }
    });
}