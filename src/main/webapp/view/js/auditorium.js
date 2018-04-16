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