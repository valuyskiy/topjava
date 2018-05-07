var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "createdRow":
            function (row, data, dataIndex) {
                $(row).attr('data-mealExceed', data.exceed);
            },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type === "display") {
                        return date.substring(0, 10) + " " + date.substring(11, 16);
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderEditBtn
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });

    $('#startDate, #endDate').datetimepicker({
            timepicker: false,
            format: 'Y-m-d'
        }
    );

    $('#startTime, #endTime').datetimepicker({
            datepicker: false,
            format: 'H:i'
        }
    );

    $('#dateTime').datetimepicker({
        format: 'Y-m-d\\TH:i'
    });

    makeEditable();
});