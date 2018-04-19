function getFormData($form) {
    var unordered_array = $form.serializeArray();
    var ordered_array = {};

    $.map(unordered_array, function (n, i) {
        ordered_array[n['name']] = n['value'];
    });
    return ordered_array;
}

$(document).ready(function () {

    $.ajax({

        url: "institution/getInstitutionsByAdmin",
        dataType: "json",
        success: function (data) {
            for (i = 0; i < data.length; i++) {
                $("#tab" + data[i].id).remove();
                $("a[href=\"#tab" + data[i].id + "\"]").remove();
                $("#tabovi").append("<li><a href=\"#tab" + data[i].id + "\" onclick='return showInstitution(" + data[i].id + ")' data-toggle=\"tab\">" + data[i].name + "</a></li>");
                $("#divovi").append("<div class=\"tab-pane fade\" id=\"tab" + data[i].id + "\">" + data[i].name + "</div>");
            }
        }
    });

    /*$.ajax({
        async: false,
        url: "institution/getInstitutions",
        dataType: "json",
        success: function (data) {
            for (i = 0; i < data.length; i++) {
                $("#tabovi").append("<li><a href=\"#tab" + data[i].id + "\" onclick='return showInstitution(" + data[i].id + ")' data-toggle=\"tab\">" + data[i].name + "</a></li>");
                $("#divovi").append("<div class=\"tab-pane fade\" id=\"tab" + data[i].id + "\">" + data[i].name + "</div>");
            }
        }
    });*/

});

function tabCinemasClick() {
    $("#friendsDiv").empty();
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
    $("#friendsDiv").empty();
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
    $("#friendsDiv").empty();
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
    $("#friendsDiv").empty();
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
    $("#friendsDiv").empty();
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
                    newShow = "<p id='currentShow'>"
                        + "<b>Projections for movie: " + dataShow.name + " </b><br> ";
                    var projList = [];
                    for (k = 0; k < dataProjections.length; k++) {
                        var timeProjection = (dataProjections[k].date).substring(10, 16);
                        newShow += "<button type=\"button\" id='" + dataProjections[k].id + "'  class=\"btn btn-primary\" onclick='configurationClick("
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

    var show = $('#currentShow');
    var idProjection = idProjection;
    var button = []
    button = show[0].getElementsByTagName("button");
    for (var i = 0; i < button.length; i++) {
        if (idProjection == button[i].id) {
            button[i].setAttribute("class", "btn btn-info");
        } else {
            button[i].setAttribute("class", "btn btn-primary");
        }
    }

    $("#friendsDiv").empty();
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
            var newConfig = "<div id='buttons'>";
            for (k = 0; k < dataConfig.segments.length; k++) {

                var segment = (dataConfig.segments[k]);
                newConfig += "<p>";
                for (j = 1; j < segment.rowCount; j++) {


                    for (i = 0; i < segment.seatTicketDTOList.length; i++) {

                        var seat = segment.seatTicketDTOList[i];

                        if (seat.row == j) {

                            if (seat.reserved == false) {
                                newConfig += "<button type=\"button\" id=\"" + seat.idTicket + "\" class=\"btn btn-primary\" onclick='reserveSeat("
                                    + seat.idTicket + ")'>" + seat.seatNumber + "</button>&thinsp;&thinsp;"
                            } else {
                                newConfig += "<button type=\"button\" class=\"btn btn-danger\" >" + seat.seatNumber +
                                    "</button>&thinsp;&thinsp;"


                            }

                        } else {
                            continue;
                        }

                    }

                    newConfig += "<br/>";

                }
                newConfig += "</p>";

            }
            newConfig += "<br/></div>";
            newConfig += "<p style='\"float:right;\"'><input type=\"text\" autocomplete=\"on\" name=\"example\" list=\"exampleList\"></p>"
            if (dataConfig.segments.length > 0) {
                $.ajax({
                    url: "friendships/getFriends",
                    type: "POST",
                    dataType: "json",
                    success: function (data) {
                        newConfig += "<button type=\"button\" class=\"btn btn-success\" onclick=\"pozoviPrijatelja()\">Invite friend</button>"
                        newConfig += "<datalist id=\"exampleList\">"
                        for (i = 0; i < data.length; i++) {
                            newConfig +=
                                "<option data-value=\"" + data[i].email + "\">" + data[i].firstName + "&nbsp;" + data[i].lastName + "</option>"
                        }
                        newConfig += "</datalist></p>";
                        newConfig += "<h4> Invited friends:</h4><ul id=\"invitedFriends\"></ul>";
                        newConfig += "<button type=\"button\" class=\"btn btn-warning\" onclick=\"createReservation()\">Create reservation</button>"
                        configDiv.append(newConfig);
                    }

                });
            }


        }

    });

}

