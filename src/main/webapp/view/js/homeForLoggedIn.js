function getFormData($form) {
    var unordered_array = $form.serializeArray();
    var ordered_array = {};

    $.map(unordered_array, function (n, i) {
        ordered_array[n['name']] = n['value'];
    });
    return ordered_array;
}
$(document).ready(function () {
    /*$.ajax({
        url: "institution/getInstitutionsByAdmin",
        dataType: "json",
        success: function (data) {
            for(i = 0; i < data.length; i++) {
                $(".nav nav-tabs").append("<li><a href=\"#tab" + data[i].id + "\" data-toggle=\"tab\">" + data[i].name + "</a></li>");
                $(".tab-content").append("<div class=\"tab-pane fade\" id=\"tab" + data[i].id + "\">" + data[i].name + "</div>");
            }
        }
    });*/

    $.ajax({
        async: false,
        url: "institution/getInstitutions",
        dataType: "json",
        success: function (data) {
            for (i = 0; i < data.length; i++) {
                $("#tabovi").append("<li><a href=\"#tab" + data[i].id + "\" onclick='return showInstitution(" + data[i].id + ")' data-toggle=\"tab\">" + data[i].name + "</a></li>");
                $("#divovi").append("<div class=\"tab-pane fade\" id=\"tab" + data[i].id + "\">" + data[i].name + "</div>");
            }
        }
    });

    $("#addAuditoriumButton").click(function () {
        var idInstitution = $("#addAuditoriumInstitutionId").val();
        auditoriumName = $("#addAuditoriumName").val();
        var dataAuditorium = JSON.stringify({"name": auditoriumName, "idInstitution": idInstitution});
        $.ajax({
            async: false,
            type: "POST",
            url: "auditorium/addAuditorium/",
            dataType: "json",
            contentType: "application/json",
            data: dataAuditorium,
            success: function (data) {
                newAuditorium = "<tr>"
                    + "<td id='auditoriumID" + data.id + "'>" + data.id + "</td>"
                    + "<td id='auditoriumName" + data.id + "'>" + data.name + "</td>"
                    + "<td class=\"td-actions\">"
                    + "<a href=\"#\" onclick=\"return editAuditorium(" + data.id + ");\" class=\"btn btn-small btn-primary\">"
                    + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return deleteAuditorium(" + idInstitution + ", " + data.id + ");\" class=\"btn btn-small btn-danger\">\n"
                    + "<i class=\"glyphicon glyphicon-remove\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return showSegments(" + idInstitution + ", " + data.id + ");\" class=\"btn btn-small btn-default\" title=\"Show segments\">"
                    + "<i class=\"glyphicon glyphicon-arrow-right\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "</td>"
                    + "</tr>";
                $("#addAuditoriumModal").modal('toggle');
                $("#tableAuditoriumsTBody" + idInstitution).append(newAuditorium);
            }
        });
    });

    $("#editAuditoriumButton").click(function () {
        idAuditorium = $("#idAuditoriumEdit").val();
        auditoriumName = $("#nameAuditoriumEdit").val();
        var dataAuditorium = JSON.stringify({"name": auditoriumName});
        $.ajax({
            async: false,
            type: "PUT",
            url: "auditorium/editAuditorium/" + idAuditorium,
            dataType: "json",
            contentType: "application/json",
            data: dataAuditorium,
            success: function (data) {
                $('#auditoriumName' + data.id).text(data.name);
                $('#editAuditoriumModal').modal('toggle');
            }
        });
    });

    $("#editSegmentButton").click(function () {

        idSegment = $("#idSegmentEdit").val();
        label = $("#labelSegmentEdit").val();
        rows = $("#rowsSegmentEdit").val();
        seats = $("#seatsInRowSegmentEdit").val();
        var idInstitution = $("#idInstitutionSegmentEdit").val();

        var dataSegment = JSON.stringify({"label": label, "rowCount": rows, "seatsInRowCount": seats});
        $.ajax({
            async: false,
            type: "PUT",
            url: "segment/editSegment/" + idSegment,
            dataType: "json",
            contentType: "application/json",
            data: dataSegment,
            success: function (data) {
                $('#segmentLabel' + data.id).text(data.label);
                $('#segmentRowCount' + data.id).text(data.rowCount);
                $('#segmentSeatsInRowCount' + data.id).text(data.seatsInRowCount);
                $('#editSegmentModal').modal('toggle');
            }
        });
    });

    $("#addSegmentButton").click(function () {
        var idInstitution = $("#addSegmentInstitutionId").val();
        var idAuditorium = $("#addSegmentAuditoriumId").val();
        label = $("#addSegmentLabel").val();
        rows = $("#addSegmentRows").val();
        seats = $("#addSegmentSeatsInRow").val();

        var dataSegment = JSON.stringify({
            "label": label,
            "idAuditorium": idAuditorium,
            "rowCount": rows,
            "seatsInRowCount": seats
        });
        $.ajax({
            async: false,
            type: "POST",
            url: "segment/addSegment",
            dataType: "json",
            contentType: "application/json",
            data: dataSegment,
            success: function (data) {
                newSegment = "<tr>"
                    + "<td id='segmentLabel" + data.id + "'>" + data.label + "</td>"
                    + "<td id='segmentRowCount" + data.id + "'>" + data.rowCount + "</td>"
                    + "<td id='segmentSeatsInRowCount" + data.id + "'>" + data.seatsInRowCount + "</td>"
                    + "<td class=\"td-actions\">"
                    + "<a href=\"#\" onclick=\"return editSegment(" + idInstitution + ", " + data.id + ");\" class=\"btn btn-small btn-primary\">"
                    + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return deleteSegment(" + idInstitution + ", " + data.id + ");\" class=\"btn btn-small btn-danger\">\n"
                    + "<i class=\"glyphicon glyphicon-remove\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "</td>"
                    + "</tr>";
                $("#addSegmentModal").modal('toggle');
                $("#tableSegmentsTBody" + idInstitution).append(newSegment);
            }
        });
    });


    $("#addShowButton").click(function () {
        var idInstitution = $("#addShowInstitutionId").val();
        name = $("#addShowName").val();
        description = $("#addShowDescription").val();
        genre = $("#addShowGenre").val();
        producer = $("#addShowProducer").val();
        duration = $("#addShowDuration").val();
        var dataShow = JSON.stringify({
            "name": name,
            "genre": genre,
            "producer": producer,
            "duration": duration,
            "idInstitution": idInstitution,
            "description": description
        });
        $.ajax({
            async: false,
            type: "POST",
            url: "show/addShow/",
            dataType: "json",
            contentType: "application/json",
            data: dataShow,
            success: function (data) {
                newShow = "<tr>"
                    + "<td id='showID" + data.id + "'>" + data.id + "</td>"
                    + "<td id='showName" + data.id + "'>" + data.name + "</td>"
                    + "<td id='showDescription" + data.id + "'>" + data.description + "</td>"
                    + "<td id='showGenre" + data.id + "'>" + data.genre + "</td>"
                    + "<td id='showProducer" + data.id + "'>" + data.producer + "</td>"
                    + "<td id='showDuration" + data.id + "'>" + data.duration + "</td>"
                    + "<td class=\"td-actions\">"
                    + "<a href=\"#\" onclick=\"return editShow(" + data.id + ");\" class=\"btn btn-small btn-primary\">"
                    + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return deleteShow(" + idInstitution + ", " + data.id + ");\" class=\"btn btn-small btn-danger\">\n"
                    + "<i class=\"glyphicon glyphicon-remove\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return showActorsAndProjections(" + idInstitution + ", " + data.id + ");\" class=\"btn btn-small btn-default\" title=\"Show actors and projections\">"
                    + "<i class=\"glyphicon glyphicon-arrow-right\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "</td>"
                    + "</tr>";
                $("#addShowModal").modal('toggle');
                $("#tableShowsTBody" + idInstitution).append(newShow);
            }
        });
    });

    $("#addActorButton").click(function () {
        var idInstitution = $("#addActorInstitutionId").val();
        var idShow = $("#addActorShowId").val();
        name = $("#addActorName").val();
        lastname = $("#addActorLastname").val();

        var dataActor = JSON.stringify({"name": name, "lastname": lastname, "idShow": idShow});
        $.ajax({
            async: false,
            type: "POST",
            url: "actors/addActor",
            dataType: "json",
            contentType: "application/json",
            data: dataActor,
            success: function (data) {
                newActor = "<tr>"
                    + "<td id='actorName" + data.id + "'>" + data.name + "</td>"
                    + "<td id='actorLastname" + data.id + "'>" + data.lastname + "</td>"
                    + "<td class=\"td-actions\">"
                    + "<a href=\"#\" onclick=\"return editActor(" + idInstitution + ", " + data.id + ");\" class=\"btn btn-small btn-primary\">"
                    + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return deleteActor(" + idInstitution + ", " + data.id + ");\" class=\"btn btn-small btn-danger\">\n"
                    + "<i class=\"glyphicon glyphicon-remove\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "</td>"
                    + "</tr>";
                $("#addActorModal").modal('toggle');
                $("#tableActorsTBody" + idInstitution).append(newActor);
            }
        });
    });


    $("#editActorButton").click(function () {

        idActor = $("#idActorEdit").val();
        name = $("#nameActorEdit").val();
        lastname = $("#lastnameActorEdit").val();

        var dataActor = JSON.stringify({"name": name, "lastname": lastname});
        $.ajax({
            async: false,
            type: "PUT",
            url: "actors/editActor/" + idActor,
            dataType: "json",
            contentType: "application/json",
            data: dataActor,
            success: function (data) {
                $('#actorName' + data.id).text(data.name);
                $('#actorLastname' + data.id).text(data.lastname);
                $('#editActorModal').modal('toggle');
            }
        });
    });


});

