<template>
  <div class="wrapper">
    <div
        style="
        margin: 200px auto;
        background-color: #fff;
        width: 350px;
        height: 360px;
        padding: 20px;
        border-radius: 10px;
      "
    >
      <div style="margin: 20px 0; text-align: center; font-size: 24px">
        <b>登 录</b>
      </div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input
              size="medium"
              style="margin: 10px 0"
              prefix-icon="el-icon-user"
              v-model="user.username"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              size="medium"
              style="margin: 10px 0"
              prefix-icon="el-icon-lock"
              show-password
              v-model="user.password"
          ></el-input>
        </el-form-item>
        <el-row :span="24">
          <el-col :span="14">
            <el-form-item>
              <el-input
                  placeholder="请输入验证码"
                  size="medium"
                  style="margin: 5px 0"
                  prefix-icon="el-icon-question"
                  v-model="user.randCode"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6" style="margin-left: 10px">
            <img id="CreateCheckCode" :src="imageUrl"  @click="Reload()" width="100px" height="46px" alt="" />
            <!-- <el-button @click="init()">获取短信</el-button> -->
          </el-col>
        </el-row>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button
              type="primary"
              size="small"
              autocomplete="off"
              @click="login"
          >登录</el-button
          >
          <el-button
              type="warning"
              size="small"
              @click="$router.push('/register')"
              autocomplete="off"
          >注册</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import user from "./User"
import qs from 'qs'
const API_BASE_URL = 'http://s764c7d7.natappfree.cc'
export default {
  name: "Login",
  data() {


    return {
      imageUrl: "",
      user: {},
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 2, max: 10, message: "长度在 2 到 10 个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 12,
            message: "长度在 6 到 12 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      const codeId = Math.random();
      localStorage.setItem("codeId", codeId);
      this.imageUrl = `/api/checkCode?codeId=${codeId}`; // 使用代理路径
    },
    Reload() {
      const codeId = Math.random();
      localStorage.setItem("codeId", codeId);
      document.getElementById("CreateCheckCode").src = `/api/checkCode?codeId=${codeId}`;
    },
    login() {
      localStorage.removeItem("token")
      this.user.id = localStorage.getItem("codeId");
        this.$refs["userForm"].validate((valid) => {
          if (valid) {
            // 表单校验合法
            this.request.post("/login?"+qs.stringify(this.user)).then((response) => {
              if (response.code === 200) {
                localStorage.setItem("user", JSON.stringify(response.data)); //存储用户信息到浏览器
                const jwt = response.data.password;
                localStorage.setItem("token", jwt)
                setTimeout(()=>{
                  localStorage.removeItem("token")
                  localStorage.removeItem("user")
                },1000*86400)
                this.$router.push("/");
                this.$message.success("登陆成功");
              } else {
                this.$message.error(response.msg);
              }
            });
          }
        });
    },
  },
};
</script>

<style>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #fc466b, #3f5efb);
  overflow: hidden;
}
</style>