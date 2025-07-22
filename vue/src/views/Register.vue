<template>
  <div class="wrapper">
    <div
        style="
        margin: 50px auto;
        background-color: #fff;
        width: 350px;
        height: 620px;
        padding: 10px;
        border-radius: 10px;
      "
    >
      <div style="margin:0; text-align: center; font-size: 24px">
        <b>注 册</b>
      </div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input
              placeholder="请输入账号"
              size="medium"
              style="margin: 1px 0"
              prefix-icon="el-icon-user"
              v-model="user.username"
              maxlength="10"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              placeholder="请输入密码"
              size="medium"
              style="margin: 1px 0"
              prefix-icon="el-icon-lock"
              show-password
              v-model="user.password"
              maxlength="20"
          ></el-input>
        </el-form-item>
        <el-form-item prop="confirm">
          <el-input
              placeholder="请确认密码"
              size="medium"
              style="margin: 1px 0"
              prefix-icon="el-icon-lock"
              show-password
              v-model="user.confirm"
              maxlength="20"
          ></el-input>
        </el-form-item>

        <el-form-item prop="invite">
          <el-input
              placeholder="请输入邀请码"
              size="medium"
              style="margin: 1px 0"
              prefix-icon="el-icon-info"
              v-model="user.invite"
              maxlength="11"
          ></el-input>
        </el-form-item>
        <el-form-item prop="name">
          <el-input
              style="margin: 1px 0"
              placeholder="请输入姓名"
              size="medium"
              prefix-icon="el-icon-question"
              v-model="user.name"
              maxlength="11"
          ></el-input>
        </el-form-item>

        <el-form-item prop="phone">
          <el-input
              style="margin: 1px 0"
              placeholder="请输入手机号码"
              size="medium"
              maxlength="11"
              prefix-icon="el-icon-question"
              v-model="user.phone"
          ></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="major" required style="margin-top: 15px">
          <el-select value v-model="user.major">
            <el-option :value="m.id" :label="m.major" v-for="m in majors"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="gender" required style="margin-top: 15px">
          <el-select value v-model="user.gender">
            <el-option value="1" label="男"></el-option>
            <el-option value="0" label="女"></el-option>
          </el-select>
        </el-form-item>
        <div style="margin: 2px 0">
          <el-upload
              class="upload-demo"
              action='#'
              :before-upload="handlePreview"
              multiple
              accept="image/*"
              :show-file-list="false"
          >
            <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
            <el-image :key="url" :src="url" style="width: 100px; height: 100px" fit="fill">
            </el-image>
          </el-upload>
        </div>

        <el-form-item style="text-align: right">

          <el-button
              type="primary"
              size="small"
              autocomplete="off"
              @click="login"
          >注册
          </el-button
          >
          <el-button
              type="warning"
              size="small"
              autocomplete="off"
              @click="$router.push('/login')"
          >返回登录
          </el-button
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
      majors: [],
      files: [],
      url: "http://127.0.0.1:9090/static/dgpz.jpg",
      user: {},
      rules: {
        username: [
          {required: true, message: "请输入用户名", trigger: "blur"},
          {min: 3, max: 10, message: "长度在 3 到 10 个字符", trigger: "blur"},
        ],
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
          {
            min: 6,
            max: 20,
            message: "长度在 6 到 20 个字符",
            trigger: "blur",
          },
        ],
        confirm: [
          {required: true, message: "请输入密码", trigger: "blur"},
          {
            min: 1,
            max: 20,
            message: "长度在 1 到 20 个字符",
            trigger: "blur",
          },
        ],
        invite: [
          {required: true, message: "请输入邀请码", trigger: "blur"},
          {
            max: 11,
            message: "邀请码长度不超过11",
          },
        ],
        name: [
          {required: true, message: "请输入姓名", trigger: "blur"},
          {
            max: 11,
            message: "姓名长度不超过11",
          },
        ],
        phone: [
          {required: true, message: "请输入邀请码", trigger: "blur"},
          {
            min: 11,
            max: 11,
            message: "请输入正确的手机号",
            trigger: "blur",
            pattern: "^[1][3-9][0-9]{9}$"
          },
        ],
        major: [{required: true, message: "请选择专业", trigger: "blur"},],
        gender: [{required: true, message: "请选择性别", trigger: "blur"},]
      },
    };
  },
  created() {
    this.initMajor()
  },
  methods: {
    initMajor() {
      this.request.get("/major/allMajor").then(res => {
        if (res.code === 200) {
          this.majors = res.data;
        } else {
          this.$message.error(res.msg)
        }

      })
    },
    handlePreview(file) {
      this.files[0] = file;
      let form = new FormData();
      form.append("file", this.files[0]);
      this.request.post("/resources/image", form).then(res => {
        this.url = res.data;
      })
    },
    init() {
      // 获取短信验证码
      // 操作
      // this.imgUrl =
      //   "http://www.wanghun.top/img/7c0063948b2fce5787fe356a8a69e0f7.jpg";
    },
    login() {
      if (this.files.length < 1) {
        this.$message.error("请您上传您的学生证")
        return;
      }
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          // 表单校验合法
          if (this.user.password !== this.user.confirm) {
            this.$message.error("两次输入的密码不一致");
            return false;
          }
          let form = new FormData();
          form.append("username", this.user.username)
          form.append("password", this.user.password)
          form.append("confirm", this.user.confirm)
          form.append("invite", this.user.invite)
          form.append("name", this.user.name)
          form.append("phone", this.user.phone)
          form.append("major", this.user.major)
          form.append("gender", this.user.gender)
          form.append("file", this.files[0])
          this.request.post("/user/register", form).then((res) => {
            console.log(res)
            if (res.code === 200) {
              this.$message.success(res.msg);
              this.$router.push("/login")
            }
          }).catch(e => {
            console.log(e)
            if (e.response.data.code !== 200) {
              this.$message.error(e.response.data.msg)
            }
          })
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