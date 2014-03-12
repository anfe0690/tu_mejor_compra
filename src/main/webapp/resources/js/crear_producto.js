
window.onload = function() {
	
	doLayout();
	
	var inputFile = document.getElementById("form_crear_producto:file");
	inputFile.onchange = function (){
		if(this.files && this.files[0]){
			var reader = new FileReader();
			
			reader.onload = function (e){
				document.getElementById("form_crear_producto:imagen").setAttribute("src",e.target.result);
			};
			
			reader.readAsDataURL(this.files[0]);
		}
	};
	
};

