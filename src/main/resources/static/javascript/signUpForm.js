		$(document).ready(function() {
			$("#btn-signup").prop('disabled', true);
			$("#logid").val("")
			$("#logpass").val("")
			if(checkIng()){
				$("#reg-log").click();
			}
			$("#btn-signup").click(function(){
				var result = checkInput();
				return result;
			});
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
		
		function checkInput() {
			var get_input = $(".card-back input");
			var textString = "";
			$.each(get_input, function(index, value) {
				if ($(value).val() == null || $(value).val() == ""
						&& $(value).attr("id") != "code") {
					console.log(value);
					console.log('id =' + $(value).attr("id"));
					console.log('name =' + $(value).attr("name"));
					console.log('value =' + $(value).val());
					textString = $(value).attr("placeholder") + "를 입력해 주세요."
					Swal.fire({
						icon : 'error',
						title : '입력오류',
						text : textString,
						footer : '<a href="">Why do I have this issue?</a>'
					});
					return false;
				}
			});
			if(textString != "")
				return false;
			return true;
		}
		
		function checkIng() {
			var get_input = $(".card-back input");
			var cnt = 0;
			$.each(get_input, function(index, value) {
				if ($(value).val() == null || $(value).val() == ""
						&& $(value).attr("id") != "code") {
					console.log(value);
					console.log('id =' + $(value).attr("id"));
					console.log('name =' + $(value).attr("name"));
					console.log('value =' + $(value).val());
					cnt = cnt + 1;
				}
			});
			console.log(cnt);
			if(cnt == 6){
				return false;
			}
			return true;
		}

		function checkId() {
			var reg = /\s/g;
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			var id = $("#id").val()
			if (id.match(reg)) {
				//공백이 있을 경우
				return;
			}
			if ($("#id").val().trim() == '') {
				Swal.fire({
							icon : 'error',
							title : '입력 오류',
							text : $("#id").attr("placeholder") + " 항목을 입력하세요.",
							footer : '<a href="">Why do I have this issue?</a>'
						});
				return;
			}
			$.ajax({
				url : '/login/idCheck', //Controller에서 요청 받을 주소
				type : 'post', //POST 방식으로 전달
				dataType : 'json',
				data : {
					'id' : id
				},
				success : function(check, aJaxtatus) { //컨트롤러에서 넘어온 cnt값을 받는다 
					console.log("check : " + check.answer)
					if (check.answer == "Success") {
						Swal.fire({
							icon : 'success',
							title : '중복확인 결과',
							text : "사용 가능한 아이디입니다.",
							footer : '<a href="">Why do I have this issue?</a>'
						});
						$("#id").prop('readonly', true);
						$("#id_hidden").val("S");
						hiddenCheck();
					} else {
						$('#id').val('');
						Swal.fire({
							icon : 'error',
							title : '중복확인 결과',
							text : "현재 사용중인 아이디입니다.",
							footer : '<a href="">Why do I have this issue?</a>'
						});
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
			$.ajax({
				url : '/email/sendEmail', //Controller에서 요청 받을 주소
				type : 'post', //POST 방식으로 전달
				dataType : 'json',
				data : {
					'email' : email,
					'action' : 'create'
				},
				success : function(check, aJaxtatus) { //컨트롤러에서 넘어온 cnt값을 받는다 
					console.log("check : " + check.result)
					if (check.result == "Success") {
						console.log("성공");
						$(".btn-email-start").css({
							"display" : "none"
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
							text : "이메일 형식이 잘못되었거나 가입된 이메일입니다.",
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
					'action' : 'create'
				},
				success : function(check, aJaxtatus) { //컨트롤러에서 넘어온 cnt값을 받는다 
					console.log("check : " + check.result)
					if (check.result == "Success") {
						console.log("성공");
						$("#email").prop('readonly', true);
						$("#email_hidden").val("S");
						hiddenCheck();
					} else {
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
				}
			});
		}

		function hiddenCheck() {
			var id_hidden = $("#id_hidden").attr("value");
			var email_hidden = $("#email_hidden").attr("value");
			if (id_hidden == "S" && email_hidden == "S") {
				$("#btn-signup").prop('disabled', false);
			}
		}