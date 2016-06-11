<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户登录</title>
	<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
    <style>
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #eee;
        }
    </style>
</head>
<body>

	<form class="form-horizontal container form-actions" method="post" action="loginSuccess">
        <h2 class="text-info page-header text-center">登录</h2>

        <div class="control-group">
            <div class="control-label">
                <label for="loginEmail">用户邮箱</label>
            </div>
            <div class="controls">
                <input type="email" id="loginEmail" name="email" required >
            </div>
        </div>
        <div class="control-group">
            <div class="control-label">
                <label for="passWord">密码</label>
            </div>
            <div class="controls">
                <input type="password" id="passWord" name="passWord" required>
                ${info }
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button class="btn btn-info" type="submit">登录</button>
                <a class="help-inline" href="reg">我还没有有账号，现在立即注册</a>
            </div>
        </div>
        <hr/>

    </form>


    <script src="js/jquery-1.9.1.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
    <script src="js/user.js" type="text/javascript"></script>
    
    <script type="text/javascript">
    	$("#keepLogin").change(
    		function(){
    			if($("#keepLogin").is("check")){
    				<% session.setMaxInactiveInterval(604800); %>
    			}
    		}		
    	);
    </script>

	

</body>
</html>