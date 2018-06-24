function goToProduct(kind) {
    window.location.href = "Products.html?kind=" + kind;
}

function goToProducts(kind) {
    $.ajax({
        url: "http://localhost:8080/Product/getProduct",
        type: "POST",
        // contentType: "application/json;charset=utf-8",
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
                td1.innerText = products[i].systemid;
                var td2 = document.createElement("td");
                td2.innerText = products[i].itemId;
                var td3 = document.createElement("td");
                td3.innerText = products[i].series;
                var td4 = document.createElement("td");
                td4.innerText = products[i].oemCode;
                var td5 = document.createElement("td");
                td5.innerText = products[i].color;
                var td6 = document.createElement("td");
                td6.innerText = products[i].suitableMachine;
                var td7 = document.createElement("td");
                td7.innerText = products[i].pageYield;
                var td8 = document.createElement("td");
                td8.innerText = products[i].itemType;

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                tr.appendChild(td7);
                tr.appendChild(td8);

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
        url: "http://localhost:8080/Product/getProduct",
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
                td1.innerText = products[i].systemid;
                var td2 = document.createElement("td");
                td2.innerText = products[i].itemId;
                var td3 = document.createElement("td");
                td3.innerText = products[i].series;
                var td4 = document.createElement("td");
                td4.innerText = products[i].oemCode;
                var td5 = document.createElement("td");
                td5.innerText = products[i].color;
                var td6 = document.createElement("td");
                td6.innerText = products[i].suitableMachine;
                var td7 = document.createElement("td");
                td7.innerText = products[i].pageYield;
                var td8 = document.createElement("td");
                td8.innerText = products[i].itemType;

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                tr.appendChild(td7);
                tr.appendChild(td8);

                document.getElementById("productsBody").appendChild(tr);
            }
        },
        error: function (msg) {
            $(".notice").html('Error:' + msg);
        }
    })
}