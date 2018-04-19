var graph;
var xPadding = 30;
var yPadding = 30;

/*var data = { values:[
        { X: "1", Y: 15 },
        { X: "2", Y: 35 },
        { X: "3", Y: 60 },
        { X: "4", Y: 14 },
        { X: "5", Y: 20 },
        { X: "6", Y: -30 },
    ]};*/

// Returns the max Y value in our data list
function getMaxY(data) {
    var max = 0;

    for (var i = 0; i < data.values.length; i++) {
        if (data.values[i].Y > max) {
            max = data.values[i].y;
        }
    }

    max += 10 - max % 10;
    return max;
}

// Return the x pixel for a graph point
function getXPixel(val, data) {
    return ((graph.width() - xPadding) / data.values.length) * val + (xPadding * 1.5);
}

// Return the y pixel for a graph point
function getYPixel(val, data) {
    return graph.height() - (((graph.height() - yPadding) / getMaxY(data)) * val) - yPadding;
}

function prikazi(idInstitution) {
    $.ajax({
        async: false,
        type: "GET",
        url: "institution/getCharts/" + idInstitution,
        dataType: "json",
        success: function (data) {
            dayData = data[0];
            weekData = data[1];
            monthData = data[2];
            nacrtaj(dayData, 'Day', idInstitution);
            nacrtaj(weekData, 'Week', idInstitution);
            nacrtaj(monthData, 'Month', idInstitution);
        }
    });

}

function nacrtaj(data, id, idInstitution) {
    var graphId = '#graph' + id + idInstitution;
    graph = $(graphId);
    var c = graph[0].getContext('2d');

    c.lineWidth = 2;
    c.strokeStyle = '#333';
    c.font = 'italic 8pt sans-serif';
    c.textAlign = "center";

// Draw the axises
    c.beginPath();
    c.moveTo(xPadding, 0);
    c.lineTo(xPadding, graph.height() - yPadding);
    c.lineTo(graph.width(), graph.height() - yPadding);
    c.stroke();

// Draw the X value texts
    for (var i = 0; i < data.values.length; i++) {
        c.fillText(data.values[i].x, getXPixel(i, data), graph.height() - yPadding + 20);
    }

// Draw the Y value texts
    c.textAlign = "right"
    c.textBaseline = "middle";

    for (var i = 0; i < getMaxY(data); i += 1) {
        c.fillText(i, xPadding - 10, getYPixel(i, data));
    }

    c.strokeStyle = '#062bff';

// Draw the line graph
    c.beginPath();
    c.moveTo(getXPixel(0, data), getYPixel(data.values[0].y, data));
    for (var i = 1; i < data.values.length; i++) {
        c.lineTo(getXPixel(i, data), getYPixel(data.values[i].y, data));
    }
    c.stroke();

// Draw the dots
    c.fillStyle = '#333';

    for (var i = 0; i < data.values.length; i++) {
        c.beginPath();
        c.arc(getXPixel(i, data), getYPixel(data.values[i].y, data), 4, 0, Math.PI * 2, true);
        c.fill();
    }
}