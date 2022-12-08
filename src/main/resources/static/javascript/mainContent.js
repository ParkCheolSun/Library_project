var scroll = function(){
    
    var $nav = null,
        $cnt = null,
        moveCnt = null,
        moveIndex = 0,
        moveCntTop = 0,
        winH = 0,
        time = false; // 새로 만든 변수

    $(document).ready(function(){
        init();
        initEvent();
        messageControl();
        
        //페이지 4 
		var trident = navigator.userAgent.match(/Trident\/(\d.\d)/i);
		if (trident != null) {
			if (trident[1] < '6.0') {
				$(".ani").removeClass('ani');
			}
		}

		$('.s2_wrap > span').click(
			function() {
				$('.s2_wrap > span.on').removeClass("on");
				$(this).addClass("on");
				$('.s2_wrap > i.on').removeClass("on");
				$("." + $(this).data("button")).addClass('on');
				$(".content_box").attr('class', 'content_box');
				$(".content_box").addClass($(this).data("show"));
				$(".section02 .content_box_sub").hide();
				$(".section02 .content_box ." + $(this).data("show")).show();
		});
		
		//광주남구문화정보도서관 - 첫 화면 출력
		new daum.roughmap.Lander({
				"timestamp" : "1669965579197",
				"key" : "2ctwc",
				"mapWidth" : "1912",
				"mapHeight" : "500"
			}).render();
		
		$('.btn_A').click(function(){
			//광주남구문화정보도서관
    		new daum.roughmap.Lander({
				"timestamp" : "1669965579197",
				"key" : "2ctwc",
				"mapWidth" : "1912",
				"mapHeight" : "500"
			}).render();
		});
		
		$('.btn_B').click(function(){
			//광주광역시립사직도서관
    		new daum.roughmap.Lander({
				"timestamp" : "1669969380551",
				"key" : "2cuz5",
				"mapWidth" : "1912",
				"mapHeight" : "500"
			}).render();
		});
		
		$('.btn_C').click(function(){
			//남구청소년도서관
			new daum.roughmap.Lander({
				"timestamp" : "1669970962548",
				"key" : "2cuzn",
				"mapWidth" : "1912",
				"mapHeight" : "500"
			}).render();
		});
		
		$('.btn_D').click(function(){
			//남구푸른길도서관
			new daum.roughmap.Lander({
				"timestamp" : "1669972505045",
				"key" : "2cuzx",
				"mapWidth" : "1912",
				"mapHeight" : "500"
			}).render();
		});
		
		$('.btn_E').click(function(){
			//금호평생교육관
			new daum.roughmap.Lander({
				"timestamp" : "1669972619007",
				"key" : "2cu2z",
				"mapWidth" : "1912",
				"mapHeight" : "500"
			}).render();
		});

		$('.s2_wrap > span').mouseenter(
			function() {
				var hover1 = $("." + $(this).data("button"));
				var hover2 = hover1.hasClass('on');
				if (hover2 == false) {
					$("." + $(this).data("button")).addClass('hover');
				}
		});

		$('.s2_wrap > span').mouseleave(
			function() {
				$("." + $(this).data("button")).removeClass('hover');
		});
		
    });
    
    var messageControl = function(){
    	var message = $('#mes').val();
    	var meslog = $('#meslog').val();
		var output = "";
		if (message == "createUSER")
			output = "회원가입이 완료되었습니다."
		if(meslog == "USERLogout")
			output = "로그아웃 되었습니다."
		if(meslog == "USERDelete")
			output = "회원탈퇴 되었습니다."
		if(output != ""){
			Swal.fire({
				position : 'top-end',
				icon : 'success',
				title : output,
				showConfirmButton : false,
				timer : 1500
			});
			$('#mes').val("");
		}
    }
    
    var init = function(){
        $cnt = $(".main-content");
        //$nav = $(".header a");
    };
    
    var initEvent = function(){
        $("html ,body").scrollTop(0);
        winResize();
        $(window).resize(function(){
            winResize();
        });
        //$nav.on("click", function(){
        //    moveIndex = $(this).parent("li").index();
        //    moving(moveIndex);
        //    return false;
        //});
        $cnt.on("mousewheel", function(e){
            if(time === false){ // time 변수가 펄스일때만 휠 이벤트 실행
              wheel(e);
            }
        });
    };
        
    var winResize = function(){
        winH = $(window).height();
        $cnt.children("div").height(winH);
        $("html ,body").scrollTop(moveIndex.scrollTop);
    };
    
    var wheel = function(e){
        if(e.originalEvent.wheelDelta < 0){
            if(moveIndex < 3) {
                moveIndex += 1;
                moving(moveIndex);
            };
        }else{
            if(moveIndex > 0){
                moveIndex -= 1;
                moving(moveIndex);
            };
        };
    };
    
    var moving = function(index){
        time = true // 휠 이벤트가 실행 동시에 true로 변경
        moveCnt = $cnt.children("div").eq(index);
        moveCntTop = moveCnt.offset().top;
        $("html ,body").stop().animate({
            scrollTop: moveCntTop
        }, 650, function(){
          time = false; // 휠 이벤트가 끝나면 false로 변경
        });
        //$nav.parent("li").eq(index).addClass("on").siblings().removeClass("on");
    };
    
};

scroll();

$(function(){
	 var windowWidth = $( window ).width();
    //initialize swiper when document ready
    var mySwiper = new Swiper('.swiper-container', {
        // Optional parameters 
       
        slidesPerView: 2, // 한 슬라이드에 보여줄 갯수
        slidesPerGroup : 2, // 그룹으로 묶을 수, slidesPerView 와 같은 값을 지정하는게 좋음
        spaceBetween: 30, // 슬라이드 사이 여백
        
        // 그룹수가 맞지 않을 경우 빈칸으로 메우기
        // 3개가 나와야 되는데 1개만 있다면 2개는 빈칸으로 채워서 3개를 만듬
        loopFillGroupWithBlank : true,

        loop: true, // loop 를 true 로 할경우 무한반복 슬라이드, false 로 할경우 슬라이드의 끝에서 더보여지지 않음

        // If we need pagination 
        pagination: { // 호출(pager)여부
            el: '.swiper-pagination',
            clickable : true, // 페이징을 클릭하면 해당 영역으로 이동, 필요시 지정해 줘야 기능 작동 
            
        },
        
        // Navigation arrows 
        navigation: { 
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        // 3초마다 자동으로 슬라이드가 넘어갑니다. 1초 = 1000
  autoplay: {
    delay: 3000},
        
        // 반응형
        breakpoints: {
            1280: { 
                slidesPerView: 2, //화면줄일때 보이는갯수
                slidesPerGroup: 2,
            },
            720: {
                slidesPerView: 1,
                slidesPerGroup: 1,
            },
        }
    })
    var sly = new Sly($('.frame'), {
			horizontal: 1,
			itemNav: 'basic',
			smart: 1,
			activateOn: 'click',
			mouseDragging: 1,
			touchDragging: 1,
			releaseSwing: 1,
			startAt: 0,
			speed: 3000,
			elasticBounds: 1,
			dragHandle: 1,
			dynamicHandle: 1,
			clickBar: 1,

			// Cycling
			cycleBy: 'items',
			cycleInterval: 3000,
			pauseOnHover: 0

			}, null).init();
	//sly.toStart();
});


function searchLoad(){
	var offset = $(".cnt04").offset(); //해당 위치 반환
	$("html, body").animate({scrollTop: offset.top},2000);
}


