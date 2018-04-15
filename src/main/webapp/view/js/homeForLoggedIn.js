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
                    + "<td id='auditoriumName" + data.id + "'>" + data.name + "</td>"
                    + "<td class=\"td-actions\">"
                    + "<a href=\"#\" onclick=\"return editAuditorium(" + data.id + ");\" class=\"btn btn-small btn-primary\">"
                    + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return deleteAuditorium(" + idInstitution + ", " + data.id + ");\" class=\"btn btn-small btn-danger\">\n"
                    + "<i class=\"glyphicon glyphicon-remove\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return showSegments(" + idInstitution + ", " + data.id + ");\" class=\"btn btn-small btn-default\">"
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
        idAuditorium = $("#idAuditoriuEdit").val();
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
});

function showInstitution(idInstitution) {
    $.ajax({
        async: false,
        type: "GET",
        url: "institution/getById/" + idInstitution,
        dataType: "json",
        success: function (data) {
            var tab = $('#tab' + idInstitution);
            tab.empty();
            newInstitution =
                "<div>"
                + "<h2>" + data.name + "</h2>"
                + "<p>" + data.description + "</p>"

                + "</div><br><br>";
            tab.append(newInstitution);
            tabela = "<div class='container'>" +
                "<div class='col-lg-6'>" +
                "<div class=\"span7\">\n" +
                "                                <div class=\"widget stacked widget-table action-table\">\n" +
                "                                    <div class=\"widget-header\">\n" +
                "<a href=\"#\" onclick=\"return addAuditorium(" + idInstitution + ");\" class=\"btn btn-small btn-success\">"
                + "<i class=\"glyphicon glyphicon-plus\"></i>"
                + "</a>&nbsp;&nbsp;&nbsp;" +
                "                                        <h3>Auditoriums</h3>\n" +

                "                                    </div>\n" +
                "                                    <div class=\"widget-content\">\n" +
                "                                        <table class=\"table table-striped table-bordered\">\n" +
                "                                            <thead>\n" +
                "                                            <tr>\n" +
                "                                                <th>Name</th>\n" +
                "                                                <th class=\"td-actions\" style=\"width:200px\"></th>\n" +
                "                                            </tr>\n" +
                "                                            </thead>\n" +
                "                                            <tbody id=\"tableAuditoriumsTBody" + idInstitution + "\">\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>" +
                "</div>" +
                "<div class='col-lg-6'>" +
                "<div class=\"span7\">\n" +
                "                                <div class=\"widget stacked widget-table action-table\">\n" +
                "                                    <div id='divAddSegment' class=\"widget-header\">\n" +
                "                                        <h3>Segments</h3>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"widget-content\">\n" +
                "                                        <table class=\"table table-striped table-bordered\">\n" +
                "                                            <thead>\n" +
                "                                            <tr>\n" +
                "                                                <th>Lable</th>\n" +
                "                                                <th class=\"td-actions\" style=\"width:200px\"></th>\n" +
                "                                            </tr>\n" +
                "                                            </thead>\n" +
                "                                            <tbody id=\"tableSegmentsTBody" + idInstitution + "\">\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>" +
                "</div>" +
                "</div>";
            tab.append(tabela);

            $.ajax({
                async: false,
                type: "GET",
                url: "auditorium/getByInstitution/" + idInstitution,
                dataType: "json",
                success: function (data) {
                    for (i = 0; i < data.length; i++) {
                        newAuditorium = "<tr>"
                            + "<td id='auditoriumName" + data[i].id + "'>" + data[i].name + "</td>"
                            + "<td class=\"td-actions\">"
                            + "<a href=\"#\" onclick=\"return editAuditorium(" + data[i].id + ");\" class=\"btn btn-small btn-primary\">"
                            + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                            + "</a>&nbsp;&nbsp;&nbsp;"
                            + "<a href=\"#\" onclick=\"return deleteAuditorium(" + idInstitution + ", " + data[i].id + ");\" class=\"btn btn-small btn-danger\">\n"
                            + "<i class=\"glyphicon glyphicon-remove\"></i>"
                            + "</a>&nbsp;&nbsp;&nbsp;"
                            + "<a href=\"#\" onclick=\"return showSegments(" + idInstitution + ", " + data[i].id + ");\" class=\"btn btn-small btn-default\">"
                            + "<i class=\"glyphicon glyphicon-arrow-right\"></i>"
                            + "</a>&nbsp;&nbsp;&nbsp;"
                            + "</td>"
                            + "</tr>";

                        $("#tableAuditoriumsTBody" + idInstitution).append(newAuditorium);
                    }
                }
            });
        }
    });
    return false;
}

