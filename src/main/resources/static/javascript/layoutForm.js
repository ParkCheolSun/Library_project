	$(document).ready(function() {
		var checkPage = $(".main-content").length;
		if(checkPage > 0){
			$(".sidebar").css({
				"display" : "none"
			});
			$(".content").css({
				"width" : "100%",
				"height" : "100%"
			});
		}
	});