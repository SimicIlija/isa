function showSegments(idInstitution, idAuditorium) {
    $.ajax({
        async: false,
        type: "GET",
        url: "segment/getByAuditorium/" + idAuditorium,
        dataType: "json",
        success: function (data) {
            $('#tableSegmentsTBody' + idInstitution).empty();
            for (i = 0; i < data.length; i++) {
                newSegment = "<tr>"
                    + "<td id='segmentLabel" + data[i].id + "'>" + data[i].label + "</td>"
                    + "<td id='segmentRowCount" + data[i].id + "'>" + data[i].rowCount + "</td>"
                    + "<td id='segmentSeatsInRowCount" + data[i].id + "'>" + data[i].seatsInRowCount + "</td>"
                    + "<td class=\"td-actions\">"
                    + "<a href=\"#\" onclick=\"return editSegment(" + idInstitution + ", " + data[i].id + ");\" class=\"btn btn-small btn-primary\">"
                    + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return deleteSegment(" + idInstitution + ", " + data[i].id + ");\" class=\"btn btn-small btn-danger\">\n"
                    + "<i class=\"glyphicon glyphicon-remove\"></i>"
                    + "</a>"
                    + "</td>"
                    + "</tr>";

                $("#tableSegmentsTBody" + idInstitution).append(newSegment);
            }
        }
    });
    var divAddSegment = $('#divAddSegment' + idInstitution);
    divAddSegment.empty();
    var addButton =
        "<a href=\"#\" onclick=\"return addSegment(" + idInstitution + ", " + idAuditorium + ");\" class=\"btn btn-small btn-success\">"
        + "<i class=\"glyphicon glyphicon-plus\"></i>"
        + "</a>&nbsp;&nbsp;&nbsp;<h3>Segments for auditorium #" + idAuditorium + "</h3>";
    divAddSegment.append(addButton);
    return false;
}

function editSegment(idInstitution, idSegment) {
    $("#idSegmentEdit").val(idSegment);
    $("#idInstitutionSegmentEdit").val(idInstitution);
    segmentLabel = $("#segmentLabel" + idSegment).text();
    segmentRowCount = $("#segmentRowCount" + idSegment).text();
    segmentSeatsInRowCount = $("#segmentSeatsInRowCount" + idSegment).text();

    $("#labelSegmentEdit").val(segmentLabel);
    $("#rowsSegmentEdit").val(segmentRowCount);
    $("#seatsInRowSegmentEdit").val(segmentSeatsInRowCount);

    $("#editSegmentModal").modal('toggle');
    return false;
}

function deleteSegment(idInstitution, idSegment) {
    $.ajax({
        async: false,
        type: "DELETE",
        url: "segment/deleteSegment/" + idSegment,
        dataType: "json",
        success: function (data) {
            $('#segmentLabel' + data.id).closest("tr").remove();
        }
    });
    return false;
}

function addSegment(idInstitution, idAuditorium) {
    $('#addSegmentInstitutionId').val(idInstitution);
    $('#addSegmentAuditoriumId').val(idAuditorium);
    $('#addSegmentLabel').val('');
    $('#addSegmentRows').val('');
    $('#addSegmentSeatsInRow').val('');
    $("#addSegmentModal").modal('toggle');
    return false;
}

