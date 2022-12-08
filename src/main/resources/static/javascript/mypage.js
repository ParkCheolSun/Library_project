var oriEmail = null;

$(document).ready(function() {
	$("#btn-signup").prop('disabled', true);
	$(".btn-email-end").hide();
	$(".code").hide();
	$("#btn-sendEmail").prop('disabled', true);
	var message = $('#mes').val();
	var output = "";
	var iconString = "success";
	
	$("#btn-emailUpdate").click(function(){
		$("#btn-sendEmail").prop('disabled', false);
		$("#btn-emailUpdate").prop('disabled', true);
		$("#btn-submit").prop('disabled', true);
	});
	
	//주소API사용
	$("#address").click(function(){
    	 //카카오 지도 발생
        new daum.Postcode({
            oncomplete: function(data) { //선택시 입력값 세팅
            	$("#address").val(data.address); // 주소 넣기
            	$("#address_detail").focus(); //상세입력 포커싱
           	}
       	}).open();
    });
    
    //비밀번호 확인
    $("#alert-success").hide();
    $("#alert-danger").hide();
    $(".password").keyup(function(){
		var pwd1=$("#password1").val();
        var pwd2=$("#password2").val();
        if(pwd1 != "" || pwd2 != ""){
            if(pwd1 == pwd2){
                $("#alert-success").show();
                $("#alert-danger").hide();
                $("#btn-submit").removeAttr("disabled");
            }else{
                $("#alert-success").hide();
                $("#alert-danger").show();
                $("#btn-submit").attr("disabled", "disabled");
            }    
        }
    });
    
    // 회원 탈퇴
    $('#btn-delete').click(function(){
    	const swalWithBootstrapButtons = Swal.mixin({
  			customClass: {
    			confirmButton: 'btn btn-success btn-swal-success',
    			cancelButton: 'btn btn-danger btn-swal-danger'
  			},
  			buttonsStyling: false
		})
		
    	swalWithBootstrapButtons.fire({
  			title: '회원탈퇴 하시겠습니까?',
  			text: "탈퇴하시면 개인정보가 전부 삭제됩니다!",
  			icon: 'warning',
  			confirmButtonText: '회원탈퇴',
  			cancelButtonText: '취소',
  			showCancelButton: true,
  			reverseButtons: true
		}).then((result) => {
  			if (result.isConfirmed) {
      			$('#mypageForm').attr("action","/login/mypage/delete");
				$('#modal-method').attr("value","delete");
				$('#mypageForm').submit();
  			} else if (
    			result.dismiss === Swal.DismissReason.cancel
  			) {
  			}
		})
    });
    
    // 수정 전 비밀번호 정규화 검사
    $("#mypageForm").submit(function(){
    	var method = $('#modal-method').attr("value");
    	if(method == "delete"){
    		checkResult = true;
    	} else if(method == "put"){
    		var regExp = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
			var pwd1=$("#password1").val();
       		var pwd2=$("#password2").val();
        
        	if(pwd1 == "" || pwd2 == ""){
        		return true;
        	} else if(pwd2.match(regExp) == null) {
    			Swal.fire({
					icon : 'error',
					title : '비밀번호 오류',
					text : "비밀번호는 영문,숫자,특수문자 조합으로 작성하여주세요.",
					footer : '<a href="">Why do I have this issue?</a>'
				});
    			return false;
			}
    	}
    	return false;
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
			'action' : 'Notfind'
		},
		success : function(check, aJaxtatus) { //컨트롤러에서 넘어온 cnt값을 받는다 
			console.log("check : " + check.result)
			if (check.result == "Success") {
				$(".btn-email-start").fadeOut();
				$(".code").fadeIn(3000);
				$(".btn-email-end").fadeIn(3000);
				console.log("성공");
			} else {
				Swal.fire({
					icon : 'error',
					title : '이메일 오류',
					text : "이메일 형식이 잘못되었거나 이미 가입되어있는 이메일입니다.",
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
			'action' : 'find3'
		},
		success : function(check, aJaxtatus) { //컨트롤러에서 넘어온 cnt값을 받는다 
			console.log("check : " + check.result)
			if (check.result == "False") {
				console.log("실패");
				Swal.fire({
					icon : 'error',
					title : '인증번호 오류',
					text : "인증번호를 다시 확인해주세요.",
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
				$("#btn-submit").prop('disabled', false);
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

function checkPass(){
	var pass1 = $("#password1").val();
	var pass2 = $("#password2").val();
	if(pass1 == "" || pass2 == ""){
		Swal.fire({
			icon : 'error',
			title : '비밀번호 오류',
			text : "비밀번호는 빈칸일 수 없습니다.",
			footer : '<a href="">Why do I have this issue?</a>'
		});
		return false;
	}
	if(pass1 == pass2){
		return true;
	}
	else{
		Swal.fire({
			icon : 'error',
			title : '비밀번호 오류',
			text : "비밀번호를 다시한번 확인하여 주세요.",
			footer : '<a href="">Why do I have this issue?</a>'
		});
		return false;
	}
	return false;
}