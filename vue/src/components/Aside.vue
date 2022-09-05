<template id="aside">
  <el-menu :default-openeds="['']" style="min-height: 100%;min-width: 200px; overflow-x: hidden;"
           background-color="rgb(48, 65, 86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; right: 5px">
      <b style="color: white" v-show="logoTextShow">后台管理系统</b>
    </div>
    <el-menu-item index="/home">
      <template slot="title">
        <i class="el-icon-house"></i>
        <span slot="title">主页</span>
      </template>
    </el-menu-item>
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-s-operation"></i>
        <span slot="title">在线考试</span>
      </template>
      <el-menu-item index="/onlineexamination">
        <i class="el-icon-s-promotion"></i>
        <span slot="title">在线考试</span>
      </el-menu-item>
    </el-submenu>

    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">系统管理</span>
      </template>
      <el-menu-item index="/examine">
        <i class="el-icon-s-check"></i>
        <span slot="title">用户审核</span>
      </el-menu-item>
      <el-menu-item index="/user">
        <i class="el-icon-s-custom"></i>
        <span slot="title">用户管理</span>
      </el-menu-item>
    </el-submenu>

    <el-submenu index="3">
      <template slot="title">
        <i class="el-icon-s-help"></i>
        <span slot="title">考试管理</span>
      </template>
      <el-menu-item index="/questionbank">
        <i class="el-icon-collection"></i>
        <span slot="title">题库管理</span>
      </el-menu-item>
      <el-menu-item index="/testquestions">
        <i class="el-icon-notebook-2"></i>
        <span slot="title">试题管理</span>
      </el-menu-item>

    </el-submenu>

    <el-submenu index="4">
      <template slot="title">
        <i class="el-icon-mobile"></i>
        <span slot="title">视频管理</span>
      </template>
      <el-menu-item index="/videoindex">
        <i class="el-icon-mobile"></i>
        <span slot="title">视频列表</span>
      </el-menu-item>
      <el-menu-item index="/videoinsert">
        <i class="el-icon-folder-add"></i>
        <span slot="title">视频添加</span>
      </el-menu-item>
    </el-submenu>
    <el-submenu index="5">
      <template slot="title">
        <i class="el-icon-question"></i>
        <span slot="title">考试详情</span>
      </template>
      <el-menu-item index="ExaminationRecord">
        <i class="el-icon-edit-outline"></i>
        <span slot="title">考试记录</span>
      </el-menu-item>
      <el-menu-item index="Passlist">
        <i class="el-icon-document"></i>
        <span slot="title">通过名单</span>
      </el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  }
}
</script>

<style>
#aside{
  overflow-y: scroll;
}
</style>