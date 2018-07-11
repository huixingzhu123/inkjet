function formValidate(name, email, message) {
    if (name == null || name =="") {
        return "Please input your NAME !"
    }
    if (email == null || email == "") {
        return "Please input your EMAIL !"
    } else if (!validateEmail(email)){
        return "Please enter the EMAIL correctly!"
    }
    if (message == null || message == "" || message.length < 5) {
        return "Please input the MESSAGE, and more than 5 words!"
    }
    return "";
}

function validateEmail(email) {
    var reg = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
    return reg.test(email);
}

function sendMsg() {
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var message = document.getElementById("message").value;
    var msg = "";
    msg = formValidate(name, email, message);
    if (msg == "" ) {
        $.ajax({
            url: "/contact/contacts",
            type: "POST",
            contentType:'application/json;charset=UTF-8',
            data: JSON.stringify({'name': name, 'email':email , 'message':message}),
            dataType: "json",
            success: function (result) {
                alert("SEND SUCCESSFULLY!");
            },
            error: function (msg) {
                alert("SEND WITH SOME UNEXCEPTED PROBLEMS!");
            }
        })

    } else {

        alert(msg);
    }

}