<template>
  <div>
    <el-main style="min-width: 1050px">
      <div style="margin-bottom: 20px">
        <el-row :span="24">
          <el-form ref="form" :model="form">
            <el-col :span="4">
              <el-form-item>
                <el-select v-model="region" placeholder="请选择活动区域" @click.native="handleClick">
                  <el-option
                      v-for="item in questions"
                      :label="item.name"
                      :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="4" style="margin-right: 40px"
            >
              <el-input
                  v-model="questionName"
                  placeholder="请输入题库名称内容"
                  @click.native="handleClick"
              ></el-input>
            </el-col>
            <el-col :span="4"
            >
              <el-input
                  v-model="questionContxt"
                  placeholder="请输入内容"
                  @click.native="handleClick"
              ></el-input>
            </el-col>
          </el-form>
          <el-col :span="3" style="margin: 0 20px"
          >
            <el-button type="primary" icon="el-icon-question" @click="select"
            >搜索
            </el-button
            >
          </el-col
          >
          <el-col :span="3"
          >

            <!-- <el-button type="primary" icon="el-icon-circle-plus-outline"
              @click="dialogFormVisible = true" >添加</el-button
            > -->

            <el-button type="primary" icon="el-icon-circle-plus-outline"
                       @click="$router.push('/testquestionsadd')">添加
            </el-button
            >

          </el-col
          >

          <el-col style="float: right" :span="4">
            <el-button icon="el-icon-upload2" @click="dialogVisible = true"
            >导入
            </el-button
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
          >下载导入模板
          </el-button
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
        <el-table-column type="selection"></el-table-column>
        <el-table-column label="题目类型"  prop="type" min-width="30%">
          <!-- <template slot-scope="scope">{{ scope.row.date }}</template> -->
          <!-- {{type}} -->
        </el-table-column>

        <el-table-column prop="question" label="题目内容" min-width="200%"></el-table-column>
        <el-table-column prop="questionBankId" label="所属题库" min-width="50%"></el-table-column>
        <el-table-column prop="score" label="分值" min-width="30%"></el-table-column>
        <el-table-column prop="solution" label="答案" min-width="60%"></el-table-column>
        <el-table-column label="图片">
          <template slot-scope="scope">
          <div>
            <el-image :key="scope.row.url" :src="scope.row.url" style="width: 50px; height: 50px" fit="fill" :preview-src-list="[scope.row.url]" >
            </el-image>
          </div>
          </template>
        </el-table-column>
        <el-table-column
            prop="createtime"
            width="150px"
            label="创建时间"
            show-overflow-tooltip
        >
        </el-table-column>
        <el-table-column prop="userId" label="创建人" ></el-table-column>
        <el-table-column label="操作" width="200" align="center" >
          <template slot-scope="scope">
            <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
            <el-popconfirm
                class="ml-5"
                confirm-button-text='确定'
                cancel-button-text='我再想想'
                icon="el-icon-info"
                icon-color="red"
                title="您确定删除吗？"
                @confirm="del(scope.row.id)"
            >
              <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
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
            :page-sizes="[5, 10,15, 20]"
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
      prop:[{url:""}],
      questionList:[],
      dialogFormVisible: false,
      dialogVisible: false,
      form: {
      },
      add: {},
      total: 30,
      pageSize: 10,
      pageNum: 1,
      region:"",
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
    };
  },

  created() {
    // console.log("11");
    this.init();
  },

  methods: {
    init() {
      this.request.get("/question/questions", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        if (res.code === 200) {
          this.total = res.data.total;
          this.questionList = res.data.list;
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    select() {
      // 搜索题库
      console.log("搜索题库");
    },
    handleSizeChange(val) {
      console.log(val);
      this.pageSize = val;
      this.init();
    },
    handleCurrentChange(val) {
      console.log(val);
      this.pageNum = val;
      this.init();
    },
    compontenimport() {
      console.log("下载导入");
    },
    handExcelImportSuccess() {
    },
    handleClose() {
      this.dialogVisible = false
    },
    addSub() {
      console.log(this.add);
    },
    handleClick() {
      this.questionContxt = ""
      this.questionName = ""
      this.region = ""
    }
  },

};
</script>

<style scoped>
</style>