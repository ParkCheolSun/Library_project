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
		} else {
			$("#main-header").css({
				"border-bottom" : "solid 1px"
			});
		}
	});
	
	// manual - 복원안함 / auto - 복원
	history.scrollRestoration = "manual"

