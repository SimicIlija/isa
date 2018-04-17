function showInstitution(idInstitution) {
    var longitude;
    var latitude;
    $.ajax({
        async: false,
        type: "GET",
        url: "institution/getById/" + idInstitution,
        dataType: "json",
        success: function (data) {
            var tab = $('#tab' + idInstitution);
            tab.empty();
            newInstitution =
                "<div>"
                + "<a href=\"#\" onclick=\"return editInstitution(" + data.id + ");\" class=\"btn btn-small btn-primary\">"
                + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                + "</a>"
                + "<div class='row'><div class='col-lg-6'>"
                + "<h2 id='institutionName" + data.id + "'>" + data.name + "</h2>"
                + "<p id='institutionDescription" + data.id + "'>" + data.description + "</p>"
                + "<p id='instisutionAdress" + data.id + "'></p>"
                + "</div><div class='col-lg-6'><div id='map" + data.id + "' style='width: 100%;height:400px;'></div></div></div></div><br><br>\n";
            longitude = data.longitude;
            latitude = data.latitude;

            tab.append(newInstitution);
            tabela =
                "<div class=\"panel panel-default\">\n" +
                "  <div class=\"panel-heading\">\n" +
                "    <h1 class=\"panel-title\" style='height: 50px; padding: 15px; font-weight: font-weight: 600'><b>Auditorium configuration</b></h1>\n" +
                "  </div>\n" +
                "  <div class=\"panel-body\" style='background-color: #FAFAFA'>" +
                "<div class='container'>" +
                "<div class='col-lg-6'>" +
                "<div class=\"span7\">\n" +
                "                                <div class=\"widget stacked widget-table action-table\">\n" +
                "                                    <div class=\"widget-header\">\n" +
                "<a href=\"#\" onclick=\"return addAuditorium(" + idInstitution + ");\" class=\"btn btn-small btn-success\">"
                + "<i class=\"glyphicon glyphicon-plus\"></i>"
                + "</a>&nbsp;&nbsp;&nbsp;" +
                "                                        <h3>Auditoriums</h3>\n" +

                "                                    </div>\n" +
                "                                    <div class=\"widget-content\">\n" +
                "                                        <table class=\"table table-striped table-bordered\">\n" +
                "                                            <thead>\n" +
                "                                            <tr>\n" +
                "                                                <th>ID</th>\n" +
                "                                                <th>Name</th>\n" +
                "                                                <th class=\"td-actions\" style=\"width:200px\"></th>\n" +
                "                                            </tr>\n" +
                "                                            </thead>\n" +
                "                                            <tbody id=\"tableAuditoriumsTBody" + idInstitution + "\">\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>" +
                "</div>" +
                "<div class='col-lg-6'>" +
                "<div class=\"span7\">\n" +
                "                                <div class=\"widget stacked widget-table action-table\">\n" +
                "                                    <div id='divAddSegment" + idInstitution + "' class=\"widget-header\">\n" +
                "                                        <h3>Segments</h3>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"widget-content\">\n" +
                "                                        <table class=\"table table-striped table-bordered\">\n" +
                "                                            <thead>\n" +
                "                                            <tr>\n" +
                "                                                <th>Lable</th>\n" +
                "                                                <th>Rows</th>\n" +
                "                                                <th>Seats in row</th>\n" +
                "                                                <th class=\"td-actions\" style=\"width:200px\"></th>\n" +
                "                                            </tr>\n" +
                "                                            </thead>\n" +
                "                                            <tbody id=\"tableSegmentsTBody" + idInstitution + "\">\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>" +
                "</div>" +
                "</div>" +
                "</div>\n" +
                "</div><br><br><br>" +


                "<div class=\"panel panel-default\">\n" +
                "  <div class=\"panel-heading\">\n" +
                "    <h1 class=\"panel-title\" style='height: 50px; padding: 15px; font-weight: font-weight: 600'><b>Shows</b></h1>\n" +
                "  </div>\n" +
                "  <div class=\"panel-body\" style='background-color: #FAFAFA; padding-left: 60px; padding-right: 60px'>" +
                "<div class=\"span7\">\n" +
                "                                <div class=\"widget stacked widget-table action-table\">\n" +
                "                                    <div class=\"widget-header\">\n" +
                "<a href=\"#\" onclick=\"return addShow(" + idInstitution + ");\" class=\"btn btn-small btn-success\">"
                + "<i class=\"glyphicon glyphicon-plus\"></i>"
                + "</a>&nbsp;&nbsp;&nbsp;" +
                "                                        <h3>Shows</h3>\n" +

                "                                    </div>\n" +
                "                                    <div class=\"widget-content\">\n" +
                "                                        <table class=\"table table-striped table-bordered\">\n" +
                "                                            <thead>\n" +
                "                                            <tr>\n" +
                "                                                <th>ID</th>\n" +
                "                                                <th>Name</th>\n" +
                "                                                <th>Desription</th>\n" +
                "                                                <th>Genre</th>\n" +
                "                                                <th>Producer</th>\n" +
                "                                                <th>Duration</th>\n" +
                "                                                <th>Poster</th>\n" +
                "                                                <th class=\"td-actions\" style=\"width:200px\"></th>\n" +
                "                                            </tr>\n" +
                "                                            </thead>\n" +
                "                                            <tbody id=\"tableShowsTBody" + idInstitution + "\">\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div><br><br>" +
                "<div class='row'>" +
                "<div class='col-lg-4'>" +
                "<div class=\"span7\">\n" +
                "                                <div class=\"widget stacked widget-table action-table\">\n" +
                "                                    <div id='divAddActor" + idInstitution + "' class=\"widget-header\">\n" +
                "                                        <h3>Actors</h3>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"widget-content\">\n" +
                "                                        <table class=\"table table-striped table-bordered\">\n" +
                "                                            <thead>\n" +
                "                                            <tr>\n" +
                "                                                <th>First name</th>\n" +
                "                                                <th>Last name</th>\n" +
                "                                                <th class=\"td-actions\" style=\"width:130px\"></th>\n" +
                "                                            </tr>\n" +
                "                                            </thead>\n" +
                "                                            <tbody id=\"tableActorsTBody" + idInstitution + "\">" +
                "                                            </tbody>" +
                "                                        </table>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>" +
                "</div>" +
                "<div class='col-lg-5'>" +

                "<div class=\"span7\">\n" +
                "                                <div class=\"widget stacked widget-table action-table\">\n" +
                "                                    <div id='divAddProjection" + idInstitution + "' class=\"widget-header\">\n" +
                "                                        <h3>Projections</h3>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"widget-content\">\n" +
                "                                        <table class=\"table table-striped table-bordered\">\n" +
                "                                            <thead>\n" +
                "                                            <tr>\n" +
                "                                                <th>ID</th>\n" +
                "                                                <th>Date</th>\n" +
                "                                                <th>Auditorium ID</th>\n" +
                "                                                <th class=\"td-actions\" style=\"width:170px\"></th>\n" +
                "                                            </tr>\n" +
                "                                            </thead>\n" +
                "                                            <tbody id=\"tableProjectionsTBody" + idInstitution + "\">\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>" +
                "</div>" +

                "<div class='col-lg-3'>" +

                "<div class=\"span7\">\n" +
                "                                <div class=\"widget stacked widget-table action-table\">\n" +
                "                                    <div id='divAddSegmentProjection" + idInstitution + "' class=\"widget-header\">\n" +
                "                                        <h3>Segments</h3>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"widget-content\">\n" +
                "                                        <table class=\"table table-striped table-bordered\">\n" +
                "                                            <thead>\n" +
                "                                            <tr>\n" +
                "                                                <th>Segment ID</th>\n" +
                "                                                <th>Price</th>\n" +
                "                                                <th class=\"td-actions\" style=\"width:120px\"></th>\n" +
                "                                            </tr>\n" +
                "                                            </thead>\n" +
                "                                            <tbody id=\"tableSegmentProjectionTBody" + idInstitution + "\">\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>" +
                "</div>" +

                "</div>" +
                "</div>" +
                "</div>\n";
            tab.append(tabela);

            $.ajax({
                async: false,
                type: "GET",
                url: "auditorium/getByInstitution/" + idInstitution,
                dataType: "json",
                success: function (data) {
                    for (i = 0; i < data.length; i++) {
                        newAuditorium =
                            "<tr>"
                            + "<td id='auditoriumID" + data[i].id + "'>" + data[i].id + "</td>"
                            + "<td id='auditoriumName" + data[i].id + "'>" + data[i].name + "</td>"
                            + "<td class=\"td-actions\">"
                            + "<a href=\"#\" onclick=\"return editAuditorium(" + data[i].id + ");\" class=\"btn btn-small btn-primary\">"
                            + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                            + "</a>&nbsp;&nbsp;&nbsp;"
                            + "<a href=\"#\" onclick=\"return deleteAuditorium(" + idInstitution + ", " + data[i].id + ");\" class=\"btn btn-small btn-danger\">\n"
                            + "<i class=\"glyphicon glyphicon-remove\"></i>"
                            + "</a>&nbsp;&nbsp;&nbsp;"
                            + "<a href=\"#\" onclick=\"return showSegments(" + idInstitution + ", " + data[i].id + ");\" class=\"btn btn-small btn-default\" title=\"Show segments\">"
                            + "<i class=\"glyphicon glyphicon-arrow-right\"></i>"
                            + "</a>"
                            + "</td>"
                            + "</tr>";

                        $("#tableAuditoriumsTBody" + idInstitution).append(newAuditorium);
                    }
                }
            });

            $.ajax({
                async: false,
                type: "GET",
                url: "show/getAllByInstitution/" + idInstitution,
                dataType: "json",
                success: function (data) {
                    for (i = 0; i < data.length; i++) {
                        newShow = "<tr>"
                            + "<td id='showID" + data[i].id + "'>" + data[i].id + "</td>"
                            + "<td id='showName" + data[i].id + "'>" + data[i].name + "</td>"
                            + "<td id='showDescription" + data[i].id + "'>" + data[i].description + "</td>"
                            + "<td id='showGenre" + data[i].id + "'>" + data[i].genre + "</td>"
                            + "<td id='showProducer" + data[i].id + "'>" + data[i].producer + "</td>"
                            + "<td id='showDuration" + data[i].id + "'>" + data[i].duration + "</td>"
                            + "<td id='showImage" + data[i].id + "'>"
                            + "<a href=\"#\" onclick=\"return uploadImageForShow(" + data[i].id + ");\" class=\"btn btn-small btn-default\">"
                            + "<i class=\"glyphicon glyphicon-upload\"></i>"
                            + "</a>"
                            + "</td>"
                            + "<td class=\"td-actions\">"
                            + "<a href=\"#\" onclick=\"return editShow(" + data[i].id + ");\" class=\"btn btn-small btn-primary\">"
                            + "<i class=\"glyphicon glyphicon-pencil\"></i>"
                            + "</a>&nbsp;&nbsp;&nbsp;"
                            + "<a href=\"#\" onclick=\"return deleteShow(" + idInstitution + ", " + data[i].id + ");\" class=\"btn btn-small btn-danger\">\n"
                            + "<i class=\"glyphicon glyphicon-remove\"></i>"
                            + "</a>&nbsp;&nbsp;&nbsp;"
                            + "<a href=\"#\" onclick=\"return showActorsAndProjections(" + idInstitution + ", " + data[i].id + ");\" class=\"btn btn-small btn-default\" title=\"Show actors and projections\">"
                            + "<i class=\"glyphicon glyphicon-arrow-right\"></i>"
                            + "</a>"
                            + "</td>"
                            + "</tr>";

                        $("#tableShowsTBody" + idInstitution).append(newShow);
                    }
                }
            });

        }
    });

    var geocoder = new google.maps.Geocoder();             // create a geocoder object
    var location = new google.maps.LatLng(latitude, longitude);    // turn coordinates into an object
    geocoder.geocode({'latLng': location}, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {           // if geocode success
                                                                 // if address found, pass to processing function
            $('#instisutionAdress' + idInstitution).append(results[0].formatted_address);

        }
    });

    map = new google.maps.Map(document.getElementById('map' + idInstitution), {
        center: {lat: latitude, lng: longitude},
        zoom: 17
    });
    var marker = new google.maps.Marker({
        position: {lat: latitude, lng: longitude},
        map: map
    });
    return false;
}

function editInstitution(idInstitution) {
    var name = $('#institutionName' + idInstitution).text();
    var description = $('#institutionDescription' + idInstitution).text();
    var address = $('#instisutionAdress' + idInstitution).text();

    $("#editInstitutionId").val(idInstitution);
    $("#nameInstitutionEdit").val(name);
    $("#descriptionInstitutionEdit").val(description);
    $("#addressInstitutionEdit").val(address);

    $("#editInstitutionModal").modal('toggle');
    return false;
}
