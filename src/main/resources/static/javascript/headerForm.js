
            $(window).scroll(function(){
                win_scr=$(window).scrollTop();
                console.log("스크롤위치값:" + win_scr); 
                if(win_scr<500){
                    $(".top_btn").stop().fadeOut(600);
                    $("header").css({background:"none"})
                }else{
                    $(".top_btn").stop().fadeOut(600);
                    $("header").css({background:"none"})
                }
            });