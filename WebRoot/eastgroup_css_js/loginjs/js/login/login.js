// JavaScript Document
//支持Enter键登录
		document.onkeydown = function(e){
			if($(".bac").length==0)
			{
				if(!e) e = window.event;
				if((e.keyCode || e.which) == 13){
					var obtnLogin=document.getElementById("submit_btn")
					obtnLogin.focus();
				}
			}
		}

    	$(function(){
			//提交表单
			$('#submit_btn').click(function(){
				show_loading();
				var myReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; //邮件正则
				if($('#account').val() == ''){
					show_err_msg('账号还没填呢！');	
					$('#account').focus();
				}else if($('#accountPassWord').val() == ''){
					show_err_msg('密码还没填呢！');
					$('#accountPassWord').focus();
				}else{
					$('#login_form').submit();
					//ajax提交表单，#login_form为表单的ID。 如：$('#login_form').ajaxSubmit(function(data) { ... });
					show_msg('登录中..！  正在为您跳转...','');	
					
				}
			});
		});