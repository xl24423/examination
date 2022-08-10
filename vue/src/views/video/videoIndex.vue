<template>
  <el-card>
    <el-button style="margin:10px" type="primary" @click="$router.push('/videoinsert')"> <i class="el-icon-plus"></i> 添加视频</el-button>
    <el-row>
      <el-col
      style="margin:10px"
        :span="5"
        v-for="(o, index) in 8"
        :key="index"
      >
        <el-card :body-style="{ padding: '0px' }">
          <!-- <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image"> -->
          <video width="320" height="240" controls>
            <source src="https://www.runoob.com/try/demo_source/movie.mp4" type="video/mp4" />
            <source src="https://www.runoob.com/try/demo_source/movie.ogg" type="video/ogg" />
            您的浏览器不支持 video 标签。
          </video>
          <div style="padding: 14px">
            <span>视频名称:{{from.name}}</span>
            <div class="bottom clearfix">
              <time class="time">{{ currentDate }}</time>
              <el-button type="text" class="button">操作按钮</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      currentDate: new Date(),
      from:{name:"视频1"},
    };
  },
};
</script>


<style>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>

