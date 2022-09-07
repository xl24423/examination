<template>
  <div>
    <el-card>
      <el-form :model="form" label-width="80px" ref="ruleForm">
        <!-- <el-form-item label="活动名称">
          <el-input v-model="form.name"></el-input>
        </el-form-item> -->
        <el-form-item label="归属题库">
          <el-select placeholder="请选择题库" v-model="form.bankId">
            <el-option
                v-for="item in question_bank"
                :value="item.id"
                :label="item.name"
            >
            </el-option
            >
          </el-select>
        </el-form-item>
        <el-form-item label="题目类型">
          <el-select placeholder="请选择类型" v-model="form.type">
            <el-option label="单选题" value="1"></el-option>
            <el-option label="多选题" value="2"></el-option>
            <el-option label="判断题" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="答案对错" v-if="form.type==='3'">
          <el-select v-model="form.solution">
            <el-option label="对" :value="'true'" ></el-option>
            <el-option label="错" :value="'false'"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="题目内容">
          <el-input type="textarea" v-model="form.content"></el-input>
        </el-form-item>


        <el-form-item label="整题解析">
          <el-input type="textarea" v-model="form.analysis"></el-input>
        </el-form-item>
        <el-form-item label="题目分值">
          <el-input type="textarea" v-model="form.score" style="max-width: 5%"></el-input>
        </el-form-item>
      </el-form>
      <el-upload
          class="upload-demo"
          action='#'
          :before-upload="handlePreview"
          multiple
          accept="image/*"
          :show-file-list="true"
      >
        <el-button size="small" type="primary">上传问题图片</el-button>
        <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->


      </el-upload>
      <div style="padding-top: 20px">
        <el-image :key="urls" :src="urls" style="width: 200px; height: 200px" fit="fill">
        </el-image>
        <span><button @click="delImage">删除</button></span>
      </div>

    </el-card>

    <el-button
        type="primary"
        style="margin: 20px 0"
        @click="add"
        plain
        icon="el-icon-plus"
    >添加
    </el-button
    >

    <div>
      <el-table
          ref="multipleTable"
          :data="tableData"
          tooltip-effect="dark"
          style="width: 100%"
          border
          :cell-style="{ textAlign: 'center' }"
          :header-cell-style="{ textAlign: 'center' }"
      >
        <el-table-column prop="type" label="是否答案" width="120">
          <template slot-scope="scope"
          >
            <el-checkbox v-model="scope.row.type">答案</el-checkbox>
          </template
          >
        </el-table-column>

        <el-table-column label="问题选项" min-width="20%">
          <template slot-scope="scope">
            <el-select v-model="scope.row.option">
              <el-option :value="o.value" :label="o.label" v-for="o in option"></el-option>
            </el-select>
            >
          </template>
        </el-table-column>

        <el-table-column label="问题内容">
          <template slot-scope="scope">
            <el-input
                type="textarea"
                :rows="2"
                placeholder="请输入内容"
                v-model="scope.row.content"
            >
            </el-input
            >
          </template>
        </el-table-column>

        <el-table-column label="操作" width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-button
                type="danger"
                icon="el-icon-delete"
                @click="del(scope.row)"
                circle
            ></el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div style="margin: 20px 0">
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="blue"
          title="您确定保存吗？"
          @confirm="submit()"
      >
        <el-button type="primary" slot="reference" size="small">保存 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-button
          size="small"
          type="info"
          @click="$router.push('/testquestions')"
      >返回
      </el-button
      >
    </div>
  </div>
</template>
<script>


