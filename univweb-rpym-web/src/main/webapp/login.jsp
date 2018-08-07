<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="/style/login.css" rel="external nofollow" >
    <script src="/js/jquery.js"></script>
    <script src="/js/vue-resource.js"></script>
    <script src="/js/vue.js"></script>
	<!-- 引入组件库 -->
	<script src="https://unpkg.com/element-ui/lib/index.js"></script>
</head>
<body>
<div class="login-box" id="app" >
 <el-row>
    <el-col :span="8">
        <el-input id="name" v-model="name" placeholder="请输入帐号">
            <template slot="prepend">帐号</template>
        </el-input> 
    </el-col>
 </el-row>
 <el-row>
    <el-col :span="8">
        <el-input id="password" v-model="password" type="password" placeholder="请输入密码">
            <template slot="prepend">密码</template>
        </el-input>
    </el-col>
 </el-row>
 <el-row>
    <el-col :span="8">
        <el-button id="login" v-on:click="check" style="width:100%" type="primary">登录</el-button>
    </el-col>
 </el-row>
</div> 
</body>
<script type="text/javascript">
    new Vue({
        el : '#app',
        data : {
            name : '',
            password : ''
        },
        methods : {
            check : function(event){
                //获取值
                var name = this.name;
                var password = this.password;
                if(name == '' || password == ''){
                    this.$message({
                        message : '账号或密码为空！',
                        type : 'error'
                    })
                    return;
                }
                $.ajax({
                    url : 'http://127.0.0.1:8080/users/login',
                    type : 'post',
                    data : {
                        name : name,
                        password : password
                    },
                    success : function(data) {
                        var result = data.status;
                        if(result == '200' || result == true){
                           	location.href="http://127.0.0.1:8080/home/index";
                        }else {
                            alert("登录失败");
                        }
                    },
                    dataType : 'json',
                })
            }
        }
    })
     
</script>
</html>