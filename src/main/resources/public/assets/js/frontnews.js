var allNews={};
var newsPageNumber=0;

function initNews() {
    showNewsList();
}

function showNewsList() {
    $("#newsList").text("");
    var url = "/inkjet/news/news";
    var method = "GET";
    $.ajax({
        url: url,
        type: method,
        // data: JSON.stringify(param),
        dataType: "json",
        contentType:"application/json",
        success: function (result) {
            // $("#systemid").val(result.systemid);
            var news = result.content;
            //<li>
            // <a href="#">{#替换第二屏1.1}Responsive Design</a>
            // <br>
            // <span class="small text-muted">Lorem ipsum dolor r adipisicing elit. Animi, laborum.</span>
            // </li>

            for (var i = 0; i < news.length; i++) {
                allNews[news[i].systemid] = news[i].content;
                var liTag = $('<li></li>');
                var aTag = $('<a></a>');
                aTag.attr('href', 'javascript:void(0);');
                aTag.text(news[i].title);
                aTag.attr("value", news[i].systemid);
                aTag.attr("onclick", "javascript:replaceDetailNews(this)");
                aTag.appendTo(liTag);

                var brTag = $('<br>');
                brTag.appendTo(liTag);

                var spanTag = $('<span></span>');
                spanTag.addClass("small text-muted");
                spanTag.text(news[i].summary);
                spanTag.appendTo(liTag);
                liTag.appendTo($("#newsList"));
            }
            var pTag = $(news[0].content);
            pTag.appendTo($("#newsDetail"));
        },
        error: function (msg) {
            alert("PAGE ERROR!");
        }
    })
}

function replaceDetailNews(obj){
    $("#newsDetail").text("");
    var key = $(obj).attr("value");
    var pTag = $(allNews[$(obj).attr("value")]);
    pTag.appendTo($("#newsDetail"));
}

function getMoreNews() {
    newsPageNumber = newsPageNumber + 1;
    var url = "/inkjet/news/news";
    var method = "POST";
    $.ajax({
        url: url,
        type: method,
        data: {'page': newsPageNumber},
        dataType: "json",
        success: function (result) {
            // $("#systemid").val(result.systemid);
            var news = result.content;
            //<li>
            // <a href="#">{#替换第二屏1.1}Responsive Design</a>
            // <br>
            // <span class="small text-muted">Lorem ipsum dolor r adipisicing elit. Animi, laborum.</span>
            // </li>

            if (news.length < 1) {
                newsPageNumber = newsPageNumber -1;
                alert("TO THE END!");
            }

            for (var i = 0; i < news.length; i++) {
                allNews[news[i].systemid] = news[i].content;
                var liTag = $('<li></li>');
                var aTag = $('<a></a>');
                aTag.attr('href', 'javascript:void(0);');
                aTag.text(news[i].title);
                aTag.attr("value", news[i].systemid);
                aTag.attr("onclick", "javascript:replaceDetailNews(this)");
                aTag.appendTo(liTag);

                var brTag = $('<br>');
                brTag.appendTo(liTag);

                var spanTag = $('<span></span>');
                spanTag.addClass("small text-muted");
                spanTag.text(news[i].summary);
                spanTag.appendTo(liTag);
                liTag.appendTo($("#newsList"));
            }
        },
        error: function (msg) {
            alert("PAGE ERROR!");
        }
    })
}