function tabCinemasClick() {
    $.ajax({
        async: false,
        type: "GET",
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
                var projectionYear = dateProjection.substring(0, 4);
                var projectionMonth = dateProjection.substring(5, 7);
                var projectionDay = dateProjection.substring(8, 10);
                if (!projList.includes(dateProjection)) {
                    projList.push(dateProjection)
                    newShow += "<button type=\"button\" class=\"btn btn-primary\" onclick='projectionClick(" + projectionYear + "," + projectionMonth + "," + projectionDay + "," + dataProjections[k].id_show + ")'>" + dateProjection + "</button>&thinsp;&thinsp;";
                }
                var timeProjection = (dataProjections[k].date).substring(10, 16);
            }
            newShow += "</p>";
        }
    });
    return newShow;
}

function projectionClick(projectionYear, projectionMonth, projectionDay, idShow) {
    if (projectionMonth.toString().length === 1) {
        projectionMonth = "0" + projectionMonth;
    }
    if (projectionDay.toString().length === 1) {
        projectionDay = "0" + projectionDay;
    }

    var s = projectionYear + "-" + projectionMonth + "-" + projectionDay;
    var data = JSON.stringify({
        "date": s,
        "id_show": idShow
    });
    var cinemaDiv = $('#cinemaDiv');
    cinemaDiv.empty();
    $.ajax({
        async: false,
        url: "/projections/getProjectionByDate",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: data,
        success: function (dataProjections) {
            $.ajax({
                url: "/show/getById/" + idShow,
                dataType: "json",
                success: function (dataShow) {
                    newShow = "<p>"
                        + "<b>Projections for movie: " + dataShow.name + " </b><br> ";
                    var projList = [];
                    for (k = 0; k < dataProjections.length; k++) {
                        var timeProjection = (dataProjections[k].date).substring(10, 16);
                        newShow += "<button type=\"button\" class=\"btn btn-primary\" onclick='configurationClick("
                            + dataProjections[k].id + ")'>" + timeProjection + "</button>&thinsp;&thinsp;";
                    }
                    newShow += "</p>";
                    newShow += "<div id='configDiv'></div>"
                    cinemaDiv.append(newShow);

                }
            });
        }

    });
}

