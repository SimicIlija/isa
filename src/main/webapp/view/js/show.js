function addShow(idInstitution) {
    $('#addShowInstitutionId').val(idInstitution);
    $('#addShowDescription').val('');
    $('#addShowName').val('');
    $('#addShowGenre').val('');
    $('#addShowProducer').val('');
    $('#addShowDuration').val('');
    $('#addShowImageURL').val('');
    $("#addShowModal").modal('toggle');
    return false;
}

function editShow(idShow) {
    $("#idShowEdit").val(idShow);

    showName = $("#showName" + idShow).text();
    showDescription = $("#showDescription" + idShow).text();
    showGenre = $("#showGenre" + idShow).text();
    showProducer = $("#showProducer" + idShow).text();
    showDuration = $("#showDuration" + idShow).text();
    showImageURL = $("#showImageURL" + idShow).text();

    $("#editShowName").val(showName);
    $("#editShowDescription").val(showDescription);
    $("#editShowGenre").val(showGenre);
    $("#editShowProducer").val(showProducer);
    $("#editShowDuration").val(showDuration);
    $("#editShowImageURL").val(showImageURL);

    $("#editShowModal").modal('toggle');
    return false;
}

function deleteShow(idInstitution, isShow) {
    $.ajax({
        async: false,
        type: "DELETE",
        url: "show/deleteShow/" + isShow,
        dataType: "json",
        success: function (data) {
            $('#showName' + data.id).closest("tr").remove();

            $('#tableActorsTBody' + idInstitution).remove();
            var divAddActor = $('#divAddActor' + idInstitution);
            divAddActor.empty();
            var act = "<h3>Actors</h3>";
            divAddActor.append(act);

            $('#tableProjectionsTBody' + idInstitution).remove();
            var divAddProjection = $('#divAddProjection' + idInstitution);
            divAddProjection.empty();
            var proj = "<h3>Projection</h3>";
            divAddProjection.append(proj);
        }
    });
    return false;
}

function uploadImageForShow(idShow) {
    $('#uploadShowImageIdShow').val(idShow);
    $('#uploadShowImageModal').modal('toggle');
    return false;
}

function showImage(idShow) {
    $.ajax({
        async: false,
        type: "GET",
        url: "show/getImageURL/" + idShow,
        dataType: "text",
        success: function (data) {
            if (!data) {
                toastr["info"]("No poster.");
            } else {
                var win = window.open(data, '_blank');
                if (win) {
                    //Browser has allowed it to be opened
                    win.focus();
                } else {
                    alert('Please allow popups for this website');
                }
            }
        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["info"]("No poster.");
        }
    });



    return false;
}

function addShowClick() {
    $("#friendsDiv").empty();
    var idInstitution = $("#addShowInstitutionId").val();

    var name = $("#addShowName").val();
    description = $("#addShowDescription").val();
    genre = $("#addShowGenre").val();
    producer = $("#addShowProducer").val();
    duration = $("#addShowDuration").val();
    posterFileName = $("#addShowImageURL").val();
    var dataShow = JSON.stringify({
        "name": name,
        "genre": genre,
        "producer": producer,
        "duration": duration,
        "idInstitution": idInstitution,
        "description": description,
        "posterFileName": posterFileName
    });
    $.ajax({
        async: false,
        type: "POST",
        url: "show/addShow/",
        dataType: "json",
        contentType: "application/json",
        data: dataShow,
        success: function (data) {
            var rating = data.rating;
            if (isNaN(rating)) {
                rating = 0;
            }
            rating = Math.round(rating * 100) / 100;
            newShow = "<tr>"
                + "<td id='showID" + data.id + "'>" + data.id + "</td>"
                + "<td id='showName" + data.id + "'>" + data.name + "</td>"
                + "<td id='showDescription" + data.id + "'>" + data.description + "</td>"
                + "<td id='showGenre" + data.id + "'>" + data.genre + "</td>"
                + "<td id='showProducer" + data.id + "'>" + data.producer + "</td>"
                + "<td id='showDuration" + data.id + "'>" + data.duration + "</td>"
                + "<td id='showImage" + data.id + "'>"
                + "<a href=\"#\" onclick=\"return showImage(" + data.id + ");\" class=\"btn btn-small btn-default\">"
                + "<i class=\"glyphicon glyphicon-eye-open\"></i>"
                + "</a>"
                + "<input type='hidden' id='showImageURL" + data.id + "'>"
                + "</td>"
                + "<td id='showRating" + data.id + "'>" + rating + "</td>"
                + "<td class=\"td-actions\">"
                + "<a href=\"#\" onclick=\"return editShow(" + data.id + ");\" class=\"btn btn-small btn-primary\">"
                + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                + "</a>&nbsp;&nbsp;&nbsp;"
                + "<a href=\"#\" onclick=\"return deleteShow(" + idInstitution + ", " + data.id + ");\" class=\"btn btn-small btn-danger\">\n"
                + "<i class=\"glyphicon glyphicon-remove\"></i>"
                + "</a>&nbsp;&nbsp;&nbsp;"
                + "<a href=\"#\" onclick=\"return showActorsAndProjections(" + idInstitution + ", " + data.id + ");\" class=\"btn btn-small btn-default\" title=\"Show actors and projections\">"
                + "<i class=\"glyphicon glyphicon-arrow-right\"></i>"
                + "</a>"
                + "</td>"
                + "</tr>";
            $("#addShowModal").modal('toggle');
            $("#tableShowsTBody" + idInstitution).append(newShow);
        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"]("Fields: name, genre and producer are mandatory. Duration must be number.");
        }
    });
}

function editShowClick() {
    var idShow = $("#idShowEdit").val();
    var name = $("#editShowName").val();
    description = $("#editShowDescription").val();
    genre = $("#editShowGenre").val();
    producer = $("#editShowProducer").val();
    duration = $("#editShowDuration").val();
    posterFileName = $("#editShowImageURL").val();
    var dataShow = JSON.stringify({
        "name": name,
        "genre": genre,
        "producer": producer,
        "duration": duration,
        "description": description,
        "posterFileName": posterFileName
    });
    $.ajax({
        async: false,
        type: "PUT",
        url: "show/editShow/" + idShow,
        dataType: "json",
        contentType: "application/json",
        data: dataShow,
        success: function (data) {
            $('#showName' + data.id).text(data.name);
            $('#showDescription' + data.id).text(data.description);
            $('#showGenre' + data.id).text(data.genre);
            $('#showProducer' + data.id).text(data.producer);
            $('#showDuration' + data.id).text(data.duration);
            $("#clickImage" + data.id).attr("onclick", "return showImage(" + data.posterFileName + ")");
            $('#editShowModal').modal('toggle');
        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"]("Fields: name, genre and producer are mandatory. Duration must be number.");
        }
    });
}