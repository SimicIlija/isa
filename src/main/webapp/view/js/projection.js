function addProjection(idInstitution, idShow) {
    var addProjectionAuditorium = $("#addProjectionAuditorium")
    addProjectionAuditorium.empty();
    addProjectionAuditorium.append("<option value=\"\" disabled selected hidden>Select auditorium</option>");

    $.ajax({
        async: false,
        url: "auditorium/getByInstitution/" + idInstitution,
        dataType: "json",
        success: function (data) {
            for (i = 0; i < data.length; i++) {
                newOption = "<option value=\"" + data[i].id + "\">"
                    + data[i].name + "</option>"
                $("#addProjectionAuditorium").append(newOption)
            }
        }
    });
    $('#addProjectionInstitutionId').val(idInstitution);
    $('#addProjectionShowId').val(idShow);
    $("#addProjectionModal").modal('toggle');
    return false;
}

function editProjection(idInstitution, idShow, idProjection) {
    var editProjectionAuditorium = $("#editProjectionAuditorium")
    editProjectionAuditorium.empty();
    editProjectionAuditorium.append("<option value=\"\" disabled selected hidden>Select auditorium</option>");

    $.ajax({
        async: false,
        url: "auditorium/getByInstitution/" + idInstitution,
        dataType: "json",
        success: function (data) {
            for (i = 0; i < data.length; i++) {
                newOption = "<option value=\"" + data[i].id + "\">"
                    + data[i].name + "</option>"
                $("#editProjectionAuditorium").append(newOption)
            }
        }
    });

    $("#idPrjectionEdit").val(idProjection);
    $("#idInstitutionProjectionEdit").val(idInstitution);
    $("#idShowProjectionEdit").val(idShow);
    date = $("#projectionDate" + idProjection).text() + ":00";
    idAuditorium = $("#projectionAuditoriumId" + idProjection).text();

    $("#editProjectionDate").val(date);
    $("#editProjectionAuditorium").val(idAuditorium);

    $("#editProjectionModal").modal('toggle');
    return false;
}

function deleteProjection(idInstitution, isShow, idProjection) {
    $.ajax({
        async: false,
        type: "DELETE",
        url: "projections/deleteProjection/" + idProjection,
        dataType: "json",
        success: function (data) {
            $('#projectionID' + data.id).closest("tr").remove();

            $('#tableSegmentProjectionTBody' + idInstitution).remove();
            var divAddSegmentProjection = $('#divAddSegmentProjection' + idInstitution);
            divAddSegmentProjection.empty();
            var sgm =
                "<h3>Segments</h3>";
            divAddSegmentProjection.append(sgm);

            $('#tableTicketsOnSaleTBody' + idInstitution).remove();
            var divAddOnSaleTickets = $('#divAddOnSaleTickets' + idInstitution);
            divAddOnSaleTickets.empty();
            var sgm =
                "<h3>On sale tickets</h3>";
            divAddOnSaleTickets.append(sgm);

        },
        error: function (jqXHR, textStatus, errorThrown) {
            toastr["error"](jqXHR.responseText);
        }
    });
    return false;
}

function addProjectionClick() {
    var idInstitution = $("#addProjectionInstitutionId").val();
    var idShow = $("#addProjectionShowId").val();
    date = $("#addProjectionDate").val();
    idAuditorium = $("#addProjectionAuditorium").find('option:selected').val();

    var dataProjection = JSON.stringify({
        "id_show": idShow,
        "id_auditorium": idAuditorium,
        "date": date
    });
    $.ajax({
        async: false,
        type: "POST",
        url: "projections/addProjection",
        dataType: "json",
        contentType: "application/json",
        data: dataProjection,
        success: function (data) {
            date = data.date.substring(0, 16);
            newProjection = "<tr>"
                + "<td id='projectionID" + data.id + "'>" + data.id + "</td>"
                + "<td id='projectionDate" + data.id + "'>" + date + "</td>"
                + "<td id='projectionAuditoriumId" + data.id + "'>" + data.id_auditorium + "</td>"
                + "<td class=\"td-actions\">"
                + "<a href=\"#\" onclick=\"return editProjection(" + idInstitution + ", " + idShow + "," + data.id + ");\" class=\"btn btn-small btn-primary\">"
                + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                + "</a>&nbsp;&nbsp;&nbsp;"
                + "<a href=\"#\" onclick=\"return deleteProjection(" + idInstitution + ", " + idShow + "," + data.id + ");\" class=\"btn btn-small btn-danger\">\n"
                + "<i class=\"glyphicon glyphicon-remove\"></i>"
                + "</a>&nbsp;&nbsp;&nbsp;"
                + "<a href=\"#\" onclick=\"return showSegmentsForProjection(" + idInstitution + ", " + data.id + ");\" class=\"btn btn-small btn-default\" title=\"Show segments\">"
                + "<i class=\"glyphicon glyphicon-arrow-right\"></i>"
                + "</a>"
                + "</td>"
                + "</tr>";
            $("#addProjectionModal").modal('toggle');
            $("#tableProjectionsTBody" + idInstitution).append(newProjection);
        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"](jqxhr.responseText);
        }
    });
}

