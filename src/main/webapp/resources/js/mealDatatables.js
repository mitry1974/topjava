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
    makeEditable(MEAL.getData);
});
var MEAL = {
    clearFilter: function () {
        var form = $("#filterMealForm");
        form.find(":input").val("");
        form.attr("filtered", false);
        updateTable();
    },

    getFiltered: function () {
        var form = $("#filterMealForm");
        $.ajax({
            url: ajaxUrl + "filter/",
            type: "GET",
            dataType: "json",
            data: form.serialize(),
            success: function (data) {
                form.attr("filtered", true);
                updateDataTable(data);
                successNoty("Filtered");
            }
        })
    },
    getData: function () {
        if (isFiltered()) {
            MEAL.getFiltered();
        }
        else {
            updateTable();
        }

        function isFiltered() {
            return $("#filterMealForm").attr("filtered");
        }
    },
};

