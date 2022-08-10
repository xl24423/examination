<template>
  <div>
    <el-card>
      <el-row :span="24">
      <el-col :span="18">
      <el-button
        type="primary"
        style="margin: 20px 0"
        @click="add"
        icon="el-icon-plus"
        >添加题库</el-button
      >
      </el-col>
      <el-col :span="6">
     <span style="color:red;font-size:28px"> 总分值：{{sum}}</span>
      </el-col>
      </el-row>

      <el-table :data="tableData" style="width: 100%">
        <el-table-column label="题库" width="180">
          <template slot-scope="scpoe">
            <el-select
              @change="changeUpdate(scpoe.row)"
              v-model="scpoe.row.type"
              placeholder="请选择活动区域"
            >
              <el-option
                v-for="(item, index) in Questions"
                :key="index"
                :label="item.name"
                :value="item.id"
              >
                {{ item.name }}</el-option
              >
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="a" label="单选题数量" width="180">
          <template slot-scope="scpoe">
            <div style="display: flex; justify-content: center">
              <el-input
                @blur="blur"
                v-model="scpoe.row.a"
                placeholder="请输入内容"
              ></el-input>
              <span>{{ scpoe.row.aa }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="a_" label="单选题分数">
          <template slot-scope="scpoe">
            <el-input
              v-model="scpoe.row.a_"
              @blur="blur"
              placeholder="请输入内容"
            ></el-input>
          </template>
        </el-table-column>

        <el-table-column prop="b" label="多选提数量">
          <template slot-scope="scpoe">
            <div style="display: flex; justify-content: center">
              <el-input
                @blur="blur"
                v-model="scpoe.row.b"
                placeholder="请输入内容"
              ></el-input>
              <span>{{ scpoe.row.bb }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column prop="b_" label="多选提分数">
          <template slot-scope="scpoe">
            <el-input
              @blur="blur"
              v-model="scpoe.row.b_"
              placeholder="请输入内容"
             
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column prop="c" label="判断题数量">
          <template slot-scope="scpoe">
            <div style="display: flex; justify-content: center">
              <el-input
                @blur="blur"
                v-model="scpoe.row.c"
                placeholder="请输入内容"
              ></el-input>
              <span>{{ scpoe.row.cc }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column prop="c_" label="判断题分数">
          <template slot-scope="scpoe">
            <el-input
              @blur="blur"
              v-model="scpoe.row.c_"
              placeholder="请输入内容"
            ></el-input>
          </template>
        </el-table-column>

        <el-table-column label="删除">
          <template slot-scope="scope">
            <el-button
              type="danger"
              icon="el-icon-delete"
              @click="del(scope.row)"
              circle
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <h4 style="margin: 20px 0">考试配置</h4>
    <el-card
      ><el-form ref="form" :model="form" label-width="140px">
        <!-- <el-form-item label="活动名称">
          <el-input v-model="form.name"></el-input>
        </el-form-item> -->

        <el-form-item label="考试名称">
          <el-input v-model="form.title"></el-input>
        </el-form-item>

        <el-form-item label="考试描述">
          <el-input type="textarea" v-model="form.context"></el-input>
        </el-form-item>
        <el-form-item label="总分数">
          <el-input
            placeholder="请输入内容"
            style="width: 300px"
            v-model="form.input3"
             disabled
            class="input-with-select"
          >
            <el-button slot="prepend" icon="el-icon-minus" @click="form.input3--"></el-button>

            <el-button slot="append" icon="el-icon-plus" @click="form.input3++"></el-button>
          </el-input>
        </el-form-item>

        <el-form-item label="及格分">
          <el-input
            placeholder="请输入内容"
            style="width: 300px"
            v-model="form.input4"
            class="input-with-select"
          >
            <el-button slot="prepend" icon="el-icon-minus" @click="form.input4--"></el-button>

            <el-button slot="append" icon="el-icon-plus" @click="form.input4++"></el-button>
          </el-input>
        </el-form-item>

        <el-form-item label="考试时长(分钟)">
          <el-input
            placeholder="请输入内容"
            style="width: 300px"
            v-model="form.input5"
            class="input-with-select"
          >
            <el-button slot="prepend" icon="el-icon-minus" @click="form.input5--"></el-button>

            <el-button slot="append" icon="el-icon-plus" @click="form.input5++"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="是否限时">
          <el-checkbox v-model="checked">备选项</el-checkbox>
        </el-form-item>
        <el-form-item label="考试时间" v-if="checked">
          <div class="block">
            <!-- <span class="demonstration">默认</span> -->
            <el-date-picker
              v-model="form.value1"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </div>
        </el-form-item>
      </el-form>
    </el-card>

    <el-button
      type="primary"
      style="margin: 20px 0;padding: 10px 30px"
      @click="submit"
      >保存</el-button
    >
  </div>
</template>

<script>
export default {
  name: "examinationAdd",
  data() {
    return {
      sum: 0,
      form: {},
      input3: {},
      Question: {},
      checked: false,
      Questions: [
        {
          id: 1,
          name: "题库1",
          aa: 10,
          bb: 100,
          cc: 20,
        },
        {
          id: 2,
          name: "题库2",
          aa: 11,
          bb: 110,
          cc: 21,
        },
        {
          id: 3,
          name: "题库3",
          aa: 13,
          bb: 103,
          cc: 23,
        },
      ],

      tableData: [
        {
          date: "2016-05-02",
          a: "",
          a_: "",
          b: "",
          b_: "",
          c: "",
          c_: "",
        },
      ],
    };
  },
  methods: {
    submit() {
      console.log(this.tableData);
      console.log(this.form);
    },
    changeUpdate(e) {
      console.log(e);
      var index = this.Questions.findIndex((i) => i.id == e.type);
      // e = this.Questions[index]
      console.log(this.Questions[index]);
      var index1 = this.tableData.findIndex((i) => i.id == e.id);
      this.Questions[index].type = e.type;
      //  this.tableData[index1] = this.Questions[index]
      this.$set(this.tableData, index1, this.Questions[index]);
    },
    add() {
      let data = {
        date: "2016-05-02",
        a: "",
        a_: "",
        b: "",
        b_: "",
        c: "",
        c_: "",
      };
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

    blur() {
      let num = 0;
      console.log(this.tableData);
      for (let i in this.tableData) {
        let x =
          parseInt(this.tableData[i].a)*parseInt( this.tableData[i].a_) +
          parseInt(this.tableData[i].b)*parseInt( this.tableData[i].b_) +
          parseInt(this.tableData[i].c)*parseInt( this.tableData[i].c_);
        num += x;
      }
      console.log(num);
      this.sum = num
      this.form.input3 = num
      if(!num){
        this.sum = "未填完"
      }
    },
  },
};
</script>



<style scoped>
span {
  width: 30px;
  text-align: center;
  color: #aaa;
}
</style>