function configurationClick(idProjection) {
    var configDiv = $('#configDiv');
    configDiv.empty();
    $.ajax({
        async: false,
        url: "/projections/getConfigurationForProjection/" + idProjection,
        type: "GET",
        dataType: "json",
        success: function (dataConfig) {

            var startString = "<br/><br/>";
            configDiv.append(startString);
            var newConfig = "";
            for (k = 0; k < dataConfig.segments.length; k++) {
                newConfig += "<p>";
                var segment = (dataConfig.segments[k]);
                for (j = 1; j < segment.rowCount; j++) {

                    newConfig += "<p>";
                    for (i = 0; i < segment.seatTicketDTOList.length; i++) {
                        var seat = segment.seatTicketDTOList[i];
                        if (seat.row == j) {
                            if (seat.reserved == false) {
                                newConfig += "<button type=\"button\" class=\"btn btn-primary\" onclick='reserveSeat("
                                    + seat.idTicket + ")'>" + seat.seatNumber + "</button>&thinsp;&thinsp;"
                            } else {
                                newConfig += "<button type=\"button\" class=\"btn btn-danger\" >" + seat.seatNumber +
                                    "</button>&thinsp;&thinsp;"
                            }

                        } else {
                            continue;
                        }

                    }
                    newConfig += "</p>";


                }
                newConfig += "</p><br/>";
            }


            configDiv.append(newConfig);
        }

    });

}



