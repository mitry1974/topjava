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
    userMakeEditable();
});

function userMakeEditable() {
    $(".user-enable").change(function () {
        enableUser($(this), this.checked);
    }).each(function () {
        setColor($(this), this.checked);
    });

    $(".delete").click(function () {
        deleteRow(getClosestRowId($(this)), null);
    });


    makeEditable();
}

function setColor(element, checked) {
    var tr = getClosestRow(element);

    if(checked){
        tr.removeClass('disabled_row');
    }
    else{
        tr.addClass('disabled_row')
    }
}
function enableUser(checkbox, checked) {
    $.ajax({
        url: ajaxUrl + getClosestRowId(checkbox),
        type: "PUT",
        contentType: "application/json",
        data:JSON.stringify(checked),
        callbackdata:{param1:checkbox},
        success: function () {
            setColor(this.callbackdata.param1, checked);
            successNoty(checked?"User enabled":"User disabled");
        }
    })
}
