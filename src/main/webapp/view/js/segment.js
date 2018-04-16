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
                    + "</a>&nbsp;&nbsp;&nbsp;"
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
        + "</a>&nbsp;&nbsp;&nbsp;<h3>Segments for auditorium with ID " + idAuditorium + "</h3>";
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