function editProjectionClick() {
    var idInstitution = $("#idInstitutionProjectionEdit").val();
    var idProjection = $("#idPrjectionEdit").val();
    var idShow = $("#idShowProjectionEdit").val();
    date = $("#editProjectionDate").val();
    var idAuditorium = $("#editProjectionAuditorium").find('option:selected').val();

    var dataProjection = JSON.stringify({
        "id_show": idShow,
        "id_auditorium": idAuditorium,
        "date": date
    });
    $.ajax({
        async: false,
        type: "PUT",
        url: "projections/editProjection/" + idProjection,
        dataType: "json",
        contentType: "application/json",
        data: dataProjection,
        success: function (data) {
            date = data.date.substring(0, 16);
            $('#projectionDate' + data.id).text(date);
            $('#projectionAuditoriumId' + data.id).text(data.id_auditorium);
            $('#editProjectionModal').modal('toggle');
        }
    });
}

function addSegmentProjectionClick() {
    var idInstitution = $("#addSegmentProjectionInstitutionId").val();
    var idProjection = $("#addSegmentProjectionProjectionId").val();
    price = $("#addSegmentProjectionPrice").val();
    idSegment = $("#addSegmentProjectionSegment").find('option:selected').val();

    var dataSegmentProjection = JSON.stringify({
        "idSegment": idSegment,
        "idProjection": idProjection,
        "price": price
    });
    $.ajax({
        async: false,
        type: "POST",
        url: "tickets/addTicketsForSegment",
        dataType: "json",
        contentType: "application/json",
        data: dataSegmentProjection,
        success: function (data) {
            newSegment = "<tr>"
                + "<td id='segmentProjectionSegmentID" + data.idSegment + "'>" + data.idSegment + "</td>"
                + "<td id='segmentProjectionPrice" + data.idSegment + "'>" + data.price + "</td>"
                + "<td class=\"td-actions\">"
                + "<a href=\"#\" onclick=\"return editSegmentProjection(" + idInstitution + ", " + idProjection + ", " + data.idSegment + ");\" class=\"btn btn-small btn-primary\">"
                + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                + "</a>&nbsp;&nbsp;&nbsp;"
                + "<a href=\"#\" onclick=\"return deleteSegmentProjection(" + idInstitution + ", " + idProjection + ", " + data.idSegment + ");\" class=\"btn btn-small btn-danger\">\n"
                + "<i class=\"glyphicon glyphicon-remove\"></i>"
                + "</a>&nbsp;&nbsp;&nbsp;"
                + "<a href=\"#\" onclick=\"return showOnSaleTickets(" + idInstitution + ", " + idProjection + ', ' + data.idSegment + ");\" class=\"btn btn-small btn-default\" title=\"Show segments\">"
                + "<i class=\"glyphicon glyphicon-arrow-right\"></i>"
                + "</a>"
                + "</td>"
                + "</tr>";
            $("#addSegmentProjectionModal").modal('toggle');
            $("#tableSegmentProjectionTBody" + idInstitution).append(newSegment);
        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"](jqxhr.responseText);
        }
    });
}

function editSegmentProjectionClick() {
    var idInstitution = $("#editSegmentProjectionInstitutionId").val();
    var idProjection = $("#editSegmentProjectionProjectionId").val();
    var idSegment = $("#editSegmentProjectionSegmentId").val();
    price = $("#priceSegmentProjectionEdit").val();

    var dataSegmentProjection = JSON.stringify({
        "idSegment": idSegment,
        "idProjection": idProjection,
        "price": price
    });
    $.ajax({
        async: false,
        type: "PUT",
        url: "tickets/editTicketsForSegment",
        dataType: "json",
        contentType: "application/json",
        data: dataSegmentProjection,
        success: function (data) {
            $('#segmentProjectionSegmentID' + data.idSegment).text(data.idSegment);
            $('#segmentProjectionPrice' + data.idSegment).text(data.price);
            $('#editSegmentProjectionModal').modal('toggle');
        }
    });
}