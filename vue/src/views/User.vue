<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入用户名" suffix-icon="el-icon-search" v-model="username"></el-input>
      <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-message" class="ml-5"
                v-model="name"></el-input>
      <el-input style="width: 200px" placeholder="请输入电话" suffix-icon="el-icon-position" class="ml-5"
                v-model="tel"></el-input>
      <el-button class="ml-5" type="primary" @click="loadSearch(username,name,tel)">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-upload
          action="https://localhost:9090/user/import" :show-file-list="false" accept=".xlsx"
          :on-success="handExcelImportSuccess" style="display:inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5"> 导出 <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" max-height="480">
      <el-table-column prop="id" label="ID" width="50"></el-table-column>
      <el-table-column prop="username" label="用户名" width="100"></el-table-column>
      <el-table-column prop="name" label="姓名" width="110"></el-table-column>
      <el-table-column prop="tel" label="电话" width="110"></el-table-column>
      <el-table-column prop="roleId" label="角色" width="100"></el-table-column>
      <el-table-column prop="majorType" label="专业"></el-table-column>
      <el-table-column prop="certificate" label="证书" width="100">
        <template slot-scope="scope">
          <div >
            <el-image :key="scope.row.certificateUrl" :src="scope.row.certificateUrl"  style="width: 50px; height: 50px"
                      fit="fill" :preview-src-list="[scope.row.certificateUrl]">
            </el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="createtime" label="创建时间" width="150"></el-table-column>
      <el-table-column label="操作" width="180" align="center">

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
      <el-table-column prop="enable" label="启用" width="70">
        <template slot-scope="scope">
          <el-switch
              :active-value=1
              :inactive-value=0
              :value="scope.row.enable"
              @change="enableUser(scope.row)"
              active-color="#13ce66"
              inactive-color="#ff4949"
          >
          </el-switch>
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

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small" :rules="rules" ref="ruleForm" :model="form">
        <el-form-item label="用户名" prop="username">
          <el-input v-model.trim="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model.trim="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model.trim="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="tel">
          <el-input v-model.trim="form.tel" autocomplete="off" maxlength="11"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="roleId" required>
          <el-select v-model="form.roleId" autocomplete="off">
            <el-option label="管理员" value="1"></el-option>
            <el-option label="用户" value="10"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专业" prop="major" required>
          <el-select v-model="form.major" autocomplete="off">
            <el-option :label="m.major" :value="m.major" v-for="m in majors"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="dialogFormVisible = false"
                     type="primary"
                     size="small"
                     autocomplete="off">取 消
          </el-button>
          <el-button type="warning"
                     size="small"
                     autocomplete="off"
                     @click="edit">确 定
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import qs from 'qs'

export default {
  name: "User",
  data() {
    return {
      majors:[],
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

      rules: {
        username: [
          {required: true, message: "请输入用户名", trigger: "blur"},
          {min: 2, max: 10, message: "长度在 3 到 10 个字符", trigger: "blur"},
        ],
        name: [
          {required: true, message: "请输入用户名", trigger: "blur"},
          {min: 1, max: 5, message: "长度在 1 到 5 个字符", trigger: "blur"},
        ],
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
          {
            min: 6,
            max: 12,
            message: "长度在 6 到 12 个字符",
            trigger: "blur",
          },
        ],
        tel: [
          {require: true, message: "请输入手机号", trigger: "blur"},
          {min: 11, max: 11, message: "请输入正确的手机号", pattern: /^1[3456789]\d{9}$/, trigger: 'blur'}
        ],
        major: [{required: true, message: "请选择专业", trigger: "blur"},],
        roleId: [{required: true, message: "请选择角色", trigger: "blur"},],
      },
    }
  },
  created() {
    this.token = localStorage.getItem("token")
    this.load()
    this.initMajor();
  },
  methods: {
    initMajor() {
      this.request.get("/major/allMajor").then(res => {
        if (res.code === 200) {
          this.majors = res.data;
        } else {
          this.$message.error(res.msg)
        }

      })
    },
    load() {
      this.request.get("/user/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
          if (res.code === 200){
            this.tableData = res.data.list;
            this.total = res.data.total
          }else{
            this.$message.error(res.msg)
          }
      })
    },
    loadSearch(username, name, tel,) {
      if (username === "" && name === "" && tel === "") {
        this.load();
        return;
      }
      this.request.get("/user/search", {
        params: {
          username: username,
          name: name,
          tel: tel,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.total = res.total
        this.tableData = res.list;
      })
    },
    edit() {
      this.$refs["ruleForm"].validate((valid) => {
        console.log(qs.stringify(this.form))
        if (valid) {
          this.request.post("/user/edit?" + qs.stringify(this.form)).then(res => {
            console.log(res)
            if (res) {
              this.$message.success("保存成功")
              this.dialogFormVisible = false
              this.load()
            } else {
              this.$message.error("保存失败")
            }
          })
        }
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
    del(id) {
      this.request.delete("/user/del?id=" + id).then(res => {
        if (res.code === 200) {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
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
    enableUser(user) {
      this.request.post("/user/enable?id=" + user.id).then(res => {
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.load();
      })
    },
    exp() {
      this.request.get("/user/export?token=" + this.token)//导出
    },
    handExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    },
  }
}
</script>

<style>
.headerBg {
  background: #eee !important;
}
</style>