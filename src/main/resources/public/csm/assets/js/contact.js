function sendMsg(){
    var formObject = {};
    var formArray = $('#contactForm').serializeArray();
    $.each(formArray,function(i,item){
        formObject[item.name] = item.value;
    });
    $.ajax({
        //几个参数需要注意一下
        type: "POST",//方法类型
        dataType: "JSON",//预期服务器返回的数据类型
        url: "/contact/contacts" ,//url
        data: JSON.stringify(formObject),
        contentType : "application/json; charset=utf-8",
        success: function (result) {
            console.log(result);//打印服务端返回的数据(调试用)
                alert("SUCCESS");
        },
        error : function() {
            alert("异常！");
        }
    });
}
