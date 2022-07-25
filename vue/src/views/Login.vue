<template>
    <body id="poster">
        <el-form class="login-container" label-position="left"  label-width="0px">
            <h3 class="login_title">
                系统登陆
                <el-button   @click="toRegister()">点我注册</el-button>
            </h3>
          <div class="alert alert-danger d-none" v-if="search.error">
            <i class="fa fa-exclamation-triangle"></i> 账号或密码错误
          </div>
          <div class="alert alert-info d-none" v-if="search.logout">
            <i class="fa fa-exclamation-triangle"></i> 已经登出系统
          </div>
          <div class="alert alert-info d-none" v-if="search.register">
            <i class="fa fa-exclamation-triangle"></i> 已经成功注册，请登录。
          </div>
            <el-form-item label="">
                <el-input  type="text" v-model="loginForm.username" autocomplete="off" placeholder="账号"></el-input>
            </el-form-item>
                       <el-form-item label="">
                <el-input type="password" v-model="loginForm.password" autocomplete="off" placeholder="密码"></el-input>
            </el-form-item>
            <el-form-item style="width:100%;">
                <el-button type="primary" style="width:100%;background:#505458;border:none" v-on:click="Login()">登陆</el-button>
            </el-form-item>
        </el-form>
    </body>
</template>

<script>
  $("#poster").search.error = location.search === "?error";
  $("#poster").search.logout = location.search === "?logout";
  $("#poster").search.register = location.search === "?register";
export default {
  name: 'Login',
  data() {
      return {
        search:{
          error:false,
          logout:false,
          register:false
        },
        loginForm: {
          username: '',
          password: ''
        }
      }
    },
    methods: {
      Login() {
        // console.log('submit!',this.loginForm);
            let username = this.loginForm.username;
            let password = this.loginForm.password;
            this.axios.post("http://localhost:9090/login?username="+username+"&password="+password).then((resp)=>{
                let data = resp.data;
                if(data.success){
                    this.loginForm= {};
                    this.$message({
                    message: '欢迎光临后台管理系统!!!',
                    type: 'success'
                    });
                    this.$router.push({path:'/Home'})
                }
            })

      },
      toRegister(){
        this.$router.push({path:'/Register'})
      }
    }

}
</script>
<style>
    #poster{
        background-position:center;
        height:100%;
        width:100%;
        background-size:cover;
        position:fixed;
    }
    body{
        margin:0px;
        padding:0px;
    }
    .login-container{
        border-radius:15px;
        background-clip:padding-box;
        margin:90px auto;
        width:350px;
        padding: 35px 35px 15px 35px;
        background:#fff;
        border:1px solid #eaeaea;
        box-shadow:0 0 25px #cac6c6;
    }
    .login_title{
        margin : 0px auto 40px auto;
        text-align:center;
        color:#505458;
    }
</style>