function editAuditorium(idAuditorium) {
    $("#idAuditoriuEdit").val(idAuditorium);
    auditoriumName = $("#auditoriumName" + idAuditorium).text();
    $("#nameAuditoriumEdit").val(auditoriumName);
    $("#editAuditoriumModal").modal('toggle');
    return false;
}

function deleteAuditorium(idInstitution, idAuditorium) {
    $.ajax({
        async: false,
        type: "DELETE",
        url: "auditorium/deleteAuditorium/" + idAuditorium,
        dataType: "json",
        success: function (data) {
            $('#auditoriumName' + data.id).closest("tr").remove();
        }
    });
    return false;
}

function addAuditorium(idInstitution) {
    $('#addAuditoriumInstitutionId').val(idInstitution);
    $('#addAuditoriumName').val('');
    $("#addAuditoriumModal").modal('toggle');
    return false;
}

function showSegments(idInstitution, idAuditorium) {
    $.ajax({
        async: false,
        type: "GET",
        url: "segment/getByAuditorium/" + idAuditorium,
        dataType: "json",
        success: function (data) {
            $('#tableSegmentsTBody').empty();
            for (i = 0; i < data.length; i++) {
                newSegment = "<tr>"
                    + "<td id='auditoriumName" + data[i].id + "'>" + data[i].label + "</td>"
                    + "<td class=\"td-actions\">"
                    + "<a href=\"#\" onclick=\"return editSegment(" + data[i].id + ");\" class=\"btn btn-small btn-primary\">"
                    + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return deleteSegment(" + data[i].id + ");\" class=\"btn btn-small btn-danger\">\n"
                    + "<i class=\"glyphicon glyphicon-remove\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "</td>"
                    + "</tr>";

                $("#tableSegmentsTBody").append(newSegment);
            }
            $('#divAddSegment').empty();
            var addButton =
                "<a href=\"#\" onclick=\"return addSegment(" + idAuditorium + ");\" class=\"btn btn-small btn-success\">"
                + "<i class=\"glyphicon glyphicon-plus\"></i>"
                + "</a>&nbsp;&nbsp;&nbsp;<h3>Segments</h3>";

            $('#divAddSegment').append(addButton);
        }
    });
    return false;
}

function editSegment(idSegment) {
    alert('edits' + idSegment);
    return false;
}

function deleteSegment(idSegment) {
    alert('deletes' + idSegment);
    return false;
}

function addSegment(idAuditorium) {
    alert('adds' + idAuditorium);
    return false;
}

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
        url: "/projections/getConfigurationForProjection/" + idProjection,
        type: "GET",
        dataType: "json",
        success: function (dataConfig) {

            var newConfig = "<p>";
            for (k = 0; k < dataConfig.segments.length; k++) {
                var segment = (dataConfig.segments[k]);
                newConfig += "<p>";
                for (i = 0; i < segment.seatTicketDTOList.length; i++) {
                    var seat = segment.seatTicketDTOList[i];
                    newConfig += "<button type=\"button\" class=\"btn btn-primary\" onclick='reserveSeat("
                        + seat.idTicket + ")'>" + seat.seatNumber + "</button>&thinsp;&thinsp;";
                }
                newConfig += "</p>";
            }
            newConfig += "</p>";

            configDiv.append(newConfig);
        }

    });

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