function createReservation() {
    var invitedFriendsList = $('#invitedFriends');
    var invitedFriends = [];
    for (var i = 0; i < invitedFriendsList[0].childNodes.length; i++) {
        invitedFriends.push(invitedFriendsList[0].childNodes[i].innerText);
    }

    var seats = $('#buttons');
    var paragraphs = seats[0].getElementsByTagName("p");
    var buttonIds = [];
    for (var j = 0; j < paragraphs.length; j++) {
        for (var i = 0; i < paragraphs[j].childNodes.length; i++) {
            if (paragraphs[j].childNodes[i].className == "btn btn-success") {
                buttonIds.push(paragraphs[j].childNodes[i].id);
            }
        }
    }
    var currentShow = $("#currentShow");

    var show = $('#currentShow');
    var idProjection;
    var timeProjection;
    var button = []
    button = show[0].getElementsByTagName("button");
    for (var i = 0; i < button.length; i++) {
        if (button[i].getAttribute("class") == "btn btn-info") {
            idProjection = button[i].id;
            timeProjection = button[i].innerText;
            break;
        }
    }

    var data = JSON.stringify({
        "idSeat": buttonIds,
        "idProjection": idProjection,
        "friends": invitedFriends
    });

    $.ajax({
        url: "reservations/createReservation",
        dataType: "json",
        type: "POST",
        contentType: "application/json",
        data: data,
        success: function (data) {
            top.location.href = "myReservations.html";
        }

    });


}


function pozoviPrijatelja() {
    var inviteList = $('#invitedFriends');
    var configDiv = $('#configDiv');
    var added = $('input[name=\'example\']').val();

    var x = document.getElementById("exampleList");
    var ret = "";
    for (var i = 0; i < x.childNodes.length; i++) {
        if (x.childNodes[i].innerText == added) {

            ret = x.childNodes[i].dataset.valueOf().value;
            break;
        }
    }

    for (var j = 0; j < inviteList.length; j++) {
        if (inviteList[j].childNodes.length == 0) {
            var addedDiv = "<li> " + ret + "</li>";
            inviteList.append(addedDiv);
        } else {
            var found = false;
            for (var k = 0; k < inviteList[j].childNodes.length; k++) {
                if (inviteList[j].childNodes[k].innerText == ret) {
                    found = true;
                    break;
                }
            }
            if (found == false) {
                var addedDiv = "<li> " + ret + "</li>";
                inviteList.append(addedDiv);
            }
        }
    }

    configDiv.append(inviteList);

}


