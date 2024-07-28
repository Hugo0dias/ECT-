var id;

$(document).ready(
    function(){
        const params = new URLSearchParams(window.location.search);
        id = params.get("id");
        imagecomments ();
    });      

function imagecomments() {
	$.get("/comments",
		{ idimg : id },
		function(response){
			showimageandinfo(response);
		});
}

function showimageandinfo(response) {
	// response.image is the image information

	var image = response.image;
	console.log(image)
    var info = '<p>Name: ' + image.name + '</p><p>Author: ' + image.author + '</p><p>' + image.datetime + '</p>' + '<img class="size-image" src="' + image.path + '" />';
    $("#imageinfo").html(info);
	
	// response.comments is the image list comments
	$("#comments").empty();

	for (let i = 0; i < response.comments.length; i++) {
		var comment = response.comments[i];
		var html = '<div class="comment-box" style="border: 3px solid">';
		html += '<p>' + comment.datetime + '</p>';
		html += '<p>Name: ' + comment.user + '</p>';
		html += '<p>' + comment.comment + '</p>';
		html += '</div>';
    	$("#comments").append(html);
	
}



	// response.votes is the image votes
}

function newcomment() {
	// obtain the user and comment from image page
	var data = new FormData();

	var user = document.getElementById("user").value;
	var comment = document.getElementById("comment").value;
	console.log("name", user)
	console.log("author", comment)

	if (user == "" || comment == "") alert("Missing name and/or author!");
	else {
		data.append("idimg", id)
		data.append("username", user);
		data.append("newcomment", comment);

		var xhr = new XMLHttpRequest();
		xhr.open("POST", "/newcomment");
		//xhr.upload.addEventListener("progress", updateProgress(this), false);
		xhr.send(data);
		console.log(data)
		imagecomments();

	}
}
function updatelikes(value) {
	document.getElementById("thumbs_up").innerHTML = value;
}

function upvote() {
	var data = new FormData();
	//var counterlikes = 0;
	//updatelikes(++counterlikes)
	/*$.post("/upvote",
		{ idimag: id },
		function(response)
		{	
			
			// update thumbs_up and thumbs_down
		});*/
	data.append("idimg", id)
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "/upvote",function(response)
	{	
		var likes = response.likes;
		//var counter = likes."onome dado"

		// update thumbs_up and thumbs_down
	})
	//xhr.upload.addEventListener("progress", updateProgress(this), false);
	xhr.send(data);

}

function downvote() {
	var data = new FormData();
	//$.post("/downvote",
	//	{ idimag: id },
	//	function(response)
	//	{
			// update thumbs_up and thumbs_down	
	//	});
	data.append("idimg", id)
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "/downvote",function(response)
	{	
		var likes = response.likes;
	})

	xhr.send(data);
}
