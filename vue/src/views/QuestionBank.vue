<template>
  <div>
    <el-main>
      <div style="margin-bottom: 20px">
        <el-row :span="24">
          <el-col :span="6"
          >
            <el-input
                v-model="questionName"
                placeholder="请输入内容"
            ></el-input>
          </el-col>
          <el-col :span="3" style="margin: 0 20px"
          >
            <el-button type="primary" icon="el-icon-question" @click="search(questionName)">搜索
            </el-button>
            <el-button type="warning" @click="reset">重置</el-button>
          </el-col
          >
          <el-col style="float: right" :span="3"
          >
            <el-button type="primary" icon="el-icon-circle-plus-outline"
                       @click="dialogFormVisible = true">添加
            </el-button
            >
          </el-col
          >
        </el-row>
      </div>

      <el-table ref="multipleTable" border :header-cell-class-name="'headerBg'" :stripe="true" :data="questionList"
                style="width: 100%" max-height="480">
        <el-table-column label="题库名称" prop="name"></el-table-column>
        <el-table-column prop="createtime" label="创建时间" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
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
            <el-button :type="scope.row.type" @click="action(scope.row)">{{ scope.row.isAction }}</el-button>
          </template>
        </el-table-column>
      </el-table>


      <!-- 分页 -->
      <div class="block" style="margin-top:10px;">
        <!-- <span class="demonstration">完整功能</span> -->
        <el-pagination
            :disabled="questionName!==''"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[10,15, 20, 30]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>
    </el-main>

    <!-- 添加 -->
    <el-dialog title="题库添加" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="120px" size="small">
        <el-form-item label="题库名称">
          <el-input v-model="add.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="考试时长">
          <el-input v-model="add.time" autocomplete="off"></el-input>
        </el-form-item>
        <!-- <el-form-item label="试题类型">
          <el-select v-model="add.type" placeholder="请选择活动区域">
                  <el-option
                    v-for="(item, i) in questions"
                    :key="i"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addSub">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
let date = new Date();
export default {
  name: "Home",
  data() {
    return {
      dialogFormVisible: false,
      add: {},
      total: 30,
      pageNum: 1,
      pageSize: 10,
      questionName: "",
      questionList: []
    };
  },

  created() {
    this.init();
  },

  methods: {
    action(row) {
      this.request.get("/user/me").then(res => {
        if (res.data.roleId === "1") {
          if (row.isAction === "关闭") {
            row.isAction = "开启";
            row.type = "primary"
            this.request.get("/questionBank/act?action=false&id=" + row.id);
          } else {
            row.isAction = "关闭";
            row.type = "info"
            this.request.get("/questionBank/act?action=true&id=" + row.id);
          }
        } else {
          this.$message.error("你没有权限这么做")
        }
      })
    },
    init() {
      this.request.get("/questionBank/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        if (res.code === 200) {
          this.questionList = res.data.list
          this.total = res.data.total
          let list = this.questionList;
          for (let i = 0; i < list.length; i++) {
            if (list[i].isAction === "true") {
              list[i].type = "info";
              list[i].isAction = "关闭";
            } else {
              list[i].type = "primary";
              list[i].isAction = "开启";
            }
          }
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    del(id) {
      this.request.delete("/questionBank", {
        params: {
          id: id,
        }
      }).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.$message.success("删除成功")
          this.init();
        } else {
          this.$message.error("删除失败,请联系管理员")
        }
      })
    },
    reset(){
      this.questionName = "";
      this.init();
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.init();
    },
    handleCurrentChange(val) {
      this.pageNum = val
      this.init();
    },
    addSub() {
      this.request.get("/user/me").then(res => {
        if (res.data.roleId === "1") {
          this.request.get("/questionBank/add", {
            params: {
              title: this.add.title,
              time: this.add.time
            },
          }).then(res => {
            if (res.code === 200) {
              this.$message.success("新增成功");
              this.add = {};
              this.dialogFormVisible = false;
              this.init();
            } else {
              this.$message.error(res.msg);
            }
          })
        } else {
          this.$message.error("你没有权限这么做");
        }
      })
    },
    search(name) {
      if (name!==""){
        this.request.get("/questionBank/selectByName?name=" + name).then(res => {
          if (res.code === 200){
            this.questionList = [];
            if (res.data !== null) {
              this.questionList.push(res.data)
              this.total = 1;
            } else {
              this.questionList = [];
              this.total = 0;
            }
            let list = this.questionList;
            for (let i = 0; i < list.length; i++) {
              if (list[i].isAction === "true") {
                list[i].type = "info";
                list[i].isAction = "关闭";
              } else {
                list[i].type = "primary";
                list[i].isAction = "开启";
              }
            }
          }else{
            this.$message.error(res.msg)
          }
        })
      }else{
       this.init();
      }
    }
  },
};
</script>

<style scoped>
</style>