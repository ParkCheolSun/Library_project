var oriEmail = null;

$(document).ready(function() {
	$("#btn-signup").prop('disabled', true);
	var message = $('#mes').val();
	var output = "";
	var iconString = "success";
	if (message == "modPassword")
		output = "비밀번호 변경이 완료되었습니다."
	else if(message == "USERLoginFail"){
		output = "로그인 실패."
		iconString = "error"
	}
	if(output != ""){
		Swal.fire({
			position : 'top-end',
			icon : iconString,
			title : output,
			showConfirmButton : false,
			timer : 1500
		});
	}
	$("#address").click(function(){
    	 //카카오 지도 발생
        new daum.Postcode({
            oncomplete: function(data) { //선택시 입력값 세팅
            	$("#address").val(data.address); // 주소 넣기
            	$("#address_detail").focus(); //상세입력 포커싱
           	}
       	}).open();
    });
});

function sendEmail() {
	var reg = /\s/g;
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	var email = $("#email").val()
	if (email.match(reg)) {
		//공백이 있을 경우
		return;
	}
	if ($("#email").val().trim() == '') {
		Swal.fire({
			icon : 'error',
			title : '입력 오류',
			text : $("#email").attr("placeholder") + " 항목을 입력하세요.",
			footer : '<a href="">Why do I have this issue?</a>'
		});
		return;
	}
	if(email == oriEmail){
		Swal.fire({
			icon : 'error',
			title : '입력 오류',
			text : "이전 이메일과 동일합니다!",
			footer : '<a href="">Why do I have this issue?</a>'
		});
		return
	}
	$.ajax({
		url : '/email/sendEmail', //Controller에서 요청 받을 주소
		type : 'post', //POST 방식으로 전달
		dataType : 'json',
		data : {
			'email' : email,
			'action' : 'find'
		},
		success : function(check, aJaxtatus) { //컨트롤러에서 넘어온 cnt값을 받는다 
			console.log("check : " + check.result)
			if (check.result == "Success") {
				console.log("성공");
				$(".input-icon-code").css({
					"display" : "inline-block"
				});
				$(".input-email-code").css({
					"display" : "inline-block"
				});
				$(".btn-email-end").css({
					"display" : "inline-block"
				});
			} else {
				Swal.fire({
					icon : 'error',
					title : '이메일 오류',
					text : "이메일 형식이 잘못되었거나 가입하지 않은 이메일입니다.",
					footer : '<a href="">Why do I have this issue?</a>'
				});
				console.log("실패");
			}
		},
		error : function(xhr, ajaxOptions, thrownError) {
			console.log(xhr)
			console.log(xhr.responseText)
			console.log(ajaxOptions)
			console.log(thrownError)
			Swal.fire({
				icon : 'error',
				title : 'Error',
				text : "데이터 수신 에러",
				footer : '<a href="">Why do I have this issue?</a>'
			});
		},
		beforeSend : function(xhr) {
			if (token && header) {
				xhr.setRequestHeader(header, token); // 헤드의 csrf meta태그를 읽어 CSRF 토큰 함께 전송
			} else {
				Swal.fire({
					icon : 'error',
					title : 'csrf Error',
					text : "csrf 토큰 에러",
					footer : '<a href="">Why do I have this issue?</a>'
				});
			}
		},
	});
}

function emailMod(){
	if(oriEmail == null){
		oriEmail = $('#email').val();
		$('#email').prop('readonly', false);
	}
}

function checkEmail() {
	var reg = /\s/g;
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	var email = $("#email").val()
	var code = $("#code").val()
	if (email.match(reg)) {
		//공백이 있을 경우
		return;
	}
	if ($("#email").val().trim() == '') {
		Swal.fire({
			icon : 'error',
			title : '입력 오류',
			text : $("#email").attr("placeholder") + " 항목을 입력하세요.",
			footer : '<a href="">Why do I have this issue?</a>'
		});
		return;
	}
	$.ajax({
		url : '/email/checkEmail', //Controller에서 요청 받을 주소
		type : 'post', //POST 방식으로 전달
		dataType : 'json',
		data : {
			'email' : email,
			'code' : code,
			'action' : 'find2'
		},
		success : function(check, aJaxtatus) { //컨트롤러에서 넘어온 cnt값을 받는다 
			console.log("check : " + check.result)
			if (check.result == "False") {
				console.log("실패");
				Swal.fire({
					icon : 'error',
					title : 'Error',
					text : "아이디를 찾을 수 없습니다.",
					footer : '<a href="">Why do I have this issue?</a>'
				});
			} else {
				console.log("Find ID Success!!");
				Swal.fire({
					icon : 'success',
					title : 'Success',
					text : "이메일 인증 성공",
					footer : '<a href="">Why do I have this issue?</a>'
				});
				$("#email").prop('readonly', true);
			}
		},
		error : function(xhr, ajaxOptions, thrownError) {
			console.log(xhr)
			console.log(xhr.responseText)
			console.log(ajaxOptions)
			console.log(thrownError)
			Swal.fire({
				icon : 'error',
				title : 'Error',
				text : "데이터 수신 에러",
				footer : '<a href="">Why do I have this issue?</a>'
			});
		},
		beforeSend : function(xhr) {
			if (token && header) {
				xhr.setRequestHeader(header, token); // 헤드의 csrf meta태그를 읽어 CSRF 토큰 함께 전송
			} else {
				Swal.fire({
					icon : 'error',
					title : 'csrf Error',
					text : "csrf 토큰 에러",
					footer : '<a href="">Why do I have this issue?</a>'
				});
			}
		}
	});
}