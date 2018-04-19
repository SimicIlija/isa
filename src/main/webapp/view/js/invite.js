window.onload = function () {
    $.ajax({
        async: false,
        url: "user/getLoggedInUser",
        dataType: "json",
        type: "POST",
        success: function (dataUserLoggedIn) {
            if (dataUserLoggedIn.id == null) {
                top.location.href = "login.html";
            }

        }, error: function (jqxhr, textStatus, errorThrown) {
            alert(errorThrown);
        }

    });
}

function tabMyInvites() {
    $.ajax({
        async: false,
        url: "user/getLoggedInUser",
        dataType: "json",
        type: "POST",
        success: function (dataUserLoggedIn) {
            if (dataUserLoggedIn.id == null) {
                top.location.href = "login.html";
            } else {

                $.ajax({
                    async: false,
                    url: "reservations/getInvites/" + dataUserLoggedIn.id,
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        var invitesDiv = $('#invitesDiv');
                        invitesDiv.empty();
                        invitesDiv.append("<p>");
                        for (var reservation = 0; reservation < data.length; reservation++) {
                            $.ajax({
                                async: false,
                                url: "user/getUserById/" + data[reservation].idReserver,
                                dataType: "json",
                                type: "POST",
                                success: function (dataReserver) {

                                    invitesDiv.append("<h4><b>" + dataReserver.firstName + "&nbsp;" + dataReserver.lastName + " invited you to see the following projection:</b></h4><br/>");
                                    var projectionId = data[reservation].idProjection;
                                    $.ajax({
                                        async: false,
                                        url: "projections/getProjection/" + projectionId,
                                        dataType: "json",
                                        type: "POST",
                                        success: function (dataProjection) {
                                            invitesDiv.append("<p><b>Reservation made:</b> " + data[reservation].date + "</p>");
                                            invitesDiv.append("<p><b>Projection date: </b>" + dataProjection.date + "</p>");
                                            $.ajax({
                                                async: false,
                                                url: "show/getByShowId/" + dataProjection.id_show,
                                                dataType: "json",
                                                type: "POST",
                                                success: function (dataShow) {
                                                    invitesDiv.append("<p><b>Projection for show: </b>" + dataShow.name + "</p>");
                                                    invitesDiv.append("<p><b>Show genre: </b>" + dataShow.genre + "</p>");
                                                    invitesDiv.append("<p><b>Show producer: </b>" + dataShow.producer + "</p>");
                                                    invitesDiv.append("<p><b>Show duration: </b>" + dataShow.duration + "</p>");
                                                    invitesDiv.append("<p><b>Show description: </b>" + dataShow.description + "</p>");

                                                }
                                            });


                                        }
                                    });

                                }
                            });
                            invitesDiv.append("<button type=\"button\" class=\"btn btn-primary\" onclick=\"potvrdiDolazak(" + data[reservation].id + "," + dataUserLoggedIn.id + ")\">Confirm reservation</button>");
                            invitesDiv.append("<button type=\"button\" class=\"btn btn-danger\" onclick=\"otkaziDolazak(" + data[reservation].id + ")\">Reject reservation</button>");
                            invitesDiv.append("</p>");
                            var friendsDiv = $('#friendsDiv');
                            friendsDiv.empty();
                            friendsDiv.append(invitesDiv);
                        }


                    }

                });
            }
        }, error: function (jqxhr, textStatus, errorThrown) {
            alert(errorThrown);
        }

    });

}

function otkaziDolazak(idReservation) {

    $.ajax({
        async: false,
        url: "reservations/decline/" + idReservation,
        dataType: "json",
        type: "PUT",
        success: function (data) {
            if (data == true) {
                tabMyInvites();
            } else {
                toastr["error"]('Could not decline invite');
            }
        }

    });

}

function potvrdiDolazak(idReservation) {

    $.ajax({
        async: false,
        url: "reservations/confirm/" + idReservation,
        dataType: "json",
        type: "POST",
        success: function (dataSuccess) {
            if (dataSuccess == true) {
                tabMyReservationsClick(dataSuccess);
            }
        }
    });

}

function tabMyReservationsClick(dataSuccess) {
    $.ajax({
        async: false,
        url: "user/getLoggedInUser",
        dataType: "json",
        type: "POST",
        success: function (dataUserLoggedIn) {
            if (dataUserLoggedIn.id == null) {
                top.location.href = "login.html";
            } else {

                $.ajax({
                    async: false,
                    url: "reservations/getConfirmedReservations/" + dataUserLoggedIn.id,
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        var invitesDiv = $('#confirmedDiv');
                        invitesDiv.empty();
                        invitesDiv.append("<p>");
                        for (var reservation = 0; reservation < data.length; reservation++) {
                            $.ajax({
                                async: false,
                                url: "user/getUserById/" + data[reservation].idReserver,
                                dataType: "json",
                                type: "POST",
                                success: function (dataReserver) {

                                    invitesDiv.append("<h4><b>You have confirmed" + dataReserver.firstName + "&nbsp;" + dataReserver.lastName + "'s invite</b></h4><br/>");
                                    var projectionId = data[reservation].idProjection;
                                    $.ajax({
                                        async: false,
                                        url: "projections/getProjection/" + projectionId,
                                        dataType: "json",
                                        type: "POST",
                                        success: function (dataProjection) {
                                            invitesDiv.append("<p><b>Reservation made:</b> " + data[reservation].date + "</p>");
                                            invitesDiv.append("<p><b>Projection date: </b>" + dataProjection.date + "</p>");
                                            $.ajax({
                                                async: false,
                                                url: "show/getByShowId/" + dataProjection.id_show,
                                                dataType: "json",
                                                type: "POST",
                                                success: function (dataShow) {
                                                    invitesDiv.append("<p><b>Projection for show: </b>" + dataShow.name + "</p>");
                                                    invitesDiv.append("<p><b>Show genre: </b>" + dataShow.genre + "</p>");
                                                    invitesDiv.append("<p><b>Show producer: </b>" + dataShow.producer + "</p>");
                                                    invitesDiv.append("<p><b>Show duration: </b>" + dataShow.duration + "</p>");
                                                    invitesDiv.append("<p><b>Show description: </b>" + dataShow.description + "</p>");

                                                }
                                            });


                                        }
                                    });

                                }
                            });
                            invitesDiv.append("</p>");
                            var friendsDiv = $('#friendsDiv');
                            friendsDiv.empty();
                            friendsDiv.append(invitesDiv);
                        }


                    }

                });
            }
        }, error: function (jqxhr, textStatus, errorThrown) {
            alert(errorThrown);
        }

    });

}