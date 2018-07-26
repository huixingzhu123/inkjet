$(".form_datetime").datetimepicker({
    format: "yyyy-mm-dd"
});
$('.form_date').datetimepicker({
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 2,
    forceParse: 0
});

function resetSearch() {
    $('#query-form').find('input').each(function () {
        $(this).val('');
    });
}

function searchMsg() {
    var name = $("#name").val();
    var email = $("#email").val()
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();

    var jumpUrl = "contact-table?name=" + name + "&email=" + email + "&startTime=" + startTime + "&endTime=" + endTime;
    window.location.href = jumpUrl;
}

function deleteByIds(ids) {
    var res = false;
    $.ajax({
        url: "/contact/contact/" + ids,
        type: "DELETE",
        data: '',
        dataType: "json",
        async: false,
        // contentType:"application/json",
        success: function (result) {
            res = true;
        },
        error: function (msg) {
            res = false;
        }
    });
    return res;
}

function deleteSingle(obj) {
    if (confirm("是否删除该记录？")) {
        var id = $(obj).parent().parent().find("[name|='systemid']").val();
        var result = deleteByIds(id);
        if (result) {
            $(obj).parent().parent().remove();
        } else {
            alert("删除时发生错误！");
        }
    }
}

function batchDelete() {
    var ids = [];
    var num = 0;
    $("#tbody input[type=checkbox]:checked").each(function () {
        if ($(this).val().trim() != "") {
            ids.push($(this).val());
        }
        num++;
    });
    if (num == 0) {
        alert("请选择需要删除的记录。");
        return;
    }
    if (confirm("是否删除选中的记录？")) {
        var result = true;
        if (ids.length > 0) {
            result = deleteByIds(ids);
        }
        if (result) {
            $("#tbody input[type=checkbox]:checked").each(function () {
                $(this).parent().parent().remove();
            });
        }
    }
}

function checkAll(obj) {
    if ($(obj).is(":checked")) {
        $("#tbody input[type=checkbox]").each(function () {
            $(this).prop("checked", true);
        });
    } else {
        $("#tbody input[type=checkbox]").each(function () {
            $(this).prop("checked", false);
        });
    }
}