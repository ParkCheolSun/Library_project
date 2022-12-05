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
	
	$("#data_list").on('click', 'tbody tr', function () {
    	var row = $("#data_list").DataTable().row($(this)).data();
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
    	}
    	return check;
	});
});

function modalDelete(){
	$('#modal-form').attr("action","/admin/memberDelete");
	$('#modal-method').attr("value","delete");
	$('#modal-form').submit();
}

function fnCreate(){
	$('#exampleModal').modal('show');
	$('#modal-detail-id').val("");
    $('#modal-detail-name').val("");
    $('#modal-detail-email').val("");
    $('#modal-detail-role').val("");
    $('#modal-detail-address').val("");
    $('#modal-detail-detailAddress').val("");
    $('#modal-detail-gender').val("");
    $('#modal-detail-number').val("");
    $('#modal-detail-createDate').val("");
	$('#modal-form').attr("action","/admin/memberSave");
	$('#modal-method').attr("value","post");
	$('#modal-submit').html('회원저장');
}

function modalHide(){
	$('#exampleModal').modal('hide');
}