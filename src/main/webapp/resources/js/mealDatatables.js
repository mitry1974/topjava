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
                "asc"
            ]
        ]
    });
    makeEditable();
});

function filter(bclear) {
    var form = $("#filterMealForm");
    if(bclear){
        form.find(":input").val("");
    }
    getFiltered(form);
    return false;
}

function getFiltered(form) {
    $.ajax({
        url: ajaxUrl + "filter/",
        type: "GET",
        dataType: "json",
        data: form.serialize()
    })
        .done(function (data) {
            datatableApi.clear().rows.add(data).draw();
            successNoty("Filtered");
        });

}

function saveMeal() {
    save(function () {
        $("#editRow").modal("hide");
        getFiltered($("#filterMealForm"));
        successNoty("Saved");
    })
}