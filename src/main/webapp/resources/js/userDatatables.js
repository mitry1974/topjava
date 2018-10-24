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

    makeEditable();
}

function setColor(element, checked) {
    var tr = getClosestRow(element);
    var currentColor=tr.css("color");
    var disabledColor = "#C2C8C1";
    var oldColor = tr.attr("oldcolor");

    if(checked && typeof oldColor !== "undefined" ){
        tr.css("color", oldColor);
    }
    if(!checked){
        tr.css("color", disabledColor);
    }

    tr.attr("oldcolor", currentColor);
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