function reserveSeat(idSeat) {


    var seats = $('#buttons');
    var paragraphs = seats[0].getElementsByTagName("p");
    var buttonIds = [];
    for (var j = 0; j < paragraphs.length; j++) {
        for (var i = 0; i < paragraphs[j].childNodes.length; i++) {
            if (paragraphs[j].childNodes[i].id == idSeat) {
                if (paragraphs[j].childNodes[i].getAttribute("class") == 'btn btn-success') {
                    paragraphs[j].childNodes[i].setAttribute("class", "btn btn-primary");
                } else {

                    paragraphs[j].childNodes[i].setAttribute("class", "btn btn-success");
                }

            }
        }
    }

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
    friendsDiv.empty();

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
                        newFriendship4 = "<br/><br/><p><h4 style=\"font-weight:bold\"> Sent requests:</h4></p>";
                        for (i = 0; i < dataFriends.length; i++) {

                            newFriendship4 +=
                                "<div class='container'>"
                                + "<div class='divFriendsAdded'>"
                            if (dataFriends[i].sent == true && dataFriends[i].accepted == false) {
                                newFriendship4 += "<p>" + dataFriends[i].receiver.firstName + " " + dataFriends[i].receiver.lastName + " " + dataFriends[i].receiver.email

                                newFriendship4 += "<button type=\"button\" class=\"btn btn-warning\" onclick=\"ukloniPrijatelja(" + dataFriends[i].receiver.id + ")\">Cancel request</button>"

                            }


                        }
                        friendsDiv.append(newFriendship4);


                    }
                    $.ajax({
                        url: "friendships/getRequests",
                        type: "POST",
                        dataType: "json",
                        success: function (dataFriends) {
                            if (dataFriends.length > 0) {
                                newFriendship1 = "<br/><br/><p><h4 style=\"font-weight:bold\"> Request list:</h4></p>";

                                for (i = 0; i < dataFriends.length; i++) {

                                    newFriendship1 += "<div class='container'>"
                                        + "<div class='divFriendsToConfirm'>"
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
                                newFriendship2 = "<br/><br/><p><h4 style=\"font-weight:bold\"> Friend list:</h4></p>";

                                for (i = 0; i < dataFriends.length; i++) {

                                    newFriendship2 += "<div class='container'>"
                                        + "<div class='divFriendsConfimed'>"
                                        + "<p>" + dataFriends[i].firstName + " " + dataFriends[i].lastName + " " + dataFriends[i].email

                                    newFriendship2 += "<button type=\"button\" class=\"btn btn-danger\" onclick=\"ukloniPrijatelja(" + dataFriends[i].id + ")\">Remove friend</button></p>"


                                }
                                newFriendship2 += "</div>"
                                    + "</div>";
                                friendsDiv.append(newFriendship2);

                            }
                        }
                    });

                    friendsDiv.append("<form><div><h4 style=\"font-weight:bold\">SEARCH FRIENDS BY:</h4><br/><div class=\"input-group\">"
                        + "<input type=\"text\" id=\"searchFirstName\" name=\"searchFirstName\" class=\"form-control\" placeholder=\"Search by first name\">"
                        + "<span class=\"input-group-btn\">"
                        + "<button class=\"btn btn-primary\" style=\"width: 200px\" type=\"button\" onclick=\"pretraziPoImenu()\"><span class=\"	glyphicon glyphicon-search\"></span> Search by first name</button>"
                        + "</span></div>");
                    friendsDiv.append("<form><div><div class=\"input-group\">"
                        + "<input type=\"text\" id=\"searchLastName\" name=\"searchLastName\" class=\"form-control\" placeholder=\"Search by last name\">"
                        + "<span class=\"input-group-btn\">"
                        + "<button class=\"btn btn-primary\" style=\"width: 200px\" type=\"button\" onclick=\"pretraziPoPrezimenu()\"><span class=\"	glyphicon glyphicon-search\"></span> Search by last name</button>"
                        + "</span></div>");
                    friendsDiv.append("<button class=\"btn btn-primary\" style=\"width:400px\" type=\"button\" onclick=\"kombinovanaPretraga()\"><span class=\"	glyphicon glyphicon-search\"></span> Search by first and last name</button>");


                }, error: function (jqxhr, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });


        }
    });


}

