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
    USER.userMakeEditable();
});

var USER = {
    userMakeEditable: function () {
        $(".user-enable").change(function () {
            USER.enableUser($(this), this.checked);
        });

        makeEditable(updateTable);
    },

    enableUser: function (checkbox, checked) {
        $.ajax({
            url: ajaxUrl + getClosestRowId(checkbox),
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(checked),
            callbackdata: {param1: checkbox},
            success: function () {
                getClosestRow(checkbox).attr("user-enabled", checked);
                successNoty(checked ? "User enabled" : "User disabled");
            }
        })
    }

}
