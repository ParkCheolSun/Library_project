var doubleCheck = false;
var member;
$(document).ready(function() {
	var table = $('#data_list').DataTable({
		"language": {
            "decimal":        "",
            "emptyTable":     "표에서 사용할 수있는 데이터가 없습니다.",
            "info":           "총 _TOTAL_명   _START_에서 _END_까지 표시",
            "infoEmpty":      "0 개 항목 중 0 ~ 0 개 표시",
            "infoFiltered":   "(_MAX_ 총 항목에서 필터링 됨)",
            "infoPostFix":    "",
            "thousands":      ",",
            "lengthMenu":     "_MENU_개씩 보기",
            "loadingRecords": "로드 중 ...",
            "processing":     "처리 중 ...",
            "search":         "검색:",
            "zeroRecords":    "일치하는 레코드가 없습니다.",
            "paginate": {
                "first":      "처음",
                "last":       "마지막",
                "next":       "다음",
                "previous":   "이전"
            },
            "aria": {
                "sortAscending":  ": 오름차순으로 정렬",
                "sortDescending": ": 내림차순으로 정렬"
            }
        }
	});
	
	table.column(4).visible(false);
	table.column(5).visible(false);
	table.column(6).visible(false);
	table.column(7).visible(false);
	
	var del;
	
	$("#data_list").on('click', 'tbody tr', function () {
    	var row = $("#data_list").DataTable().row($(this)).data();
    	member = row;
    	doubleCheck = false
    	$('#exampleModal').modal('show');
    	$('#modal-detail-id').val(row[0]);
    	$('#modal-detail-name').val(row[1]);
    	$('#modal-detail-email').val(row[2]);
    	$('#modal-detail-role').val(row[3]);
    	$('#modal-detail-address').val(row[4]);
    	$('#modal-detail-detailAddress').val(row[5]);
    	$('#modal-detail-gender').val(row[6]);
    	$('#modal-detail-number').val(row[7]);
    	$('#modal-detail-createDate').val(row[8]);
    	$('#modal-delete').show();
    	$('#modal-detail-id').attr("readonly", true );
    	
    	// 클릭한 아이디가 관리자일경우 삭제 불가
    	
    	if(row[3] == "ADMIN"){
    		del = $('#modal-delete').detach();
    	} else {
    		$('#btn-close').after(del);
    	}
	});
	
	$('#modal-form').submit(function(){
		// 유효성 검사
		var check = true;
		var method = $('#modal-method').attr("value");
		if(method == 'delete'){
			return check;
		}
		var id = $('#modal-detail-id').val();
    	var name = $('#modal-detail-name').val();
    	var email = $('#modal-detail-email').val();
    	var role = $('#modal-detail-role').val();
    	if(id == null || name == null || email == null || role == null){
    		Swal.fire({
				icon : 'error',
				title : '입력 오류',
				text : "필수 항목을 입력하세요.",
				footer : '<a href="">Why do I have this issue?</a>'
			});
    		check = false;
    	} else if(email == member[2]){
    		check = true;
    	} else {
    		if(doubleCheck == false){
    			Swal.fire({
					icon : 'error',
					title : '중복확인 진행 요청',
					text : "중복확인을 먼저 진행하여주세요.",
					footer : '<a href="">Why do I have this issue?</a>'
				});
				check = false;
    		}
    	}
    	return check;
	});
});

function modalDelete(){
	//유효성 검사
	var id = $('#modal-detail-id').val();
	var number = $('#modal-detail-number').val();
	if(id != null || number != null || id != "" || number != ""){
		$('#modal-form').attr("action","/admin/memberDelete");
		$('#modal-method').attr("value","delete");
		$('#modal-form').submit();
	} else {
		Swal.fire({
			icon : 'error',
			title : '데이터 오류',
			text : "삭제가 불가능한 아이디입니다.",
			footer : '<a href="">Why do I have this issue?</a>'
		});
	}
}

function check(){
    var email = $('#modal-detail-email').val();
    if(member != null){
    	if(email == member[2]){
    		Swal.fire({
				icon : 'error',
				title : '중복확인 오류',
				text : "이메일 값이 이전값과 같습니다.",
				footer : '<a href="">Why do I have this issue?</a>'
			});
			return;
    	}
    }
    var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
				url : '/admin/check',
				type : 'post', //POST 방식으로 전달
				dataType : 'json',
				data : {
					'email' : email
				},
				success : function(check, aJaxtatus) { //컨트롤러에서 넘어온 cnt값을 받는다 
					if (check.answer == "Success") {
						Swal.fire({
							icon : 'success',
							title : '중복확인 결과',
							text : "사용가능한 아이디/이메일 입니다.",
							footer : '<a href="">Why do I have this issue?</a>'
						});
						doubleCheck = true;
						$('#modal-submit').attr("disabled", false );
					} else {
						Swal.fire({
							icon : 'error',
							title : '중복확인 결과',
							text : "현재 사용중인 아이디/이메일 입니다.",
							footer : '<a href="">Why do I have this issue?</a>'
						});
					}
				},
				error : function(xhr, ajaxOptions, thrownError) {
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

function fnCreate(){
	$('#exampleModal').modal('show');
	$('#modal-detail-id').val("");
    $('#modal-detail-name').val("");
    $('#modal-detail-password').val("");
    $('#modal-detail-email').val("");
    $('#modal-detail-role').val("");
    $('#modal-detail-address').val("");
    $('#modal-detail-detailAddress').val("");
    $('#modal-detail-gender').val("");
    $('#modal-detail-number').val("");
    $('#modal-detail-createDate').val("");
    $('#modal-delete').hide();
	$('#modal-form').attr("action","/admin/memberSave");
	$('#modal-method').attr("value","post");
	$('#modal-submit').html('회원저장');
	$('#modal-submit').attr("disabled", true );
	$('#modal-detail-id').attr("readonly", false );
}

function modalHide(){
	$('#exampleModal').modal('hide');
}