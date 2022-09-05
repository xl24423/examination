<template>
  <div>
    <el-main style="min-width: 1050px">
      <div style="margin-bottom: 20px">
        <el-row :span="24">
          <el-form ref="form" :model="form">
            <el-col :span="4" style="margin-right: 20px"
            >
              <el-input
                  v-model="questionName"
                  placeholder="请输入考试名称"
              ></el-input>
            </el-col>
          </el-form>
          <el-col :span="3" style="margin: 0 20px"
          >
            <el-button type="primary" icon="el-icon-question" @click="select(questionName)"
            >搜索
            </el-button>
            <el-button type="warning" @click="reset">重置</el-button>
          </el-col
          >
        </el-row>
      </div>
      <el-table
          max-height="480"
          ref="multipleTable"
          border
          :header-cell-class-name="'headerBg'"
          :cell-style="{ textAlign: 'center' }"
          :header-cell-style="{ textAlign: 'center' }"
          :stripe="true"
          :data="questionList"
          style="width: 100%"
      >
        <el-table-column label="考试名称" prop="name">
          <!-- <template slot-scope="scope">{{ scope.row.date }}</template> -->
          <!-- {{type}} -->
        </el-table-column>
        <el-table-column prop="time" label="考试时长"></el-table-column>
        <el-table-column prop="count" label="考试总分"></el-table-column>
        <el-table-column prop="pass" label="及格线"></el-table-column>
        <el-table-column prop="admin" label="出题人"></el-table-column>
        <el-table-column label="操作" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-button
                type="primary"
                @click="jump(scope.row.id)"
            >
              <i class="el-icon-position"></i> 去考试
            </el-button
            >

          </template>

        </el-table-column>
      </el-table>
      <el-dialog
          title="提示"
          :visible.sync="ExamVisible"
          width="30%">
        <span>你已经考过这门考试,您确定要重新考试吗?</span>
        <span slot="footer" class="dialog-footer">
    <el-button @click="ExamVisible = false">取 消</el-button>
    <el-button type="primary" @click="$router.push('/prepareExam/'+jumpId)">确 定</el-button>
  </span>
      </el-dialog>

      <!-- 分页 -->
      <div class="block" style="margin-top: 10px">
        <!-- <span class="demonstration">完整功能</span> -->
        <el-pagination
            :disabled="IsSearch"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[10, 15, 20, 30]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
        >
        </el-pagination>
      </div>
    </el-main>

    <!-- 添加 -->
    <el-dialog title="试题添加" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="试题内容">
          <el-input
              type="textarea"
              v-model="add.context"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="试题答案">
          <el-input v-model="add.true" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="试题类型">
          <el-select v-model="add.type" placeholder="请选择活动区域">
            <el-option
                v-for="(item, i) in questions"
                :key="i"
                :label="item.name"
                :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addSub">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      IsSearch: false,
      jumpId: 0,
      ExamVisible: false,
      dialogFormVisible: false,
      dialogVisible: false,
      form: {},
      add: {},
      total: 30,
      pageSize: 10,
      pageNum: 1,
      questionContxt: "",
      questionName: "",
      questionList: [],
    };
  },

  created() {
    // console.log("11");
    this.init();
  },

  methods: {
    jump(id) {
      this.jumpId = id;
      this.request.get("/exam/isExam?bankId=" + id).then(res => {
        if (res.code === 200) {
          if (!res.data) {
            this.ExamVisible = true;
          } else {
            this.$router.push("/prepareExam/" + id)
          }
        } else {
          this.$message.error("当前服务器异常,请稍后再试")
        }
      })
    },
    init() {
      this.IsSearch = false;
      this.request.get("/questionBank/actionExam", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        if (res.code === 200) {
          this.questionList = res.data.list;
          this.total = res.data.total
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    select(questionName) {
       if (questionName !==""){
         this.IsSearch = true;
         this.request.get("/questionBank/actionExamSearch?name="+questionName).then(res=>{
            if (res.code === 200){
              if (res.data !== null){
                this.total = 1;
                this.questionList =[];
                this.questionList.push(res.data);
              }else{
                this.total = 0;
                this.questionList = [];
              }
            }
         })
       }else{
         this.init();
       }
    },
    reset(){
      this.questionName = "";
      this.init();
    },
    handleSizeChange(val) {
      console.log(val);
      this.pageSize = val;
      this.init()
    },
    handleCurrentChange(val) {
      console.log(val);
      this.pageNum = val;
      this.init()
    },
    compontenimport() {
      console.log("下载导入");
    },
    handExcelImportSuccess() {
    },
    handleClose() {
      this.dialogVisible = false;
    },
    addSub() {
      console.log(this.add);
    },
  },
};
</script>

<style scoped>
</style>