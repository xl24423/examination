<template>
  <el-card>
    <el-button style="margin:10px" type="primary" @click="$router.push('/videoinsert')" v-if="roleId==='1'"><i class="el-icon-plus"></i>
      添加视频
    </el-button>
    <el-row>
      <el-col
          style="margin:10px"
          :span="5"
          v-for="index in list"
          :key="index.id"
      >
        <el-card :body-style="{ padding: '0px',}">
          <div>
            <!-- <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image"> -->
            <video controls require :src="index.fileUrl+'?token='+token"
                   style="width: 100%; height: 200px; object-fit: cover"
                   :id="'videobox'+index.id"
            >
            </video>
          </div>
          <div style="padding: 14px">
            <span>视频名称:{{ index.fileName }}</span>
            <div class="bottom clearfix">
              <span class="text-sm-right">{{ index.content }}</span>
              <el-popconfirm
                  class="ml-5"
                  confirm-button-text='确定'
                  cancel-button-text='我再想想'
                  icon="el-icon-info"
                  icon-color="red"
                  title="您确定删除吗？"
                  @confirm="deleteResource(index.objectKey)"
              >
                <el-button type="danger" slot="reference" style="float:right">删除 <i class="el-icon-remove-outline"></i>
                </el-button>
              </el-popconfirm>
            </div>
          </div>
        </el-card>
      </el-col>

    </el-row>
    <div class="block">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 4, 8, 10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </el-card>

</template>

<script>
export default {
  data() {
    return {
      roleId: "",
      total: 0,
      list: [],
      pageNum: 1,
      pageSize: 10,
      token: "",
    };
  },
  created() {
    this.token = localStorage.getItem("token");
    this.loadAllVideo();
    this.me();
  },
  methods: {
    me(){
      this.request.get("/user/me").then(res=>{
        this.roleId = res.data.roleId;

      })
    },
    loadAllVideo() {
      this.request.get("/fileAddress/getAll", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        if (res.code === 200) {
          console.log(res)
          if (res.data.length === 0) {
            this.$message.error("该页面没有资源或服务器出错")
          }
          this.total = res.data.length;
          this.list = res.data;
          console.log(this.list)
        } else {
          this.$message.error(res.msg)
        }

      })
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.loadAllVideo()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.loadAllVideo()
    },
    deleteResource: function (objectKey) {
      this.request.get("/resources/delete?objectKey=" + objectKey).then(res => {
        if (res.code === 200) {
          this.$message.success(res.msg);
        } else {
          this.$message.error(res.msg)
        }
        this.loadAllVideo();
      })
    }
  }
};
</script>


<style>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
video {
  display: block;
}

</style>

