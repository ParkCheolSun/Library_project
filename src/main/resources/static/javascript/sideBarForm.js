			$(document).ready(function() {
				var pathName = $(location).attr('pathname');
				//console.log("완성문자 : " + pathName);
				if(pathName != "/"){
					$('.components').find('a').each(function(index, item){
						if($(item).attr('href') == "#homeSubmenu" || 
								$(item).attr('href') == "#pageSubmenu" ||
								$(item).attr('href') == "#likeSubmenu" ||
								$(item).attr('href') == "/search?keyword=" ||
								$(item).attr('href') == "#noticeSubMenu"||
								$(item).attr('href') == "#adminSubmenu"){
							var id = $(item).attr('id');
							//console.log("id : " + id);
							if(id == "guideMenu" || id == "cultureMenu" || id == "noticeMenu" ||
									id == "openMenu" || id == "sLibraryMenu" || id == "adminMenu"){
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
								var temp = pathName.split('/');
								
								if(pathName.match("notice") != null){
									$('#noticeMenu').click();
								} else if(pathName.match("faq") != null || 
								pathName.match("suggestion") != null ||
								pathName.match("request") != null){
									$('#openMenu').get(0).click();
								}
								//console.log("test : " + $(item).parent().parent().parent().children('a').attr('id'));
							}
						}
					});
				}
			});