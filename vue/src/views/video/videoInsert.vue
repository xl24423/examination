<template>
  <div>
    <el-card style="width:800px;padding-left: 70px;">
      <el-form>
        <el-form-item label="视频名称" label-width="80px">
          <el-input style="width:300px" v-model="name" placeholder="请输入视频名称"></el-input>
        </el-form-item>
      </el-form>
      <el-form>
        <el-form-item label="视频详情" label-width="80px">
          <el-input style="width:300px" v-model="content" placeholder="请输入视频内容"></el-input>
        </el-form-item>
      </el-form>
      <div style="margin:10px">
        <el-upload
            class="upload-demo"
            action="#"
            accept="video/x-matroska,video/mp4"
            :auto-upload="false"
            :on-change="uploadFile"
            :show-file-list="false"

        >
          <el-button type="primary">点击上传视频</el-button>
          <div slot="tip" class="el-upload__tip">请上传[mkv,mp4]格式文件</div>
        </el-upload>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: "",
      content:""
    }
  },
  methods:{
    uploadFile (item) {
      let formData = new FormData()
      let file = item.raw
      let videoName = this.name;
      console.log(videoName)
      let content = this.content;
      if (videoName === "" || content === "") {
        this.$message.error("请填写视频名称以及视频内容")
        return;
      }
      const isLt1M = file.size / 1024 / 1024 < 1000;
      if (!(file.type === 'video/x-matroska' || file.type === 'video/mp4')) {
        this.$message.error("只能上传[mkv,mp4]格式的文件")
        return;
      }
      if (!isLt1M) {
        this.$message.error('上传文件大小不能超过 1GB!');
        return;
      }
      formData.append('file', file)
      formData.append('videoName', videoName)
      formData.append('content', content)
      this.request({
        url: "/resources/upFile", //后端提供的接口
        method: 'post',
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(data => {
        console.log(data)
        if (data.code === 200) {
          this.$message.success(data.msg);
          this.name = ""
          this.content = ""
        } else {
          this.$message.error(data.msg)
        }
      })
    }
    },
};
</script>
