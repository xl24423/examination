<template>
  <div>
    <el-main style="min-width: 1050px">
      <div style="margin-bottom: 20px" v-if="role==='1'">
        <el-row :span="24">
          <el-form ref="form" :model="form">
            <el-col :span="4" style="margin-right: 20px"
            ><el-input
                v-model="name"
                placeholder="请输入答题人姓名"
            ></el-input>
            </el-col>
            <el-col :span="4" style="margin-right: 20px"
            ><el-input
                v-model="questionBankName"
                placeholder="请输入试卷名称"
            ></el-input>
            </el-col>
          </el-form>

          <el-button type="primary" icon="el-icon-question" @click="select"
          >搜索</el-button

          >
        </el-row>
      </div  >
      <el-table
          ref="multipleTable"
          border
          :header-cell-class-name="'headerBg'"
          :cell-style="{ textAlign: 'center' }"
          :header-cell-style="{ textAlign: 'center' }"
          :stripe="true"
          :data="ExamList"
          style="width: 100%"
      >
        <el-table-column label="试卷名称" prop="examName">
          <!-- <template slot-scope="scope">{{ scope.row.date }}</template> -->
          <!-- {{type}} -->
        </el-table-column>

        <el-table-column prop="username" label="用户名"> </el-table-column>
        <el-table-column prop="startdate" label="考试时间">
        </el-table-column>
        <el-table-column prop="username" label="答题人姓名" >
        </el-table-column>
        <el-table-column prop="count" label="考试得分"> </el-table-column>
        <el-table-column prop="sum" label="考试总分"> </el-table-column>
        <el-table-column label="操作" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-button
                type="primary"
                @click="$router.push('/ExaminationDetails/' + scope.row.username+'/'+scope.row.bankId)"
            >
              <i class="el-icon-position"></i> 详情</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="block" style="margin-top: 10px">
        <!-- <span class="demonstration">完整功能</span> -->
        <el-pagination
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

  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      role: "",
      form: {},
      total: 0,
      pageSize: 10,
      pageNum: 1,
      questionBankName: "",
      name: "",
      ExamList: [],
    };
  },

  created() {
    this.roleCheck();
    // console.log("11");
    this.init();
  },

  methods: {
    roleCheck(){
      this.request.get("user/me").then(res=>{
        if (res.code===200){
          this.role = res.data.roleId;
        }else{
          this.$message.error(res.msg)
        }
      })
    },
    init() {
      this.request.get("/exam/myExam",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res=>{
        if (res.code === 200){
          if (res.data.total !== 0){
            this.total = res.data.total;
            this.ExamList = res.data.list;
          }else {
            this.$message.error("你还没有考试记录")
          }
        }else{
          this.$message.error(res.msg)
        }
      })
    },
    select() {
      if (this.questionBankName==="" && this.name === ""){
        this.init();
      }else{
        this.request.get("/exam/recodeSearch",{
          params:{
            name : this.name,
            questionBankName: this.questionBankName
          }
        })
      }
    },
    handleSizeChange(val) {
      this.pageSize = val;
      if (this.questionBankName!=="" || this.name !== ""){
        this.select();
      }else{
        this.init();
      }
    },
    handleCurrentChange(val) {
      this.current = val;
      if (this.questionBankName!=="" || this.name !== ""){
        this.select();
      }else {
        this.init();
      }
    },
  },
};
</script>

<style scoped>
</style>