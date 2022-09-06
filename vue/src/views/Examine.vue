<template>

  <div>
    <div style="margin: 10px 0; ">
      <el-input style="width: 200px" placeholder="请输入用户名" suffix-icon="el-icon-search" v-model="username"></el-input>
      <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-message" class="ml-5"
                v-model="name"></el-input>
      <el-input style="width: 200px" placeholder="请输入电话" suffix-icon="el-icon-position" class="ml-5"
                v-model="tel"></el-input>
      <el-button class="ml-5" type="primary" @click="loadSearch(username,name,tel)">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
              @selection-change="handleSelectionChange" max-height="500">
      <el-table-column prop="id" label="ID" width="50"></el-table-column>
      <el-table-column prop="username" label="用户名" width="100"></el-table-column>
      <el-table-column prop="name" label="姓名" width="110"></el-table-column>
      <el-table-column prop="tel" label="电话" width="110"></el-table-column>
      <el-table-column prop="roleId" label="角色" width="100"></el-table-column>
      <el-table-column prop="majorType" label="专业"></el-table-column>
      <el-table-column prop="gender" label="性别" :formatter="stateFormat" width="50"></el-table-column>
      <el-table-column prop="companyId" label="公司"></el-table-column>
      <el-table-column prop="certificate" label="证书" width="80">
        <template slot-scope="scope">
          <div class="block" >
            <el-image :key="scope.row.certificateUrl" :src="scope.row.certificateUrl"  style="width: 80px; height: 50px"
                      fit="fill" :preview-src-list="[scope.row.certificateUrl]">
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>

          </div>
        </template>
      </el-table-column>
      <el-table-column prop="createtime" label="创建时间" width="150"></el-table-column>

      <el-table-column prop="enable" label="操作" width="100">
        <template slot-scope="scope">
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再看看'
              icon="el-icon-info"
              icon-color="red"
              title="您确定审核完毕吗？"
              @confirm="check(scope.row.id)"
          >
            <el-button type="primary" slot="reference">审核 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>

    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 20 , 50]"
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
  name: "User",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      username: "",
      password: "",
      name: "",
      tel: "",
      form: {},
      token: "",
      dialogFormVisible: false,
      multipleSelection: [],
    }
  },
  created() {
    this.token = localStorage.getItem("token")
    this.load()
  },
  methods: {
    stateFormat(row,column){
        if (row.gender==="1"){
          return "男";
        }else{
          return "女";
        }
    },
    load() {
      this.request.get("/user/checkPage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
            if (res.code === 200) {
              this.tableData = res.data.list;
              this.total = res.data.total
            } else {
              this.$message.error(res.msg)
            }

          }
      )
    },
    loadSearch(username, name, tel,) {
      if (username === "" && name === "" && tel === "") {
        this.load();
        return;
      }
      this.request.get("/user/checkSearch", {
        params: {
          username: username,
          name: name,
          tel: tel,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.total = res.data.total
        this.tableData = res.data.list;
      })
    },
    check(id) {
      this.request.post("/user/check?id=" + id).then(res => {
        if (res.code === 200) {
          this.$message.success(res.msg)
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
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
      if (this.username !== "" || this.name !== "" || this.tel !== "") {
        this.loadSearch(this.username, this.name, this.tel);
      } else {
        this.load()
      }
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      if (this.username !== "" || this.name !== "" || this.tel !== "") {
        this.loadSearch(this.username, this.name, this.tel);
      } else {
        this.load()
      }
    },
  }
}
</script>

<style>
.headerBg {
  background: #eee !important;
}
</style>