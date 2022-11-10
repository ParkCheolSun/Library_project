		$(document).ready(function() {
			$("#btn-signup").prop('disabled', true);
		});

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
				alert($("#id").attr("placeholder") + " 항목을 입력하세요.");
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
						$("#id").prop('readonly', true);
						$("#id_hidden").val("S");
						hiddenCheck();
					} else {
						$('#id').val('');
					}
				},
				error : function(xhr, ajaxOptions, thrownError) {
					console.log(xhr)
					console.log(xhr.responseText)
					console.log(ajaxOptions)
					console.log(thrownError)
					alert("에러입니다");
				},
				beforeSend : function(xhr) {
					if (token && header) {
						xhr.setRequestHeader(header, token); // 헤드의 csrf meta태그를 읽어 CSRF 토큰 함께 전송
					} else {
						alert("csrf 토큰 에러");
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
				alert($("#email").attr("placeholder") + " 항목을 입력하세요.");
				return;
			}
			$.ajax({
				url : '/email/sendEmail', //Controller에서 요청 받을 주소
				type : 'post', //POST 방식으로 전달
				dataType : 'json',
				data : {
					'email' : email
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
						console.log("실패");
					}
				},
				error : function(xhr, ajaxOptions, thrownError) {
					console.log(xhr)
					console.log(xhr.responseText)
					console.log(ajaxOptions)
					console.log(thrownError)
					alert("에러입니다");
				},
				beforeSend : function(xhr) {
					if (token && header) {
						xhr.setRequestHeader(header, token); // 헤드의 csrf meta태그를 읽어 CSRF 토큰 함께 전송
					} else {
						alert("csrf 토큰 에러");
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
				alert($("#email").attr("placeholder") + " 항목을 입력하세요.");
				return;
			}
			$.ajax({
				url : '/email/checkEmail', //Controller에서 요청 받을 주소
				type : 'post', //POST 방식으로 전달
				dataType : 'json',
				data : {
					'email' : email,
					'code' : code
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
					alert("에러입니다");
				},
				beforeSend : function(xhr) {
					if (token && header) {
						xhr.setRequestHeader(header, token); // 헤드의 csrf meta태그를 읽어 CSRF 토큰 함께 전송
					} else {
						alert("csrf 토큰 에러");
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