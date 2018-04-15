function getFormData($form) {
    var unordered_array = $form.serializeArray();
    var ordered_array = {};

    $.map(unordered_array, function (n, i) {
        ordered_array[n['name']] = n['value'];
    });
    return ordered_array;
}

function tabCinemasClick() {
    $.ajax({
        url: "institution/getCinemas",
        dataType: "json",
        success: function (data) {
            var cinemaDiv = $('#cinemaDiv');
            cinemaDiv.empty();
            for (i = 0; i < data.length; i++) {
                newCinema =
                    "<div class='container'>"
                    + "<div class='divCinemaTheatre'>"
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

function cinemaRepertoar(idInstitution) {
    $.ajax({
        async: false,
        url: "show/getByInstitution/" + idInstitution,
        dataType: "json",
        success: function (data) {
            var cinemaDiv = $('#cinemaDiv');
            cinemaDiv.empty();
            var newShow;
            var i;
            var idShow;
            for (i = 0; i < data.length; i++) {
                idShow = data[i].id;
                newShow =
                    "<div class='container'>"
                    + "<div class=\"divCinemaTheatre\">"
                    + "<h2>" + data[i].name + "</h2>"
                    + "<p>"
                    + "<b>Genre:</b> " + data[i].genre + "<br>"
                    + "<b>Producer:</b> " + data[i].producer + "<br>"
                    + "<b>Duration:</b> " + data[i].duration + "min." + "<br>"
                    + "<b>Description:</b> " + data[i].description + "<br>"
                    + "<b>Actors:</b> ";
                newShow = loadActors(idShow, newShow);
                newShow += "</p>";
                newShow = loadProjections(idShow, newShow);
                newShow += "</div>"
                    + "</div>";
                cinemaDiv.append(newShow);
            }
        }
    });
}

function loadActors(idShow, newShow) {
    $.ajax({
        async: false,
        url: "actors/getByShow/" + idShow,
        dataType: "json",
        success: function (dataActors) {
            for (j = 0; j < dataActors.length; j++) {
                newShow += dataActors[j].name + " " + dataActors[j].lastname;
                if (j < dataActors.length - 1) {
                    newShow += ", "
                }
            }
        }
    });
    return newShow;
}

function loadProjections(idShow, newShow) {
    $.ajax({
        async: false,
        url: "projections/getByShow/" + idShow,
        dataType: "json",
        success: function (dataProjections) {
            newShow += "<p>"
                + "<b>Projections: </b><br> ";
            var projList = [];
            for (k = 0; k < dataProjections.length; k++) {
                var dateProjection = (dataProjections[k].date).substring(0, 10);
                if (!projList.includes(dateProjection)) {
                    projList.push(dateProjection)
                    newShow += "<button type=\"button\" class=\"btn btn-primary\" onclick='projectionClick(" + dateProjection + "," + dataProjections[k].id_show + ")'>" + dateProjection + "</button>&thinsp;&thinsp;";
                }
                var timeProjection = (dataProjections[k].date).substring(10, 16);
            }
            newShow += "</p>";
        }
    });
    return newShow;
}

function projectionClick(dateProjection, idShow) {
    alert(dateProjection + " " + idShow);
}


function tabCinemaSettingsClick() {
    var tableCinemasTBody = $('#tableCinemasTBody');
    tableCinemasTBody.empty();
    $.ajax({
        url: "institution/getCinemas",
        dataType: "json",
        success: function (data) {
            var cinemaDiv = $('#cinemaDiv');
            cinemaDiv.empty();
            for (i = 0; i < data.length; i++) {
                newCinema = "<tr>"
                    + "<td>" + data[i].name + "</td>"
                    + "<td>" + data[i].description + "</td>"
                    + "<td class=\"td-actions\">"
                    + "<a href=\"javascript:;\" class=\"btn btn-small btn-primary\">"
                    + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"javascript:;\" class=\"btn btn-small btn-danger\">\n"
                    + "<i class=\"glyphicon glyphicon-remove\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"javascript:;\" class=\"btn btn-small btn-default\">"
                    + "<i class=\"glyphicon glyphicon-film\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "</td>"
                    + "</tr>";

                tableCinemasTBody.append(newCinema);
            }
        }
    });
}

function tabFriendsClick() {
    var friendsDiv = $('#friendsDiv');
    addFriend = "<button type=\"button\" class=\"btn btn-primary\">Add friend</button>";
    $.ajax({
        url: "users/getAllUsers",
        dataType: "json",
        success: function (data) {

            friendsDiv.empty();
            for (i = 0; i < data.length; i++) {
                newFriendship =
                    "<div class='container'>"
                    + "<div class='divFriendsToAdd'>"
                    + "<p>" + data[i].firstName + " " + data[i].lastName + " " + data[i].email
                    + "<button type=\"button\" class=\"btn btn-primary\" onclick=\"dodajPrijatelja(" + data[i].id + ")\">Add friend</button></p>"
                    + "</div>"
                    + "</div>";
                friendsDiv.append(newFriendship);
            }
        }
    });


}

function dodajPrijatelja(idFriend) {

    $.ajax({
        async: false,
        url: "friendships/create/" + idFriend,
        type: "POST",

        success: function (data) {
            if (data != "") {
                toastr["error"]("Failed to add friend");

            } else {
                $.ajax({
                    url: "friendships/getFriendsNotAccepted",
                    dataType: "json",
                    success: function (data) {
                        var friendsDiv = $('#friendsDivAdded');
                        friendsDiv.empty();
                        for (i = 0; i < data.length; i++) {
                            newFriend =
                                "<div class='container'>"
                                + "<div class='divFriends'>"

                                + "<p>" + data[i].receiver.firstName + " " + data[i].receiver.lastName + " " + data[i].receiver.email + "   "
                                + "<button type=\"button\" class=\"btn btn-warning\" onclick=\"obrisiZahtev(" + data[i].id + ")\">Cancel request</button>"
                                + "</div>"
                                + "</div>";
                            friendsDiv.append(newFriend);
                        }
                    }
                });
                toastr["success"]("Friend request sent");

            }
        }, error: function (jqxhr, textStatus, errorThrown) {
            alert(errorThrown);
        }

    });

}


