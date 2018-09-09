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

function searchNews() {
    var title = $("#title").val();
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();

    var jumpUrl = "news-table?title=" + title + "&startTime=" + startTime + "&endTime=" + endTime;
    window.location.href = jumpUrl;
}

function deleteByIds(ids) {
    var res = false;
    $.ajax({
        url:  getContextPath() + "/news/news/" + ids,
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

function addSingle() {
    window.open("news-add");
}

function modifySingle(obj) {
    var systemid = $(obj).parent().parent().find("[name|='systemid']").val();
    window.open("news-add?systemid=" + systemid);
}

function save() {
    var systemid = $("#systemid").val();

    var param = {};
    param["title"] = $("#title").val();
    param["author"] = $("#author").val();
    param["summary"] = $("#summary").val();
    param["content"] = UE.getEditor('editor').getContent();

    var reqUrl = "";
    if (systemid == "") {
        method = "POST";
        reqUrl = getContextPath() + "/news/News";
    } else {
        method = "PUT";
        reqUrl =  getContextPath() + "/news/news/" + systemid;
    }

    $.ajax({
        url: reqUrl,
        type: method,
        data: JSON.stringify(param),
        dataType: "json",
        contentType:"application/json",
        success: function (result) {
            $("#systemid").val(result.systemid);
            alert("保存成功！");
        },
        error: function (msg) {
            alert("保存时发生错误！");
        }
    })
}

function closeWindow() {
    window.close();
}

/**
 *js获取上下文
 * */
function getContextPath() {
    var contextPath = document.location.pathname;
    var index = contextPath.substr(1).indexOf("/");
    contextPath = contextPath.substr(0, index + 1);
    delete index;
    return contextPath;
}