function tabMyVisitsClick() {
    $.ajax({
        async: false,
        type: "GET",
        url: "user/getMyVisits",
        dataType: "json",
        success: function (data) {
            var myVisitsDiv = $('#myVisitsDiv');
            myVisitsDiv.empty();
            for (i = 0; i < data.length; i++) {
                date = data[i].date.substring(0, 16);
                newMyVisit =
                    "<div class='container'>"
                    + "<div class='divCinemaTheatre'>"
                    + "<div class='row'>"
                    + "<div class='col-lg-8'>"
                    + "<h3>" + data[i].institutionName + "</h3>"
                    + "<h3>" + data[i].showName + "</h3>"
                    + "<p>" + date + "</p>"
                    + "</div>"
                    + "<div class='col-lg-4'><h4>Rating:</h4>"
                    + "Projection:" +
                    //"<div id=\"main\" style=\"background:url(images/1.0-star.gif) no-repeat;\">\n" +
                    "    <span id='starProj" + data[i].projectionId + "1' class=\"rating glyphicon glyphicon-star-empty\" onmouseover=\"setRating(1, " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\" onclick=\"saveRating(1, " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ");\" onmouseout=\"setRating(" + data[i].myProjectionRating + ", " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\"></span>\n" +
                    "    <span id='starProj" + data[i].projectionId + "2' class=\"rating glyphicon glyphicon-star-empty\" onmouseover=\"setRating(2, " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\" onclick=\"saveRating(2, " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ");\" onmouseout=\"setRating(" + data[i].myProjectionRating + ", " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\"></span>\n" +
                    "    <span id='starProj" + data[i].projectionId + "3' class=\"rating glyphicon glyphicon-star-empty\" onmouseover=\"setRating(3, " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\" onclick=\"saveRating(3, " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ");\" onmouseout=\"setRating(" + data[i].myProjectionRating + ", " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\"></span>\n" +
                    "    <span id='starProj" + data[i].projectionId + "4' class=\"rating glyphicon glyphicon-star-empty\" onmouseover=\"setRating(4, " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\" onclick=\"saveRating(4, " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ");\" onmouseout=\"setRating(" + data[i].myProjectionRating + ", " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\"></span>\n" +
                    "    <span id='starProj" + data[i].projectionId + "5' class=\"rating glyphicon glyphicon-star-empty\" onmouseover=\"setRating(5, " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\" onclick=\"saveRating(5, " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ");\" onmouseout=\"setRating(" + data[i].myProjectionRating + ", " + data[i].projectionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\"></span><br><br>" +
                    "Ambient:" +
                    //"<div id=\"main\" style=\"background:url(images/1.0-star.gif) no-repeat;\">\n" +
                    "    <span id='starAmb" + data[i].projectionId + "1' class=\"ratingA glyphicon glyphicon-star-empty\" onmouseover=\"setRatingA(1, " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\" onclick=\"saveRatingA(1, " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ");\" onmouseout=\"setRatingA(" + data[i].myInstitutionRating + ", " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\"></span>\n" +
                    "    <span id='starAmb" + data[i].projectionId + "2' class=\"ratingA glyphicon glyphicon-star-empty\" onmouseover=\"setRatingA(2, " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\" onclick=\"saveRatingA(2, " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ");\" onmouseout=\"setRatingA(" + data[i].myInstitutionRating + ", " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\"></span>\n" +
                    "    <span id='starAmb" + data[i].projectionId + "3' class=\"ratingA glyphicon glyphicon-star-empty\" onmouseover=\"setRatingA(3, " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\" onclick=\"saveRatingA(3, " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ");\" onmouseout=\"setRatingA(" + data[i].myInstitutionRating + ", " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\"></span>\n" +
                    "    <span id='starAmb" + data[i].projectionId + "4' class=\"ratingA glyphicon glyphicon-star-empty\" onmouseover=\"setRatingA(4, " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\" onclick=\"saveRatingA(4, " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ");\" onmouseout=\"setRatingA(" + data[i].myInstitutionRating + ", " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\"></span>\n" +
                    "    <span id='starAmb" + data[i].projectionId + "5' class=\"ratingA glyphicon glyphicon-star-empty\" onmouseover=\"setRatingA(5, " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\" onclick=\"saveRatingA(5, " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ");\" onmouseout=\"setRatingA(" + data[i].myInstitutionRating + ", " + data[i].projectionId + ", " + data[i].institutionId + ", " + data[i].idRating + ', ' + data[i].idUser + ")\"></span><br><br>" +

                    "</div>"
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "</div>";
                myVisitsDiv.append(newMyVisit);
                if (data[i].myProjectionRating != -1) {
                    for (j = 1; j <= data[i].myProjectionRating; j++) {
                        id = '#starProj'.concat(data[i].projectionId).concat(j);
                        $(id).css('background', '#FFFF00');
                    }
                }
                if (data[i].myInstitutionRating != -1) {
                    for (j = 1; j <= data[i].myInstitutionRating; j++) {
                        id = '#starAmb'.concat(data[i].projectionId).concat(j);
                        $(id).css('background', '#FFFF00');
                    }
                }
            }
        }
    });
}

function setRating(number, idProjection, idRating, idUser) {
    for (i = 1; i <= number; i++) {
        id = '#starProj'.concat(idProjection).concat(i);
        $(id).css('background', '#FFFF00');
    }
    for (i = number + 1; i <= 5; i++) {
        id = '#starProj'.concat(idProjection).concat(i);
        $(id).css('background', '0');
    }
}

function saveRating(number, idProjection, idRating, idUser) {
    jQuery('.rating').attr('onmouseout', 'setRating(\'' + number + ',' + idProjection + ', ' + idRating + ', ' + idUser + ')');
    var data = JSON.stringify({
        "id": idRating,
        "idProjection": idProjection,
        "projectionRating": number,
        "idUser": idUser
    });
    $.ajax({
        async: false,
        type: "POST",
        url: "projectionRating/addProjectionRating",
        dataType: "json",
        contentType: "application/json",
        data: data,
        success: function (data) {
            toastr["success"]("Thank you for rating!");
        }
    });
}

function setRatingA(number, idProjection, idInstitution, idRating, idUser) {
    for (i = 1; i <= number; i++) {
        id = '#starAmb'.concat(idProjection).concat(i);
        $(id).css('background', '#FFFF00');
    }
    for (i = number + 1; i <= 5; i++) {
        id = '#starAmb'.concat(idProjection).concat(i);
        $(id).css('background', '0');
    }
}

function saveRatingA(number, idProjection, idInstitution, idRating, idUser) {
    jQuery('.ratingA').attr('onmouseout', 'setRatingA(\'' + number + ',' + idProjection + ', ' + idInstitution + ', ' + idRating + ', ' + idUser + ')');
    var data = JSON.stringify({
        "id": idRating,
        "institutionRating": number,
        "idProjection": idProjection,
        "idUser": idUser
    });
    $.ajax({
        async: false,
        type: "POST",
        url: "projectionRating/addInstitutionRating",
        dataType: "json",
        contentType: "application/json",
        data: data,
        success: function (data) {
            toastr["success"]("Thank you for rating!");
        }
    });
}