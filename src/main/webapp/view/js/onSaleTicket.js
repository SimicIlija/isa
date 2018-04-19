function showOnSaleTickets(idInstitution, idProjection, idSegment) {
    $.ajax({
        async: false,
        type: "GET",
        url: "onSaleTicket/getOnSaleTickets/" + idProjection + "/" + idSegment,
        dataType: "json",
        success: function (data) {
            var tableTicketsOnSaleTBody = $('#tableTicketsOnSaleTBody' + idInstitution);
            tableTicketsOnSaleTBody.empty();
            for (i = 0; i < data.length; i++) {
                newOnSaleTicket = "<tr>"
                    + "<td id='onSaleTicketShowName" + data[i].id + "'>" + data[i].showName + "</td>"
                    + "<td id='onSaleTicketOldPrice" + data[i].id + "'>" + data[i].oldPrice + "</td>"
                    + "<td id='onSaleTicketDiscount" + data[i].id + "'>" + data[i].discount + "</td>"
                    + "<td id='onSaleTicketSeatRow" + data[i].id + "'>" + data[i].seatRow + "</td>"
                    + "<td id='onSaleTicketSeatNumber" + data[i].id + "'>" + data[i].seatNumber + "</td>"
                    + "<td class=\"td-actions\">"
                    + "<a href=\"#\" onclick=\"return editOnSaleTicket(" + idInstitution + ", " + data[i].id + ");\" class=\"btn btn-small btn-primary\">"
                    + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return deleteOnSaleTicket(" + idInstitution + ", " + data[i].id + ");\" class=\"btn btn-small btn-danger\">\n"
                    + "<i class=\"glyphicon glyphicon-remove\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "</td>"
                    + "</tr>";

                tableTicketsOnSaleTBody.append(newOnSaleTicket);
            }
        }
    });

    var divAddOnSaleTickets = $('#divAddOnSaleTickets' + idInstitution);
    divAddOnSaleTickets.empty();
    var addButton1 =
        "<a href=\"#\" onclick=\"return addOnSaleTicket(" + idInstitution + ", " + idProjection + ", " + idSegment + ");\" class=\"btn btn-small btn-success\">"
        + "<i class=\"glyphicon glyphicon-plus\"></i>"
        + "</a>&nbsp;&nbsp;&nbsp;<h3>On sale tickets for projection #" + idProjection + "</h3>";
    divAddOnSaleTickets.append(addButton1);

    return false;
}

function addOnSaleTicket(idInstitution, idProjection, idSegment) {
    $('#addOnSaleTicketInstitutionId').val(idInstitution);
    $('#addOnSaleTicketProjectionId').val(idProjection);
    $('#addOnSaleTicketSegmentId').val(idSegment);

    $('#addOnSaleTicketSeatRow').val('');
    $('#addOnSaleTicketSeatNumber').val('');
    $('#addOnSaleTicketDiscount').val('');
    $("#addOnSaleTicketModal").modal('toggle');
    return false;
}

function editOnSaleTicket(idInstitution, idOnSaleTicket) {
    $("#editOnSaleTicketInstitutionId").val(idInstitution);
    $("#editOnSaleTicketId").val(idOnSaleTicket);
    discount = $("#onSaleTicketDiscount" + idOnSaleTicket).text();

    $("#editOnSaleTicketDiscount").val(discount);

    $("#editOnSaleTicketModal").modal('toggle');
    return false;
}

function deleteOnSaleTicket(idInstitution, idOnSaleTicket) {
    $.ajax({
        async: false,
        type: "DELETE",
        url: "onSaleTicket/deleteOnSaleTicket/" + idOnSaleTicket,
        dataType: "json",
        success: function (data) {
            $('#onSaleTicketShowName' + data.id).closest("tr").remove();
        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"](jqxhr.responseText);
        }
    });
    return false;
}