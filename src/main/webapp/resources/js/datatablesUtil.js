function makeEditable(updateCallback) {
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    $(".delete").click(function () {
        deleteRow(getClosestRowId($(this)), updateCallback);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function getClosestRow(element) {
    return element.closest("tr");
}

function getClosestRowId(element) {
    return getClosestRow(element).attr("id")
}

function add() {
    $("#detailsForm").find(":input").val("");
    $("#editRow").modal();
}

function deleteRow(id, updateDataCallback) {
    $.ajax({
        url: ajaxUrl + id,
        type: "DELETE",
        success: function () {
            updateDataCallback();
            successNoty("Deleted");
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        updateDataTable(data)
    });
}

function updateDataTable(data) {
    datatableApi.clear().rows.add(data).draw();
}

function save(updateDataCallback) {
    var form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $("#editRow").modal("hide");
            updateDataCallback();
            successNoty("Saved");
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
        type: "error",
        layout: "bottomRight"
    }).show();
}