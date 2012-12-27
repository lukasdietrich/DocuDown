function search(term) {
	var ul = document.querySelectorAll("nav ul li");
	for(var i = 0; i < ul.length; i++) {
		var li = ul[i];
		var value = li.querySelector("a").innerHTML;
		if(value.toLowerCase().indexOf(term.toLowerCase()) !== -1)
			li.style.display = "block";
		else
			li.style.display = "none";
	}
}