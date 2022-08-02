<template>
  <div>
    <el-main style="min-width: 1050px">
      <div style="margin-bottom: 20px">
        <el-row :span="24">
          <el-form ref="form" :model="form">
            <el-col :span="4">
              <el-form-item v-model="form.region">
                <el-select v-model="form.region" placeholder="请选择活动区域">
                  <el-option
                    v-for="(item, i) in questions"
                    :key="i"
                    :label="item.name"
                    value="shanghai"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="4" style="margin-right: 40px"
              ><el-input
                v-model="questionName"
                placeholder="请输入题库名称内容"
              ></el-input>
            </el-col>
            <el-col :span="4"
              ><el-input
                v-model="questionContxt"
                placeholder="请输入内容"
              ></el-input>
            </el-col>
          </el-form>
          <el-col :span="3" style="margin: 0 20px"
            ><el-button type="primary" icon="el-icon-question" @click="select"
              >搜索</el-button
            ></el-col
          >
          <el-col :span="3"
            >
            
            <!-- <el-button type="primary" icon="el-icon-circle-plus-outline"
              @click="dialogFormVisible = true" >添加</el-button
            > -->

            <el-button type="primary" icon="el-icon-circle-plus-outline"
              @click="$router.push('/testquestionsadd')" >添加</el-button
            >
            
            </el-col
          >

          <el-col style="float: right" :span="4">
            <el-button icon="el-icon-upload2" @click="dialogVisible = true"
              >导入</el-button
            >
            <el-button icon="el-icon-download">导出</el-button>
          </el-col>
        </el-row>
      </div>
      <el-dialog
        title="导入试题"
        :visible.sync="dialogVisible"
        width="40%"
        :before-close="handleClose"
      >
        <el-upload
          style="margin-right: 20px; display: inline-block"
          action="#"
          :show-file-list="false"
          accept=".xlsx"
          :on-success="handExcelImportSuccess"
        >
          <el-button type="primary" class="ml-5">上传导入</el-button>
        </el-upload>
        <!-- <el-button type="primary">上传导入</el-button> -->
        <el-upload
          style="margin-right: 20px; display: inline-block"
          action="#"
          :show-file-list="false"
          accept=".xlsx"
          :on-success="handExcelImportSuccess"
        >
          <el-button type="warning" @click="compontenimport"
            >下载导入模板</el-button
          >
        </el-upload>
        <!-- <el-button type="warning" @click="compontenimport">下载导入模板</el-button> -->
      </el-dialog>
      <el-table
        ref="multipleTable"
        border
        :header-cell-class-name="'headerBg'"
        :stripe="true"
        :data="questionList"
        style="width: 100%"
      >
        <el-table-column type="selection"> </el-table-column>
        <el-table-column label="题目类型" width="100px" prop="type">
          <!-- <template slot-scope="scope">{{ scope.row.date }}</template> -->
          <!-- {{type}} -->
        </el-table-column>

        <el-table-column prop="context" label="题目内容"> </el-table-column>
        <el-table-column
          prop="time"
          width="250px"
          label="创建时间"
          show-overflow-tooltip
        >
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
        <el-dialog title="试题添加" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="试题内容">
          <el-input type="textarea" v-model="add.context" autocomplete="off"></el-input>
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
      dialogFormVisible:false,
      dialogVisible: false,
      form: {},
      add:{},
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
          type: "选择题",
          context: "JAVA python",
          time:
            new Date().getFullYear() +
            "年" +
            new Date().getMonth() +
            "月" +
            new Date().getDate() +
            "日",
        },
        {
          type: "选择题",
          context: "JAVA python",
          time:
            new Date().getFullYear() +
            "年" +
            new Date().getMonth() +
            "月" +
            new Date().getDate() +
            "日",
        },
        {
          type: "选择题",
          context: "JAVA python",
          time:
            new Date().getFullYear() +
            "年" +
            new Date().getMonth() +
            "月" +
            new Date().getDate() +
            "日",
        },
        {
          type: "选择题",
          context: "JAVA python",
          time:
            new Date().getFullYear() +
            "年" +
            new Date().getMonth() +
            "月" +
            new Date().getDate() +
            "日",
        },
        {
          type: "选择题",
          context: "JAVA python",
          time:
            new Date().getFullYear() +
            "年" +
            new Date().getMonth() +
            "月" +
            new Date().getDate() +
            "日",
        },
        {
          type: "选择题",
          context: "JAVA python",
          time:
            new Date().getFullYear() +
            "年" +
            new Date().getMonth() +
            "月" +
            new Date().getDate() +
            "日",
        },
        {
          type: "选择题",
          context: "JAVA python",
          time:
            new Date().getFullYear() +
            "年" +
            new Date().getMonth() +
            "月" +
            new Date().getDate() +
            "日",
        },
        {
          type: "选择题",
          context: "JAVA python",
          time:
            new Date().getFullYear() +
            "年" +
            new Date().getMonth() +
            "月" +
            new Date().getDate() +
            "日",
        },
        {
          type: "选择题",
          context: "JAVA python",
          time:
            new Date().getFullYear() +
            "年" +
            new Date().getMonth() +
            "月" +
            new Date().getDate() +
            "日",
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
      // 搜索题库
      console.log("搜索题库");
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
      this.dialogVisible = false
    },
    addSub(){
      console.log(this.add);
    }
  },
};
</script>

<style scoped>
</style>