export default {
  data() {
    return {
      id: "",
      files: [],
      urls: "http://127.0.0.1:9090/static/wjz.jpg",
      option: [
        {
          label: "A",
          value: "A",
        }, {
          label: "B",
          value: "B",
        }, {
          label: "C",
          value: "C",
        }, {
          label: "D",
          value: "D",
        }
      ],
      question_bank: [],
      form: {
        bankId: "",
        analysis: "",
        content: "",
        type: "",
        score: "",
        solution: "",
      },
      tableData: [],
    };
  },
  methods: {
    init() {
      this.request.get("/questionBank/getAllQuestionBank").then(res => {
        this.question_bank = res.data;
        console.log(this.question_bank)
        this.id = location.pathname.split("/")[2]
        this.request.get("/question/searchOne?id=" + this.id).then(res => {
          this.$set(this.form, "bankId", Number(res.data.questionBankId))
          this.$set(this.form, "analysis", res.data.questionAnalysis)
          this.$set(this.form, "content", res.data.question)
          this.$set(this.form, "type", res.data.type)
          this.$set(this.form, "score", res.data.score)
          this.$set(this.form, "solution", res.data.solution)

          if (res.data.url !== null) {
            this.$set(this, "urls", res.data.url)
          }
          if (res.data.type === "1" || res.data.type === "2") {
            let A = {};
            A.type = false;
            let B = {};
            B.type = false;
            let C = {};
            C.type = false;
            let D = {};
            D.type = false;
            if (res.data.type === "1") {
              let sp = res.data.solution
              switch (sp) {
                case 'A': {
                  A.type = true;
                }
                  break;
                case 'B': {
                  B.type = true;
                }
                  break;
                case 'C': {
                  C.type = true;
                }
                  break;
                case 'D': {
                  D.type = true;
                }
              }
            } else if (res.data.type === "2") {
              let sps = [];
              sps = res.data.solution.split(",");
              sps.forEach((i) => {
                if (i === "A") {
                  A.type = true;
                }
                if (i === "B") {
                  B.type = true;
                }
                if (i === "C") {
                  C.type = true;
                }
                if (i === "D") {
                  D.type = true;
                }
              })
            }
            A.content = res.data.a;
            A.option = "A"
            B.content = res.data.b;
            B.option = "B"
            C.content = res.data.c;
            C.option = "C"
            D.content = res.data.d;
            D.option = "D"
            if (res.data.a !== "") {
              this.tableData.push(A)
            }
            if (res.data.b !== "") {
              this.tableData.push(B)
            }
            if (res.data.c !== "") {
              this.tableData.push(C)
            }
            if (res.data.d !== "") {
              this.tableData.push(D)
            }
          }
        })

      })
    },
    delImage() {
      this.files = [];
      this.urls = "http://127.0.0.1:9090/static/wjz.jpg"
    },
    handlePreview(file) {
      if (this.files.length < 1) {
        if (file != null) {
          if (file.type != null && file.type.match("image/*")) {
            let form = new FormData();
            form.append("file", file);
            this.request.post("/resources/image", form).then(res => {
              this.files.push(file);
              this.urls = res.data;
            })
          } else {
            this.$message.error("文件只支持上传图片")
            return;
          }
        } else {
          this.$message.error("文件出错")
          return;
        }
        this.$message.success("上传" + file.name + "成功");

      } else {
        this.$message.error("文件限制上传1份")
      }

    },
    add() {
      if (this.tableData.length < 4) {
        this.tableData.push({
          type: false,
          content: "",
          option: "",
        },);
      } else {
        this.$message.error("最多新增4个选项");
      }
    },
    del(item) {
      var index = this.tableData.findIndex((i) => i == item);
      //判断索引值是否有效，当索引值为-1时说明没有符合条件的对象
      if (index != -1) {
        //当符合时，删除，此处的1用于表示删除1个，后续使用可自行测试
        this.tableData.splice(index, 1);
      }
    },
    submit() {
      let tableData = this.tableData;
      let list = [];
      for (let i = 0; i < tableData.length; i++) {
        list.push(tableData[i].option);
      }
      if (this.form.bankId === "") {
        this.$message.error("请选择题库");
        return;
      }
      if (this.form.type === "") {
        this.$message.error("请选择题目类型");
        return;
      }
      if (this.form.content === "") {
        this.$message.error("题目内容不能为空")
        return;
      }
      if (this.form.score === "") {
        this.$message.error("请填写分值");
        return;
      }
      if (this.form.type !== "3" && this.tableData.length < 2) {
        this.$message.error("非判断题请添加至少两个选项")
        return;
      }
      if (this.form.type === "3" && this.tableData.length > 0) {
        this.$message.error("判断题请删除选项")
        this.form.solution='';
        this.tableData = [];
        return;
      }
      if (this.form.type === "3" && this.form.solution === "") {
        this.$message.error("请选择对错")
        return;
      }
      if (this.isRepeat(list)) {
        this.$message.error("不可添加重复选项");
        return;
      }
      let one = false;
      let num = 0;
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].type === true) {
          num++;
          one = true;
        }
        if (this.tableData[i].option === "") {
          this.$message.error("请选择选项");
          return;
        }
        if (this.tableData[i].content === "") {
          this.$message.error("请填写问题内容")
          return;
        }
      }
      if (this.form.type === "2") {
        if (this.tableData.length < 3 || num < 2) {

          this.$message.error("多选至少添加3个选项和两个答案")
          return;
        }
      }

      if (this.form.type === "1") {
        if (num !== 1) {
          this.$message.error("单选只能有一个答案")
          return;
        }
      }
      if (!one && this.form.type !== "3") {
        this.$message.error("请至少选择一个答案");
        return;
      }

      if (isNaN(this.form.score) || this.form.score > 100 || this.form.score < 1) {
        this.$message.error("分值只能为1-100数字")
        return;
      }
      let form = new FormData;
      form.append("analysis", this.form.analysis)
      form.append("bankId", this.form.bankId)
      form.append("questionContent", this.form.content)
      form.append("score", this.form.score)
      form.append("type", this.form.type)
      form.append("id", this.id)
      if (this.form.solution !== "" && this.form.type === "3") {
        form.append("solution", this.form.solution)
      }
      form.append("tableData", JSON.stringify(this.tableData))
      this.request.post("/question/questionEdit", form
      ).then(res => {
        if (res.code === 200) {
          let id = res.data;
          form = new FormData();
          form.append("id", id);
          form.append("file", this.files[0]);
          if (this.files.length !== 0) {
            this.request.post("/question/resource", form).then(res => {
              if (res.code === 200) {
                this.$message.success(res.msg);
                this.files = [];
                form = new FormData();
              } else {
                this.$message.error(res.msg);
              }
            })
          }
          this.$message.success("保存成功")
          this.$router.push("/testquestions");
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    isRepeat(v) {
      let obj = {}
      for (let i in v) {
        if (obj[v[i]]) {
          return true
        }
        obj[v[i]] = true
      }
      return false
    },
  },
  created() {
    this.tableData = [];
    this.form = {};
    this.files = [];
    this.init();


  }
};
</script>