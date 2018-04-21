function tabMyReservationsClick() {
    var configDiv = $('#configDiv');
    configDiv.empty();
    var invitesDiv = $('#invitesDiv');
    var confirmedDiv = $('#confirmedDiv');
    confirmedDiv.empty();
    invitesDiv.empty();
    var reserverDiv = $('#reserverDiv');
    reserverDiv.empty();
    $.ajax({
        async: false,
        url: "reservations/getReserverReservations",
        dataType: "json",
        type: "POST",
        success: function (data) {

            for (var j = 0; j < data.length; j++) {

                var projectionId = data[j].idProjection;
                $.ajax({
                    async: false,
                    url: "projections/getProjection/" + projectionId,
                    dataType: "json",
                    type: "GET",
                    success: function (dataProjection) {
                        reserverDiv.append("<h3>Reservation for:</h3>");
                        reserverDiv.append("<p><b>Reservetion made:</b> " + data[j].date + "</p>");
                        reserverDiv.append("<p><b>Projection date: </b>" + dataProjection.date + "</p>");
                        $.ajax({
                            async: false,
                            url: "show/getByShowId/" + dataProjection.id_show,
                            dataType: "json",
                            type: "POST",
                            success: function (dataShow) {
                                reserverDiv.append("<p><b>Projection for show: </b>" + dataShow.name + "</p>");
                                reserverDiv.append("<p><b>Show genre: </b>" + dataShow.genre + "</p>");
                                reserverDiv.append("<p><b>Show producer: </b>" + dataShow.producer + "</p>");
                                reserverDiv.append("<p><b>Show duration: </b>" + dataShow.duration + "</p>");
                                reserverDiv.append("<p><b>Show description: </b>" + dataShow.description + "</p>");
                                for (var i = 0; i < data[j].friends.length; i++) {

                                    var friend = JSON.stringify({
                                        "email": data[j].friends[i]
                                    });

                                    $.ajax({
                                        async: false,
                                        url: "user/getUserByEmail",
                                        contentType: "application/json",
                                        data: friend,
                                        dataType: "json",
                                        type: "POST",
                                        success: function (dataFriends) {

                                            reserverDiv.append("<p><b>Invited guest: </b>" + dataFriends.firstName + "&nbsp;" + dataFriends.lastName + "</p>");
                                        }
                                    });
                                }
                                reserverDiv.append("<button type=\"button\" class=\"btn btn-warninig\" onclick=\"otkaziRezervaciju(" + data[j].id + ")\">Cancel reservation</button>");


                            }
                        });


                    }
                });
            }
        }
    });


}

function otkaziRezervaciju(idReservation) {
    $.ajax({
        async: false,
        url: "reservations/cancelReservation/" + idReservation,
        dataType: "json",
        type: "DELETE",
        success: function (data) {
            if (data == true) {
                $.ajax({
                    async: false,
                    url: "invites/cancelInvites/" + idReservation,
                    dataType: "json",
                    type: "DELETE",
                    success: function (dataInvite) {
                        if (dataInvite == true) {
                            tabMyReservationsClick();
                        } else {
                            toastr["error"]("Could not delete invites");
                        }

                    }
                });
            } else {
                toastr["error"]("Could not delete reservation");
            }
        }

    });

}

