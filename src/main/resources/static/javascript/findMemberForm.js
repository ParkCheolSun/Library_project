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