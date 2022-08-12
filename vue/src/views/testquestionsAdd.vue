<template>
  <div>
    <el-card>
      <el-form ref="form" :model="form" label-width="80px">
        <!-- <el-form-item label="活动名称">
          <el-input v-model="form.name"></el-input>
        </el-form-item> -->
        <el-form-item label="题目类型">
          <el-select v-model="form.type" placeholder="请选择活动区域">
            <el-option label="单选题" value="1"></el-option>
            <el-option label="多选题" value="2"></el-option>
            <el-option label="判断题" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="难度等级">
          <el-select v-model="form.type2" placeholder="请选择活动区域">
            <el-option label="普通" value="1"></el-option>
            <el-option label="较难" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="归属题库">
          <el-select v-model="form.region" placeholder="请选择活动区域">
            <el-option
              v-for="(item, index) in tests"
              :key="index"
              :label="item.name"
              :value="item.id"
              >{{ item.name }}</el-option
            >
          </el-select>
        </el-form-item>

        <el-form-item label="题目内容">
          <el-input type="textarea" v-model="form.desc"></el-input>
        </el-form-item>

        <el-form-item label="试题图片">
          <el-upload
            class="upload-demo"
            action="https://jsonplaceholder.typicode.com/posts/"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple
            :limit="3"
            :on-exceed="handleExceed"
            :file-list="fileList"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
          </el-upload>
        </el-form-item>
        <el-form-item label="整题解析">
          <el-input type="textarea" v-model="form.desc"></el-input>
        </el-form-item> </el-form
    ></el-card>

    <el-button
      type="primary"
      style="margin: 20px 0"
      @click="add"
      plain
      icon="el-icon-plus"
      >添加</el-button
    >

    <div>
      <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        border
        :cell-style="{ textAlign: 'center' }"
        :header-cell-style="{ textAlign: 'center' }"
      >
        <el-table-column prop="type" label="是否答案" width="120">
          <template slot-scope="scope"
            ><el-checkbox v-model="scope.row.type">答案</el-checkbox></template
          >
        </el-table-column>
        <el-table-column prop="imgs" label="选项图片" width="120">
          <el-upload
            class="upload-demo"
            action="https://jsonplaceholder.typicode.com/posts/"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple
            :limit="3"
            :on-exceed="handleExceed"
            :file-list="fileList"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
          </el-upload>
        </el-table-column>
        <el-table-column label="答案内容">
          <template slot-scope="scope">
            <el-input
              type="textarea"
              :rows="2"
              placeholder="请输入内容"
              v-model="scope.row.context"
            >
            </el-input
          ></template>
        </el-table-column>

        <el-table-column label="答案解析">
          <template slot-scope="scope">
            <el-input
              type="textarea"
              :rows="2"
              placeholder="请输入内容"
              v-model="scope.row.context_"
            >
            </el-input
          ></template>
        </el-table-column>

        <el-table-column label="操作" width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-button
              type="danger"
              icon="el-icon-delete"
              @click="del(scope.row)"
              circle
            ></el-button
          ></template>
        </el-table-column>
      </el-table>
    </div>

    <div style="margin: 20px 0">
      <el-button size="small" @click="submit" type="primary">保存</el-button>
      <el-button
        size="small"
        type="info"
        @click="$router.push('/testquestions')"
        >返回</el-button
      >
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tests: [
        {
          id: 1,
          name: "题库1",
        },
        {
          id: 2,
          name: "题库2",
        },
        {
          id: 3,
          name: "题库3",
        },
      ],

      fileList: [],
      form: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: "",
      },
      tableData: [
        {
          id: 1,
          type: true,
          imgs: [],
          context: "111",
          context_: "1111222",
        },
      ],
    };
  },
  methods: {
    onSubmit() {
      console.log("submit!");
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },

    add() {
      let data = { type: null, imgs: [], context: "", context_: "" };
      this.tableData.push(data);
    },

    del(item) {
      var index = this.tableData.findIndex((i) => i == item);
      //判断索引值是否有效，当索引值为-1时说明没有符合条件的对象
      if (index != -1) {
        //当符合时，删除，此处的1用于表示删除1个，后续使用可自行测试
        this.tableData.splice(index, 1);
      }
    },
    submit() {
      console.log(this.tableData);
      console.log(this.form);
    },
  },
};
</script>