function reserveSeat(idSeat) {


}


/*function tabCinemaSettingsClick() {

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
                    + "<td id='cinemaName" + data[i].id + "'>" + data[i].name + "</td>"
                    + "<td id='cinemaDescription" + data[i].id + "'>" + data[i].description + "</td>"
                    + "<td class=\"td-actions\">"
                    + "<a href=\"#\" onclick=\"return editCinema(" + data[i].id + ");\" class=\"btn btn-small btn-primary\">"
                    + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return deleteCinema(" + data[i].id + ");\" class=\"btn btn-small btn-danger\">\n"
                    + "<i class=\"glyphicon glyphicon-remove\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" class=\"btn btn-small btn-default\">"
                    + "<i class=\"glyphicon glyphicon-film\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "</td>"
                    + "</tr>";

                tableCinemasTBody.append(newCinema);
            }
        }
    });
}*/

function tabFriendsClick() {

    var profileDiv = $('#profileDiv');
    profileDiv.empty();
    var friendsDiv = $('#friendsDiv');
    addFriend = "<button type=\"button\" class=\"btn btn-primary\">Add friend</button>";
    $.ajax({
        async: false,
        url: "friendships/getAllUsersExceptLoggedIn",
        dataType: "json",
        success: function (data) {

            $.ajax({
                url: "friendships/getAllFriendships",
                type: "POST",
                dataType: "json",
                success: function (dataFriends) {
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

                    if (dataFriends.length > 0) {
                        for (i = 0; i < dataFriends.length; i++) {

                            newFriendship =
                                "<div class='container'>"
                                + "<div class='divFriendsToAdd'>"
                            if (dataFriends[i].sent == true && dataFriends[i].accepted == false) {
                                newFriendship += "<p>" + dataFriends[i].receiver.firstName + " " + dataFriends[i].receiver.lastName + " " + dataFriends[i].receiver.email

                                newFriendship += "<button type=\"button\" class=\"btn btn-warning\" onclick=\"ukloniPrijatelja(" + dataFriends[i].receiver.id + ")\">Cancel request</button>"
                                friendsDiv.append(newFriendship);
                            }


                        }


                    }
                    $.ajax({
                        url: "friendships/getRequests",
                        type: "POST",
                        dataType: "json",
                        success: function (dataFriends) {
                            if (dataFriends.length > 0) {
                                newFriendship1 = "<br/><br/><p><h2> Request list:</h2></p>";

                                for (i = 0; i < dataFriends.length; i++) {

                                    newFriendship1 += "<div class='container'>"
                                        + "<div class='divFriendsToAdd'>"
                                        + "<p>" + dataFriends[i].sender.firstName + " " + dataFriends[i].sender.lastName + " " + dataFriends[i].sender.email

                                    newFriendship1 += "<button type=\"button\" class=\"btn btn-success\" onclick=\"prihvatiPrijatelja(" + dataFriends[i].sender.id + ")\">Accept </button></p>"
                                    newFriendship1 += "<button type=\"button\" class=\"btn btn-warning\" onclick=\"ukloniPrijatelja(" + dataFriends[i].sender.id + ")\">Decline </button></p>"


                                }
                                newFriendship1 += "</div>"
                                    + "</div>";
                                friendsDiv.append(newFriendship1);
                            }
                        }
                    });

                    $.ajax({
                        url: "friendships/getConfirmed",
                        type: "POST",
                        dataType: "json",
                        success: function (dataFriends) {
                            if (dataFriends.length > 0) {
                                newFriendship2 = "<br/><br/><p><h2> Friend list:</h2></p>";

                                for (i = 0; i < dataFriends.length; i++) {

                                    newFriendship2 += "<div class='container'>"
                                        + "<div class='divFriendsToAdd'>"
                                        + "<p>" + dataFriends[i].firstName + " " + dataFriends[i].lastName + " " + dataFriends[i].email

                                    newFriendship2 += "<button type=\"button\" class=\"btn btn-danger\" onclick=\"ukloniPrijatelja(" + dataFriends[i].id + ")\">Remove friend</button></p>"


                                }
                                newFriendship2 += "</div>"
                                    + "</div>";
                                friendsDiv.append(newFriendship2);
                            }
                        }
                    });

                }, error: function (jqxhr, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });



        }
    });


}


function prihvatiPrijatelja(idFriend) {
    $.ajax({
        async: false,
        url: "friendships/accept/" + idFriend,
        type: "PUT",
        dataType: "json",
        success: function (data) {
            if (data == null) {
                toastr["error"]("Failed to remove friend");

            } else {
                tabFriendsClick();
                toastr["success"]("Friend request accepted");

            }
        }, error: function (jqxhr, textStatus, errorThrown) {
            alert(errorThrown);
        }

    });

}

function ukloniPrijatelja(idFriend) {
    $.ajax({
        async: false,
        url: "friendships/removeFriend/" + idFriend,
        type: "DELETE",
        success: function (data) {
            if (data != "") {
                toastr["error"]("Failed to remove friend");

            } else {
                tabFriendsClick();
                toastr["success"]("Friend deleted");

            }
        }, error: function (jqxhr, textStatus, errorThrown) {
            alert(errorThrown);
        }

    });
}

function logout() {
    $.ajax({
        url: "user/logout",
        type: "POST",
        dataType: "json",
        success: function (data) {
            if (data == true) {
                top.location.href = "login.html";
            } else {
                toastr["error"]("Failed to logout");
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


                tabFriendsClick();

            }
        }, error: function (jqxhr, textStatus, errorThrown) {
            alert(errorThrown);
        }

    });


}


function signout() {
    $.ajax({
        url: "user/signout",
        type: "POST",
        contentType: "application/json",
        success: function () {
            toastr["success"]("signed out");
        }, error: function () {
            toastr["error"]("Username/password is incorrect,doesn't exist or empty");
        }

    });
}

function myProfile() {
    var friendsDiv = $('#friendsDiv');
    friendsDiv.empty();
    var divHome = $('#tabHomeMyProfile');
    divHome.empty();
    $.ajax({
        url: "user/getLoggedInUser",
        dataType: "json",
        type: "POST",
        success: function (data) {
            var profileDiv = $('#profileDiv');
            profileDiv.empty();

            currentUser =
                "<div class='container'>"
                + "<div class='divFriends'>"

                + "<p> Name: " + data.firstName + "  <button type=\"button\" class=\"btn btn-primary\" onclick=\"changeFirstName()\">Change</button></p>"
                + "<p> Surname: " + data.lastName + "<button type=\"button\" class=\"btn btn-primary\" onclick=\"changeLastName()\">Change</button></p>"
                + "<p> Email: " + data.email + "<button type=\"button\" class=\"btn btn-primary\" onclick=\"changeEmail()\">Change</button></p>"
                + "<p> Phone number: " + data.phoneNumber + "<button type=\"button\" class=\"btn btn-primary\" onclick=\"changePhoneNumber()\">Change</button></p>"
                + "<a href=\"changePassword.html\">Change password</a>"
                + "</div>"
                + "</div>";
            profileDiv.append(currentUser);

        }


    });
}

function changeFirstName() {
    $.ajax({
        url: "user/getLoggedInUser",
        type: "POST",
        success: function (data) {
            var changeInformationDiv = $('#changeInformationDiv');
            changeInformationDiv.empty();

            changeFistName =
                "<div class='container'>"
                + "<div class='changeInformationDiv'>"

                + "<p> Change first name: </p>"
                + "<form id=\"changeFirstNameForm\" method=\"POST\"><input class=\"form-control\" type=\"text\" id=\"firstName\" name=\"firstName\"\n" +
                "                               value=\"" + data.firstName + "\"><br/></form>"
                + "  <button type=\"button\" class=\"btn btn-primary\" onclick='changeFirstNameConfirm()'\">Confirm</button>"
                + "</div>"
                + "</div>";
            changeInformationDiv.append(changeFistName);

        }


    });
}

function changeLastName() {
    $.ajax({
        url: "user/getLoggedInUser",
        type: "POST",
        success: function (data) {
            var changeInformationDiv = $('#changeInformationDiv');
            changeInformationDiv.empty();

            changeLastName =
                "<div class='container'>"
                + "<div class='changeInformationDiv'>"

                + "<p> Change last name: </p>"
                + "<form id=\"changeLastNameForm\" method=\"POST\"><input class=\"form-control\" type=\"text\" id=\"lastName\" name=\"lastName\"\n" +
                "                               value=\"" + data.lastName + "\"><br/></form>"
                + "  <button type=\"button\" class=\"btn btn-primary\" onclick='changeLastNameConfirm()'\">Confirm</button>"
                + "</div>"
                + "</div>";
            changeInformationDiv.append(changeLastName);

        }


    });
}

function changeEmail() {
    $.ajax({
        url: "user/getLoggedInUser",
        type: "POST",
        success: function (data) {
            var changeInformationDiv = $('#changeInformationDiv');
            changeInformationDiv.empty();

            changeEmail =
                "<div class='container'>"
                + "<div class='changeInformationDiv'>"

                + "<p> Change email: </p>"
                + "<form id=\"changeEmailForm\" method=\"POST\"><input class=\"form-control\" type=\"text\" id=\"email\" name=\"email\"\n" +
                "                               value=\"" + data.email + "\"><br/></form>"
                + "  <button type=\"button\" class=\"btn btn-primary\" onclick='changeEmailConfirm()'\">Confirm</button>"
                + "</div>"
                + "</div>";
            changeInformationDiv.append(changeEmail);

        }


    });
}

function changePhoneNumber() {
    $.ajax({
        url: "user/getLoggedInUser",
        type: "POST",
        success: function (data) {
            var changeInformationDiv = $('#changeInformationDiv');
            changeInformationDiv.empty();

            changePhoneNumber =
                "<div class='container'>"
                + "<div class='changeInformationDiv'>"

                + "<p> Change phone number: </p>"
                + "<form id=\"changePhoneNumberForm\" method=\"POST\"><input class=\"form-control\" type=\"text\" id=\"phoneNumber\" name=\"phoneNumber\"\n" +
                "                               value=\"" + data.phoneNumber + "\"><br/></form>"
                + "  <button type=\"button\" class=\"btn btn-primary\" onclick='changePhoneNumberConfirm()'\">Confirm</button>"
                + "</div>"
                + "</div>";
            changeInformationDiv.append(changePhoneNumber);

        }


    });
}

function changeFirstNameConfirm() {
    $form = $("#changeFirstNameForm");
    var data = getFormData($form);
    var s = JSON.stringify(data);
    $.ajax({
        url: "user/changeFirstName",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: s,
        success: function (data) {
            var changeInformationDiv = $('#changeInformationDiv');
            changeInformationDiv.empty();

            myProfile();

        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"]("Could not change firstName");
        }


    });
}

function changeLastNameConfirm() {
    $form = $("#changeLastNameForm");
    var data = getFormData($form);
    var s = JSON.stringify(data);
    $.ajax({
        url: "user/changeLastName",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: s,
        success: function (data) {
            var changeInformationDiv = $('#changeInformationDiv');
            changeInformationDiv.empty();

            myProfile();

        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"]("Could not change last name");
        }


    });
}

function changeEmailConfirm() {
    $form = $("#changeEmailForm");
    var data = getFormData($form);
    var s = JSON.stringify(data);
    $.ajax({
        url: "user/changeEmail",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: s,
        success: function (data) {
            var changeInformationDiv = $('#changeInformationDiv');
            changeInformationDiv.empty();

            myProfile();

        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"]("Email already exists on a different account");
        }


    });
}

function changePhoneNumberConfirm() {
    $form = $("#changePhoneNumberForm");
    var data = getFormData($form);
    var s = JSON.stringify(data);
    $.ajax({
        url: "user/changePhoneNumber",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: s,
        success: function (data) {
            var changeInformationDiv = $('#changeInformationDiv');
            changeInformationDiv.empty();

            myProfile();

        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"]("Could not change phone number");
        }


    });
}
