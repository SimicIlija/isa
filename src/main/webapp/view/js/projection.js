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
        },
        error: function (jqXHR, textStatus, errorThrown) {
            toastr["error"](jqXHR.responseText);
        }
    });
    return false;
}