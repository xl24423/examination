<template>
  <div>
    <el-main style="min-width: 1050px">
      <div style="margin-bottom: 20px">
        <el-row :span="24">
          <el-form ref="form" :model="form">
            <el-col :span="3">
              <el-form-item v-model="form.region">
                <el-select v-model="form.region" placeholder="开放类型">
                  <el-option
                    v-for="(item, i) in questions"
                    :key="i"
                    :label="item.name"
                    value="shanghai"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="4" style="margin-right: 20px"
              ><el-input
                v-model="questionName"
                placeholder="请输入考试名称"
              ></el-input>
            </el-col>
          </el-form>
          <el-col :span="3" style="margin: 0 20px"
            ><el-button type="primary" icon="el-icon-question" @click="select"
              >搜索</el-button
            ></el-col
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
        <el-table-column label="考试名称" prop="name">
          <!-- <template slot-scope="scope">{{ scope.row.date }}</template> -->
          <!-- {{type}} -->
        </el-table-column>

        <el-table-column prop="context" label="考试类型"> </el-table-column>
        <el-table-column prop="timetype" label="考试时间" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="timenumber" label="考试时长"> </el-table-column>
        <el-table-column prop="all" label="考试总分"> </el-table-column>
        <el-table-column prop="Pass" label="及格线"> </el-table-column>
        <el-table-column label="操作" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-button
              type="primary"
              @click="$router.push('/prepareexam/' + scope.row.id)"
            >
              <i class="el-icon-position"></i> 去考试</el-button
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
      dialogFormVisible: false,
      dialogVisible: false,
      form: {},
      add: {},
      total: 30,
      pageSize: 10,
      current: 1,
      // 题库列表
      questions: [
        {
          id: 1,
          name: "单选题",
        },
        {
          id: 2,
          name: "多选题",
        },
        {
          id: 3,
          name: "判断题",
        },
      ],
      questionContxt: "",
      questionName: "",
      questionList: [
        {
          id: 1,
          name: "演示考试",
          type: "完全公开",
          timetype: "不限时",
          timenumber: "30分钟",
          all: "100",
          Pass: 60,
        },
        {
          id: 222222,

          name: "演示考试",
          type: "完全公开",
          timetype: "不限时",
          timenumber: "30分钟",
          all: "100",
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
    compontenimport() {
      console.log("下载导入");
    },
    handExcelImportSuccess() {},
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