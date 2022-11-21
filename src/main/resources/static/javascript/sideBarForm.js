			$(document).ready(function() {
				var pathName = $(location).attr('pathname');
				if(pathName != "/"){
					$('.components').find('a').each(function(index, item){
						if($(item).attr('href') == "#homeSubmenu" || 
								$(item).attr('href') == "#pageSubmenu" ||
								$(item).attr('href') == "#likeSubmenu" ||
								$(item).attr('href') == "#cultureMenu" ||
								$(item).attr('href') == "#noticeMenu"  ||
								$(item).attr('href') == "#AdminSubMenu") {
							var id = $(item).attr('id');
							//console.log("id : " + id);
							if(id == "guideMenu" || id == "cultureMenu" || id == "noticeMenu" ||
									id == "openNoticeMenu" || id == "sLibraryMenu ||id == "AdminMenu"){
								var temp = "#" + id;
								//console.log("완성문자 : " + temp);
								var search = $(item).parent().children('ul').children('li').children('a');
								var len = search.length;
								
								search.each(function(idx, data){
									if($(data).attr('href') == pathName){
										$(temp).get(0).click();
									}
								});
								return;
							} else {
								//console.log("test : " + $(item).parent().parent().parent().children('a').attr('id'));
							}
						}
					});
				}
			});