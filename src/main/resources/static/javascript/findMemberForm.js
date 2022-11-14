		$(document).ready(function() {
			$(".input-icon-code").css({
				"display" : "none"
			});
			$(".input-icon-code2").css({
				"display" : "none"
			});
			$(".input-icon-pass").css({
				"display" : "none"
			});
			$("#password1").css({
				"display" : "none"
			});
			$("#password2").css({
				"display" : "none"
			});
			$("#btn-change").css({
				"display" : "none"
			});
		});
		function sendEmail() {
			var reg = /\s/g;
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			var email = $("#findEmail").val()
			if (email.match(reg)) {
				//공백이 있을 경우
				return;
			}
			if ($("#findEmail").val().trim() == '') {
				Swal.fire({
					icon : 'error',
					title : '입력 오류',
					text : $("#findEmail").attr("placeholder") + " 항목을 입력하세요.",
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

		function checkEmail() {
			var reg = /\s/g;
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			var email = $("#findEmail").val()
			var code = $("#findCode").val()
			if (email.match(reg)) {
				//공백이 있을 경우
				return;
			}
			if ($("#findEmail").val().trim() == '') {
				Swal.fire({
					icon : 'error',
					title : '입력 오류',
					text : $("#findEmail").attr("placeholder") + " 항목을 입력하세요.",
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
					'action' : 'find'
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
							text : "아이디 : " + check.result,
							footer : '<a href="">Why do I have this issue?</a>'
						});
						$("#findEmail").prop('readonly', true);
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
		$(document).ready(function() {
			$("#btn-change").click(function() {
				var result = checkPass();
				return result;
			});
		});
		
		function checkPass(){
			var pass1 = $("#password1").val();
			var pass2 = $("#password2").val();
			
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
		
		
		function sendEmail2() {
			var reg = /\s/g;
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			var email = $("#email2").val()
			if (email.match(reg)) {
				//공백이 있을 경우
				return;
			}
			if ($("#email2").val().trim() == '') {
				Swal.fire({
					icon : 'error',
					title : '입력 오류',
					text : $("#findEmail").attr("placeholder") + " 항목을 입력하세요.",
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
					'action' : 'find'
				},
				success : function(check, aJaxtatus) { //컨트롤러에서 넘어온 cnt값을 받는다 
					console.log("check : " + check.result)
					if (check.result == "Success") {
						console.log("성공");
						$(".input-icon-code2").css({
							"display" : "inline-block"
						});
						$(".input-email-code2").css({
							"display" : "inline-block"
						});
						$(".btn-email-end2").css({
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

		function checkEmail2() {
			var reg = /\s/g;
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			var email = $("#email2").val()
			var code = $("#findCode2").val()
			var id = $("#id2").val()
			if (email.match(reg)) {
				//공백이 있을 경우
				return;
			}
			if ($("#email2").val().trim() == '') {
				Swal.fire({
					icon : 'error',
					title : '입력 오류',
					text : $("#findEmail").attr("placeholder") + " 항목을 입력하세요.",
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
					if (check.result == "NotID") {
						console.log("실패");
						Swal.fire({
							icon : 'error',
							title : 'Error',
							text : "아이디를 찾을 수 없습니다.",
							footer : '<a href="">Why do I have this issue?</a>'
						});
					} else if (check.result == "False") {
						Swal.fire({
							icon : 'error',
							title : 'Error',
							text : "인증번호 오류!!",
							footer : '<a href="">Why do I have this issue?</a>'
						});
					} else {
						if (id == check.result) {
							$(".input-icon-pass").css({
								"display" : "inline-block"
							});
							$(".result-hide").css({
								"display" : "none"
							});
							$(".input-icon-pass").css({
								"display" : "inline-block"
							});
							$("#password1").css({
								"display" : "inline-block"
							});
							$("#password2").css({
								"display" : "inline-block"
							});
							$("#btn-change").css({
								"display" : "inline-block"
							});
						}
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