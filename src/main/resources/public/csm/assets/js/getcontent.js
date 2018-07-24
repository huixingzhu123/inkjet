function goToProducts(kind) {
    window.location.href = "Products.html?kind=" + kind;
}

function searchProducts() {
    document.getElementById("page").value = -1;
    document.getElementById("productsBody").innerHTML = "";
    getMoreProducts();
}

function getMoreProducts() {
    var kind = document.getElementById("cartridgeType").value;
    var itemId = document.getElementById("itemId").value;
    var oemCode = document.getElementById("oemCode").value;
    var suitableMachine = document.getElementById("suitableMachine").value;
    var number = Number(document.getElementById("page").value);
    var page = number + 1;

    $.ajax({
        url: "/Product/getProduct",
        type: "POST",
        data: {'kind': kind, 'itemId':itemId , 'oemCode':oemCode , 'suitableMachine':suitableMachine, 'page': page },
        dataType: "json",
        success: function (result) {
            document.getElementById("cartridgeType").value = kind;
            document.getElementById("page").value = result.number;
            var products = result.content;

            for (var i = 0; i < products.length; i++) {
                var tr = document.createElement("tr");
                var td1 = document.createElement("td");
                td1.innerText = products[i].pid;
                var td2 = document.createElement("td");
                td2.innerText = products[i].itemId;
                var td3 = document.createElement("td");
                td3.innerText = products[i].oemCode;
                var td4 = document.createElement("td");
                td4.innerText = products[i].color;
                var td5 = document.createElement("td");
                td5.innerText = products[i].suitableMachine;
                var td6 = document.createElement("td");
                td6.innerText = products[i].pageYield;


                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);

                document.getElementById("productsBody").appendChild(tr);
            }
        },
        error: function (msg) {
            $(".notice").html('Error:' + msg);
        }
    })
}

function getProducts(kind) {
    $.ajax({
        url: "/Product/getProduct",
        type: "POST",
        data: {'kind': kind},
        dataType: "json",
        success: function (result) {
            document.getElementById("cartridgeType").value = kind;
            document.getElementById("page").value = result.number;
            var products = result.content;
            document.getElementById("productsBody").innerHTML = "";

            for (var i = 0; i < products.length; i++) {
                var tr = document.createElement("tr");
                var td1 = document.createElement("td");
                td1.innerText = products[i].pid;
                var td2 = document.createElement("td");
                td2.innerText = products[i].itemId;
                var td3 = document.createElement("td");
                td3.innerText = products[i].oemCode;
                var td4 = document.createElement("td");
                td4.innerText = products[i].color;
                var td5 = document.createElement("td");
                td5.innerText = products[i].suitableMachine;
                var td6 = document.createElement("td");
                td6.innerText = products[i].pageYield;


                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);

                document.getElementById("productsBody").appendChild(tr);
            }
        },
        error: function (msg) {
            $(".notice").html('Error:' + msg);
        }
    })
}

function getMore() {
    var number = Number(document.getElementById("page").value);
    var page = number + 1;
    var kind = document.getElementById("cartridgeType").value;
    $.ajax({
        url: "/Product/getProduct",
        type: "POST",
        data: {'kind': kind, 'page': page},
        dataType: "json",
        success: function (result) {
            document.getElementById("cartridgeType").value = kind;
            document.getElementById("page").value = result.number;
            var products = result.content;

            for (var i = 0; i < products.length; i++) {
                var tr = document.createElement("tr");
                var td1 = document.createElement("td");
                td1.innerText = products[i].pid;
                var td2 = document.createElement("td");
                td2.innerText = products[i].itemId;
                var td3 = document.createElement("td");
                td3.innerText = products[i].oemCode;
                var td4 = document.createElement("td");
                td4.innerText = products[i].color;
                var td5 = document.createElement("td");
                td5.innerText = products[i].suitableMachine;
                var td6 = document.createElement("td");
                td6.innerText = products[i].pageYield;

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);

                document.getElementById("productsBody").appendChild(tr);
            }
        },
        error: function (msg) {
            $(".notice").html('Error:' + msg);
        }
    })
}

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]]=strs[i].split("=")[1];
        }
    }
    return theRequest;
}

function resetSearch() {
    $('#query-form').find('[name]').each(function () {
        $(this).val('');
    });
}