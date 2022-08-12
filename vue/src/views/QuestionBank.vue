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
            @click="dialogFormVisible = true"  >添加</el-button
            ></el-col
          >
        </el-row>
      </div>

      <el-table ref="multipleTable" border :header-cell-class-name="'headerBg'"  :stripe="true" :data="questionList" style="width: 100%">
        <el-table-column type="selection"> </el-table-column>
        <el-table-column label="题库名称" prop="name"></el-table-column>
        <el-table-column prop="createtime" label="创建时间" show-overflow-tooltip></el-table-column>
        <el-table-column  label="操作">
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
            <el-button type="warning" @click="selectAll(scope.row)">题库详情</el-button>
          </template>
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

      <!-- 添加 -->
        <el-dialog title="题库添加" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="120px" size="small">
        <el-form-item label="题库名称">
          <el-input  v-model="add.title" autocomplete="off"></el-input>
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
      dialogFormVisible:false,
      add:{},
        total:30,
        pageNum: 1,
        pageSize:10,
        current:1,
      questionName: "",
      questionList: [
        {
          name: "2",
          createtime: "3",
        },
        ]
    };
  },

  created() {
    // console.log("11");
    this.init();
  },

  methods: {
    init() {
        this.request.get("/questionBank/page",{
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
          }
        }).then(res=>{
          console.log(res)
           this.questionList = res.list
        })
    },
    del(id){
        this.request.delete("/questionBank",{
          params: {
            id: id,
            pageNum: this.pageNum,
            pageSize: this.pageSize,
          }
            }).then(res=>{
              if (res.data.code === 200){
                  console.log(res)
                  this.questionList = res.list;
              }else {
                 alert("删除失败,请联系管理员")
              }
        })
    },

    handleSizeChange(val){
        console.log(val);
        this.pageSize = val
    },
    handleCurrentChange(val){
        console.log(val);
        this.current = val
    },
    addSub(){
      console.log(this.add);
    },

  },
};
</script>

<style scoped>
</style>