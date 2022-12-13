
$(window).scroll(function(){
	win_scr=$(window).scrollTop();
	//console.log("스크롤위치값:" + win_scr); 
	if(win_scr<500){
		$(".top_btn").stop().fadeOut(600);
		$("header").css({background:"none"})
	}else{
 		$(".top_btn").stop().fadeOut(600);
		$("header").css({background:"none"})
	}
});

function searchLoad(){
	var pathname = $(location).attr('pathname');
	if(pathname == "/"){
		var offset = $(".cnt04").offset(); //해당 위치 반환
		$("html, body").animate({scrollTop: offset.top},2000);
	} else {
		Swal.fire({
			icon : 'error',
			title : '위치 오류',
			text : "메인페이지에서 실행해주세요.",
			footer : '<a href="">Why do I have this issue?</a>'
		});
	}
	
}