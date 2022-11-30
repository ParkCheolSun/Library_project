		let frm = $("#frm");
		let origFileDiv;
		let fileMaxCnt = 3, fileMaxSize = 10485760, fileAllowExt = [ "jpg",
				"jpeg", "png" ];
				
		$(document).ready(
			function() {
				origFileDiv = $(".custom-file");
			});

		function fnAddFileDiv() {
			let fileDivCnt = $(".custom-file").length;

			if (fileDivCnt >= fileMaxCnt) {
				alert("Can't add any more file.");
				return false;
			}

			let copyFileDiv = origFileDiv.clone(true);

			copyFileDiv.find("input").val("");
			copyFileDiv.find("label").text("파일 선택");
			copyFileDiv.find("label").attr("for", "customFile" + fileDivCnt);
			copyFileDiv.find("input").attr("id", "customFile" + fileDivCnt);
			copyFileDiv.find("input").attr("name", "customFile" + fileDivCnt);

			$("#fileDiv").append(copyFileDiv);
		}

		function fnDelFileDiv() {
			if ($(".custom-file").length <= 1) {
				alert("Can't Delete any more file.");
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
				alert("Attachments can be registered up to 10MB.");
			} else {
				flag = false;
				$(obj).next("label").text(fileVal);
			}

			if (flag) {
				$(obj).val("");
				$(obj).next("label").text("파일 선택");
			}
		}

		function fnSubmit() {
			$("#frm").submit();
		}

		$(function() {
			frm.validate({
				messages : {
					// Message Custom..
					title : {
						required : "Custom required, Please enter a title."
					}
				},
				submitHandler : function(form) {
					// Submit Action..
					form.submit();
				}
			});
		});