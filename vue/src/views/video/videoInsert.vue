<template>
  <div>
    <el-card style="width:800px;padding-left: 70px;">
      <el-form>
        <el-form-item label="视频名称" label-width="80px">
          <el-input
              style="width:300px"
              v-model="name"
              placeholder="请输入视频名称"
          ></el-input>
        </el-form-item>
      </el-form>
      <el-form>
        <el-form-item label="视频详情" label-width="80px">
          <el-input
              style="width:300px"
              v-model="content"
              placeholder="请输入视频内容"
          ></el-input>
        </el-form-item>
      </el-form>
      <div style="margin:10px">
        <el-upload
            class="upload-demo"
            action="#"
            accept=".mkv,.mp4"
            :auto-upload="false"
            :on-change="handleFileChange"
            :show-file-list="false"
        >
          <el-button type="primary">点击上传视频</el-button>
          <div slot="tip" class="el-upload__tip">请上传[mkv,mp4]格式文件，最大1GB</div>
        </el-upload>
      </div>
    </el-card>

    <!-- 进度显示 -->
    <el-progress
        v-if="uploadProgress > 0"
        :percentage="uploadProgress"
        :status="uploadStatus"
        style="width: 300px; margin-top: 10px;"
    ></el-progress>
  </div>
</template>

<script>
import OSS from 'ali-oss';

export default {
  data() {
    return {
      name: "",
      content: "",
      file: null,
      uploadProgress: 0,       // 新增进度属性
      uploadStatus: undefined,  // 新增状态属性
      fileUrl:""
    };
  },
  methods: {
    // 文件选择处理
    handleFileChange(file) {
      this.file = file.raw;
      this.uploadToOSS();
    },

    // 获取STS凭证
    async getStsToken() {
      try {
        const res = await this.request({
          url: "/api/sts-token",
          method: "get"
        });
        return res;
      } catch (error) {
        this.$message.error("获取上传凭证失败");
        throw error;
      }
    },

    // 上传到OSS
    async uploadToOSS() {
      // 重置进度状态
      this.uploadProgress = 0;
      this.uploadStatus = undefined;

      // 验证表单
      if (!this.name.trim() || !this.content.trim()) {
        this.$message.error("请填写视频名称和内容");
        return;
      }

      if (!this.file) {
        this.$message.error("请先选择视频文件");
        return;
      }

      // 增强文件验证
      const validExtensions = ['.mp4', '.mkv'];
      const maxSizeMB = 1000;
      const fileExtension = this.file.name.slice(this.file.name.lastIndexOf('.')).toLowerCase();

      if (!validExtensions.includes(fileExtension)) {
        this.$message.error("只能上传MP4或MKV格式的视频");
        return;
      }

      if (this.file.size > maxSizeMB * 1024 * 1024) {
        this.$message.error(`视频大小不能超过${maxSizeMB}MB`);
        return;
      }
      console.log(this.name,this.file,this.content)

      try {
        const credentials = await this.getStsToken();
        const client = new OSS({
          region: 'cn-beijing',
          accessKeyId: credentials.AccessKeyId,
          accessKeySecret: credentials.AccessKeySecret,
          stsToken: credentials.SecurityToken,
          bucket: 'xionglei-bucket',
          // 关键修复：添加正确的endpoint格式
          endpoint: 'https://oss-cn-beijing.aliyuncs.com',
          // 强制使用HTTPS
          secure: true
        });

        // 生成唯一文件名
        const fileName = `${Date.now()}_${Math.random().toString(36).slice(2)}_${this.file.name}`;

        // 分片上传
        const result = await client.multipartUpload(fileName, this.file, {
          progress: (p) => {
            this.uploadProgress = Math.round(p * 100);
          },
        });
        // 上传成功处理
        this.fileUrl = "https://xionglei-bucket.oss-cn-beijing.aliyuncs.com/"+fileName
        // 1. 首先上传文件信息到后端
        this.uploadStatus = 'success';
        const saveRes = await this.request({
          url: "/resources/upFileName",
          method: "post",
          data: {
            fileUrl: this.fileUrl,
            fileName: this.name,
            content: this.content,
            objectKey: fileName
          },
          timeout: 5000
        });
        // https://xionglei-bucket.oss-cn-beijing.aliyuncs.com/1753167009831_rfzn83o9ht_9b0c0c95342b0d3c1b5990c10fb9acc7.mp4
        console.log(saveRes)
        if (saveRes.code === 200) {
          this.$message.success("视频上传成功");
          this.resetForm();
        } else {
          this.$message.error("视频信息保存失败");
        }
      } catch (error) {
        console.error("上传失败:", error);
        this.uploadStatus = 'exception';
        this.$message.error("视频上传失败: " + (error.message || '未知错误'));
      }
    },

    // 重置表单
    resetForm() {
      this.name = "";
      this.content = "";
      this.file = null;
      setTimeout(() => {
        this.uploadProgress = 0;
        this.uploadStatus = undefined;
      }, 1500);
    }
  }
};
</script>

<style scoped>
.upload-demo {
  margin-top: 20px;
}
</style>