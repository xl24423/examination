<template>
  <div class="wrapper">
    <div
      style="
        margin: 100px auto;
        background-color: #fff;
        width: 350px;
        height: 500px;
        padding: 20px;
        border-radius: 10px;
      "
    >
      <div style="margin: 20px 0; text-align: center; font-size: 24px">
        <b>注 册</b>
      </div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input
            placeholder="请输入账号"
            size="medium"
            style="margin: 5px 0"
            prefix-icon="el-icon-user"
            v-model="user.username"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            placeholder="请输入密码"
            size="medium"
            style="margin: 5px 0"
            prefix-icon="el-icon-lock"
            show-password
            v-model="user.password"
          ></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input
            placeholder="请确认密码"
            size="medium"
            style="margin: 5px 0"
            prefix-icon="el-icon-lock"
            show-password
            v-model="user.confirmPassword"
          ></el-input>
        </el-form-item>

        <!-- <el-row :span="24">
          <el-col :span="14">
            <el-form-item>
              <el-input
                placeholder="请输入验证码"
                size="medium"
                style="margin: 5px 0"
                prefix-icon="el-icon-question"
                v-model="user.verificationCode"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6" style="margin-left: 10px">
            <img @click="init" :src="imgUrl" width="100px" height="46px" alt="" />
          </el-col>
        </el-row> -->

        
        <el-form-item prop="InvitationCode">
          <el-input
            placeholder="请输入邀请码"
            size="medium"
            style="margin: 5px 0"
            prefix-icon="el-icon-info"
            v-model="user.InvitationCode"
          ></el-input>
        </el-form-item>
                <div
          style="
            display: flex;
            justify-content: space-around;
            align-items: center;
            height: 36px;
            margin-bottom: 20px;
          "
        >

          <el-form-item style="margin-top: 18px">
            <el-input
              placeholder="请输入手机号码"
              size="medium"

              prefix-icon="el-icon-question"
              v-model="user.phone"
            ></el-input>
          </el-form-item>
          <!-- <img :src="imgUrl" width="100px" height="46px" alt="" /> -->
          <el-button style="margin-left: 5px" @click="init()"
            >获取短信</el-button
          >
        </div>
<el-form-item>
            <el-input
              placeholder="请输入验证码"
              size="medium"

              prefix-icon="el-icon-question"
              v-model="user.verificationCode"
            ></el-input>
          </el-form-item>

        <el-form-item style="margin: 5px 0; text-align: right">
          <el-button
            type="primary"
            size="small"
            autocomplete="off"
            @click="login"
            >注册</el-button
          >
          <el-button
            type="warning"
            size="small"
            autocomplete="off"
            @click="$router.push('/login')"
            >返回登录</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Login",
  data() {
    return {
      imgurl: "",
      user: {},
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 10, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度在 1 到 20 个字符",
            trigger: "blur",
          },
        ],
        confirmPassword: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度在 1 到 20 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
  },
  methods: {
    init() {
      // 获取短信验证码
      // 操作
      // this.imgUrl =
      //   "http://www.wanghun.top/img/7c0063948b2fce5787fe356a8a69e0f7.jpg";
    },
    login() {
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          // 表单校验合法
          if (this.user.password !== this.user.confirmPassword) {
            this.$message.error("两次输入的密码不一致");
            return false;
          }
          this.request.post("/user/register", this.user).then((res) => {
            if (res.code === "200") {
              this.$message.success("注册成功");
            } else {
              this.$message.error(res.msg);
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