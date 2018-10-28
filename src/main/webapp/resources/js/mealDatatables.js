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
    mealMakeEditable();
});

function mealMakeEditable() {
    $(".delete").click(function (){
        deleteRow(getClosestRowId($(this)), function () {
            getFiltered($("#filterMealForm"));
            successNoty("Deleted");

        });
    });

    makeEditable();
}

function filter() {
    var form = $("#filterMealForm");
    getFiltered(form);
    return false;
}

function clearFilter(){
    var form = $("#filterMealForm");
    form.find(":input").val("");
    updateTable();
}

function getFiltered(form) {
    $.ajax({
        url: ajaxUrl + "filter/",
        type: "GET",
        dataType: "json",
        data: form.serialize()
    })
        .done(function (data) {
            updateDataTable(data);
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

function deleteMeal(id) {
    deleteRow(id, function () {
        getFiltered($("#filterMealForm"));
        successNoty("Deleted");

    })
}
