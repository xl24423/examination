<template>
  <div>
    <el-main>
      <div style="margin-bottom: 20px">
        <el-row :span="24">
          <el-form ref="form" :model="form">
            <el-col :span="3">
              <el-form-item v-model="form.region">
                <el-select v-model="form.region" placeholder="开发类型">
                  <el-option
                    v-for="(item, i) in types"
                    :key="i"
                    :label="item.name"
                    value="shanghai"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item >
               <el-col :span="11">
                  <el-date-picker
                    type="date"
                    placeholder="考试开始时间"
                    v-model="form.dateStart"
                    style="width: 100%"
                  ></el-date-picker>
                </el-col>
                <el-col :span="11">
                  <el-date-picker
                    type="date"
                    placeholder="考试结束时间"
                    v-model="form.dateEnd"
                    style="width: 100%"
                  ></el-date-picker>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col :span="3"
              ><el-input
                v-model="ExaminationName"
                placeholder="搜索考试名称"
              ></el-input>
            </el-col>
          </el-form>
          <el-col :span="3" style="margin: 0 20px"
            ><el-button type="primary" icon="el-icon-question" @click="select"
              >搜索</el-button
            ></el-col
          >
          <el-col :span="2"
            ><el-button type="primary" @click="add" icon="el-icon-circle-plus-outline"
              >添加</el-button
            ></el-col
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
        <el-table-column prop="name" label="考试名称"> </el-table-column>
        <el-table-column label="考试类型" width="100px" prop="type">
          <!-- <template slot-scope="scope">{{ scope.row.date }}</template> -->
          <!-- {{type}} -->
        </el-table-column>

        <el-table-column prop="time" label="考试时间"> </el-table-column>
        <el-table-column prop="score" label="考试总分"> </el-table-column>
        <el-table-column prop="pass" label="及格线"> </el-table-column>
        <el-table-column prop="status" label="状态"> </el-table-column>
        
        <el-table-column
          prop="time"
          width="250px"
          label="操作"
          show-overflow-tooltip
        >
        <template slot-scope="scpoe">
            <el-button type="primary" @click="update(scope.row)">修改</el-button>
            <el-button type="warning" @click="selectAll(scpoe.row)">考试详情</el-button>
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
      form: {},
      total: 30,
      pageSize: 10,
      current: 1,
      // 类型列表
      types: [
        {
          id: 1,
          name: "类型1",
        },
        {
          id: 2,
          name: "类型2",
        },
        {
          id: 3,
          name: "类型3",
        },
        {
          id: 4,
          name: "类型4",
        },
        {
          id: 5,
          name: "类型5",
        },
      ],

      ExaminationName: "",
      questionList: [
        {
            id:1,
            name:"考试名称1",
          type: "开卷考",
          context: "JAVA python",
          score:100,
          pass:60,
          status:"考试中",
          time:
            new Date().getFullYear() +
            "年" +
            new Date().getMonth() +
            "月" +
            new Date().getDate() +
            "日",
        },
        {
            id:1,
            name:"考试名称1",
          type: "开卷考",
          context: "JAVA python",
          score:100,
          pass:60,
          status:"考试中",
          time:
            new Date().getFullYear() +
            "年" +
            new Date().getMonth() +
            "月" +
            new Date().getDate() +
            "日",
        },{
            id:1,
            name:"考试名称1",
          type: "开卷考",
          context: "JAVA python",
          score:100,
          pass:60,
          status:"考试中",
          time:
            new Date().getFullYear() +
            "年" +
            new Date().getMonth() +
            "月" +
            new Date().getDate() +
            "日",
        },{
            id:1,
            name:"考试名称1",
          type: "开卷考",
          context: "JAVA python",
          score:100,
          pass:60,
          status:"考试中",
          time:
            new Date().getFullYear() +
            "年" +
            new Date().getMonth() +
            "月" +
            new Date().getDate() +
            "日",
        },{
            id:1,
            name:"考试名称1",
          type: "开卷考",
          context: "JAVA python",
          score:100,
          pass:60,
          status:"考试中",
          time:
            new Date().getFullYear() +
            "年" +
            new Date().getMonth() +
            "月" +
            new Date().getDate() +
            "日",
        },{
            id:1,
            name:"考试名称1",
          type: "开卷考",
          context: "JAVA python",
          score:100,
          pass:60,
          status:"考试中",
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
    add(){},
  },
};
</script>

<style scoped>
</style>