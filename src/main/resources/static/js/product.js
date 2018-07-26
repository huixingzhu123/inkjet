function searchProducts() {
    var cartridgeType = $('#cartridgeType').val();
    var pid = $('#pid').val();
    var itemId = $('#itemId').val();
    var oemCode = $('#oemCode').val();
    var suitableMachine = $('#suitableMachine').val();

    var jumpUrl = "findProductQuery" + "?cartridgeType=" + cartridgeType + "&pid="
        + pid + "&itemId=" + itemId + "&oemCode=" + oemCode + "&suitableMachine=" + suitableMachine;
    window.location.href = jumpUrl;

}

function resetSearch() {
    $('#query-form').find('[name]').each(function () {
        $(this).val('');
    });
}

function modifySingle(obj) {
    $(obj).parent().parent().find("span").each(function () {
        $(this).hide();
        $(this).after($("<input type='text' name='"+ $(this).attr("name") +"' value='" + $(this).text() + "' />"));
    })

    $(obj).html("<span class=\"am-icon-pencil-square-o\"></span> 保存");
    $(obj).attr("onclick", "saveSingle(this)");
    $(obj).next().html("<span class=\"am-icon-trash-o\"></span> 取消");
    $(obj).next().attr("onclick", "cancelSingle(this)");
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

function deleteByIds(ids) {
    var res = false;
    $.ajax({
        url: "/Product/products/" + ids,
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

function addRow() {
    var addedHtml = "<tr>\n" +
        "               <td><input type=\"checkbox\" name='systemid' value=''/></td>\n" +
        "               <td><input type='text' name='pid' /></td>\n" +
        "               <td><input type='text' name='itemId' /></td>\n" +
        "               <td><input type='text' name='oemCode' /></td>\n" +
        "               <td><input type='text' name='color' /></td>\n" +
        "               <td><input type='text' name='suitableMachine' /></td>\n" +
        "               <td><input type='text' name='pageYield' /></td>\n" +
        "               <td>\n" +
        "                   <button type=\"button\" class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" onclick=\"saveSingle(this)\">\n" +
        "                       <span class=\"am-icon-pencil-square-o\"></span> 保存\n" +
        "                   </button>\n" +
        "                   <button type=\"button\" class=\"am-btn am-btn-default am-btn-xs am-text-danger\" onclick=\"cancelSingle(this)\">\n" +
        "                       <span class=\"am-icon-trash-o\"></span> 取消\n" +
        "                   </button>\n" +
        "               </td>\n" +
        "            </tr>";
    var ele = $(addedHtml).get(0);
    document.getElementById("tbody").appendChild(ele);
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

function saveSingle(obj) {
    if ($("#cartridgeType").val() == "" || $("#cartridgeType").val() == "undefined") {
        alert("请先选择产品类型，再进行保存。");
        return;
    }

    var param = {};
    var systemId = "";
    $(obj).parent().parent().find("input").each(function () {
        param[$(this).attr("name")] = $(this).val();
        if($(this).attr("name") == "systemid") {
            systemId = $(this).val();
        }
    });
    param["cartridgeType"] = $("#cartridgeType").val();

    var reqUrl = "";
    if (systemId == "") {
        method = "POST";
        reqUrl = "/Product/product";
    } else {
        method = "PUT";
        reqUrl = "/Product/product/" + systemId;
    }

    $.ajax({
        url: reqUrl,
        type: method,
        data: JSON.stringify(param),
        dataType: "json",
        contentType:"application/json",
        success: function (result) {
            $(obj).parent().parent().find("input").each(function () {
                if($(this).attr("name") == "systemid") {
                     $(this).val(result.systemid);
                } else {
                    $(this).parent().html("<span name='"+$(this).attr("name")+"'>" + result[$(this).attr("name")] + "</span>");
                }
            });

            $(obj).html("<span class=\"am-icon-pencil-square-o\"></span> 编辑");
            $(obj).attr("onclick", "modifySingle(this)");
            $(obj).next().html("<span class=\"am-icon-trash-o\"></span> 删除");
            $(obj).next().attr("onclick", "deleteSingle(this)");

        },
        error: function (msg) {
            alert("保存时发生错误！");
        }
    })
}

function cancelSingle(obj) {
    if ($(obj).parent().parent().find("[name|='systemid']").val() == "") {
        $(obj).parent().parent().remove();
    } else {
        $(obj).parent().parent().find("span").each(function () {
            $(this).show();
            $(this).next().remove();
        });
        $(obj).prev().html("<span class=\"am-icon-pencil-square-o\"></span> 编辑");
        $(obj).prev().attr("onclick", "modifySingle(this)");
        $(obj).html("<span class=\"am-icon-trash-o\"></span> 删除");
        $(obj).attr("onclick", "deleteSingle(this)");
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