function showSegmentsForProjection(idInstitution, idProjection) {
    $.ajax({
        async: false,
        type: "GET",
        url: "segment/getByProjection/" + idProjection,
        dataType: "json",
        success: function (data) {
            $("#tableSegmentProjectionTBody" + idInstitution).empty();
            for (i = 0; i < data.length; i++) {
                newSegment = "<tr>"
                    + "<td id='segmentProjectionSegmentID" + data[i].idSegment + "'>" + data[i].idSegment + "</td>"
                    + "<td id='segmentProjectionPrice" + data[i].idSegment + "'>" + data[i].price + "</td>"
                    + "<td class=\"td-actions\">"
                    + "<a href=\"#\" onclick=\"return editSegmentProjection(" + idInstitution + ", " + idProjection + ", " + data[i].idSegment + ");\" class=\"btn btn-small btn-primary\">"
                    + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return deleteSegmentProjection(" + idInstitution + ", " + idProjection + ", " + data[i].idSegment + ");\" class=\"btn btn-small btn-danger\">\n"
                    + "<i class=\"glyphicon glyphicon-remove\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return showOnSaleTickets(" + idInstitution + ", " + idProjection + ', ' + data[i].idSegment + ");\" class=\"btn btn-small btn-default\" title=\"Show segments\">"
                    + "<i class=\"glyphicon glyphicon-arrow-right\"></i>"
                    + "</a>"
                    + "</td>"
                    + "</tr>";

                $("#tableSegmentProjectionTBody" + idInstitution).append(newSegment);
            }
        }
    });

    var divAddSegmentProjection = $('#divAddSegmentProjection' + idInstitution);
    divAddSegmentProjection.empty();
    var addButton =
        "<a href=\"#\" onclick=\"return addSegmentProjection(" + idInstitution + ", " + idProjection + ");\" class=\"btn btn-small btn-success\">"
        + "<i class=\"glyphicon glyphicon-plus\"></i>"
        + "</a>&nbsp;&nbsp;&nbsp;<h3>Segments for #" + idProjection + "</h3>";
    divAddSegmentProjection.append(addButton);
    return false;
}

function addSegmentProjection(idInstitution, idProjection) {
    $('#addSegmentProjectionInstitutionId').val(idInstitution);
    $('#addSegmentProjectionProjectionId').val(idProjection);
    $('#addSegmentProjectionPrice').val('');
    var addSegmentProjectionSegment = $("#addSegmentProjectionSegment");
    addSegmentProjectionSegment.empty();
    addSegmentProjectionSegment.append("<option value=\"\" disabled selected hidden>Select segment</option>");

    $.ajax({
        async: false,
        url: "segment/getRest/" + idProjection,
        dataType: "json",
        success: function (data) {
            for (i = 0; i < data.length; i++) {
                newOption = "<option value=\"" + data[i].id + "\">"
                    + data[i].label + "</option>"
                $("#addSegmentProjectionSegment").append(newOption);
            }
        }
    });

    $("#addSegmentProjectionModal").modal('toggle');
    return false;
}

function editSegmentProjection(idInstitution, idProjection, idSegment) {
    $('#editSegmentProjectionInstitutionId').val(idInstitution);
    $('#editSegmentProjectionProjectionId').val(idProjection);
    $('#editSegmentProjectionSegmentId').val(idSegment);

    segmentProjectionPrice = $("#segmentProjectionPrice" + idSegment).text();
    $('#priceSegmentProjectionEdit').val(segmentProjectionPrice);
    $("#editSegmentProjectionModal").modal('toggle');
    return false;
}

function deleteSegmentProjection(idInstitution, idProjection, idSegment) {
    var dataSegmentProjection = JSON.stringify({
        "idSegment": idSegment,
        "idProjection": idProjection
    });
    $.ajax({
        async: false,
        type: "DELETE",
        url: "tickets/deleteTicketsForSegment",
        contentType: "application/json",
        data: dataSegmentProjection,
        dataType: "json",
        success: function (data) {
            $('#segmentProjectionSegmentID' + data.idSegment).closest("tr").remove();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            toastr["error"](jqXHR.responseText);
        }
    });
    return false;
}

function addSegmentClick() {
    var idInstitution = $("#addSegmentInstitutionId").val();
    var idAuditorium = $("#addSegmentAuditoriumId").val();
    label = $("#addSegmentLabel").val();
    rows = $("#addSegmentRows").val();
    seats = $("#addSegmentSeatsInRow").val();

    if (isNaN(rows) || isNaN(seats)) {
        toastr["error"]("Rows and seats number must be numbers.");
    } else {

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
                    + "</a>"
                    + "</td>"
                    + "</tr>";
                $("#addSegmentModal").modal('toggle');
                $("#tableSegmentsTBody" + idInstitution).append(newSegment);
            }, error: function (jqxhr, textStatus, errorThrown) {
                toastr["error"](jqxhr.responseText);
            }
        });
    }
}

function editSegmentClick() {
    $("#friendsDiv").empty();
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
        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"](jqxhr.responseText);
        }
    });
}
