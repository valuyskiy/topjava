var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();
});

function getFilter() {
    var out = "";

    if ($(":input[name=startDate]", "#filterForm").val()) {
        out = out + "&startDate=" + $(":input[name=startDate]", "#filterForm").val();
    }

    if ($(":input[name=endDate]", "#filterForm").val()) {
        out = out + "&endDate=" + $(":input[name=endDate]", "#filterForm").val();
    }

    if ($(":input[name=startTime]", "#filterForm").val()) {
        out = out + "&startTime=" + $(":input[name=startTime]", "#filterForm").val();
    }

    if ($(":input[name=endTime]", "#filterForm").val()) {
        out = out + "&endTime=" + $(":input[name=endTime]", "#filterForm").val();
    }

    if (out) {
        out = "filter?" + out;
    }

    return out;
}
