function showActorsAndProjections(idInstitution, idShow) {
    //actors
    $.ajax({
        async: false,
        type: "GET",
        url: "actors/getByShow/" + idShow,
        dataType: "json",
        success: function (data) {
            $('#tableActorsTBody' + idInstitution).empty();
            for (i = 0; i < data.length; i++) {
                newActor = "<tr>"
                    + "<td id='actorName" + data[i].id + "'>" + data[i].name + "</td>"
                    + "<td id='actorLastname" + data[i].id + "'>" + data[i].lastname + "</td>"
                    + "<td class=\"td-actions\">"
                    + "<a href=\"#\" onclick=\"return editActor(" + idInstitution + ", " + data[i].id + ");\" class=\"btn btn-small btn-primary\">"
                    + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return deleteActor(" + idInstitution + ", " + data[i].id + ");\" class=\"btn btn-small btn-danger\">\n"
                    + "<i class=\"glyphicon glyphicon-remove\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "</td>"
                    + "</tr>";

                $("#tableActorsTBody" + idInstitution).append(newActor);
            }
        }
    });

    $.ajax({
        async: false,
        type: "GET",
        url: "projections/getByShow/" + idShow,
        dataType: "json",
        success: function (data) {
            $('#tableProjectionsTBody' + idInstitution).empty();
            for (i = 0; i < data.length; i++) {
                date = data[i].date.substring(0, 16);
                newProjection = "<tr>"
                    + "<td id='projectionID" + data[i].id + "'>" + data[i].id + "</td>"
                    + "<td id='projectionDate" + data[i].id + "'>" + date + "</td>"
                    + "<td id='projectionAuditoriumId" + data[i].id + "'>" + data[i].id_auditorium + "</td>"
                    + "<td class=\"td-actions\">"
                    + "<a href=\"#\" onclick=\"return editProjection(" + idInstitution + ", " + idShow + ", " + data[i].id + ");\" class=\"btn btn-small btn-primary\">"
                    + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return deleteProjection(" + idInstitution + ", " + idShow + ", " + data[i].id + ");\" class=\"btn btn-small btn-danger\">\n"
                    + "<i class=\"glyphicon glyphicon-remove\"></i>"
                    + "</a>&nbsp;&nbsp;&nbsp;"
                    + "<a href=\"#\" onclick=\"return showSegmentsForProjection(" + idInstitution + ", " + data[i].id + ");\" class=\"btn btn-small btn-default\" title=\"Show segments\">"
                    + "<i class=\"glyphicon glyphicon-arrow-right\"></i>"
                    + "</a>"
                    + "</td>"
                    + "</tr>";

                $("#tableProjectionsTBody" + idInstitution).append(newProjection);
            }
        }
    });

    var divAddActor = $('#divAddActor' + idInstitution);
    divAddActor.empty();
    var addButton1 =
        "<a href=\"#\" onclick=\"return addActor(" + idInstitution + ", " + idShow + ");\" class=\"btn btn-small btn-success\">"
        + "<i class=\"glyphicon glyphicon-plus\"></i>"
        + "</a>&nbsp;&nbsp;&nbsp;<h3>Actors in show #" + idShow + "</h3>";
    divAddActor.append(addButton1);

    var divAddProjection = $('#divAddProjection' + idInstitution);
    divAddProjection.empty();
    var addButton2 =
        "<a href=\"#\" onclick=\"return addProjection(" + idInstitution + ", " + idShow + ");\" class=\"btn btn-small btn-success\">"
        + "<i class=\"glyphicon glyphicon-plus\"></i>"
        + "</a>&nbsp;&nbsp;&nbsp;<h3>Projections for show #" + idShow + "</h3>";
    divAddProjection.append(addButton2);

    var divAddSegmentProjection = $('#divAddSegmentProjection' + idInstitution);
    divAddSegmentProjection.empty();
    var addButton3 = "<h3>Segments</h3>";
    divAddSegmentProjection.append(addButton3);
    $('#tableSegmentProjectionTBody' + idInstitution).empty();

    return false;
}

function addActor(idInstitution, idShow) {
    $('#addActorInstitutionId').val(idInstitution);
    $('#addActorShowId').val(idShow);
    $('#addActorName').val('');
    $('#addActorLastname').val('');
    $("#addActorModal").modal('toggle');
    return false;
}

function editActor(idInstitution, idActor) {
    $("#idActorEdit").val(idActor);
    $("#idInstitutionActorEdit").val(idInstitution);
    actorName = $("#actorName" + idActor).text();
    actorLastname = $("#actorLastname" + idActor).text();

    $("#nameActorEdit").val(actorName);
    $("#lastnameActorEdit").val(actorLastname);

    $("#editActorModal").modal('toggle');
    return false;
}

function deleteActor(idInstitution, idActor) {
    $.ajax({
        async: false,
        type: "DELETE",
        url: "actors/deleteActor/" + idActor,
        dataType: "json",
        success: function (data) {
            $('#actorName' + data.id).closest("tr").remove();
        }
    });
    return false;
}