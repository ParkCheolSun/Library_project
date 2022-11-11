$(function(){
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
});






