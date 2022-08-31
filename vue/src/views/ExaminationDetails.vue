
<template>
  <div>
    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">

      <el-table-column prop="TopicType" label="题目类型(单选 多选)" width="140"></el-table-column>
      <el-table-column prop="option1" label="A"></el-table-column>
      <el-table-column prop="option2" label="B"></el-table-column>
      <el-table-column prop="option3" label="C"></el-table-column>
      <el-table-column prop="option4" label="D"></el-table-column>
      <el-table-column prop="answer" label="回答"></el-table-column>
      <el-table-column prop="score" label="得分"></el-table-column>

    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>


  </div>
</template>

<script>
import qs from 'qs'
export default {
  name: "",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      username: "",
      password:"",
      name:"",
      tel:"",
      form: {},
      token:"",
      dialogFormVisible: false,
      multipleSelection: [],

    }
  },

  methods: {
    load() {
      this.request.get("", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.tableData = res.list;
        this.total = res.total
      })
    },
    loadSearch(username,name,tel,){
      if (username===""&&name===""&&tel===""){
        this.load();
        return;
      }
      this.request.get("",{
        params: {
          username: username,
          name: name,
          tel: tel,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res=>{
        console.log(res)
        this.total = res.total
        this.tableData = res.list;
      })
    },

    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
    },

    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },

    reset() {
      this.username = ""
      this.name = ""
      this.tel = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      if (this.username !== "" || this.name !== "" || this.tel !== ""){
        this.loadSearch(this.username, this.name, this.tel);
      }else{
        this.load()
      }
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      if (this.username !== "" || this.name !== "" || this.tel !== ""){
        this.loadSearch(this.username, this.name, this.tel);
      }else {
        this.load()
      }
    },

  }
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>