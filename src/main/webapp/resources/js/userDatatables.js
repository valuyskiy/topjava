var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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
                "asc"
            ]
        ]
    });
    makeEditable();

    $(':checkbox').change(function () {
        var id = $(this).parents("tr").attr("id");
        var c = this.checked;
        enable(id, c);
        $(this).parents("tr").attr("data-userEnabled" , c);
    });

});

function enable(id, isEnable) {
    $.ajax({
        url: ajaxUrl + id + "/" + isEnable,
        type: "POST",
        async: false,
        success:successNoty(isEnable ? "User enabled" : "Used disabled")
    });
}