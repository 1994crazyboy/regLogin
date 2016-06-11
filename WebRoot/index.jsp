<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>欢迎光临</title>
	<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #eee;
        }
    </style>
    
   

</head>
<body>

	<div class="container">

        <!-- 跳转登录注册页面 -->
        <div id="regLogin" class="row clearfix">
            <a href="login">
                <button id="login" class="btn btn-large btn-info">现在登录</button>
            </a>
            <a href="reg">
                <button id="reg" class="btn btn-large btn-info">现在注册</button>
            </a>
        </div>

        <!-- 用户信息div -->
        <div class="row clearfix hide" id="user">
            <!--用户头像div-->
            <div class="span3">
                <img class="img-circle img-polaroid" style="height: 100px;width: 100px" src="img/tx1.jpg" alt="用户头像"><br>
                <a data-toggle="modal" data-backdrop="false" data-keyboard="false"  data-target="#headModal">更改用户头像</a>
            </div>

            <!-- 用户信息 -->
            <div class="span8">
                <table class="table tab-content">
                    <tr>
                        <td><span>用户名：</span></td>
                        <td><span>${userName}</span></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><span>邮箱：</span></td>
                        <td><span>${email}</span></td>
                        <td>
                        <c:if test="${status==0 }">
                        	<a class="text-warning" data-toggle="modal" data-backdrop="false" data-keyboard="false"  data-target="#modifyEmailModal">您还未验证邮箱，现在立即验证！</a>
                        </c:if>                        
                        </td>
                    </tr>
                    <tr>
                        <td><span>手机：</span></td>
                        <td><span>${phone}</span></td>
                        <td>
                        <c:if test="${phone==null }">
                        	<a class="text-warning" data-toggle="modal" data-backdrop="false" data-keyboard="false"  data-target="#modifyPhoneModal">您未绑定手机，现在立即绑定！</a>
                        </c:if>
                        <c:if test="${phone!=null }">
                        	<a class="text-info" data-toggle="modal" data-backdrop="false" data-keyboard="false"  data-target="#modifyPhoneModal">更换手机号码</a>
                        </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td><span>注册时间：</span></td>
                        <td><span>${regTime}</span></td>
                        <td></td>
                    </tr>
                </table>
                <a data-toggle="modal" data-backdrop="false" data-keyboard="false"  data-target="#exit">退出用户</a>
            </div>

            <!-- 上传头像Modal -->
            <div id="headModal" class="modal hide fade">
                <div class="modal-header">
                    <button class="close" data-dismiss="modal">×</button>
                    <h4>修改头像</h4>
                </div>
                <div class="modal-body">
                    <p>允许的图片类型JPG,JPEG,GIF或PNG，最大不超过2M<br />使用高质量图片，可以生成高清头像 。</p>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-info" data-dismiss="modal">保存</button>
                    <button class="btn btn-info" data-dismiss="modal">取消</button>
                </div>
            </div>

            <!-- 绑定手机的Modal -->
            <div id="modifyPhoneModal" class="modal hide fade">
                <div class="modal-header">
                    <button class="close" data-dismiss="modal">×</button>
                    <h4>绑定手机号码</h4>
                </div>
                <div class="modal-body">
                    <div class="input-prepend input-append">
                        <label for="newPhone">输入手机号码：</label>
                        <input id="newPhone" type="text" placeholder="输入手机号码" required  />
                        <input type="button" class="btn btnSendPhone" value="免费发送验证码"/>
                    </div>
                    <br><label for="captchaPhone">输入验证码</label>
                    <input id="captchaPhone" type="text" placeholder="输入邮箱验证码" required />
                </div>
                <div class="modal-footer">
                    <button class="btn btn-info" data-dismiss="modal">保存</button>
                    <button class="btn btn-info" data-dismiss="modal">取消</button>
                </div>
            </div>

            <!-- 绑定邮箱的Modal -->
           	<div id="modifyEmailModal" class="modal hide fade">
               <div class="modal-header">
                   <button class="close" data-dismiss="modal">×</button>
                   <h4>激活邮箱 </h4>
               </div>
               <div class="modal-body">
                   <div class="input-prepend input-append">
                   	<%-- 如果还没有激活邮箱，那么提示输入验证码激活 --%>
                   	<input id="inputEmail" name="email" type="email" value="${email }" disabled="disabled" placeholder="输入新邮箱地址" required  />
                       <input type="button" class="btn btnSendEmail" value="免费发送验证码"/>
                   </div>
                   <br><label for="captchaEmail">输入验证码</label>
                   <input id="captchaEmail" name="captcha" type="text" placeholder="输入邮箱验证码" required />
               </div>
                <div class="modal-footer">	                
                    <button class="btn btn-info activateUser" data-dismiss="modal">确定</button>
                    <button class="btn btn-info" data-dismiss="modal">取消</button>
                </div>
           	</div>
            

            <!-- 退出确认的Modal -->
            <div id="exit" class="modal hide fade">
                <div class="modal-header">
                    <button class="close" data-dismiss="modal">×</button>
                    <h4>注销用户</h4>
                </div>
                <div class="modal-body">
                    <p>确认注销么？</p>
                </div>
                <div class="modal-footer">
                    <button id="loginOffOK" class="btn btn-info" data-dismiss="modal">确定</button>
                    <button class="btn btn-info" data-dismiss="modal">取消</button>
                </div>
            </div>
            
        </div>
    </div>
    

	<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/user.js" type="text/javascript"></script>
    <c:if test="${status!=null }">
    	<script type="text/javascript">
    		showUser();
    	</script>
    </c:if>
    

</body>
</html>