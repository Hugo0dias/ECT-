// Image UpLoad Javascript
var file;

function updatePhoto(event) {

	var reader = new FileReader();
	reader.onload = function(event) {

		const canvas = $("#photo")[0];
    	const ctx = canvas.getContext("2d");
    	ctx.clearRect(0, 0, canvas.width, canvas.height);

		//Create an imagem

		var img = new Image();
		img.onload = function() {
			
			//Put imagen on screen
			const canvas = $("#photo")[0];
			const ctx = canvas.getContext("2d");
			ctx.drawImage(img,0,0,img.width,img.height,0,0,550, 450);
		}
		img.src = event.target.result;
	}

	file = event.target.files[0];
	//Obtain the file
	reader.readAsDataURL(file);
}

function uploadImage() {
	//var xhr = new XMLHttpRequest();

    if(file != null) {
		//xhr.open("GET", "/getPaths?temp_path=")
	//	var pathExists = JSON.parse(xhr.responseText);
		//if (pathExists) {
			//alert("Ola")
		//} 
		try {
			sendFile(file);
		} catch (error) {
			console.log("------------------------------------");
			console.log(error);
			console.log("------------------------------------");
			
			//alert("ERORROORROORORO") //completa
		}
		console.log(file);

        //Release the resources alocated to the selected image
        window.URL.revokeObjectURL(picURL);     
    }
    else alert("Missing image!");
}

function sendFile(file) {
	var data = new FormData();
	data.append("myFile", file);


	//Obtain nameImg and authorImg and fill the form
	var name = document.getElementById("nameImg").value;
	var author = document.getElementById("authorImg").value;
	console.log("name", name)
	console.log("author", author)

	console.log("dhuawgdiuqwduipasduaipgsdpiawefdypoaivlds")

	if (name == "" || author == "") alert("Missing name and/or author!");
	else {
		data.append("nameImg", name);
		data.append("authorImg", author);

		var xhr = new XMLHttpRequest();
		xhr.open("POST", "/upload");
		xhr.upload.addEventListener("progress", updateProgress(this), false);
		xhr.send(data);

		console.log(data)
	}

}

function updateProgress(evt){
	if(evt.loaded == evt.total) alert("Okay");
}
