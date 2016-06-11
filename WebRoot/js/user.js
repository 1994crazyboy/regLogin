/**
 * Created by Administrator on 2016/5/31 0031.
 */

//input改变的时候出发ajax请求或者检查两次密码是否相同
$("input").change(
    function(){
        //设置发送ajax请求的部分参数
        var ajaxType="GET";
        var url=null;
        var data=null;

        //获取选中元素的id
        var id=$(this).attr("id");

        //移除提示信息的元素
        $(this).nextAll("span").remove();
        
        //判断选中元素的id
        if(id=="loginEmail"){
            //设置发送请求的ajax参数            
            url="check";
            data={
              "email":$(this).val()
            };

            //发送ajax请求
            $.ajax({
            	type:ajaxType,
                url:url,
                data:data,
                contentType:"application/json;charset=utf-8",
                success:function(result){
                	//如果当前的是登录
                	if(result==0){
               		 	$("#loginEmail").after("<span class='help-inline' style='color: #ff0000'>该邮箱还未注册，请先注册！</span>");
               		 	$("#loginEmail").select();
                	}
                }
            });


        }else if(id=="regEmail"){
            //设置发送请求的ajax参数            
            url="check";
            data={
              "email":$(this).val()
            };

            //发送ajax请求
            $.ajax({
            	type:ajaxType,
                url:url,
                data:data,
                contentType:"application/json;charset=utf-8",
                success:function(result){
                	//如果当前的是注册
                	if(result==1){
                		 $("#regEmail").after("<span class='help-inline' style='color: #ff0000'>邮箱已被注册！</span>");
                		 $("#regEmail").select();
                	}else{
                		$("#regEmail").after("<span class='help-inline' style='color: #00ff00'>恭喜，邮箱可用！</span>");
                	}
                }
            });
        	
        }else if(id=="userName"){
            //设置发送请求的ajax参数
            url="check";
            data={
                "userName":$(this).val()
            };

            //发送ajax请求
            $.ajax({
                type:ajaxType,
                url:url,
                data:data,
                contentType:"application/json;charset=utf-8",
                success:function(result){
                	if(result==1){
               		 	$("#userName").after("<span class='help-inline' style='color: #ff0000'>当前用户已经存在！</span>");
               		 	$("#userName").select();
                	}else{
                		$("#userName").after("<span class='help-inline' style='color: #00ff00'>恭喜，用户名可用！</span>");
                	}
                }
            });

        }else if(id=="captcha"){
            //设置发送请求的ajax参数
            url="checkCaptcha";
            data={
                "captcha":$(this).val()
            };

            //发送ajax请求
            $.ajax({
                type:ajaxType,
                url:url,
                data:data,
                contentType:"application/json;charset=utf-8",
                success:function(result){
                	if(result==1){
                		$("#captcha").after("<span class='help-inline' style='color: #00ff00'>验证码正确！</span>");
                	}else{
                		$("#captcha").after("<span class='help-inline' style='color: #ff0000'>验证码不正确！</span>");
                		$("#captcha").select();
                	}
                }
            });

        }else if(id=="rePassWord"){
            //如果是重复输入密码，那么检查两次输入是否相同
            if($("#passWord").val()!=$(this).val()){
                $(this).after("<span class='help-inline' style='color: #ff0000'>两次输入不相同！</span>");
            }

        }
});

/**
 * 发送邮件验证码
 */
$(".btnSendEmail").click(function() {
	setTime(this);
	var data={
		"email":$("#inputEmail").val()
	};
	$.ajax({
		type:"GET",
        url:"sendCaptchaEmail",
        data:data,
        contentType:"application/json;charset=utf-8",
	});
});

/**
 * 检测用户输入的验证码是否正确
 */
$("#captchaEmail").change(function(){
	 //移除提示信息的元素
    $(this).nextAll("span").remove();
    
	var data={
		"captcha":$(this).val(),
		"email":$("#inputEmail").val()			
	};
	$.ajax({
		type:"GET",
        url:"checkEmailCaptcha",
        data:data,
        contentType:"application/json;charset=utf-8",
        success:function(result){
        	if(result==1){
        		$("#captchaEmail").after("<span class='help-inline' style='color: #00ff00'>验证码输入正确！</span>");
        	}else if(result==-1){
        		$("#captchaEmail").after("<span class='help-inline' style='color: #ff0000'>验证码错误！</span>");
        	}else if(result==0){
        		$("#captchaEmail").after("<span class='help-inline' style='color: #ff0000'>验证码已过期！</span>");
        	}
        }		
	});
	
});

/**
 *激活用户
 */
$(".activateUser").click(function(){
	var data={
		"email":$("#inputEmail").val(),
		"captcha":$("#captchaEmail").val()
				
	};
	//发送ajax请求
    $.ajax({
        type:"GET",
        url:"activateUser",
        data:data,
        contentType:"application/json;charset=utf-8",
        success:function(result){
        	if(result==1){
        		window.location.reload();
        	}else{
        		alert("激活失败！请稍后重试！");
        	}
        }        
    });	
	
});



	




/**
 * 发送手机验证码
 */
$(".btnSendPhone").click(function() {
	setTime(this);
});

/**
 * 设置按钮的60秒发送
 */
var countdown = 60;
function setTime(obj) {
	if (countdown == 0) {
		obj.removeAttribute("disabled");
		obj.value = "免费发送验证码";
		countdown = 60;
		return;
	} else {
		obj.setAttribute("disabled", true);
		obj.value = "重新发送(" + countdown + ")";
		countdown--;
	}
	setTimeout(function() {
		setTime(obj)
	}, 1000)
}

/**
 * 显示用户信息
 */
function showUser () {
    $("#regLogin").toggle();
    $("#user").toggle();
}

/**
 * 如果确认注销用户
 */
$("#loginOffOK").click(function(){
	//发送ajax请求
    $.ajax({
        type:"GET",
        url:"loginOff",
        data:null,
        contentType:"application/json;charset=utf-8",
        success:function(result){
        	if(result==1){
        		window.location.reload();
        	}
        }        
    });	
	
});






