<template>
  <div>
    <el-main style="min-width: 1050px">
      <div style="margin-bottom: 20px">
        <el-row :span="24">
          <el-form ref="form" :model="form">
            <el-col :span="3">
              <el-form-item v-model="form.region">


              </el-form-item>
            </el-col>
            <el-col :span="4" style="margin-right: 20px"
            ><el-input
                v-model="questionName"
                placeholder="请输入答题人姓名"
            ></el-input>
            </el-col>
          </el-form>

          <el-button type="primary" icon="el-icon-question" @click="select"
          >搜索</el-button

          >
        </el-row>
      </div>
      <el-table
          ref="multipleTable"
          border
          :header-cell-class-name="'headerBg'"
          :cell-style="{ textAlign: 'center' }"
          :header-cell-style="{ textAlign: 'center' }"
          :stripe="true"
          :data="questionList"
          style="width: 100%"
      >
        <el-table-column label="id" prop="id">
          <!-- <template slot-scope="scope">{{ scope.row.date }}</template> -->
          <!-- {{type}} -->
        </el-table-column>

        <el-table-column prop="name" label="用户名"> </el-table-column>
        <el-table-column prop="timetype" label="考试时间">
        </el-table-column>
        <el-table-column prop="username" label="答题人姓名" >
        </el-table-column>
        <el-table-column prop="all" label="考试总分"> </el-table-column>
        <el-table-column label="操作" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-button
                type="primary"
                @click="$router.push('/ExaminationDetails/' + scope.row.id)"
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
            :current-page="current"
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
      dialogFormVisible: false,
      dialogVisible: false,
      form: {},
      add: {},
      total: 30,
      pageSize: 10,
      current: 1,

      questionContxt: "",
      questionName: "",
      questionList: [
        {
          id: 1,
          name: "",
          type: "",
          timetype: "",
          timenumber: "",
          all: "",
          Pass: 60,
        },
        {
          id: 222222,

          name: "",
          type: "",
          timetype: "",
          timenumber: "",
          all: "",
          Pass: 60,
        },
      ],
    };
  },

  created() {
    // console.log("11");
    this.init();
  },

  methods: {
    init() {},
    select() {

    },
    handleSizeChange(val) {
      console.log(val);
      this.pageSize = val;
    },
    handleCurrentChange(val) {
      console.log(val);
      this.current = val;
    },

    handExcelImportSuccess() {},
    handleClose() {
      this.dialogVisible = false;
    },

  },
};
</script>

<style scoped>
</style>