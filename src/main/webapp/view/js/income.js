function showIncomeClick(idInstitution) {
    var incomeDiv = $('#divShowIncome' + idInstitution);
    incomeDiv.empty();

    dateFrom = $('#showIncomeDate1' + idInstitution).val();
    dateTo = $('#showIncomeDate2' + idInstitution).val();

    var dataIncome = JSON.stringify({
        "dateFrom": dateFrom,
        "dateTo": dateTo
    });
    $.ajax({
        async: false,
        type: "POST",
        url: "institution/getIncome/" + idInstitution,
        dataType: "json",
        contentType: "application/json",
        data: dataIncome,
        success: function (data) {
            incomeDiv.append("<h4>Income is: " + data + "</h4>");
        }, error: function (jqxhr, textStatus, errorThrown) {
            toastr["error"]("Wrong date format.");
        }
    });
}