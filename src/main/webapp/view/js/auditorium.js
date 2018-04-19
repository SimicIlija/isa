function editAuditorium(idAuditorium) {
    $("#idAuditoriumEdit").val(idAuditorium);
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
            $('#tableSegmentsTBody' + idInstitution).remove();
            var divAddSegment = $('#divAddSegment' + idInstitution);
            divAddSegment.empty();
            var sgm =
                "<h3>Segments</h3>";
            divAddSegment.append(sgm);
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

function editAuditoriumClick() {
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
        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"](jqxhr.responseText);
        }
    });
}

function addAuditoriumClick() {
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
        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"](jqxhr.responseText);
        }
    });
}