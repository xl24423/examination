<template>
  <div>
    <el-main>
      <div style="margin-bottom: 20px">
        <el-row :span="24">
          <el-form ref="form" :model="form" >
            <el-col :span="4">
              <el-form-item v-model="form.region" >
                <el-select v-model="form.region" placeholder="请选择活动区域">
                  <el-option v-for="(item,i) in questions" :key="i" :label="item.name" value="shanghai"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="4" style="margin-right:40px"
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
          <el-col  :span="3"
            ><el-button type="primary" icon="el-icon-circle-plus-outline"
              >添加</el-button
            ></el-col
          >

          <el-col style="float: right" :span="3"
            >
            <el-button  icon="el-icon-upload2"
              >导入</el-button
            >
            <el-button  icon="el-icon-download"
              >导出</el-button
            >
            </el-col
          >
        </el-row>
      </div>

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
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
        form:{},
      total: 30,
      pageSize: 10,
      current: 1,
      // 题库列表
      questions:[
        {
            id:1,
            name:'题库1'
        },
        {
            id:2,
            name:'题库2'
        },{
            id:3,
            name:'题库3'
        },{
            id:4,
            name:'题库4'
        },{
            id:5,
            name:'题库5'
        }
      ],
      questionContxt:"",
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
  },
};
</script>

<style scoped>
</style>