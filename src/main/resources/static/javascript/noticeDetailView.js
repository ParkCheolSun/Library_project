
let frm = $("#frm");
let origFileDiv;
let fileMaxCnt = 3, fileMaxSize = 10485760, fileAllowExt = [
						"jpg", "jpeg", "png", "gif" ];
let deleteFileIdArr = [];
				
	$(document).ready(
			function() {
				origFileDiv = $(".custom-file");
				$('.txt-content').attr('readonly', true);
				$('.txt-title').attr('readonly', true);
				$('.txt-register').attr('readonly', true);
				$('.btn-update').css({"display" : "none"});
				$('.btn-filedelete').css({"display" : "none"});
				$('.btn-fileupdate').css({"display" : "none"});
				$('.custom-file-input').prop('disabled', true);
				var content = $('.content_area').val();
				$('#counter').html("(" + content.length + " / 1000)");
				$('.label-fileupload').css({"display" : "none"});
				$('#fileDiv').css({"display" : "none"});
				$('.noticeDetail-label').css({"display" : "none"});
				$('#counter').css({"display" : "none"});
				
				$('.clear').click(function() {
					$('.txt-content').attr('readonly', false)
					$('.txt-title').attr('readonly', false);
					$('.clear').fadeOut();
					$('.btn-update').delay(1000).fadeIn();
					$(".custom-file").attr('readonly', false)
					$('.btn-filedelete').delay(1000).fadeIn();
					$('.btn-fileupdate').delay(1000).fadeIn();
					$('.custom-file-input').prop('disabled', false);
					$('.label-fileupload').delay(1000).fadeIn();
					$('#fileDiv').delay(1000).fadeIn();
					$('.noticeDetail-label').delay(1000).fadeIn();
					$('#counter').delay(1000).fadeIn();
					
				}); // 내용 readonly 해제
				
				$('.content_area').keyup(function(e) {
					var content = $(this).val();
					$('#counter').html("(" + content.length + " / 1000)"); //글자수 실시간 카운팅

					if (content.length > 1000) {
						Swal.fire({
							position : 'top-end',
							icon : 'error',
							title : "최대 1000자까지 입력 가능합니다.",
							showConfirmButton : false,
							timer : 1500
						});
						$(this).val(content.substring(0, 1000));
						$('#counter').html("(1000 / 최대 1000자)");
					}
				});
			});
		
	function fnAddFileDiv() {
		var fileDivCnt = $(".custom-file").length;
		var fileListCnt = $(".fileList").length;

		if (fileDivCnt+fileListCnt >= fileMaxCnt) {
			Swal.fire({
				position : 'top-end',
				icon : 'error',
				title : "파일은 최대 3개까지만 업로드가 가능합니다!",
				showConfirmButton : false,
				timer : 1500
			});
			return false;
		}

		var copyFileDiv = origFileDiv.clone(true);
		copyFileDiv.find("input").val("");
		copyFileDiv.find("label").text("파일 선택");
		copyFileDiv.find("label").attr("for", "customFile" + fileDivCnt);
		copyFileDiv.find("input").attr("id", "customFile" + fileDivCnt);
		copyFileDiv.find("input").attr("name", "customFile" + fileDivCnt);
		console.log("copyFileDiv : " + copyFileDiv.html());

		$("#fileDiv").append(copyFileDiv);
	}

	function fnDelFileDiv() {
		if ($(".custom-file").length <= 1) {
			Swal.fire({
				position : 'top-end',
				icon : 'error',
				title : "더이상 삭제할 파일이 존재하지않습니다!",
				showConfirmButton : false,
				timer : 1500
			});
			return false;
		}
		$(".custom-file")[$(".custom-file").length - 1].remove();
	}

	function fnChngFile(obj) {
		let fileObj = $(obj)[0];
		let fileVal = fileObj.files[0].name;
		let fileSize = fileObj.files[0].size;
		let fileExt = fileVal.substring(fileVal.lastIndexOf(".") + 1,
				fileVal.length);
		let flag = true;

		if (fileSize > fileMaxSize) {
			Swal.fire({
				position : 'top-end',
				icon : 'error',
				title : "최대 10MB 파일만 업로드가 가능합니다.",
				showConfirmButton : false,
				timer : 1500
			});
		}  else if (($(".fileList").length + $(".custom-file-input").length) > 3) {
			Swal.fire({
				position : 'top-end',
				icon : 'error',
				title : "파일은 최대 3개까지만 업로드가 가능합니다!",
				showConfirmButton : false,
				timer : 1500
			});
		} else {
			flag = false;
			$(obj).next("label").text(fileVal);
		}

		if (flag) {
			$(obj).val("");
			$(obj).next("label").text("파일 선택");
		}
	}

	function fnFileDelete(obj, id) {
		const swalWithBootstrapButtons = Swal.mixin({
  			customClass: {
    			confirmButton: 'btn btn-success btn-swal-success',
    			cancelButton: 'btn btn-danger btn-swal-danger'
  			},
  			buttonsStyling: false
		})

		swalWithBootstrapButtons.fire({
  			title: '첨부파일을 삭제하시겠습니까?',
  			text: "수정 완료 시 첨부파일이 영구적으로 삭제됩니다!",
  			icon: 'warning',
  			confirmButtonText: '삭제',
  			cancelButtonText: '취소',
  			showCancelButton: true,
  			reverseButtons: true
			}).then((result) => {
  				if (result.isConfirmed) {
      				$(obj).parents(".fileList").remove();
			deleteFileIdArr.push(id);
  				} else if (
    				/* Read more about handling dismissals below */
    				result.dismiss === Swal.DismissReason.cancel
  				) {
  				}
			})
	}

	function fnViewDelete() {
		const swalWithBootstrapButtons = Swal.mixin({
  			customClass: {
    			confirmButton: 'btn btn-success btn-swal-success',
    			cancelButton: 'btn btn-danger btn-swal-danger'
  			},
  			buttonsStyling: false
		})

		swalWithBootstrapButtons.fire({
  			title: '게시글을 삭제하시겠습니까?',
  			text: "게시글 및 첨부파일이 영구적으로 삭제됩니다!",
  			icon: 'warning',
  			confirmButtonText: '삭제',
  			cancelButtonText: '취소',
  			showCancelButton: true,
  			reverseButtons: true
			}).then((result) => {
  				if (result.isConfirmed) {
      				let frm = $("#frm");
					frm.attr("action", "/board/noticeDetailView/delete");
					frm.submit();
  				} else if (
    				/* Read more about handling dismissals below */
    				result.dismiss === Swal.DismissReason.cancel
  				) {
  				}
			})
	}

	function fnSubmit() {
		const swalWithBootstrapButtons = Swal.mixin({
  			customClass: {
    			confirmButton: 'btn btn-success btn-swal-success',
    			cancelButton: 'btn btn-danger btn-swal-danger'
  			},
  			buttonsStyling: false
		})

		swalWithBootstrapButtons.fire({
  			title: '수정사항을 저장하시겠습니까?',
  			text: "저장하시면 이전 데이터는 삭제됩니다!",
  			icon: 'warning',
  			confirmButtonText: '수정완료',
  			cancelButtonText: '취소',
  			showCancelButton: true,
  			reverseButtons: true
			}).then((result) => {
  				if (result.isConfirmed) {
      				$("#frm").submit();
  				} else if (
    				/* Read more about handling dismissals below */
    				result.dismiss === Swal.DismissReason.cancel
  				) {
  				}
			})
	}
	
	function fnReplySubmit() {
		let frm=$("#frm");
		if (confirm("등록하시겠습니까?")) {
			frm.attr("method","get");
			frm.attr("action","/board/reply");
			frm.submit();
		}
	}

	$(function() {
		$("#frm").validate({
					messages : {
						title : {
							required : "Custom required, Please enter a title."
						}
					},
					submitHandler : function(form) {
						if (deleteFileIdArr.length > 0) {
							$.ajax({
										url : "/file/delete.ajax",
										type : "post",
										data : {
											idArr : deleteFileIdArr
										},
										dataType : "json",
										success : function(r) {
											if (r.result) {
												form.submit();
											} else {
												Swal.fire({
													position : 'top-end',
													icon : 'error',
													title : "A problem occurred, and progress is interrupted. Try again in a few minutes.",
													showConfirmButton : false,
													timer : 1500
												});
											}
										},
										error : function(e) {
											console.log(e);
										}
									});
						} else {
							form.submit();
						}
					}
				});
	});