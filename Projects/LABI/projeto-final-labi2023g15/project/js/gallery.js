$(document).ready(function(){
    imageslist("all");
});

function imageslist(id) {
    var author;
    if (id == "all") {
        author = "all";
    } else {
        author = $("#authorImg").val();
        if (author == "") {
            author = "all";
        }
    }
    $.get("/list", { id: author }, function(response){
        showimages(response);
    });
}

function showimages(response) {
    $("#showimages").html("");
    console.log("tamanho: ", response.images.length) //numero de fotos associadas a cada nome
    for (let i = 0; i < response.images.length; i++) {
        var image = response.images[i];
        var html = '<div class="image">';

        html += '<img src="' + image.path + '" onclick="showimagecomments(' + image.id + ')" />'; // com botao
        //html += '<img src="' + image.path + '" />';
        console.log("log path", html)

        html += '</div>';
        $("#showimages").append(html);

        console.log("log path depois", image.path)

    }
}

function showimagecomments(id) {
    window.open("../html/image.html?id=" + id, '_blank');
}
