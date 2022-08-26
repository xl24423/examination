<template>
  <div>
    <el-card style="font-size: 18px">
      <span>距离考试结束还有：</span>
      <span style="color: red; font-weight: 550"
      >{{ minute }}分钟{{ seconds }}秒</span
      >
    </el-card>

    <div
        style="display: flex; justify-content: space-between; margin-top: 20px"
    >
      <el-card style="width: 27%; text-align: center">
        <div>
          <p
              style="
              background-color: #eee;
              height: 30px;
              line-height: 30px;
              font-size: 15px;
            "
          >
            答题卡
          </p>
          <div style="margin: 10px 0; text-align: left">
            <el-tag type="info">未作答</el-tag>
            <el-tag style="margin-left: 10px" type="success">已作答</el-tag>
          </div>
        </div>

        <div v-for="(item, index) in datas" :key="index">
          <p
              style="
              background-color: #eee;
              height: 30px;
              line-height: 30px;
              font-size: 15px;
            "
          >
            {{ item.type }}
          </p>
          <div style="margin: 10px 0; text-align: left">
            <el-tag
                v-for="(item1, index1) in item.size"
                :key="index1"
                :type="
                item.flag && item.indexnumber == item1 ? 'warning' : 'info'
              "
            >{{ item1 }}</el-tag
            >
          </div>
        </div>
      </el-card>
      <el-card style="width: 72%">
        <p>{{ indexnumber+1 }}.{{ test.question }}</p>
        <img width="200px" height="200px" v-if="test.image" :src="test.image" alt=""/>
        <el-radio-group v-model="Answer">
          <el-radio-button
              text-color="rgb(255, 255, 255)"
              fill="rgb(255, 255, 255)"
              style="margin:5px 1px"
              :label="test.a"
              key="a"
          ><p style="width: 900px;line-height: 30px;text-align: left;font-size: 18px;">{{ item.abc }}.{{ item.content }}</p>
            <div v-if="item.image!=''&&item.image.length!=0"><img  width="200px" height="200px" :src="item.image" alt=""></div>
          </el-radio-button
          >
          <el-radio-button
              text-color="rgb(255, 255, 255)"
              fill="rgb(255, 255, 255)"
              style="margin:5px 1px"
              :label="test.b"
              key="b"
          ><p style="width: 900px;line-height: 30px;text-align: left;font-size: 18px;">{{ item.abc }}.{{ item.content }}</p>
          </el-radio-button
          > <el-radio-button
              text-color="rgb(255, 255, 255)"
              fill="rgb(255, 255, 255)"
              style="margin:5px 1px"
              :label="test.c"
              key="c"
          ><p style="width: 900px;line-height: 30px;text-align: left;font-size: 18px;">{{ item.abc }}.{{ item.content }}</p>
          </el-radio-button
          > <el-radio-button
              text-color="rgb(255, 255, 255)"
              fill="rgb(255, 255, 255)"
              style="margin:5px 1px"
              :label="test.d"
              key="d"
          ><p style="width: 900px;line-height: 30px;text-align: left;font-size: 18px;">{{ item.abc }}.{{ item.content }}</p>
          </el-radio-button
          >
        </el-radio-group>
      </el-card>
    </div>
    <div style="margin-left: 29%;margin-top: 20px;">
      <el-button v-if="indexnumber>0" type="primary">上一题</el-button>
      <el-button @click="next" type="warning">{{ message }}</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      Answer: "",
      minute: 90,
      seconds: 0,
      indexnumber: 0,
      test: {
        a:"213",
        b:"321",
        c:"321",
        d:"321",
        id: 1,
        question:"312",
        score:"312",
        url:"321",
        userId: "312",
      },
      datas: [
        {
          type: "单选题",
          size: 5,
          flag: true,
          indexnumber: 1,
        },
        {
          type: "多选题",
          size: 5,
          flag: false,
          indexnumber: 1,
        },
        {
          type: "判断题",
          size: 5,
          flag: false,
          indexnumber: 1,
        },
      ],
      // 单选题
      datas1: [],
      // 多选
      datas2: [],
      // 判断
      datas3: [],
      //
      message: "下一题",
    };
  },
  created() {
    this.init();
    this.f1();
    console.log(this.datas1)
    console.log(this.datas2)
    console.log(this.datas3)
    console.log(this.datas1[0].valueOf())
    if (this.indexnumber === 0) {
      this.test = this.datas1[0];
    }
  },
  methods: {
    init() {
      let id = location.pathname.split("/")[2]
      this.request.get("/question/exam", {
        params: {
          id: id
        }
      }).then(res => {
        console.log(res)
        let index1 = 0;
        let index2 = 0;
        let index3 = 0;
        let question = res.data;
        for (let i = 0; i < question.length; i++) {
          if (question[i].type === "1"){
            this.datas1[index1] = question[i];
            index1++;
          }else if(question[i].type === "2"){
            this.datas2[index2] = question[i];
            index2++;
          }else if (question[i].type === "3"){
            this.datas3[index3] = question[i];
            index3++;
          }
        }
        this.datas[0].size = this.datas1.length
        this.datas[1].size = this.datas2.length
        this.datas[2].size = this.datas3.length
      })
    },
    success() {
      console.log("提交");
    },
    next() {
      if (this.message == "提交答案") {
        return this.success();
      }

    },
    f1() {
      var time;
      if (sessionStorage.getItem("countDown")) {
        time = sessionStorage.getItem("countDown");
        var time1 = new Date().getTime();
        this.minute = 90 - Math.floor((time1 - time) / 1000 / 60); //分
        this.seconds = 60 - Math.floor(((time1 - time) / 1000) % 60); //秒
      } else {
        time = new Date().getTime();
        sessionStorage.setItem("countDown", time);
      }
      var timer = setInterval(() => {
        // console.log(this.minute + " " + this.seconds);
        if (this.seconds == "00" && this.minute > 0) {
          this.seconds = 59;
          this.minute--;
        } else {
          this.seconds--;
        }
        if (this.seconds >= 0 && this.seconds < 10) {
          this.seconds = "0" + Number(this.seconds);
        }
        if (this.minute >= 0 && this.minute < 10) {
          this.minute = "0" + Number(this.minute);
        }
        if (this.minute == 0 && this.seconds == 0) {
          clearInterval(timer);
        }
      }, 1000);
    },
  },
  destroyed() {
    sessionStorage.clear();
  },
};
</script>