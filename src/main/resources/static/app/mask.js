var mask = {
	open : function(message) {
		HoldOn.open({
			theme : "sk-fading-circle",
			message : "<h1>" + message + "</h1>"
		});
	},
	close : function() {
		HoldOn.close();
	}
}
