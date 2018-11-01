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
            var form = $("#filterMealForm");
            return form.find("#endDate").val() != ''
                || form.find("#startDate").val() != ''
                || form.find("#startTime").val()!=''
                || form.find("#endTime").val()!='';
        }
    },
};

