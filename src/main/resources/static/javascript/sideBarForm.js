			$(document).ready(function() {
					var pathName = $(location).attr('pathname');
					if(pathName != "/"){
						$('.components').find('a').each(function(index, item){
							if($(item).attr('href') == "#homeSubmenu" || 
									$(item).attr('href') == "#pageSubmenu" ||
									$(item).attr('href') == "#likeSubmenu" ||
									$(item).attr('href') == "#cultureMenu" ||
									$(item).attr('href') == "#noticeMenu"){
								var id = $(item).attr('id');
								//console.log("id : " + id);
								if(id == "guideMenu" || id == "cultureMenu" || id == "noticeMenu" ||
										id == "openNoticeMenu" || id == "sLibraryMenu"){
									var temp = "#" + id;
									//console.log("완성문자 : " + temp);
									$(temp).get(0).click();
									return;
								} else {
									//console.log("test : " + $(item).parent().parent().parent().children('a').attr('id'));
								}
							}
						});
					}
				});