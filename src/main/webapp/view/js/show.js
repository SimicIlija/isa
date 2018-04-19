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