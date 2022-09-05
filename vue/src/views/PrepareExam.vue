<template>
  <div>
    <el-row>
      <el-tag
          type="danger"
          :closable="true"
          style="width: 100%; font-size: 16px; height: 40px; line-height: 40px"
      >
        <span style="display: inline-block; width: 96%"
        >点击`开始考试`后将自动进入考试，请诚信考试！</span
        >
      </el-tag>
    </el-row>
    <el-card style="margin-top: 20px">
      <div class="form_style">
        <el-row>
          出题人:
          <span>{{ form.admin }}</span>
        </el-row>
        <el-row>
          考试名称:
          <span>{{ form.name }}</span>
        </el-row>
        <el-row>
          考试时长:
          <span>{{ form.time }}</span>
        </el-row>
        <el-row>
          试卷总分:
          <span>{{ form.count }}</span>
        </el-row>
        <el-row>
          及格分数:
          <span>{{ form.pass }}</span>
        </el-row>
        <el-row>
          考试描述:
          <span>点击`开始考试`后将自动进入考试，请诚信考试!</span>
        </el-row>
      </div>
    </el-card>
    <div style="margin: 20px 0px">
      <el-button style="padding: 10px 20px; font-size: 22px" type="primary"
                 @click="$router.push('/startexam/'+form.id)"
      ><i class="el-icon-position"></i> 开始考试
      </el-button
      >
      <el-button style="padding: 10px 20px; font-size: 22px" @click="$router.push('/onlineexamination')">返回</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {},
    };
  },
  methods: {
    init() {
      let id = location.pathname.split("/")[2];
      this.request.post("/exam/deleteExam?id="+id)
      this.request.get("/questionBank/selectOne", {
        params: {
          id: id
        }
      }).then(res => {
          this.form = res.data
      })
    }
  },
  created() {
      this.init();
  }
};
</script>
<style>
.form_style > .el-row {
  line-height: 42px;
  font-size: 22px;
  font-weight: 550;
}

.form_style > .el-row > span {
  display: inline-block;
  margin-left: 10px;
  color: #666;
  font-weight: normal;
}
</style>