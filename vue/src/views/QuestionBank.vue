<template>
  <div>
    <el-main>
      <div style="margin-bottom: 20px">
        <el-row :span="24">
          <el-col :span="6"
            ><el-input
              v-model="questionName"
              placeholder="请输入内容"
            ></el-input>
          </el-col>
          <el-col :span="3" style="margin: 0 20px"
            ><el-button type="primary" icon="el-icon-question"
              >搜索</el-button
            ></el-col
          >
          <el-col style="float: right" :span="3"
            ><el-button type="primary" icon="el-icon-circle-plus-outline"
              >添加</el-button
            ></el-col
          >
        </el-row>
      </div>

      <el-table ref="multipleTable" border :header-cell-class-name="'headerBg'"  :stripe="true" :data="questionList" style="width: 100%">
        <el-table-column type="selection"> </el-table-column>
        <el-table-column label="题库名称">
          <template slot-scope="scope">{{ scope.row.date }}</template>
        </el-table-column>

        <el-table-column prop="number" label="单选题数量"> </el-table-column>
        <el-table-column prop="dan" label="多选题数量"> </el-table-column>
        <el-table-column prop="duo" label="判断题数量"> </el-table-column>
        <el-table-column prop="time" label="创建时间" show-overflow-tooltip>
        </el-table-column>
      </el-table>


      <!-- 分页 -->
      <div class="block" style="margin-top:10px;">
    <!-- <span class="demonstration">完整功能</span> -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="current"
      :page-sizes="[10,15, 20, 30]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
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
        total:30,
        pageSize:10,
        current:1,
      questionName: "",
      questionList: [
        { number: 1, dan: 1 , duo: 10 , time: new Date().getFullYear()+"年"+new Date().getMonth()+"月"+new Date().getDate()+"日"},
        { number: 1, dan: 1 , duo: 10 , time: new Date().getFullYear()+"年"+new Date().getMonth()+"月"+new Date().getDate()+"日"},

        { number: 1, dan: 1 , duo: 10 , time: new Date().getFullYear()+"年"+new Date().getMonth()+"月"+new Date().getDate()+"日"},
        { number: 1, dan: 1 , duo: 10 , time: new Date().getFullYear()+"年"+new Date().getMonth()+"月"+new Date().getDate()+"日"},
        { number: 1, dan: 1 , duo: 10 , time: new Date().getFullYear()+"年"+new Date().getMonth()+"月"+new Date().getDate()+"日"},
        { number: 1, dan: 1 , duo: 10 , time: new Date().getFullYear()+"年"+new Date().getMonth()+"月"+new Date().getDate()+"日"},
        { number: 1, dan: 1 , duo: 10 , time: new Date().getFullYear()+"年"+new Date().getMonth()+"月"+new Date().getDate()+"日"},
        { number: 1, dan: 1 , duo: 10 , time: new Date().getFullYear()+"年"+new Date().getMonth()+"月"+new Date().getDate()+"日"},
        { number: 1, dan: 1 , duo: 10 , time: new Date().getFullYear()+"年"+new Date().getMonth()+"月"+new Date().getDate()+"日"},
        { number: 1, dan: 1 , duo: 10 , time: new Date().getFullYear()+"年"+new Date().getMonth()+"月"+new Date().getDate()+"日"},

        ]
    };
  },

  created() {
    // console.log("11");
    this.init();
  },

  methods: {
    init() {
      
    },

    handleSizeChange(val){
        console.log(val);
        this.pageSize = val
    },
    handleCurrentChange(val){
        console.log(val);
        this.current = val
    }
  },
};
</script>

<style scoped>
</style>