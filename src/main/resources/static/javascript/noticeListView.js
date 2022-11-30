	// header checkbox event
	$("#chkAll").click(function() {
		if (this.checked) {
			$("input[name='deleteId']").prop("checked", true);
		} else {
			$("input[name='deleteId']").prop("checked", false);
		}
	});

	// body checkbox event
	$("input[name='deleteId']").click(function() {
		var delInpLen = $("input[name='deleteId']").length;
		var delInpChkLen = $("input[name='deleteId']:checked").length;

		if (delInpLen == delInpChkLen) {
			$("#chkAll").prop("checked", true);
		} else {
			$("#chkAll").prop("checked", false);
		}
	});

	function fnDelete() {
		var delInpChkLen = $("input[name='deleteId']:checked").length;

		if (delInpChkLen > 0) {
			const swalWithBootstrapButtons = Swal.mixin({
  				customClass: {
    				confirmButton: 'btn btn-success btn-swal-success',
    				cancelButton: 'btn btn-danger btn-swal-danger'
  				},
  				buttonsStyling: false
			})

			swalWithBootstrapButtons.fire({
  				title: '삭제하시겠습니까?',
  				text: "해당 글은 영구적으로 삭제됩니다!",
  				icon: 'warning',
  				confirmButtonText: '삭제',
  				cancelButtonText: '취소',
  				showCancelButton: true,
  				reverseButtons: true
				}).then((result) => {
  					if (result.isConfirmed) {
      					let frm = $("#frm");
						frm.attr("action", "/board/notice/delete");
						frm.attr("method", "post");
						frm.submit();
  					} else if (
    					/* Read more about handling dismissals below */
    					result.dismiss === Swal.DismissReason.cancel
  					) {}
				})
		} else {
			alert("선택된 게시글이 없습니다.");
		}
	}