function kombinovanaPretraga() {
    var firstName = $("#searchFirstName").val();
    var lastName = $("#searchLastName").val();
    var data = JSON.stringify({
        "firstName": firstName,
        "lastName": lastName
    });
    var friendsDiv = $('#friendsDiv');
    $.ajax({
        url: "friendships/searchCombined",
        type: "POST",
        data: data,
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var newFriendship2 = $('.divFriendsConfimed');
            newFriendship2.empty();
            if (data.length > 0) {

                newFriendship2 = "<br/><br/><p><h2> Friend list:</h2></p>";

                for (i = 0; i < data.length; i++) {

                    newFriendship2 = "<div class='container'>"
                        + "<div class='divFriendsConfimed'>"
                        + "<p>" + data[i].firstName + " " + data[i].lastName + " " + data[i].email

                    newFriendship2 += "<button type=\"button\" class=\"btn btn-danger\" onclick=\"ukloniPrijatelja(" + data[i].id + ")\">Remove friend</button></p>"


                }
                newFriendship2 += "</div>"
                    + "</div>";
                friendsDiv.append(newFriendship2);
            } else {

                friendsDiv.append(newFriendship2);

            }
        }, error: function (jqxhr, textStatus, errorThrown) {
            alert(errorThrown);
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
        dataType: "text",
        success: function (data) {
                top.location.href = "login.html";

        }, error: function (jqxhr, textStatus, errorThrown) {
            alert(errorThrown);
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

function FindLatLong(address, callback) {
    var geocoder = new google.maps.Geocoder();

    geocoder.geocode({'address': address}, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            var lat = results[0].geometry.location.lat();
            var lng = results[0].geometry.location.lng();
            callback({Status: "OK", Latitude: lat, Longitude: lng});
        }
    });
}

function pretraziPoImenu() {
    var firstName = $("#searchFirstName").val();
    var lastName = $("#searchLastName").val();
    var data = JSON.stringify({
        "firstName": firstName,
        "lastName": lastName
    });
    var friendsDiv = $('#friendsDiv');
    $.ajax({
        url: "friendships/searchByFirstName",
        type: "POST",
        data: data,
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var newFriendship2 = $('.divFriendsConfimed');
            newFriendship2.empty();
            if (data.length > 0) {

                newFriendship2 = "<br/><br/><p><h2> Friend list:</h2></p>";

                for (i = 0; i < data.length; i++) {

                    newFriendship2 = "<div class='container'>"
                        + "<div class='divFriendsConfimed'>"
                        + "<p>" + data[i].firstName + " " + data[i].lastName + " " + data[i].email

                    newFriendship2 += "<button type=\"button\" class=\"btn btn-danger\" onclick=\"ukloniPrijatelja(" + data[i].id + ")\">Remove friend</button></p>"


                }
                newFriendship2 += "</div>"
                    + "</div>";
                friendsDiv.append(newFriendship2);
            } else {

                friendsDiv.append(newFriendship2);

            }
        }, error: function (jqxhr, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });

}

function pretraziPoPrezimenu() {
    var firstName = $("#searchFirstName").val();
    var lastName = $("#searchLastName").val();
    var data = JSON.stringify({
        "firstName": firstName,
        "lastName": lastName
    });
    var friendsDiv = $('#friendsDiv');
    $.ajax({
        url: "friendships/searchByLastName",
        type: "POST",
        data: data,
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var newFriendship2 = $('.divFriendsConfimed');
            newFriendship2.empty();
            if (data.length > 0) {

                newFriendship2 = "<br/><br/><p><h2> Friend list:</h2></p>";

                for (i = 0; i < data.length; i++) {

                    newFriendship2 = "<div class='container'>"
                        + "<div class='divFriendsConfimed'>"
                        + "<p>" + data[i].firstName + " " + data[i].lastName + " " + data[i].email

                    newFriendship2 += "<button type=\"button\" class=\"btn btn-danger\" onclick=\"ukloniPrijatelja(" + data[i].id + ")\">Remove friend</button></p>"


                }
                newFriendship2 += "</div>"
                    + "</div>";
                friendsDiv.append(newFriendship2);
            } else {

                friendsDiv.append(newFriendship2);

            }
        }, error: function (jqxhr, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}
